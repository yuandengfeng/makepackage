package com.es;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.es.util.ESClient;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/2/9.
 */
@Service
public class ElasticSearchService {

    public static final long EXPIRE_TIME = 3 * 60 * 1000;

    /**
     * 获取当前渠道下的累积访客数
     *
     * @param channelCode
     * @return
     */
    public int searchVisitorNumByChannel(String channelCode) {
//        ElasticSearchClient client = ElasticSearchClient.getInstance();
//        String url = Global.getConfig("elastic.search.visitor.num.url");
//        String jsonString = "{\"query\":{\"prefix\":{\"channelpath\":\"${{channelPath}}\"}}}";
//        jsonString = jsonString.replace("${{channelPath}}", channelCode.replaceAll("#", "_").toLowerCase());
//        Map response = client.post(url, jsonString);

        // 解析searchResult
//        return resolveVisitorNumfromElasticSearchResult(response);
        TransportClient client = ESClient.getTransportClient();
        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter").setQuery(QueryBuilders.prefixQuery("channelpath", channelCode.replaceAll("#", "_").toLowerCase()));
        System.out.println(result1.toString());
        long a = 0;
        try {
            SearchResponse json1 = result1.execute().actionGet();
            a = json1.getHits().getTotalHits();
        } catch (ElasticsearchException e) {
            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
        }
        return (int) a;
    }

    /**
     * 获取当前渠道下的在线访客数
     *
     * @param channelCode
     * @return
     */
    public int searchOnlineVisitorNumByChannel(String channelCode) {
//        ElasticSearchClient client = ElasticSearchClient.getInstance();
//        String url = Global.getConfig("elastic.search.visitor.num.url");
//        String jsonString = "{\"query\":{\"bool\":{\"must\":[{\"range\":{\"timestamp\":{\"gte\":${{startTime}}}}},{\"prefix\":{\"channelpath\":\"${{channelPath}}\"}}]}}}";
//        jsonString = jsonString.replace("${{channelPath}}", channelCode.replaceAll("#", "_").toLowerCase())
//                .replace("${{startTime}}", String.valueOf(new Date().getTime() - EXPIRE_TIME));
//        Map response = client.post(url, jsonString);
//
//        // 解析searchResult
//        return resolveVisitorNumfromElasticSearchResult(response);
        TransportClient client = ESClient.getTransportClient();
        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("timestamp").gte(new Date().getTime() - EXPIRE_TIME))
                .must(QueryBuilders.prefixQuery("channelpath", channelCode.replaceAll("#", "_").toLowerCase()));
        result1.setQuery(boolBuilder);
        System.out.println(result1.toString());
        long a = 0;
        try {
            SearchResponse json1 = result1.execute().actionGet();
            a = json1.getHits().getTotalHits();
        } catch (ElasticsearchException e) {
            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
        }
        return (int) a;
    }

    /**
     * 获取当前MAC下的累积访客数
     *
     * @param gwId
     * @param cod
     * @return
     */
    public int searchVisitorNumByRouterMac(String gwId, String cod) {
//        String code = cod.replaceAll("#", "_").toLowerCase();
//        ElasticSearchClient client = ElasticSearchClient.getInstance();
//        String url = Global.getConfig("elastic.search.visitor.num.url");
////        String jsonString = "{\"query\":{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}}}";
//        String jsonString = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}},{\"prefix\":{\"channelpath\":\"${{channel_path}}\"}}]}}}";
//        jsonString = jsonString.replace("${{gwId}}", gwId)
//                .replace("${{channel_path}}", code);
//
//        Map response = client.post(url, jsonString);
//        // 解析searchResult
//        return resolveVisitorNumfromElasticSearchResult(response);
        TransportClient client = ESClient.getTransportClient();
        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.queryString(gwId).defaultField("gwId"))
                .must(QueryBuilders.prefixQuery("channelpath", cod.replaceAll("#", "_").toLowerCase()));
        result1.setQuery(boolBuilder);
        System.out.println(result1.toString());
        long a = 0;
        try {
            SearchResponse json1 = result1.execute().actionGet();
            a = json1.getHits().getTotalHits();
        } catch (ElasticsearchException e) {
            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
        }
        return (int) a;
    }

    /**
     * 获取当前MAC下的在线访客数
     *
     * @param gwId
     * @return
     */
    public int searchOnlineVisitorNumByRouterMac(String gwId) {
//        ElasticSearchClient client = ElasticSearchClient.getInstance();
//        String url = Global.getConfig("elastic.search.visitor.num.url");
//        String jsonString = "{\"query\":{\"bool\":{\"must\":[{\"range\":{\"timestamp\":{\"gte\":${{startTime}}}}},{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}}]}}}";
//        jsonString = jsonString.replace("${{gwId}}", gwId)
//                .replace("${{startTime}}", String.valueOf(new Date().getTime() - EXPIRE_TIME));
//
//        Map response = client.post(url, jsonString);
//
//        return resolveVisitorNumfromElasticSearchResult(response);
        TransportClient client = ESClient.getTransportClient();
        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("timestamp").gte(new Date().getTime() - EXPIRE_TIME))
                .must(QueryBuilders.queryString(gwId).defaultField("gwId"));
        result1.setQuery(boolBuilder);
        System.out.println(result1.toString());
        long a = 0;
        try {
            SearchResponse json1 = result1.execute().actionGet();
            a = json1.getHits().getTotalHits();
        } catch (ElasticsearchException e) {
            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
        }
        return (int) a;
    }


    private int resolveVisitorNumfromElasticSearchResult(Map response) {
        int num = 0;
        try {
            num = (int) ((Map) response.get("hits")).get("total");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return num;
    }

    /**
     * 获取当前渠道下的访客信息
     *
     * @param vr
     * @return
     */
//    public List<VisitorRecord> searchVisitorByChannel(VisitorRecord vr) {
//        // 渠道id 小写可查询
//        String code = UserUtils.get(vr.getUserId()).getCode().replaceAll("#", "_").toLowerCase();
//        Page page = vr.getPage();
//
//        int pageNo = page.getPageNo();
//        int pageSize = page.getPageSize();
////        ElasticSearchClient client = ElasticSearchClient.getInstance();
////        String url = Global.getConfig("elastic.search.visitor.list.url");
////        String jsonString = "{\"from\":${{start}},\"size\":${{pageSize}},\"query\":{\"prefix\":{\"channelpath\":\"${{channel_path}}\"}},\"sort\":[{\"timestamp\":\"desc\"}]}";
////        jsonString = jsonString.replace("${{channel_path}}", code)
////                .replace("${{start}}", String.valueOf((pageNo - 1) * pageSize))
////                .replace("${{pageSize}}", String.valueOf(pageSize));
//////                .replace("${{start}}", "0")
//////                .replace("${{pageSize}}", "25000");
////        Map response = client.post(url, jsonString);
////        return resolveVisitorListFromElasticSearchResult(response, page);
//
//        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter");
//        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("channelpath", code);
//        result1.setQuery(prefixQueryBuilder);
//        result1.addSort("@timestamp", SortOrder.DESC);
//        result1.setFrom((pageNo - 1) * pageSize);
//        result1.setSize(pageSize);
//        System.out.println(result1.toString());
//        return resolveVisitorListFromElasticSearchResult(result1, page);
//    }

    /**
     * 获取当前渠道下黑白名单访客信息
     *
     * @param vr
     */
//    public void setBlackAndWhiteESInfo(VisitorRecord vr) {
//        // 渠道id 小写可查询(黑白名单访客信息)
//        String code = UserUtils.get(vr.getUserId()).getCode().replaceAll("#", "_").toLowerCase();
//
////        ElasticSearchClient client = ElasticSearchClient.getInstance();
////        String url = Global.getConfig("elastic.search.visitor.list.url");
//////        String jsonString1 = "{\"from\":${{start}},\"size\":${{pageSize}},\"query\":{\"query_string\":{\"default_field\":\"channelpath\",\"query\":\"${{channel_path}}\"}},\"sort\":[{\"timestamp\":\"desc\"}]}";
////        String jsonString1 = "{\"from\":${{start}},\"size\":${{pageSize}},\"query\":{\"bool\":{\"must\":[{\"prefix\":{\"channelpath\":\"${{channel_path}}\"}},{\"query_string\":{\"default_field\":\"mac\",\"default_operator\":\"AND\",\"query\":\"${{mac}}\"}}]}},\"sort\":[{\"timestamp\":\"desc\"}]}";
////        jsonString1 = jsonString1.replace("${{channel_path}}", code).replace("${{mac}}", vr.getStationmac().replaceAll(":", "-"))
////                .replace("${{start}}", "0")
////                .replace("${{pageSize}}", "1");
////        try {
////            Map response = client.post(url, jsonString1);
////            resolveBlackAndWhiteESInfoFromElasticSearchResult(response, vr);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter");
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.prefixQuery("channelpath", code))
//                .must(QueryBuilders.queryString(vr.getStationmac().replaceAll(":", "-")).defaultField("mac").defaultOperator(QueryStringQueryBuilder.Operator.AND));
//        result1.setQuery(boolBuilder);
//        result1.addSort("@timestamp", SortOrder.DESC);
//        result1.setFrom(0);
//        result1.setSize(1);
//        System.out.println(result1.toString());
//        try {
//            SearchResponse json1 = result1.execute().actionGet();
//            resolveBlackAndWhiteESInfoFromElasticSearchResult(json1, vr);
//        } catch (Exception e) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
//        }
//
//    }

    /**
     * 获取当前MAC下的访客信息
     *
     * @param vr
     * @return
     */
