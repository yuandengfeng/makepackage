package Main;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
//        File alipay=new File("E:\\坤腾\\a开票程序\\富友\\alipay.csv");
//        List<String> list= FileUtils.readLines(alipay);
//        for (String str:list){
//            System.out.println(str);
//        }
        /**

        String msg = "dragon";
        String tag="ccv";
        switch (msg) {
            case "rabbit":
                System.out.println("rabbit ");
            case "dragon":
                if(tag=="tt"){
                    System.out.println("happy new year");
                    System.out.println("happy new year");
                    System.out.println("happy new year");
                }else if(tag == "cc"){
                    System.out.println("happy new year");
                    System.out.println("happy new year");
                }else {
                    System.out.println("happy new year");
                }
                break;
            case "monkey":
                System.out.println("monkey");
                break;
            case "tiger":
                System.out.println("tiger!!");
            default:
                System.out.println("what ?");
        }
         */

//        String value ="";
//
//        System.out.println("String.valueOf(entry.getValue()) == "+value);
        File cityarray=new File("E:\\坤腾\\a开票程序\\富友\\cityarray.txt");
//        List<String>list=FileUtils.readLines(alipay);
        String content=FileUtils.readFileToString(cityarray);
        System.out.println(content);
        JSONObject jsonObject =JSONObject.fromObject(content);
        JSONArray jsonArray=JSONArray.fromObject(jsonObject.getString("citylist"));
        System.out.println(jsonObject.getString("citylist"));
        System.out.println(jsonArray.size());
        int count=0;
        for(Object obj:jsonArray){
            JSONObject o = (JSONObject) obj;
            String shen = o.getString("p");
            if(o.containsKey("c")){
                JSONArray c=JSONArray.fromObject(o.getString("c"));
                for(Object city:c){
                    JSONObject n= (JSONObject) city;
                    String shi=n.getString("n");
                    if(n.containsKey("a")){
                        JSONArray a=JSONArray.fromObject(n.getString("a"));
                        for(Object area:a){
                            JSONObject s=(JSONObject)area;
                            String chu=s.getString("s");
                            System.out.println(shen+"  省  "+shi+"  市  "+chu+"  区");
                            count++;
                        }
                    }else {
                        System.out.println(shen+"  省  "+shi+"  市");
                        count++;
                    }
                }
            }else {
                System.out.println(shen+"  省");
                count++;
            }
        }
        System.out.println("count == "+count);



    }
}
