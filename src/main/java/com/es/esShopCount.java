package com.es;

import com.es.util.DateUtil;
import com.es.util.ESClient;
import com.es.util.Global;
import com.es.util.MapDouble;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
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
import java.util.*;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
public class esShopCount {


    public static void insertMoney(long start,long end){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Client client = ESClient.getTransportClient();
        QueryBuilder query2 = QueryBuilders.queryStringQuery("2.0").defaultField("action");
        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("time").gt(start).lt(end))
                .must(QueryBuilders.queryStringQuery("2.0").defaultField("action"));
        //显示查询结果数
        SearchRequestBuilder result1 = client.prepareSearch(Global.getConfig("index")).setTypes("record").setQuery(query);
        long a;
        SearchResponse json1 = result1.execute().actionGet();
        a = json1.getHits().getTotalHits();
        System.out.println("总条数："+a);
        Map<String,String> enterids=new HashMap<String,String>();
        MapDouble moneys=new MapDouble();
        MapDouble sty1=new MapDouble();
        MapDouble sty2=new MapDouble();
        MapDouble sty3=new MapDouble();
        MapDouble sty4=new MapDouble();
        MapDouble sty5=new MapDouble();
        MapDouble sty6=new MapDouble();
//        遍历查询结果
        try {
            SearchRequestBuilder result = client.prepareSearch(Global.getConfig("index")).setTypes("record").setQuery(query).setFrom(0).setSize((int)a);
            SearchResponse searchResponse = result.execute().actionGet();
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHists = hits.getHits();
            for (SearchHit hit : searchHists) {
                String action = String.valueOf(hit.getSource().get("action"));
                String enterid = String.valueOf(hit.getSource().get("enterid"));
                String name = String.valueOf(hit.getSource().get("name"));
                String paystyle = String.valueOf(hit.getSource().get("paystyle"));
                Double money = Double.parseDouble(hit.getSource().get("money").toString());
                enterids.put(enterid,name);
                moneys.put(enterid,money);

                switch (paystyle) {
                    case "1":
                        sty1.put(enterid,money);
                        break;
                    case "2":
                        sty2.put(enterid,money);
                        break;
                    case "3":
                        sty3.put(enterid,money);
                        break;
                    case "4":
                        sty4.put(enterid,money);
                        break;
                    case "5":
                        sty5.put(enterid,money);
                        break;
                    case "6":
                        sty6.put(enterid,money);
                        break;
                    default:
                        System.out.println(paystyle);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


//        for (Map.Entry<String, String> entry : enterids.entrySet()) {
//            String key=entry.getKey();
//            System.out.println("yesterday="+yesterday+" name=" + entry.getValue() + " enterid=" +
//                    entry.getKey() + " money=" + moneys.get(key) + " paystyle1=" + sty1.get(key) +
//                    " paystyle2=" + sty2.get(key)+" paystyle3=" + sty3.get(key)+" paystyle4=" +
//                    sty4.get(key)+ " paystyle5=" + sty5.get(key)+" paystyle6=" + sty6.get(key));
//
//        }
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
//                String name = rs.getString("utctime");
//                System.out.println(name);
                channel.put(rs.getString("luckycatno"),rs.getString("code"));
            }
            rs.close();// 关闭数据库


            connection.setAutoCommit(false); //设置是否自动提交
            String insert_sql = "insert into shop_date_money(date,name,shopno,money,channel,sty1,sty2,sty3,sty4,sty5,sty6) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";

            prepareStatement = connection.prepareStatement(insert_sql);

            int count = 0;
//            for (Map.Entry<String, String> entry : enterids.entrySet()) {
            for (Map.Entry<String, String> entry : channel.entrySet()) {
                count++;
                String key=entry.getKey();
                if(moneys.get(key)==0.0)
                    continue;
                prepareStatement.setString(1,df.format(new java.util.Date(start)));
                prepareStatement.setString(2, enterids.get(key));
                prepareStatement.setString(3, entry.getKey());
                prepareStatement.setString(4, moneys.get(key)+"");
                prepareStatement.setString(5, channel.get(key));
                prepareStatement.setString(6, sty1.get(key)+"");
                prepareStatement.setString(7, sty2.get(key)+"");
                prepareStatement.setString(8, sty3.get(key)+"");
                prepareStatement.setString(9, sty4.get(key)+"");
                prepareStatement.setString(10, sty5.get(key)+"");
                prepareStatement.setString(11,sty6.get(key)+"");

                prepareStatement.addBatch();  // 加入批量处理
                if(count%20==0)
                {
                    prepareStatement.executeBatch(); // 执行批量处理
                    connection.commit();  // 提交
                    System.out.println("20提交"+count);
                }

            }

            prepareStatement.executeBatch(); // 执行批量处理
            connection.commit();  // 提交
            System.out.println("总提交条目"+count);
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
        long time = date.getTime() + i * 3600 * 24 * 1000L;  //L 不加L会正型越界
//        return df.format(new java.util.Date(time));
        return time;
    }

    public static void main(String args[]) {


//        String yesterday=Global.getConfig("yesterday");
        String yesterday=DateUtil.getDayOfYesterday();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

//        for(int i=0;i<177;i++)
//        {
//            long  start = getDay(yesterday,i);
//            long  end = getDay(yesterday,i+1);
            long  start = getDay(yesterday,0);
            long  end = getDay(yesterday,1);
            insertMoney(start,end);
//        System.out.println(start+"    "+end);

//        }


    }


}
