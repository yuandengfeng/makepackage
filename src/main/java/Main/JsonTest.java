package Main;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Administrator on 2016/8/2.
 */
public class JsonTest {



    public static HashMap<String, Object> toMap(JSONObject object) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }
    public static List<Object> toList(JSONArray array) throws Exception {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    public static void main(String[] args) throws Exception {
        JSONObject root = new JSONObject();
        root.put("cat","it");
        JSONObject lan1 = new JSONObject();
        lan1.put("id",1);
        lan1.put("ide","eclipse");
        lan1.put("name","java");
        JSONObject lan2 = new JSONObject();
        lan2.put("id",2);
        lan2.put("ide","ex");
        lan2.put("name","C#");
        JSONObject lan3 = new JSONObject();
        lan3.put("id",3);
        lan3.put("ide","cc");
        lan3.put("name","C");
        JSONArray array = new JSONArray();
//        array.add(0,lan1);
//        array.add(1,lan2);
//        array.add(2,lan3);
        array.add(lan1);
        array.add(lan2);
        array.add(lan3);
        root.put("lan",array);

        System.out.println("JsonRoot:  "+root.toString());
        System.out.println("map: "+toMap(root));

        JSONArray array1 = new JSONArray();

        array1 = root.getJSONArray("lan");
        System.out.println("lan array1: "+array1.toString());
        System.out.println(System.currentTimeMillis());

    }
}
