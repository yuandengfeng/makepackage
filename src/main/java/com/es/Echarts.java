package com.es;

import com.es.util.ESClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.LoggerFactory;

/**
 * Created by yuandengfeng on 2017/2/9.
 */
public class Echarts {

    public static void main(String args[]) {


        //查询条件按时间段
        String startTime=UtcTime.getDateString("2017-02-09 00:00:00");
        String endTime=UtcTime.getDateString("2017-02-09 23:59:59");
        String mac="D4EE0732F834";
        JSONArray array = getRatelog(startTime,endTime,mac);
        for(Object obj:array){
            JSONObject js=JSONObject.fromObject(obj);
            System.out.println(js.getString("@timestamp")+"===="+js.getString("max_out"));
        }

    }


    public static JSONArray getRatelog(String startTime,String endTime,String mac){
        Client client = ESClient.getTransportClient();

        //查询条件按时间段
        QueryBuilder time = QueryBuilders.rangeQuery("@timestamp").gt(startTime).lt(endTime);
//        QueryBuilder dev =QueryBuilders.queryString("D4EE0732F834").defaultField("dev");
        QueryBuilder dev= QueryBuilders.prefixQuery("dev", mac.toLowerCase());


        QueryBuilder query = QueryBuilders.boolQuery().must(dev).must(time);

        //显示查询结果
        SearchRequestBuilder result1 = client.prepareSearch("ratelog").setTypes("ratelog").setQuery(query)
                .addSort("@timestamp",SortOrder.ASC).setFrom(0).setSize(200000);  //按时间升序显示
        long total;
        SearchResponse searchResponse = result1.execute().actionGet();
        total = searchResponse.getHits().getTotalHits();


        JSONArray array = new JSONArray();
        try {
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHists = hits.getHits();
            for(SearchHit hit:searchHists){

                System.out.println(hit.getSourceAsString());
                array.add(JSONObject.fromObject(hit.getSourceAsString()));
//                String max_out =String.valueOf(hit.getSource().get("max_out"));
//                String timestamp =String.valueOf(hit.getSource().get("@timestamp"));
//                System.out.println(timestamp+"===="+max_out);
            }
        } catch (ElasticsearchException e) {
            LoggerFactory.getLogger(ReEsService.class).warn("{}", e);
        }
        System.out.println(total);
        return array;

    }


}
