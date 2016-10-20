package com.es;

import com.es.util.ESClient;
import org.elasticsearch.action.deletebyquery.DeleteByQueryAction;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by yuandengfeng on 2016/10/19.
 */
public class Main1Del {

    public static void main(String args[]) {

        Client client = ESClient.getTransportClient();

        //查询条件按时间段
        QueryBuilder query = QueryBuilders.rangeQuery("@timestamp").gt("2016-06-05T08:34:19.355Z").lt("2016-06-06T02:34:19.355Z");

        //显示查询结果
        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("roam").setQuery(query);
        long a;
        SearchResponse json1 = result1.execute().actionGet();
        a = json1.getHits().getTotalHits();
        System.out.println(a);

        //执行删除
        client.prepareDeleteByQuery("wifiauth").setTypes("roam").setQuery(query).execute().actionGet();

//       DeleteByQueryResponse response = client.prepareDeleteByQuery("library").setQuery(QueryBuilders.termQuery("title", "ElasticSearch")).execute().actionGet();


    }

}