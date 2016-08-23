package com.es.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用ElasticSearch的客户端模式 替换旧的 httpclient模式
 * Created by ShiYun on 2016/5/25 0025.
 */
public class ESClient {
    static Map<String, String> m = new HashMap<String, String>();
    // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
    static Settings settings = ImmutableSettings.settingsBuilder().put(m).put("cluster.name", Global.getConfig("cluster.name")).put("client.transport.sniff", true).build();
    // 创建私有对象
    private static TransportClient client;

    static {
        try {
            Class<?> clazz = Class.forName(TransportClient.class.getName());
            Constructor<?> constructor = clazz.getDeclaredConstructor(Settings.class);
            constructor.setAccessible(true);
            client = (TransportClient) constructor.newInstance(settings);
            String content = Global.getConfig("cluster.ips");
            String[] ip_ports = content.split(",");
            for (int i = 0; i < ip_ports.length; i++) {
                String ip_port = ip_ports[i];
                String ip = ip_port.split(":")[0];
                int port = Integer.parseInt(ip_port.split(":")[1]);
                client.addTransportAddress(new InetSocketTransportAddress(ip, port));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 取得实例
    public static synchronized TransportClient getTransportClient() {
        return client;
    }

}