//    public List<VisitorRecord> searchVisitorByMac(VisitorRecord vr) {
//        // 根据MAC 查询访客信息，关联本渠道
//        String code = UserUtils.get(vr.getUserId()).getCode().replaceAll("#", "_").toLowerCase();
//
//        Page page = vr.getPage();
//        int pageNo = page.getPageNo();
//        int pageSize = page.getPageSize();
//
////        ElasticSearchClient client = ElasticSearchClient.getInstance();
////        String url = Global.getConfig("elastic.search.visitor.list.url");
//////        String jsonString = "{\"from\":${{start}},\"size\":${{pageSize}},\"query\":{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}},\"sort\":[{\"timestamp\":\"desc\"}]}";
//////        String jsonString = "{\"from\":${{start}},\"size\":${{pageSize}},{\"query\":{\"bool\":{\"must\":[{\"prefix\":{\"channelpath\":\"${{channel_path}}\"}}\"}},{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}}],\"must_not\":[],\"should\":[]}},\"sort\":[{\"timestamp\":\"desc\"}],\"aggs\":{}}";
////        String jsonString = "{\"from\":${{start}},\"size\":${{pageSize}},\"query\":{\"bool\":{\"must\":[{\"prefix\":{\"channelpath\":\"${{channel_path}}\"}},{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}}]}},\"sort\":[{\"timestamp\":\"desc\"}]}";
////
////        jsonString = jsonString.replace("${{gwId}}", vr.getMac())
////                .replace("${{channel_path}}", code)
////                .replace("${{start}}", String.valueOf((pageNo - 1) * pageSize))
////                .replace("${{pageSize}}", String.valueOf(pageSize));
////
////        Map response = client.post(url, jsonString);
////
////        return resolveVisitorListFromElasticSearchResult(response, page);
//        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter");
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.prefixQuery("channelpath", code))
//                .must(QueryBuilders.queryString(vr.getMac()).defaultField("gwId"));
//        result1.setQuery(boolBuilder);
//        result1.addSort("@timestamp", SortOrder.DESC);
//        result1.setFrom((pageNo - 1) * pageSize);
//        result1.setSize(pageSize);
//        System.out.println(result1.toString());
//        return resolveVisitorListFromElasticSearchResult(result1, page);
//    }

