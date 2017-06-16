package com.es.group.test;

import com.es.util.ESClient;
import net.sf.json.JSONObject;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class BulkIndexTest {
    public static void main(String[] args) {
        Client client = ESClient.getTransportClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(int i=0;i<10;i++){
//            String json = ESUtils.toJson(new FacetTestModel());
            String json = JSONObject.fromObject(new FacetTestModel()).toString();
                    IndexRequestBuilder indexRequest = client.prepareIndex("test", "test")
                    //指定不重复的ID
                    .setSource(json).setId(String.valueOf(i));
            //添加到builder中
            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            System.out.println(bulkResponse.buildFailureMessage());
        }
    }
}
