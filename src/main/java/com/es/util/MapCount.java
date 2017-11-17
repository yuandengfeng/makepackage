package com.es.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
public class MapCount {

    private Map<String, Integer> map = new HashMap<String, Integer>();

    public void put(String enterid) {

            if (map.containsKey(enterid)) {
                map.put(enterid, map.get(enterid) + 1);
            } else {
                map.put(enterid, 1);
            }
    }
    public int get(String key){
        if(map.containsKey(key)){
            return map.get(key);
        }else{
            return 0;
        }
    }



}