//    private void resolveBlackAndWhiteESInfoFromElasticSearchResult(Map response, VisitorRecord vr) {
//        try {
//            List rs = (List) ((Map) response.get("hits")).get("hits");
//            long currentTime = new Date().getTime() - EXPIRE_TIME;
//            for (Object item : rs) {
//                try {
//                    Map jsItem = (Map) item;
//                    Map record = (Map) jsItem.get("_source");
//
//                    if (vr.getStationmac().replaceAll(":", "-").equals((String) record.get("mac"))) {
//
//                        vr.setStationstatus((((long) record.get("timestamp")) > currentTime) ? 1 : 0);
//                        vr.setApmac((String) record.get("gwId"));
////                    vr.setStationmac(((String) record.get("mac")).replaceAll("-", ":"));
//                        if (record.get("name") != null && !record.get("name").equals("null")) {
//                            vr.setDevname(record.get("name").toString());
//                        }
//                        if (record.containsKey("wired")) {
//                            vr.setWired((int) record.get("wired"));
//                        }
//                        if (vr.getStationstatus() == 1) {
//                            Date uptime = new Date();
//                            int timestamp = (int) record.get("loginTime");
//                            uptime.setTime(timestamp * 1000L);
//                            vr.setUptime(uptime);
//                        }
//                        int olinetime = (int) record.get("onlineTime");
//                        int duration = olinetime / 60;
//                        if (olinetime % 60 != 0) {
//                            duration++;
//                        }
//                        vr.setDuration(duration);
//                    }
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception ex) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//        }
//    }
//
//    private void resolveBlackAndWhiteESInfoFromElasticSearchResult(SearchResponse json1, VisitorRecord vr) {
//        try {
//            long currentTime = new Date().getTime() - EXPIRE_TIME;
//            for (SearchHit hit : json1.getHits().getHits()) {
//                try {
//                    Map<String, Object> map = hit.getSource();
//
//                    if (vr.getStationmac().replaceAll(":", "-").equals((String) map.get("mac"))) {
//
//                        vr.setStationstatus((((long) map.get("timestamp")) > currentTime) ? 1 : 0);
//                        vr.setApmac((String) map.get("gwId"));
////                    vr.setStationmac(((String) record.get("mac")).replaceAll("-", ":"));
//                        if (map.get("name") != null && !map.get("name").equals("null")) {
//                            vr.setDevname(map.get("name").toString());
//                        }
//                        if (map.containsKey("wired")) {
//                            vr.setWired((int) map.get("wired"));
//                        }
//                        if (vr.getStationstatus() == 1) {
//                            Date uptime = new Date();
//                            int timestamp = (int) map.get("loginTime");
//                            uptime.setTime(timestamp * 1000L);
//                            vr.setUptime(uptime);
//                        }
////                        int olinetime = (int) map.get("onlineTime");
////                        int duration = olinetime / 60;
////                        if (olinetime % 60 != 0) {
////                            duration++;
////                        }
////                        vr.setDuration(duration);
//                        String olinetime = map.get("onlineTime").toString();
//                        vr.setDuration(getOnlineTime(olinetime));
//                    }
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception ex) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//        }
//    }

    /**
     * 在线时长数出现超过Integer范围导致抛出异常，致使页面访客显示不出来（对应bug[Yun-440]）
     *
     * @param onlinetime
     * @return
     */
    public int getOnlineTime(String onlinetime) {
        Long online = new Long(0);
        try {
            online = new Long(onlinetime);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        long c = online.longValue();
        int duration = (int) (c / 60);
        if (c % 60 != 0) {
            duration++;
        }
        return duration;
    }

//    private List<VisitorRecord> resolveVisitorListFromElasticSearchResult(Map response, Page page) {
//        List<VisitorRecord> list = new ArrayList<>();
//        try {
//            int total = (int) ((Map) response.get("hits")).get("total");
//            page.setCount(total);
//
//            List rs = (List) ((Map) response.get("hits")).get("hits");
//            long currentTime = new Date().getTime() - EXPIRE_TIME;
//            for (Object item : rs) {
//                try {
//                    Map jsItem = (Map) item;
//                    Map record = (Map) jsItem.get("_source");
//
//                    VisitorRecord vr = new VisitorRecord();
//                    vr.setStationstatus((((long) record.get("timestamp")) > currentTime) ? 1 : 0);
//                    vr.setApmac((String) record.get("gwId"));
//                    vr.setStationmac(((String) record.get("mac")).replaceAll("-", ":"));
//                    if (record.get("name") != null && !record.get("name").equals("null")) {
//                        vr.setDevname(record.get("name").toString());
//                    }
//                    if (record.containsKey("wired")) {
//                        vr.setWired((int) record.get("wired"));
//                    }
//                    if (vr.getStationstatus() == 1) {
//                        Date uptime = new Date();
//                        int timestamp = (int) record.get("loginTime");
//                        if (timestamp == 0) {
//                            vr.setShowOnlineInfo(false);
//                        }
//                        uptime.setTime(timestamp * 1000L);
//                        vr.setUptime(uptime);
//                    }
//                    int olinetime = (int) record.get("onlineTime");
//                    int duration = olinetime / 60;
//                    if (olinetime % 60 != 0) {
//                        duration++;
//                    }
//                    vr.setDuration(duration);
//                    list.add(vr);
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception ex) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//        }
//        return list;
//    }

    /**
     * 使用ES客户端模式转化查询结果
     *
     * @param result1
     * @param page
     * @return
     */
