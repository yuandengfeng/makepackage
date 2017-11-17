package com.es;

import com.es.util.ESClient;
import com.es.util.Global;
import com.es.util.MapCount;
import com.es.util.MapDouble;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
public class ShopFlowTaskCount {

    public static void main(String[] args) throws Throwable {
        excute(args[0]);
    }

    public static void excute(String date){
//        String yesterday= DateUtil.getDayOfYesterday();
//        String yesterday= "2017-11-15";
        String yesterday= date;
        System.out.println(yesterday);
        long  start = getDay(yesterday,0);
        long  end = getDay(yesterday,1);
        insertMoney(start,end);
    }

    public static void insertMoney(long start,long end){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Client client = ESClient.getTransportClient();
        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("time").gt(start).lt(end))
                .must(QueryBuilders.queryStringQuery("2.0").defaultField("action"));
        //显示查询结果数
        SearchRequestBuilder result1 = client.prepareSearch(Global.getConfig("index")).setTypes("record").setQuery(query);
        long a;
        SearchResponse json1 = result1.execute().actionGet();
        a = json1.getHits().getTotalHits();
        System.out.println("总条数："+a);
//        Map<String,String> enterids=new HashMap<String,String>();
        MapCount moneys=new MapCount();
        MapCount sty1=new MapCount();
        MapCount sty2=new MapCount();
        MapCount sty3=new MapCount();
        MapCount sty4=new MapCount();
        MapCount sty5=new MapCount();
        MapCount sty6=new MapCount();
//        遍历查询结果
        try {
            SearchRequestBuilder result = client.prepareSearch(Global.getConfig("index")).setTypes("record").setQuery(query).setFrom(0).setSize((int)a);
            SearchResponse searchResponse = result.execute().actionGet();
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHists = hits.getHits();
            for (SearchHit hit : searchHists) {
                String enterid = String.valueOf(hit.getSource().get("enterid"));
                String name = String.valueOf(hit.getSource().get("name"));
                String paystyle = String.valueOf(hit.getSource().get("paystyle"));
                Double money = Double.parseDouble(hit.getSource().get("money").toString());
                moneys.put(enterid);

                switch (paystyle) {
                    case "1":
                        sty1.put(enterid);
                        break;
                    case "2":
                        sty2.put(enterid);
                        break;
                    case "3":
                        sty3.put(enterid);
                        break;
                    case "4":
                        sty4.put(enterid);
                        break;
                    case "5":
                        sty5.put(enterid);
                        break;
                    case "6":
                        sty6.put(enterid);
                        break;
                    default:
                        System.out.println("paystyle"+paystyle);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //数据库操作
        Connection connection=null;
        PreparedStatement prepareStatement=null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://192.168.20.83/yunac?useUnicode=true&characterEncoding=UTF-8", "root", "123456");
            connection = DriverManager.getConnection(Global.getConfig("jdbc.url"), Global.getConfig("jdbc.username"), Global.getConfig("jdbc.password"));

            // 执行SQL语句
            Statement stmt = connection.createStatement();// 创建语句对象，用以执行sql语言
            ResultSet rs = stmt.executeQuery("SELECT luckycatno,code from yunpos_shop INNER JOIN channel ON channel.id=yunpos_shop.id and luckycatno != \"\"");
            // 处理结果集
            Map<String,String> channel=new HashMap<String,String>();
            while (rs.next()) {
                channel.put(rs.getString("luckycatno"),rs.getString("code"));
            }
            rs.close();// 关闭数据库
            connection.setAutoCommit(false); //设置是否自动提交
//            String insert_sql = "insert into shop_date_money(date,name,shopno,money,channel,sty1,sty2,sty3,sty4,sty5,sty6) "
//                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
            String update_sql="update shop_date_money set count1=?,count2=?,count3=?,count4=?,count5=?,count6=?" +
                    " where channel=? and `date`=?";

            prepareStatement = connection.prepareStatement(update_sql);

            int count = 0;
//            for (Map.Entry<String, String> entry : enterids.entrySet()) {
            for (Map.Entry<String, String> entry : channel.entrySet()) {
                String key=entry.getKey();
                if(moneys.get(key)==0)
                    continue;
                prepareStatement.setString(1, sty1.get(key)+"");
                prepareStatement.setString(2, sty2.get(key)+"");
                prepareStatement.setString(3, sty3.get(key)+"");
                prepareStatement.setString(4, sty4.get(key)+"");
                prepareStatement.setString(5, sty5.get(key)+"");
                prepareStatement.setString(6, sty6.get(key)+"");
                prepareStatement.setString(7, channel.get(key));
                prepareStatement.setString(8, df.format(new java.util.Date(start)));

                prepareStatement.addBatch();  // 加入批量处理
                count++;
                if(count%20==0)
                {
                    prepareStatement.executeBatch(); // 执行批量处理
                    connection.commit();  // 提交
                    System.out.println("20提交:"+count);
                }

            }

            prepareStatement.executeBatch(); // 执行批量处理
            connection.commit();  // 提交
            System.out.println("总提交条目 "+count);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                prepareStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    //获取距离dateTime多少天的时间，前+ ,后-
    public static long getDay(String dateTime, int i) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime() + i * 3600 * 24 * 1000L;  //L 不加L会整型越界
//        return df.format(new java.util.Date(time));
        return time;
    }

}
