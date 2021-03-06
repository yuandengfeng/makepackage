package com.es.group.test;

import com.es.util.ESClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class GroupTest {

    public static void  main(String[] argv){
        Client client = ESClient.getTransportClient();
        TermsFacetBuilder facetBuilder = FacetBuilders.termsFacet("typeFacetName");
        facetBuilder.field("type").size(Integer.MAX_VALUE);
        facetBuilder.facetFilter(FilterBuilders.matchAllFilter());
        SearchResponse response = client.prepareSearch("test")
                .setTypes("test").addFacet(facetBuilder)
                .setPostFilter(FilterBuilders.matchAllFilter())
                .execute()
                .actionGet();

        Facets f = response.getFacets();
        //跟上面的名称一样
        TermsFacet facet = (TermsFacet)f.getFacets().get("typeFacetName");
        for(TermsFacet.Entry tf :facet.getEntries()){
            System.out.println(tf.getTerm()+"\t:\t" + tf.getCount());
        }
        client.close();
    }
}