//    private List<VisitorRecord> resolveVisitorListFromElasticSearchResult(SearchRequestBuilder result1, Page page) {
//        List<VisitorRecord> list = new ArrayList<>();
//        try {
//            SearchResponse json1 = result1.execute().actionGet();
//
//            int total = (int) (json1.getHits().getTotalHits());
//            page.setCount(total);
//            long currentTime = new Date().getTime() - EXPIRE_TIME;
//            for (SearchHit hit : json1.getHits().getHits()) {
//                System.out.println("----------------------------------------------");
//                Map<String, Object> map = hit.getSource();
//                for (Map.Entry<String, Object> entry : map.entrySet()) {
//                    String string = entry.getKey();
//                    Object object = entry.getValue();
//                    System.out.println(string + "\t" + object);
//                }
//                VisitorRecord vr = new VisitorRecord();
//                vr.setStationstatus((((long) map.get("timestamp")) > currentTime) ? 1 : 0);
//                vr.setApmac((String) map.get("gwId"));
//                vr.setStationmac(((String) map.get("mac")).replaceAll("-", ":"));
//                String name = (String) map.get("name");
//                if (name != null && !name.equals("null")) {
//                    vr.setDevname(name);
//                }
//                if (map.containsKey("wired")) {
//                    vr.setWired((int) map.get("wired"));
//                }
//                if (vr.getStationstatus() == 1) {
//                    Date uptime = new Date();
//                    int timestamp = (int) map.get("loginTime");
//                    if (timestamp == 0) {
//                        vr.setShowOnlineInfo(false);
//                    }
//                    uptime.setTime(timestamp * 1000L);
//                    vr.setUptime(uptime);
//                }
////                int olinetime = (int) map.get("onlineTime");
////                int duration = olinetime / 60;
////                if (olinetime % 60 != 0) {
////                    duration++;
////                }
////                vr.setDuration(duration);
//                String olinetime =  map.get("onlineTime").toString();
//                vr.setDuration(getOnlineTime(olinetime));
//                list.add(vr);
//                System.out.println(map.size() + "\t" + hit.getSourceAsString());
//            }
//        } catch (Exception ex) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//        }
//        return list;
//    }

    /**
     * 根据MAC 查询访客信息，关联本渠道
     *
     * @param channelpath
     * @param mac
     * @param apmac
     * @return
     */
