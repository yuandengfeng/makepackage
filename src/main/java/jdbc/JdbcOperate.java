package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yuandengfeng on 2016/11/28.
 */
public class JdbcOperate {


    public static void select(String mac){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");
            conn.setAutoCommit(false); //设置是否自动提交
            String select_sql = "select mac from location where mac='"+mac+"'";

            PreparedStatement psts = conn.prepareStatement(select_sql);
            System.out.println(psts.toString());
            ResultSet rs =  psts.executeQuery();

            int count=0;
            while (rs.next()){

                System.out.println(rs.getString(1));
                count++;

            }
            System.out.println(count);

            rs.close();
            psts.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     public static void delete(String mac){
         try {
             Class.forName("com.mysql.jdbc.Driver");

             Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");
             conn.setAutoCommit(false); //设置是否自动提交
             String delete_sql = "delete from location where mac='" + mac + "'";

             PreparedStatement psts = conn.prepareStatement(delete_sql);

           int status= psts.executeUpdate();
             System.out.println(status);

             psts.close();
             conn.close();


         } catch (Exception e) {
             e.printStackTrace();
         }


     }


    public static void insert(){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");
            conn.setAutoCommit(false); //设置是否自动提交
            String insert_sql="insert into location(mac,lng,lat,formatted_address,business,country,country_code,province,city,district,adcode,street,street_number,direction,distance,pois,poiRegions,sematic_description,cityCode) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement psts = conn.prepareStatement(insert_sql);

               psts.setString(1, "40A5EF8742F8");
                psts.setString(2, "116.31474");
                psts.setString(3, "40.043705");
                psts.setString(4,"北京市海淀区信息路11号");
                psts.setString(5, "上地,马连洼,西二旗");
                psts.setString(6, "中国");
                psts.setString(7, "0");
                psts.setString(8, "北京市");
                psts.setString(9, "北京市");
                psts.setString(10, "海淀区");
                psts.setString(11,"110108");
                psts.setString(12, "信息路");
                psts.setString(13,"11号");
                psts.setString(14,"西南");
                psts.setString(15, "79");
                psts.setString(16, "[{\\\"addr\":\"北京市海淀上地三街9号\",\"cp\":\"NavInfo\",\"direction\":\"内\",\"distance\":\"0\",\"name\":\"金隅嘉华大厦\",\"poiType\":\"房地产\",\"point\":{\"x\":116.3145,\"y\":40.042313},\"tag\":\"房地产\\\",\"tel\":\"\",\"uid\":\"07f3c8b579e77d7a6340a9f0\",\"zip\":\"\"},{\"addr\":\"北京市海淀区上地三街9号\",\"cp\":\" \",\"direction\":\"附近\",\"distance\":\"38\",\"name\":\"金隅嘉华大厦F座\",\"poiType\":\"房地产\",\"point\":{\"x\":116.31503,\"y\":40.04356},\"tag\":\"房地产;写字楼\",\"tel\":\"\",\"uid\":\\\"85442dc648bab3438f1a89c9\",\"zip\":\"\"},{\"addr\":\"北京市海淀区上地四街甲9号\",\"cp\":\" \",\"direction\":\"东\",\"distance\":\"108\",\"name\":\"北京四方继保自动化股份有限公司\",\"poiType\":\"公司企业\",\"point\":{\"x\":116.31382,\"y\":40.043934},\"tag\":\"公司企业\",\"tel\":\"\",\"uid\":\"cd9d05ddf829ffb685420e6e\",\"zip\":\"\"},{\"addr\":\"海淀区上地信息路15号\",\"cp\":\" \",\"direction\":\"西\",\"distance\":\"128\",\"name\":\"中国银行\",\"poiType\":\"金融\",\"point\":{\"x\":116.315895,\"y\":40.04365},\"tag\\\":\"金融;银行\",\"tel\":\"\",\"uid\":\"0893fc17fee2b5a8a6da39e1\",\"zip\":\"\"},{\"addr\":\"北京市海淀区嘉华大厦E座103\",\"cp\":\" \",\"direction\":\"东北\",\"distance\":\"153\",\"name\":\"物美\",\"poiType\":\"购物\",\"point\":{\"x\":116.31348,\"y\":40.043285},\"tag\":\"购物;超市\",\"tel\":\"\",\"uid\":\"2b32f777b665c1441894c67d\",\"zip\":\"\"},{\"addr\":\"北京市海淀区上地三街9号\",\"cp\":\" \",\"direction\":\"北\",\"distance\":\"123\",\"name\":\"金隅嘉华大厦C座\",\"poiType\":\"房地产\",\"point\":{\"x\":116.31498,\"y\":40.04287},\"tag\":\"房地产;写字楼\",\"tel\":\"\",\"uid\":\"826cac09fa9bc58b2dfaa718\",\"zip\":\"\"},{\"addr\":\"北京市海淀区上地三街9号嘉华大厦B座1楼\",\"cp\":\" \",\"direction\":\"北\",\"distance\":\"203\",\"name\":\"光大银行\",\"poiType\":\"金融\",\"point\":{\"x\":116.31531,\"y\":40.042374},\"tag\":\"金融;银行\",\"tel\":\"\",\"uid\":\"2fd2beab3f5d7c2f7adbd236\"");
                psts.setString(17, "");
                psts.setString(18, "金隅嘉华大厦内,物美东北153米");
                psts.setString(19, "131");

          int i= psts.executeUpdate();

            System.out.println(i);
            psts.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

   public static void update(){

       try {
           Class.forName("com.mysql.jdbc.Driver");

           Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");
           conn.setAutoCommit(false); //设置是否自动提交
           String update_sql = "update location set mac='40A5EF8742F8',lng='116.31474',lat='40.043705'," +
                   "formatted_address='北京市海淀区信息路11号',business='上地,马连洼,西二旗',country='中国',country_code='0',province='北京市'," +
                   "city='北京市',district='海淀区',adcode='110108',street='信息路',street_number='11号',direction='西南',distance='79'," +
                   "pois='',poiRegions='',sematic_description='金隅嘉华大厦内,物美东北153米',cityCode='131' where mac='40A5EF8742F8' ";

           PreparedStatement psts = conn.prepareStatement(update_sql);

           int i= psts.executeUpdate();
           System.out.println(i);
           psts.close();
           conn.close();


       } catch (Exception e) {
           e.printStackTrace();
       }

    }


    public static void main(String[] args){


        select("40A5EF857480");
//          delete("40A5EF8742F8");
//           insert();
//        update();



    }


}
