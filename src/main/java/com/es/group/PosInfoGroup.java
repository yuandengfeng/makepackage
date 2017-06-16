package com.es.group;

import com.es.util.ESClient;
import com.es.util.Global;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class PosInfoGroup {

    //获取距离dateTime多少天的时间，前+ ,后-
    public static long getDay(String dateTime, int i) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime()/1000 + i * 3600 * 24L;  //L 不加L会正型越界
        return time;
    }

    public static String stampTodate(Long s) throws ParseException {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(s * 1000);
        return d;
    }

    public static void getSnCount(long start,long end) throws ParseException {
        Client client = ESClient.getTransportClient();
        TermsFacetBuilder facetBuilder = FacetBuilders.termsFacet("typeFacetName");
        facetBuilder.field("sn").size(Integer.MAX_VALUE);  //根据sn进行聚合
        facetBuilder.facetFilter(FilterBuilders.matchAllFilter());
//        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("time").gt(1496592024).lt(1496678378));
        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("time").gt(start).lt(end));
        SearchResponse response = client.prepareSearch("jyt")
                .setTypes("posinfo").addFacet(facetBuilder).setPostFilter(FilterBuilders.matchAllFilter())
                .setQuery(query).execute().actionGet();
        Facets f = response.getFacets();
        //跟上面的名称一样
        TermsFacet facet = (TermsFacet)f.getFacets().get("typeFacetName");
        String date=stampTodate(start);
        System.out.println(date+"   ----------------------------------------------");
        //数据库操作
        Connection connection=null;
        PreparedStatement prepareStatement=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(Global.getConfig("jdbc.url"), Global.getConfig("jdbc.username"), Global.getConfig("jdbc.password"));
            connection.setAutoCommit(false); //设置是否自动提交
            String insert_sql = "insert into shop_date_posinfo(date,sn,count) values (?,?,?)";
            prepareStatement = connection.prepareStatement(insert_sql);
            int count = 0;
            for(TermsFacet.Entry tf :facet.getEntries()){
//                System.out.println(tf.getTerm()+"\t:\t" + tf.getCount());
                prepareStatement.setString(1,date);
                prepareStatement.setString(2, tf.getTerm().toString());
                prepareStatement.setString(3,tf.getCount()+"");
                prepareStatement.addBatch();
                count++;
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

//        client.close();
    }


    public static void  main(String[] argv) throws ParseException {

//        String yesterday= DateUtil.getDayOfYesterday();
        String yesterday="2017-06-01";
        for(int i=0;i<11;i++)
        {
            long  start = getDay(yesterday,i);
            long  end = getDay(yesterday,i+1);
//        long  start = getDay(yesterday,0);
//        long  end = getDay(yesterday,1);
            getSnCount(start,end);


//            System.out.println(start+"    "+end);
        }
    }



}



