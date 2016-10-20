package com.es;

import com.es.util.ESClient;
import com.es.util.Global;
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/8/12.
 */

//该类是用于查询wifiauth索引login表中的访客mac,除重
public class GetMac implements Runnable {

    private String index;   //索引
    private  String type;   //类型
    private  String filed;  //查询字段
    private  String suffixes; //文件名后缀
    private int from;    //开始数目
    private int num;   //一次查询数目
    ReEsService es = new ReEsService();
    public GetMac(){}
    public GetMac(String index,String type,String filed,int from,int num,String suffixes )
    {
        this.index=index;
        this.type=type;
        this.filed=filed;
        this.from=from;
        this.num=num;
        this.suffixes=suffixes;

    }


    //显示查询结果,返回Set


    public Set<String> showResult(String index,String type,String filed,int from ,int size)
    {
        Set<String> srcMac =new HashSet<String>();

        TransportClient client = ESClient.getTransportClient();
//      SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes(type).setQuery(QueryBuilders.queryStringQuery("*").defaultField(filed).defaultOperator(QueryStringQueryBuilder.Operator.AND)).setFrom(0).setSize(size);
        SearchRequestBuilder result1 = client.prepareSearch(index).setTypes(type).setQuery(QueryBuilders.queryStringQuery("*").defaultField(filed).defaultOperator(QueryStringQueryBuilder.Operator.AND)).setFrom(from).setSize(size);//size为每次查询固定大小
//        addSort("@timestamp", SortOrder.DESC);  //根据时间排序
        try {
            SearchResponse searchResponse = result1.execute().actionGet();
//          a = json1.getHits().getTotalHits();
            SearchHits hits = searchResponse.getHits();
//        System.out.println("查询到记录数=" + hits.totalHits());
            SearchHit[] searchHists = hits.getHits();
//        System.out.println(searchHists.length);
            for(SearchHit hit:searchHists){
//             String type =hit.getType();
//                String id = hit.getId();
//                System.out.println(hit.getSource().toString());
                String mac =String.valueOf(hit.getSource().get("mac"));

//                String outgoing =String.valueOf(hit.getSource().get("outgoing"));
//                String timestamp =String.valueOf(hit.getSource().get("@timestamp"));
//                JSONObject js = new JSONObject();
//              js.put("type",type);
//                js.put("id",id);
//                js.put("incoming",incoming);
//                js.put("outgoing",outgoing);
//                js.put("@timestamp",timestamp);
                srcMac.add(mac);
//              System.out.println( id + "||" + incoming + "||" + outgoing+"||"+timestamp);
            }
        } catch (ElasticsearchException e) {
            LoggerFactory.getLogger(ReEsService.class).warn("{}", e);
        }
        return srcMac;

    }

     FileWriter fw = null;
     BufferedWriter bufw =null;

    @Override
    public void run() {

            String filename = Global.getConfig("filename") + suffixes;

            try {
//            fw = new FileWriter(new String(filename.getBytes("ISO8859-1"),"utf-8"),true);
                fw = new FileWriter(filename, true);
                bufw = new BufferedWriter(fw);

//            int totalNum= es.searchTotalNum(index,type,filed);  //总条目
//            Set<String> macs = GetMac.showResult(index, type, filed, totalNum);
//            showResult(String index,String type,String filed,int from ,int size)


                Set<String> macs = this.showResult(index, type, filed, from, num);

                System.out.println("from"+from+"=====to==="+num+"====macs.size===" + macs.size());

                for (String mac : macs)

                {

                    bufw.write(mac);
                    bufw.newLine();
                    bufw.flush();
//                System.out.println(mac);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufw.close();
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


    }


     public static void main(String args[])
     {

//             GetMac srcMac = new GetMac ("wifiauth", "login", "@timestamp");
//          GetMac(String index,String type,String filed,int from,int num,String suffixes )
//         ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3); //指定数目线程池
         ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();  //单例线程池
         ReEsService es = new ReEsService();
         int totalNum= es.searchTotalNum("wifiauth", "login", "mac");  //总条目
         int i=1;
//         int j=100000;
         int j=10000;  //每次查询条目数
         GetMac macc=  new GetMac();
//         int x=0;
//           while(true){
//                 macc.write("wifiauth", "login", "mac",x*5,(x+1)*5,"single");
//
//                 if((x+1)*5>totalNum){
//                     break;
//                 }
//               x++;
//             }
         for(int m=1;m <totalNum;m=j*i,i++)
         {
//             String suffixes=String.valueOf(m);

            if(i==1){
//                fixedThreadPool.execute(new GetMac("wifiauth", "login", "mac",i,j*i,j+""));
                singleThreadExecutor.execute(new GetMac("wifiauth", "login", "mac",i,j,"single"));

            }else{
//                fixedThreadPool.execute(new GetMac("wifiauth", "login", "mac",j*(i-1),j*i,suffixes));
                 singleThreadExecutor.execute(new GetMac("wifiauth", "login", "mac",j*(i-1),j,"single"));

            }


         }


     }
}
