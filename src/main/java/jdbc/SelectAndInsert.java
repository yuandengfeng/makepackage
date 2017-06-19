package jdbc;

import com.es.util.DateUtil;
import com.es.util.ESClient;
import com.es.util.Global;
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
 * Created by Administrator on 2017/4/27 0027.
 */
public class SelectAndInsert {

    /**
     * 时间戳转换成String
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static String stampTodate(Long s) {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(s * 1000);
        return d;
    }

    public static void selectandinsert(){
        //数据库操作
        Connection connection=null;
        PreparedStatement prepareStatement=null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://192.168.20.83/yunac?useUnicode=true&characterEncoding=UTF-8", "root", "123456");
            connection = DriverManager.getConnection(Global.getConfig("jdbc.url"), Global.getConfig("jdbc.username"), Global.getConfig("jdbc.password"));

            // 执行SQL语句
            Statement stmt = connection.createStatement();// 创建语句对象，用以执行sql语言
            ResultSet rs = stmt.executeQuery("select DISTINCT sn,time from yunpos_pos where time >0 and sn not in (SELECT DISTINCT sn from shop_date_posinfo)");
            // 处理结果集
            Map<String,String> channel=new HashMap<String,String>();
            while (rs.next()) {
//                stampTodate(Long.parseLong(rs.getString("time")));

                System.out.println(rs.getString("sn")+"         "+stampTodate(Long.parseLong(rs.getString("time"))));
                channel.put(rs.getString("sn"),stampTodate(Long.parseLong(rs.getString("time"))));
            }
            rs.close();// 关闭数据库


            connection.setAutoCommit(false); //设置是否自动提交
            String insert_sql = "insert into shop_date_posinfo (date,sn,count) values (?,?,?) ";

            prepareStatement = connection.prepareStatement(insert_sql);

            int count = 0;
//            for (Map.Entry<String, String> entry : enterids.entrySet()) {
            for (Map.Entry<String, String> entry : channel.entrySet()) {
                count++;
                String sn=entry.getKey();
                String time=entry.getValue();
                int counts=1;



                prepareStatement.setString(1,time);
                prepareStatement.setString(2,sn);
                prepareStatement.setInt(3,counts);

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


    public static void main(String args[]) {

//        long time=1495691200;
//        System.out.println(stampTodate(time));
        selectandinsert();
    }


}
