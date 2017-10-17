package com.es;

import com.es.util.ESClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
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

import javax.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/4 0004.
 */
public class snFirstTime {

    public static List<String> getSns(String file) {
        File f=new File(file);
        List<String> sns=null;
        try {
             sns=FileUtils.readLines(f,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sns;
    }


    public static void main(String[] args){
        int count=0;
        List<String> sns=getSns("/tmp/sn.txt");
        File writeFile=new File("/tmp/sntime.csv");

        Client client = ESClient.getTransportClient();
        List<String>snsFile=new ArrayList<String>();
        for(String sn:sns){
            System.out.println(sn);
//            QueryBuilder query= QueryBuilders.prefixQuery("sn", sn);
            QueryBuilder query= QueryBuilders.queryString(sn).defaultField("sn");
            SearchRequestBuilder result1 = client.prepareSearch("jyt").setTypes("posinfo").setQuery(query)
                    .addSort("@timestamp", SortOrder.ASC).setFrom(0).setSize(1);  //按时间升序显示,获取最早的一条
            SearchResponse searchResponse = result1.execute().actionGet();
            try {
                SearchHits hits = searchResponse.getHits();
                SearchHit[] searchHists = hits.getHits();
                for(SearchHit hit:searchHists){
                    JSONObject js=JSONObject.fromObject(hit.getSourceAsString());
                    System.out.println(js.toString());
                    count++;
                    snsFile.add(js.getString("sn")+","+js.getString("time"));
                }
                System.out.println(count);
            } catch (ElasticsearchException e) {
                LoggerFactory.getLogger(ReEsService.class).warn("{}", e);
            }
        }
        try {
            FileUtils.writeLines(writeFile,"utf-8",snsFile,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