//    public VisitorRecord searchNetconnectLog(String channelpath, String mac, String apmac) {
//        // 根据MAC 查询访客信息，关联本渠道
//        String code = channelpath.replaceAll("#", "_").toLowerCase();
//        apmac = apmac.replaceAll(":", "-");
////        ElasticSearchClient client = ElasticSearchClient.getInstance();
////        String url = Global.getConfig("elastic.search.visitor.list.url");
////        String jsonString = "{\"from\":${{start}},\"size\":${{pageSize}},\"query\":{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}},\"sort\":[{\"timestamp\":\"desc\"}]}";
////        String jsonString = "{\"from\":${{start}},\"size\":${{pageSize}},{\"query\":{\"bool\":{\"must\":[{\"prefix\":{\"channelpath\":\"${{channel_path}}\"}}\"}},{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}}],\"must_not\":[],\"should\":[]}},\"sort\":[{\"timestamp\":\"desc\"}],\"aggs\":{}}";
////        String jsonString = "{\"from\":${{start}},\"size\":${{pageSize}},\"query\":{\"bool\":{\"must\":[{\"prefix\":{\"channelpath\":\"${{channel_path}}\"}},{\"query_string\":{\"default_field\":\"gwId\",\"query\":\"${{gwId}}\"}}]}},\"sort\":[{\"timestamp\":\"desc\"}]}";
//
////        String jsonString = "{\"query\":{\"bool\":{\"must\":[" +
////                "{\"query_string\":{\"default_field\":\"counter.channelpath\",\"query\":\"${{channel_path}}\"}}," +
////                "{\"query_string\":{\"default_field\":\"counter.gwId\",\"query\":\"${{gwId}}\"}}," +
////                "{\"query_string\":{\"default_field\":\"counter.mac\",\"query\":\"${{apmac}}\"}}" +
////                "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1,\"sort\":[{\"timestamp\":\"desc\"}],\"aggs\":{}}";
////        jsonString = jsonString.replace("${{channel_path}}", code).replace("${{gwId}}", mac).replace("${{apmac}}", apmac);
////        Map response = client.post(url, jsonString);
//
//        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("counter");
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.prefixQuery("channelpath", code))
//                .must(QueryBuilders.queryString(mac).defaultField("gwId"))
//                .must(QueryBuilders.queryString(apmac).defaultField("mac").defaultOperator(QueryStringQueryBuilder.Operator.AND));
//        result1.setQuery(boolBuilder);
//        result1.addSort("@timestamp", SortOrder.DESC);
//        result1.setFrom(0);
//        result1.setSize(1);
//        System.out.println(result1.toString());
//        try {
//            SearchResponse json1 = result1.execute().actionGet();
//            long currentTime = new Date().getTime() - EXPIRE_TIME;
//            for (SearchHit hit : json1.getHits().getHits()) {
//                try {
//                    System.out.println("----------------------------------------------");
//                    Map<String, Object> map = hit.getSource();
//                    for (Map.Entry<String, Object> entry : map.entrySet()) {
//                        String string = entry.getKey();
//                        Object object = entry.getValue();
//                        System.out.println(string + "\t" + object);
//                    }
//                    VisitorRecord vr = new VisitorRecord();
//                    vr.setStationstatus((((long) map.get("timestamp")) > currentTime) ? 1 : 0);
//                    vr.setApmac((String) map.get("gwId"));
//                    vr.setStationmac(((String) map.get("mac")).replaceAll("-", ":"));
//                    String name = (String) map.get("name");
//                    if (name != null && !name.equals("null")) {
//                        vr.setDevname(name);
//                    } else {
//                        vr.setDevname("");
//                    }
//                    if (vr.getStationstatus() == 1) {
//                        Date uptime = new Date();
//                        int timestamp = (int) map.get("loginTime");
//                        uptime.setTime(timestamp * 1000L);
//                        vr.setUptime(uptime);
//                    }
////                    int olinetime = (int) map.get("onlineTime");
////                    int duration = olinetime / 60;
////                    if (olinetime % 60 != 0) {
////                        duration++;
////                    }
////                    vr.setDuration(duration);
//
//                    String olinetime = map.get("onlineTime").toString();
//                    vr.setDuration(getOnlineTime(olinetime));
//                    return vr;
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception ex) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//        }
//        return null;
//
////        try {
////            List rs = (List) ((Map) response.get("hits")).get("hits");
////            long currentTime = new Date().getTime() - EXPIRE_TIME;
////            for (Object item : rs) {
////                try {
////                    Map jsItem = (Map) item;
////                    Map record = (Map) jsItem.get("_source");
////                    VisitorRecord vr = new VisitorRecord();
////                    vr.setStationstatus((((long) record.get("timestamp")) > currentTime) ? 1 : 0);
////                    vr.setApmac((String) record.get("gwId"));
////                    vr.setStationmac(((String) record.get("mac")).replaceAll("-", ":"));
////                    vr.setDevname((String) record.get("name"));
////                    if (vr.getStationstatus() == 1) {
////                        Date uptime = new Date();
////                        int timestamp = (int) record.get("loginTime");
////                        uptime.setTime(timestamp * 1000L);
////                        vr.setUptime(uptime);
////                    }
////                    int olinetime = (int) record.get("onlineTime");
////                    int duration = olinetime / 60;
////                    if (olinetime % 60 != 0) {
////                        duration++;
////                    }
////                    vr.setDuration(duration);
////                    return vr;
////                } catch (Exception ex) {
////                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
////                }
////            }
////        } catch (Exception e) {
////            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
////        }
////        return null;
//    }

    /**
     * 根据渠道、路由mac、访客mac 查询登录内网ip和外网ip
     *
     * @param channelpath
     * @param mac
     * @param apmac
     * @return
     */
//    public JSONObject searchIP(String channelpath, String mac, String apmac) {
//        // 根据MAC 查询访客信息，关联本渠道
//        String code = channelpath.replaceAll("#", "_").toLowerCase();
//        apmac = apmac.replaceAll(":", "");
////        ElasticSearchClient client = ElasticSearchClient.getInstance();
////        String url = Global.getConfig("elastic.search.visitor.ip.url");
////        String jsonString = "{\"query\":{\"bool\":{\"must\":[" +
////                "{\"query_string\":{\"default_field\":\"login.gw_id\",\"query\":\"${{gwId}}\"}}," +
////                "{\"query_string\":{\"default_field\":\"login.mac\",\"query\":\"${{apmac}}\"}}," +
////                "{\"query_string\":{\"default_field\":\"login.channel_path\",\"query\":\"${{channel_path}}\"}}" +
////                "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1,\"sort\":[{\"timestamp\":\"desc\"}],\"aggs\":{}}";
////        jsonString = jsonString.replace("${{channel_path}}", code).replace("${{gwId}}", mac).replace("${{apmac}}", apmac);
////        Map response = client.post(url, jsonString);
////        JSONObject jsonObject = new JSONObject();
//        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("login");
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.prefixQuery("channel_path", code))
//                .must(QueryBuilders.queryString(mac).defaultField("gwId"))
//                .must(QueryBuilders.queryString(apmac).defaultField("mac"));
//        result1.setQuery(boolBuilder);
//        result1.addSort("@timestamp", SortOrder.DESC);
//        result1.setFrom(0);
//        result1.setSize(1);
//        System.out.println(result1.toString());
//        JSONObject jsonObject = new JSONObject();
//        try {
//            SearchResponse json1 = result1.execute().actionGet();
//            for (SearchHit hit : json1.getHits().getHits()) {
//                try {
//                    Map<String, Object> map = hit.getSource();
//                    String rip = (String) map.get("rip");
//                    String gwaddress = (String) map.get("gw_address");
//                    String gwport = (String) map.get("gw_port");
//                    String ip = (String) map.get("ip");
//                    jsonObject.put("rip", rip);
//                    jsonObject.put("ip", ip);
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception e) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
//        }
//        return jsonObject;
//
//
////        try {
////            List rs = (List) ((Map) response.get("hits")).get("hits");
////            for (Object item : rs) {
////                try {
////                    Map jsItem = (Map) item;
////                    Map record = (Map) jsItem.get("_source");
////                    String rip = (String) record.get("rip");
////                    String gwaddress = (String) record.get("gw_address");
////                    String gwport = (String) record.get("gw_port");
////                    String ip = (String) record.get("ip");
////                    jsonObject.put("rip", rip);
////                    jsonObject.put("ip", ip);
////                } catch (Exception ex) {
////                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
////                }
////            }
////        } catch (Exception e) {
////            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
////        }
////        return jsonObject;
//    }

    /**
     * 根据渠道、访客查询一键上网信息
     *
     * @param channelpath
     * @param apmac
     * @return
     */
