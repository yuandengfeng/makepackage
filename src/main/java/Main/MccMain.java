package Main;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/8/28 0028.
 */
public class MccMain {


    public static void main(String[] args){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/yunpos619?useUnicode=true&characterEncoding=UTF-8", "yunpos619", "yunpos619");
            conn.setAutoCommit(false); //设置是否自动提交
//            String select_category = "select DISTINCT category from fuymccode ";
//            String select_category = "select DISTINCT province from fuyaddresscode ";
            String select_category = "select DISTINCT cate1 from fuyweixincode ";

            PreparedStatement psts_category = conn.prepareStatement(select_category);
            System.out.println(psts_category.toString());
            ResultSet rs =  psts_category.executeQuery();

            File mccArray=new File("E:\\坤腾\\a开票程序\\富友\\fuyweixincode.min.js");
            JSONObject citylist =new JSONObject();
            JSONArray array=new JSONArray();
            while (rs.next()){
                System.out.println("category ======================== ");
                System.out.println(rs.getString(1));
                JSONObject p=new JSONObject();
                p.put("p",rs.getString(1));

//                String select_classify = "select DISTINCT city from fuyaddresscode where province= '"+rs.getString(1)+"'";
                String select_classify = "select DISTINCT cate2 from fuyweixincode where cate1= '"+rs.getString(1)+"'";

                PreparedStatement psts_classify = conn.prepareStatement(select_classify);
                ResultSet rs_classify =  psts_classify.executeQuery();
                System.out.println("classify ========== ");
                JSONArray c=new JSONArray();
                while (rs_classify.next()){
                    System.out.println(rs_classify.getString(1));
                    JSONObject n=new JSONObject();
                    n.put("n",rs_classify.getString(1));

//                    String select_shopclass = "select DISTINCT county from fuyaddresscode where province= '"+rs.getString(1)+"' and city ='"+rs_classify.getString(1)+"'";
                    String select_shopclass = "select DISTINCT cate3 from fuyweixincode where cate1= '"+rs.getString(1)+"' and cate2 ='"+rs_classify.getString(1)+"'";
                    PreparedStatement psts_shopclass = conn.prepareStatement(select_shopclass);
                    ResultSet rs_shopclass =  psts_shopclass.executeQuery();
                    System.out.println("shopclass ========== ");
                    JSONArray a=new JSONArray();
                    while (rs_shopclass.next()){
                        System.out.println(rs_shopclass.getString(1));
                        JSONObject s=new JSONObject();
                        s.put("s",rs_shopclass.getString(1));
                        a.add(s);
                    }
                    n.put("a",a);
                    c.add(n);
                    rs_shopclass.close();
                    psts_shopclass.close();

                }
                p.put("c",c);
                array.add(p);
                rs_classify.close();
                psts_classify.close();

            }
            citylist.put("citylist",array);
            rs.close();
            psts_category.close();
            conn.close();

            FileUtils.writeStringToFile(mccArray,citylist.toString(),"utf-8");
            System.out.println(citylist.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
