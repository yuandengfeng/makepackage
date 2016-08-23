package com.es;

/**
 * Created by Administrator on 2016/7/12.
 */
public class Main {

    public static void main(String[] args) {

       String[] type={"appstatus","auth","counter","forcedoffline"};
//        String[] type = {"counter"};
        for (String str : type) {
            esExe es1 = new esExe("wifiauth", str, "@timestamp", "tt");
            Thread t1 = new Thread(es1);
            t1.start();
        }
    }
}