//    public JSONArray searchOneKey(String channelpath, String apmac) {
//        String code = channelpath.replaceAll("#", "_").toLowerCase();
//        apmac = apmac.replaceAll(":", "");
////        ElasticSearchClient client = ElasticSearchClient.getInstance();
////        String url = Global.getConfig("elastic.search.visitor.ip.url");
////        String jsonString = "{\"query\":{\"bool\":{\"must\":[" +
////                "{\"query_string\":{\"default_field\":\"onekey.mac\",\"query\":\"${{apmac}}\"}}," +
////                "{\"query_string\":{\"default_field\":\"onekey.channel_path\",\"query\":\"${{channel_path}}\"}}" +
////                "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10000,\"sort\":[{\"@timestamp\":\"desc\"}],\"aggs\":{}}";
////        jsonString = jsonString.replace("${{channel_path}}", code).replace("${{apmac}}", apmac);
////        Map response = client.post(url, jsonString);
////        JSONArray jsonArray = new JSONArray();
//
//        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("onekey");
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.prefixQuery("channel_path", code))
//                .must(QueryBuilders.queryString(apmac).defaultField("mac"));
//        result1.setQuery(boolBuilder);
//        result1.addSort("@timestamp", SortOrder.DESC);
//        result1.setFrom(0);
//        result1.setSize(10000);
//        System.out.println(result1.toString());
//        JSONArray jsonArray = new JSONArray();
//        try {
//            SearchResponse json1 = result1.execute().actionGet();
//            for (SearchHit hit : json1.getHits().getHits()) {
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    Map<String, Object> map = hit.getSource();
//
//                    String time = (String) map.get("@timestamp");
//                    String rip = (String) map.get("rip");
//                    String state = (String) map.get("state");
//                    String refer = (String) map.get("refer");
//                    String ua = (String) map.get("ua");
//                    String eventType = (String) map.get("eventType");
//                    String gw_id = (String) map.get("gw_id");
//                    String gwaddress = (String) map.get("gw_address");
//                    String gwport = (String) map.get("gw_port");
//                    String name = (String) map.get("name");
//                    String os = (String) map.get("os");
//                    String os_name = (String) map.get("os_name");
//                    String device = (String) map.get("device");
//                    jsonObject.put("type", "一键上网");
//                    jsonObject.put("mac", gw_id);
//                    jsonObject.put("refer", refer);
//                    jsonObject.put("name", name);
//                    jsonObject.put("os", os);
//                    jsonObject.put("os_name", os_name);
//                    jsonObject.put("device", device);
//                    jsonObject.put("time", changeDate(time));
//                    jsonArray.add(jsonObject);
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception e) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
//        }
//        return jsonArray;
//
////        try {
////            List rs = (List) ((Map) response.get("hits")).get("hits");
////            for (Object item : rs) {
////                JSONObject jsonObject = new JSONObject();
////                try {
////                    Map jsItem = (Map) item;
////                    Map record = (Map) jsItem.get("_source");
////                    String time = (String) record.get("@timestamp");
////                    String rip = (String) record.get("rip");
////                    String state = (String) record.get("state");
////                    String refer = (String) record.get("refer");
////                    String ua = (String) record.get("ua");
////                    String eventType = (String) record.get("eventType");
////                    String gw_id = (String) record.get("gw_id");
////                    String gwaddress = (String) record.get("gw_address");
////                    String gwport = (String) record.get("gw_port");
////                    String name = (String) record.get("name");
////                    String os = (String) record.get("os");
////                    String os_name = (String) record.get("os_name");
////                    String device = (String) record.get("device");
////                    jsonObject.put("type", "一键上网");
////                    jsonObject.put("mac", gw_id);
////                    jsonObject.put("refer", refer);
////                    jsonObject.put("name", name);
////                    jsonObject.put("os", os);
////                    jsonObject.put("os_name", os_name);
////                    jsonObject.put("device", device);
////                    jsonObject.put("time", changeDate(time));
////                    jsonArray.add(jsonObject);
////                } catch (Exception ex) {
////                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
////                }
////            }
////        } catch (Exception e) {
////            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
////        }
////        return jsonArray;
//    }

    /**
     * 根据渠道、访客查询访客短信认证信息
     *
     * @param channelpath
     * @param apmac
     * @return
     */
