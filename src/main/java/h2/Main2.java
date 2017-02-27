package h2;

import com.alibaba.fastjson.JSONObject;
import com.es.util.ESClient;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import h2.database.DBConn;
import h2.entities.Content;
import h2.entities.WenShuData;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandengfeng on 2017/2/21.
 */
public class Main2 {

    public static List<String> showResult(String index,String type,String filed,int start,int size)
    {
       List<String> list=new ArrayList<String>();
        TransportClient client = ESClient.getTransportClient();
        SearchRequestBuilder result1 = client.prepareSearch(index).setTypes(type).setQuery(QueryBuilders.queryStringQuery("*").defaultField(filed).defaultOperator(QueryStringQueryBuilder.Operator.AND)).addSort("publish_time", SortOrder.ASC).setFrom(start).setSize(size);
        try {
            SearchResponse searchResponse = result1.execute().actionGet();
            SearchHits hits = searchResponse.getHits();
            System.out.println(hits.totalHits());
            SearchHit[] searchHists = hits.getHits();
            for(SearchHit hit:searchHists){
                String id = hit.getId();
                String content =String.valueOf(hit.getSource().get("content"));
                String publish_time =String.valueOf(hit.getSource().get("publish_time"));

                list.add(id+"\t"+content+"\t"+publish_time);
//              System.out.println( id + "||" + incoming + "||" + outgoing+"||"+timestamp);
            }
        } catch (ElasticsearchException e) {
            System.out.println(e.toString());
        }
        return list;

    }


    public static void main(String[] args) throws Exception {
/**
 * 将h2中所有的WENSHUID写入文件中
 *
 *
        Class.forName("org.h2.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://192.168.0.251/home/kt/databaseh2/wenshu3", "sa", "sa");
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://192.168.0.251/~/data/wenshu", "sa", "sa");
        // add application code here
        Statement stmt = conn.createStatement();

//        stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));");
//        stmt.executeUpdate("INSERT INTO TEST_MEM VALUES(1, 'Hello_Mem');");

        ResultSet rs = stmt.executeQuery("SELECT * FROM WENSHUDATA");
//        System.out.println(rs.l);
        File file = new File("/opt/WENSHUID.txt");
        List<String> list = new ArrayList<String>();
        while(rs.next()) {
//            System.out.println(rs.getString("WENSHUID"));
            list.add(rs.getString("WENSHUID"));

        }

        FileUtils.writeLines(file,list,true);

        conn.close();
**/
        /***
         * 获取es中的所有数据将_id和content写入文件中
         *
//        List<String> list = showResult("case","verdict","title",1258340);
        for(int i=0;i<10;i++){
            List<String> list = showResult("case","verdict","title",40000*i,40000);
            System.out.println(list.size());
//        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i));
//        }
            File file = new File("/opt/40wan.txt");
            FileUtils.writeLines(file,list,true);
        }
         *
         *
         */

  /**
   *
            ConnectionSource connectionSource = DBConn.getConnection();
            TableUtils.createTableIfNotExists(connectionSource, Content.class);
            Dao<Content,String> contentDao = DaoManager.createDao(connectionSource,Content.class);

            File idfile = new File("/opt/WENSHUID/WENSHUID.txt");
            File contentfile = new File("/opt/content/content.txt");
//        File idfile = new File("G:\\坤腾\\网络爬虫\\爬虫ECEL\\10000.txt");
//        File contentfile = new File("G:\\坤腾\\网络爬虫\\爬虫ECEL\\100.txt");
        File mapEx = new File("/opt/content/map.txt");
        File ido = new File("/opt/WENSHUID/ido.txt");
        List<String> emap=new ArrayList<String>();
        List<String> ide=new ArrayList<String>();

        List<String> ids = FileUtils.readLines(idfile);
        System.out.println(ids.size());
            List<String> content = FileUtils.readLines(contentfile);
        Map<String,String> map=new HashedMap();
        for(int i=0;i<content.size();i++){

            String[] str=content.get(i).split("\t");
            try{

                map.put(str[0],str[1]);
            }catch (Exception e){
                System.out.println(content.get(i));
                emap.add(content.get(i));
            }

        }
        System.out.println(map.size());
       int count =0;
        Content content1 = new Content();
        for (int i=0;i<ids.size();i++){
            if(map.get(ids.get(i))!=null)
            {
             content1.setWENSHUID(ids.get(i));
             content1.setContent(map.get(ids.get(i)));
                try{
                    contentDao.create(content1);
                    System.out.println(count++);
                }catch (Exception e){
                    System.out.println(ids.get(i) +"\t"+map.get(ids.get(i)));
                    emap.add(ids.get(i) +"\t"+map.get(ids.get(i)));
                }

            }else{
                System.out.println(ids.get(i));
                ide.add(ids.get(i));
            }
        }

        FileUtils.writeLines(mapEx,emap,true);
        FileUtils.writeLines(ido,ide,true);

       **/

        ConnectionSource connectionSource = DBConn.getConnection();
        TableUtils.createTableIfNotExists(connectionSource, Content.class);
        Dao<Content,String> contentDao = DaoManager.createDao(connectionSource,Content.class);
        File idfile = new File("/opt/40wan.txt");

        List<String> ids = FileUtils.readLines(idfile);
        int count=0;
        for (int i=0;i<ids.size();i++){
            String[] str=ids.get(i).split("\t");
            try{
            Content content1 = new Content();
                content1.setWENSHUID(str[0]);
                content1.setContent(str[1]);
                content1.setPublish_time(str[2]);
                    contentDao.create(content1);
                    System.out.println(count++);
                }catch (Exception e){
                    System.out.println(ids.get(i));

                }

        }



    }





}



