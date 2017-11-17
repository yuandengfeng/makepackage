package com.es;

import com.es.util.ESClient;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by Administrator on 2017/11/16 0016.
 */
public class DelShopFlow {
    public static void main(String args[]) {

        Client client = ESClient.getTransportClient();

        //查询条件按时间段
        QueryBuilder query = QueryBuilders.rangeQuery("time").gt(1510588800000L).lt(1510675199000L);
//        QueryBuilder query = QueryBuilders.rangeQuery("@timestamp").gt("2015-01-05T08:34:19.355Z").lt("2016-12-01T02:34:19.355Z");

        //显示查询结果  args[0]  类型
        SearchRequestBuilder result1 = client.prepareSearch("logs").setTypes("record").setQuery(query);
        long a;
        SearchResponse json1 = result1.execute().actionGet();
        a = json1.getHits().getTotalHits();
        System.out.println(a);

        //执行删除
        client.prepareDeleteByQuery("logs").setTypes("record").setQuery(query).execute().actionGet();

//       DeleteByQueryResponse response = client.prepareDeleteByQuery("library").setQuery(QueryBuilders.termQuery("title", "ElasticSearch")).execute().actionGet();


    }

}
