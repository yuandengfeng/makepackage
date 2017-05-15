package jdbc;


import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandengfeng on 2016/11/24.
 */
public class JdbcMain {

    static Connection conn=null;
    static PreparedStatement psts=null;
    static ResultSet rs=null;
    static List<String> macList=null;

    public static void main1()  {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");

            conn.setAutoCommit(false); //设置是否自动提交
            String insert_sql = "insert into location(mac,lng,lat,formatted_address,business,country,country_code,province,city,district,adcode,street,street_number,direction,distance,pois,poiRegions,sematic_description,cityCode) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psts = conn.prepareStatement(insert_sql);


            File macsLoc23 = new File("G:\\坤腾\\超汇VIPLog接口\\cc\\2016-11-23_Loc_OK.txt");
            File macsLoc24 = new File("G:\\坤腾\\超汇VIPLog接口\\cc\\2016-11-24_Loc_OK.txt");
            File macsLoc28 = new File("G:\\坤腾\\超汇VIPLog接口\\cc\\2016-11-28_Loc_OK.txt");
            List<String> str23 = FileUtils.readLines(macsLoc23,"utf-8");

            List<String> str24 = FileUtils.readLines(macsLoc24,"utf-8");
            str23.addAll(str24);

            List<String> str28 = FileUtils.readLines(macsLoc28,"utf-8");

            List<String> insert = new ArrayList<String>();
            List<String> liMac = new ArrayList<String>();


            for (String s : str23) {
                String mac = s.split("\t")[0];
                if (!liMac.contains(mac)) {
                    liMac.add(mac);
//                    insert.add(s);
                }
            }


            for (String s : str28) {
                String mac = s.split("\t")[0];
                if (!liMac.contains(mac)) {
                    liMac.add(mac);
                    insert.add(s);
                }
            }


            int count = 0;
            for (String str : insert) {
                String[] srcs = str.split("\t");

//        System.out.println("macs=="+srcs[0]);
                psts.setString(1, srcs[0]);
                JSONObject js = JSONObject.fromObject(srcs[1]);
                JSONObject result = js.getJSONObject("result");


                if (result.containsKey("location")) {
                    JSONObject location = result.getJSONObject("location");
//            System.out.println(result.getJSONObject("location").toString());
//            if(!location.equals(null)){
                    //        System.out.println("lng=="+location.getString("lng"));
                    psts.setString(2, location.getString("lng"));
//        System.out.println("lat=="+location.getString("lat"));
                    psts.setString(3, location.getString("lat"));
                } else {
                    //        System.out.println("lng=="+location.getString("lng"));
                    psts.setString(2, "");
//        System.out.println("lat=="+location.getString("lat"));
                    psts.setString(3, "");
                }


//        System.out.println("formatted_address=="+result.getString("formatted_address"));
                if (result.containsKey("formatted_address")) {
                    psts.setString(4, result.getString("formatted_address"));
                } else {
                    psts.setString(4, "");
                }
//        System.out.println("business=="+result.getString("business"));
                if (result.containsKey("formatted_address")) {
                    psts.setString(5, result.getString("business"));
                } else {
                    psts.setString(5, "");
                }

                if (result.containsKey("addressComponent")) {
                    JSONObject addressComponent = result.getJSONObject("addressComponent");
//        String country = addressComponent.getString("country");
//        System.out.println("country=="+country);
                    psts.setString(6, addressComponent.getString("country"));
//        String country_code=addressComponent.getString("country_code");
//        System.out.println("country_code=="+country_code);
                    psts.setString(7, addressComponent.getString("country_code"));
//        String province=addressComponent.getString("province");
//        System.out.println("province=="+province);
                    psts.setString(8, addressComponent.getString("province"));
//        String city=addressComponent.getString("city");
//        System.out.println("city=="+city);
                    psts.setString(9, addressComponent.getString("city"));
//                    System.out.println(addressComponent.getString("city"));
//        String district=addressComponent.getString("district");
//        System.out.println("district=="+district);
                    psts.setString(10, addressComponent.getString("district"));
//        String adcode=addressComponent.getString("adcode");
//        System.out.println("adcode=="+adcode);
                    psts.setString(11, addressComponent.getString("adcode"));
//        String street=addressComponent.getString("street");
//        System.out.println("street=="+street);
                    psts.setString(12, addressComponent.getString("street"));
//        String street_number=addressComponent.getString("street_number");
//        System.out.println("street_number=="+street_number);
                    psts.setString(13, addressComponent.getString("street_number"));
//        String direction=addressComponent.getString("direction");
//        System.out.println("direction=="+direction);
                    psts.setString(14, addressComponent.getString("direction"));
//        String distance=addressComponent.getString("distance");
//        System.out.println("distance=="+distance);
                    psts.setString(15, addressComponent.getString("distance"));
                } else {
                    psts.setString(6, "");
                    psts.setString(7, "");
                    psts.setString(8, "");
                    psts.setString(9, "");
                    psts.setString(10, "");
                    psts.setString(11, "");
                    psts.setString(12, "");
                    psts.setString(13, "");
                    psts.setString(14, "");
                    psts.setString(15, "");
                }


//        String  pois = result.getString("pois");
//        System.out.println(" pois=="+ pois);
                if (result.containsKey("pois")) {
                    psts.setString(16, result.getString("pois"));
                } else {
                    psts.setString(16, "");
                }

//        System.out.println("poiRegions=="+result.getString("poiRegions"));
                if (result.containsKey("poiRegions")) {
                    psts.setString(17, result.getString("poiRegions"));
                } else {
                    psts.setString(17, "");
                }

//        System.out.println("sematic_description=="+result.getString("sematic_description"));
                if (result.containsKey("sematic_description")) {
                    psts.setString(18, result.getString("sematic_description"));
                } else {
                    psts.setString(18, "");
                }
//        System.out.println("cityCode=="+result.getString("cityCode"));
                if (result.containsKey("cityCode")) {
                    psts.setString(19, result.getString("cityCode"));
                } else {
                    psts.setString(19, "");
                }

                psts.addBatch();  // 加入批量处理
                if(count%1000==0)
                {
                    psts.executeBatch(); // 执行批量处理
//                    System.out.println(psts.toString()); //打印查如语句
                    conn.commit();  // 提交
                }
                System.out.println(++count);
            }
            psts.executeBatch(); // 执行批量处理
            conn.commit();  // 提交

            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean select(String mac){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");
            conn.setAutoCommit(false); //设置是否自动提交
            String select_sql = "select mac from location where mac='"+mac+"'";
            psts = conn.prepareStatement(select_sql);
            rs =  psts.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
            rs.close();
            psts.close();
            conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;

    }
    public static List<String> selectAll(){

        macList=new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");
            conn.setAutoCommit(false); //设置是否自动提交
            String select_sql = "select mac from location";
            psts = conn.prepareStatement(select_sql);
            rs =  psts.executeQuery();
            while(rs.next()){

//                System.out.println(rs.getString(1));
                macList.add(rs.getString(1));

            }
            return macList;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                psts.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return null;

    }

    public static void main(String[] args){

        Connection connection=null;
        PreparedStatement  prepareStatement=null;
            try {
                
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://192.168.20.83/macloc?useUnicode=true&characterEncoding=UTF-8", "macloc", "macloc");

                connection.setAutoCommit(false); //设置是否自动提交


                String insert_sql = "insert into location(mac,lng,lat,formatted_address,business,country,country_code,province,city,district,adcode,street,street_number,direction,distance,pois,poiRegions,sematic_description,cityCode) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                prepareStatement = connection.prepareStatement(insert_sql);


                File macsLoc30 = new File("G:\\坤腾\\超汇VIPLog接口\\cc\\2017-01-04_Loc_OK.txt");

                List<String> str30 = FileUtils.readLines(macsLoc30,"utf-8");
                List<String> li = selectAll();

                int count = 0;
                for (String s : str30) {
                    String[] srcs = s.split("\t");
                    String mac = srcs[0];
//                    System.out.println("srcmac=="+mac);
                    if (!li.contains(mac)){
                        count++;
                        System.out.println(mac);
//                        insert.add(s);

                            prepareStatement.setString(1,mac);
                            JSONObject js = JSONObject.fromObject(srcs[1]);
                            JSONObject result = js.getJSONObject("result");

                            if (result.containsKey("location")) {
                                JSONObject location = result.getJSONObject("location");
                                prepareStatement.setString(2, location.getString("lng"));
                                prepareStatement.setString(3, location.getString("lat"));
                            } else {
                                prepareStatement.setString(2, "");
                                prepareStatement.setString(3, "");
                            }

                            if (result.containsKey("formatted_address")) {
                                prepareStatement.setString(4, result.getString("formatted_address"));
                            } else {
                                prepareStatement.setString(4, "");
                            }
                            if (result.containsKey("formatted_address")) {
                                prepareStatement.setString(5, result.getString("business"));
                            } else {
                                prepareStatement.setString(5, "");
                            }

                            if (result.containsKey("addressComponent")) {
                                JSONObject addressComponent = result.getJSONObject("addressComponent");
                                prepareStatement.setString(6, addressComponent.getString("country"));
                                prepareStatement.setString(7, addressComponent.getString("country_code"));
                                prepareStatement.setString(8, addressComponent.getString("province"));
                                prepareStatement.setString(9, addressComponent.getString("city"));
                                prepareStatement.setString(10, addressComponent.getString("district"));
                                prepareStatement.setString(11, addressComponent.getString("adcode"));
                                prepareStatement.setString(12, addressComponent.getString("street"));
                                prepareStatement.setString(13, addressComponent.getString("street_number"));
                                prepareStatement.setString(14, addressComponent.getString("direction"));
                                prepareStatement.setString(15, addressComponent.getString("distance"));
                            } else {
                                prepareStatement.setString(6, "");
                                prepareStatement.setString(7, "");
                                prepareStatement.setString(8, "");
                                prepareStatement.setString(9, "");
                                prepareStatement.setString(10, "");
                                prepareStatement.setString(11, "");
                                prepareStatement.setString(12, "");
                                prepareStatement.setString(13, "");
                                prepareStatement.setString(14, "");
                                prepareStatement.setString(15, "");
                            }

                            if (result.containsKey("pois")) {
                                prepareStatement.setString(16, result.getString("pois"));
                            } else {
                                prepareStatement.setString(16, "");
                            }

                            if (result.containsKey("poiRegions")) {
                                prepareStatement.setString(17, result.getString("poiRegions"));
                            } else {
                                prepareStatement.setString(17, "");
                            }

                            if (result.containsKey("sematic_description")) {
                                prepareStatement.setString(18, result.getString("sematic_description"));
                            } else {
                                prepareStatement.setString(18, "");
                            }
                            if (result.containsKey("cityCode")) {
                                prepareStatement.setString(19, result.getString("cityCode"));
                            } else {
                                prepareStatement.setString(19, "");
                            }

                            prepareStatement.addBatch();  // 加入批量处理
                            if(count%10==0)
                            {
                                prepareStatement.executeBatch(); // 执行批量处理
//                                prepareStatement.executeUpdate();
                                connection.commit();  // 提交
                            }
                            System.out.println(count);
                        }

                    }


                prepareStatement.executeBatch(); // 执行批量处理
                connection.commit();  // 提交


            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                      prepareStatement.close();
                      connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


    }

}