//    public JSONArray searchSMS(String channelpath, String apmac) {
//        String code = channelpath.replaceAll("#", "_").toLowerCase();
//        apmac = apmac.replaceAll(":", "");
////        ElasticSearchClient client = ElasticSearchClient.getInstance();
////        String url = Global.getConfig("elastic.search.visitor.ip.url");
////        String jsonString = "{\"query\":{\"bool\":{\"must\":[" +
////                "{\"query_string\":{\"default_field\":\"sms.mac\",\"query\":\"${{apmac}}\"}}," +
////                "{\"query_string\":{\"default_field\":\"sms.channel_path\",\"query\":\"${{channel_path}}\"}}" +
////                "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10000,\"sort\":[{\"@timestamp\":\"desc\"}],\"aggs\":{}}";
////        jsonString = jsonString.replace("${{channel_path}}", code).replace("${{apmac}}", apmac);
////        Map response = client.post(url, jsonString);
////        JSONArray jsonArray = new JSONArray();
//
//        TransportClient client = ESClient.getTransportClient();
//        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("sms");
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.prefixQuery("channel_path", code))
//                .must(QueryBuilders.queryString(apmac).defaultField("mac"));
//        result1.setQuery(boolBuilder);
//        result1.addSort("@timestamp", SortOrder.DESC);
//        result1.setFrom(0);
//        result1.setSize(10000);
//        System.out.println(result1.toString());
//        JSONArray jsonArray = new JSONArray();
//
//        try {
//            SearchResponse json1 = result1.execute().actionGet();
//            for (SearchHit hit : json1.getHits().getHits()) {
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    Map<String, Object> map = hit.getSource();
//
//                    String time = (String) map.get("@timestamp");
//                    String rip = (String) map.get("rip");
//                    String state = (String) map.get("state");
//                    String refer = (String) map.get("refer");
//                    String ua = (String) map.get("ua");
//                    String eventType = (String) map.get("eventType");
//                    String gw_id = (String) map.get("gw_id");
//                    String gwaddress = (String) map.get("gw_address");
//                    String gwport = (String) map.get("gw_port");
//                    String phone = (String) map.get("phone");
//                    String name = (String) map.get("name");
//                    String os = (String) map.get("os");
//                    String os_name = (String) map.get("os_name");
//                    String device = (String) map.get("device");
//                    jsonObject.put("type", "短信认证");
//                    jsonObject.put("mac", gw_id);
//                    jsonObject.put("refer", refer);
//                    jsonObject.put("phone", phone);
//                    jsonObject.put("name", name);
//                    jsonObject.put("os", os);
//                    jsonObject.put("os_name", os_name);
//                    jsonObject.put("device", device);
//                    jsonObject.put("time", changeDate(time));
//                    jsonArray.add(jsonObject);
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception e) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
//        }
//        return jsonArray;
//
////        try {
////            List rs = (List) ((Map) response.get("hits")).get("hits");
////            for (Object item : rs) {
////                JSONObject jsonObject = new JSONObject();
////                try {
////                    Map jsItem = (Map) item;
////                    Map record = (Map) jsItem.get("_source");
////                    String time = (String) record.get("@timestamp");
////                    String rip = (String) record.get("rip");
////                    String state = (String) record.get("state");
////                    String refer = (String) record.get("refer");
////                    String ua = (String) record.get("ua");
////                    String eventType = (String) record.get("eventType");
////                    String gw_id = (String) record.get("gw_id");
////                    String gwaddress = (String) record.get("gw_address");
////                    String gwport = (String) record.get("gw_port");
////                    String phone = (String) record.get("phone");
////                    String name = (String) record.get("name");
////                    String os = (String) record.get("os");
////                    String os_name = (String) record.get("os_name");
////                    String device = (String) record.get("device");
////                    jsonObject.put("type", "短信认证");
////                    jsonObject.put("mac", gw_id);
////                    jsonObject.put("refer", refer);
////                    jsonObject.put("phone", phone);
////                    jsonObject.put("name", name);
////                    jsonObject.put("os", os);
////                    jsonObject.put("os_name", os_name);
////                    jsonObject.put("device", device);
////                    jsonObject.put("time", changeDate(time));
////                    jsonArray.add(jsonObject);
////                } catch (Exception ex) {
////                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
////                }
////            }
////        } catch (Exception e) {
////            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
////        }
////        return jsonArray;
//    }

    /**
     * 根据渠道、访客查询访客微信上网信息
     *
     * @param channelpath
     * @param apmac
     * @return
     */
    public JSONArray searchWeiXin(String channelpath, String apmac) {
        String code = channelpath.replaceAll("#", "_").toLowerCase();
        apmac = apmac.replaceAll(":", "");
//        ElasticSearchClient client = ElasticSearchClient.getInstance();
//        String url = Global.getConfig("elastic.search.visitor.ip.url");
//        String jsonString = "{\"query\":{\"bool\":{\"must\":[" +
//                "{\"query_string\":{\"default_field\":\"weixin.mac\",\"query\":\"${{apmac}}\"}}," +
//                "{\"query_string\":{\"default_field\":\"weixin.channel_path\",\"query\":\"${{channel_path}}\"}}" +
//                "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10000,\"sort\":[{\"@timestamp\":\"desc\"}],\"aggs\":{}}";
//        jsonString = jsonString.replace("${{channel_path}}", code).replace("${{apmac}}", apmac);
//        Map response = client.post(url, jsonString);
//        JSONArray jsonArray = new JSONArray();

        TransportClient client = ESClient.getTransportClient();
        SearchRequestBuilder result1 = client.prepareSearch("wifiauth").setTypes("weixin");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.prefixQuery("channel_path", code))
                .must(QueryBuilders.queryString(apmac).defaultField("mac"));
        result1.setQuery(boolBuilder);
        result1.addSort("@timestamp", SortOrder.DESC);
        result1.setFrom(0);
        result1.setSize(10000);
        System.out.println(result1.toString());
        JSONArray jsonArray = new JSONArray();

        try {
            SearchResponse json1 = result1.execute().actionGet();
            for (SearchHit hit : json1.getHits().getHits()) {
                JSONObject jsonObject = new JSONObject();
                try {
                    Map<String, Object> map = hit.getSource();

                    String time = (String) map.get("@timestamp");
                    String rip = (String) map.get("rip");
                    String state = (String) map.get("state");
                    String refer = (String) map.get("refer");
                    String ua = (String) map.get("ua");
                    String eventType = (String) map.get("eventType");
                    String gw_id = (String) map.get("gw_id");
                    String gwaddress = (String) map.get("gw_address");
                    String gwport = (String) map.get("gw_port");
                    String openId = (String) map.get("openId");
                    String tId = (String) map.get("tid");
                    String extend = (String) map.get("extend");
                    String sign = (String) map.get("sign");
                    String name = (String) map.get("name");
                    String os = (String) map.get("os");
                    String os_name = (String) map.get("os_name");
                    String device = (String) map.get("device");
                    jsonObject.put("type", "微信认证");
                    jsonObject.put("mac", gw_id);
                    jsonObject.put("refer", refer);
                    jsonObject.put("openid", openId);
                    jsonObject.put("tid", tId);
                    jsonObject.put("extend", extend);
                    jsonObject.put("sign", sign);
                    jsonObject.put("name", name);
                    jsonObject.put("os", os);
                    jsonObject.put("os_name", os_name);
                    jsonObject.put("device", device);
                    jsonObject.put("time", changeDate(time));
                    jsonArray.add(jsonObject);
                } catch (Exception ex) {
                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
                }
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
        }
        return jsonArray;

//        try {
//            List rs = (List) ((Map) response.get("hits")).get("hits");
//            for (Object item : rs) {
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    Map jsItem = (Map) item;
//                    Map record = (Map) jsItem.get("_source");
//                    String time = (String) record.get("@timestamp");
//                    String rip = (String) record.get("rip");
//                    String state = (String) record.get("state");
//                    String refer = (String) record.get("refer");
//                    String ua = (String) record.get("ua");
//                    String eventType = (String) record.get("eventType");
//                    String gw_id = (String) record.get("gw_id");
//                    String gwaddress = (String) record.get("gw_address");
//                    String gwport = (String) record.get("gw_port");
//                    String openId = (String) record.get("openId");
//                    String tId = (String) record.get("tid");
//                    String extend = (String) record.get("extend");
//                    String sign = (String) record.get("sign");
//                    String name = (String) record.get("name");
//                    String os = (String) record.get("os");
//                    String os_name = (String) record.get("os_name");
//                    String device = (String) record.get("device");
//                    jsonObject.put("type", "微信认证");
//                    jsonObject.put("mac", gw_id);
//                    jsonObject.put("refer", refer);
//                    jsonObject.put("openid", openId);
//                    jsonObject.put("tid", tId);
//                    jsonObject.put("extend", extend);
//                    jsonObject.put("sign", sign);
//                    jsonObject.put("name", name);
//                    jsonObject.put("os", os);
//                    jsonObject.put("os_name", os_name);
//                    jsonObject.put("device", device);
//                    jsonObject.put("time", changeDate(time));
//                    jsonArray.add(jsonObject);
//                } catch (Exception ex) {
//                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
//                }
//            }
//        } catch (Exception e) {
//            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
//        }
//        return jsonArray;
    }

    /**
     * 将ES里面的时区时间转换为显示标准时间 （相差8个小时）
     *
     * @param d
     * @return
     */
    public static String changeDate(String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(d);
            Date date1 = new Date(date.getTime() + (60 * 8 * 60 * 1000));
            d = sdf1.format(date1);
        } catch (ParseException ex) {
            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", ex);
        }
        return d;
    }


    /**
     * 根据时间段查询所有渠道的PV和UV
     *
     * @param map
     * @return
     */
    public List<Map<String, String>> getAllPvUvFromES(Map map) {
        TransportClient client = ESClient.getTransportClient();
        SearchRequestBuilder result = client.prepareSearch("wifiauth").setTypes("portal");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.rangeQuery("@timestamp").gte((String) map.get("startTime")).lte((String) map.get("endTime")));
        result.setQuery(boolBuilder);
        TermsBuilder termsBuilder = AggregationBuilders.terms("group_by").field("channel_path").size(0);
        termsBuilder.subAggregation(AggregationBuilders.cardinality("distinct").field("mac"));
        result.addAggregation(termsBuilder);
        return resolvePvUvESResponse(result);
    }

    /**
     * 解析ES接口查询PV和UV的返回数据
     *
     * @param result
     * @return
     */
    public List<Map<String, String>> resolvePvUvESResponse(SearchRequestBuilder result) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            SearchResponse json = result.execute().actionGet();
            // 获取一级聚合数据
            Terms terms = json.getAggregations().get("group_by");
            // 得到一级聚合结果里面的分桶集合
            List<Terms.Bucket> buckets = terms.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                try {
                    //读取二级聚合数据集引用
                    Aggregations sub = bucket.getAggregations();
                    //获取二级聚合集合
                    InternalCardinality count = (InternalCardinality) sub.getAsMap().get("distinct");
                    String key = bucket.getKey();
                    int pv = (int) bucket.getDocCount();
                    int uv = (int) count.getValue();
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("key", key);
                    hm.put("pv", String.valueOf(pv));
                    hm.put("uv", String.valueOf(uv));
                    list.add(hm);
                } catch (Exception e) {
                    LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
                }
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(ElasticSearchService.class).warn("{}", e);
        }
        return list;
    }

}
