package Main;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Created by Administrator on 2016/8/2.
 */
public class JsonTest {


    public static void main(String[] args)
    {
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

        System.out.println(root.toString());

        JSONArray array1 = new JSONArray();

        array1 = root.getJSONArray("lan");
        System.out.println(array1.toString());
        System.out.println(System.currentTimeMillis());

    }
}
