package com.es;

import com.alibaba.fastjson.JSONObject;
import com.es.util.ESClient;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class ReEsService {


    //获取查询结果总条数
    public int searchTotalNum(String index,String type,String filed) {

        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter").setQuery(QueryBuilders.prefixQuery("channelpath", "*"));
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter").setQuery(QueryBuilders.queryStringQuery("*").defaultField(filed).defaultOperator(QueryStringQueryBuilder.Operator.AND));
//        SearchRequestBuilder result = client.prepareSearch("wifiauth").setTypes("counter").setQuery(QueryBuilders.queryStringQuery("*"));
        SearchRequestBuilder result1 = client.prepareSearch(index).setTypes(type).setQuery(QueryBuilders.queryStringQuery("*").defaultField(filed).defaultOperator(QueryStringQueryBuilder.Operator.AND));
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter").setQuery(QueryBuilders.queryStringQuery("*").defaultField("channelpath").defaultOperator(QueryStringQueryBuilder.Operator.AND));
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter").setQuery(QueryBuilders.queryStringQuery("*"));
//        System.out.println(result1.toString());
        long a = 0;
        try {
            SearchResponse json1 = result1.execute().actionGet();
            a = json1.getHits().getTotalHits();
        } catch (ElasticsearchException e) {
            LoggerFactory.getLogger(ReEsService.class).warn("{}", e);
        }
        System.out.println("searchTotalNum========"+a);
        return (int) a;
    }

    //显示查询结果,并封装成List<JSONObject>
    List<JSONObject> list=null;
  public List<JSONObject> showResult(String index,String type,String filed,int size)
  {
      list=new ArrayList<JSONObject>();
      TransportClient client = ESClient.getTransportClient();
//      SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes(type).setQuery(QueryBuilders.queryStringQuery("*").defaultField(filed).defaultOperator(QueryStringQueryBuilder.Operator.AND)).setFrom(0).setSize(size);
      SearchRequestBuilder result1 = client.prepareSearch(index).setTypes(type).setQuery(QueryBuilders.queryStringQuery("*").defaultField(filed).defaultOperator(QueryStringQueryBuilder.Operator.AND)).setFrom(0).setSize(size);
      try {
          SearchResponse searchResponse = result1.execute().actionGet();
//          a = json1.getHits().getTotalHits();
          SearchHits hits = searchResponse.getHits();
//        System.out.println("查询到记录数=" + hits.totalHits());
          SearchHit[] searchHists = hits.getHits();
//        System.out.println(searchHists.length);
          for(SearchHit hit:searchHists){
//             String type =hit.getType();
              String id = hit.getId();
              String incoming =String.valueOf(hit.getSource().get("incoming"));
              String outgoing =String.valueOf(hit.getSource().get("outgoing"));
              String timestamp =String.valueOf(hit.getSource().get("@timestamp"));
             JSONObject js = new JSONObject();
//              js.put("type",type);
              js.put("id",id);
              js.put("incoming",incoming);
              js.put("outgoing",outgoing);
              js.put("@timestamp",timestamp);
             list.add(js);
//              System.out.println( id + "||" + incoming + "||" + outgoing+"||"+timestamp);
          }
      } catch (ElasticsearchException e) {
          LoggerFactory.getLogger(ReEsService.class).warn("{}", e);
      }
      return list;

  }

    //创建索引
    public void createIndex(String newindex,String type,List<JSONObject> list)
    {

        TransportClient client = ESClient.getTransportClient();

            for(JSONObject js:list)
            {
                try
                {
                    //指定记录的_id
//                    client.prepareIndex("test", js.getString("type"),js.getString("id")).setSource(js.toString()).get();
                    client.prepareIndex(newindex, type,js.getString("id")).setSource(js.toString()).get();
//                    System.out.println("insert"+"newindex="+newindex+"type="+type+"id="+js.getString("id"));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }




    }





    public static void main(String[] args)
    {
        ReEsService es = new ReEsService();
       int a=  es.searchTotalNum("wifiauth","counter","@timestamp");
       List<JSONObject>list= es.showResult("wifiauth","counter","@timestamp", a);
        es.createIndex("tt","counter",list);
    }











}
