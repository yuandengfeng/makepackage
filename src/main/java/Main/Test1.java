package Main;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Test1 {

    public static List<String> getImgs(String path) throws IOException {
        List<String>list=new ArrayList<String>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File tmp = files[i];
            if (tmp.isDirectory()) {
                File[] data = tmp.listFiles();
                for (int j = 0; j < data.length; j++) {
                    if(data[j].isDirectory()){
                        File[] f = data[j].listFiles();
                        for (int k = 0; k < f.length; k++) {
//                            System.out.println(f[k].getAbsolutePath());
                            List<String>list1=FileUtils.readLines(new File(f[k].getAbsolutePath()),"utf-8");
                            list.addAll(list1);
                        }
                    }else{
                        System.out.println(data[j].getAbsolutePath());
                        List<String>list2=FileUtils.readLines(new File(data[j].getAbsolutePath()),"utf-8");
                        list2.remove(0);
                        System.out.println(list2.size());
                        list.addAll(list2);
                    }

                }
            }
        }
        return list;

    }


    public static void main(String[] args) throws IOException {
        if (args.length < 6) {
            System.out.println("您调用main方法时没有指定正确参数！");
            System.out.println("输入的参数依次为 ip database user password table dataFlod");
            System.out.println("参数依次用空格隔开");
            System.exit(0);
        }
        String ip =args[0];
        String database=args[1];
        String user=args[2];
        String password=args[3];
        String table=args[4];
        String dataFlod=args[5];


        Connection connection=null;
        PreparedStatement prepareStatement=null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://192.168.20.83/yunpos619?useUnicode=true&characterEncoding=UTF-8", "yunpos619", "yunpos619");
            connection = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+database+"?useUnicode=true&characterEncoding=UTF-8", user,password);

            connection.setAutoCommit(false); //设置是否自动提交


            String insert_sql = "insert into "+table+"(userid,mobile,nickname,pid,realname,username,idcard,identityuserid,identityusername," +
                    "identityrealname,bankaddress,bankname,banknumber,phoneno,bankcarduserid,bankno)" +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            prepareStatement = connection.prepareStatement(insert_sql);
//            List<String>list=getImgs("F:\\test\\data\\aaa");
            List<String>list=getImgs(dataFlod);
            Set<String> set =new HashSet(list);
            System.out.println("list.size()=="+list.size()+"  set.size()=="+set.size());
            int count = 0;
            for (String str:set) {
                count++;
                System.out.println(str);
                str=str.replaceAll("[\\x{10000}-\\x{10FFFF}]", "");
                String[] srcs = str.split(",");
                String userid = srcs[0];
                String mobile=srcs[1];
                String nickname=srcs[2];
                String pid=srcs[3];
                String realname = srcs[4];
                String username = srcs[5];
                String idcard=srcs[6];
                String identityuserid=srcs[7];
                String identityusername=srcs[8];
                String identityrealname = srcs[9];
                String bankaddress=srcs[10];
                String bankname=srcs[11];
                String banknumber=srcs[12];
                String phoneno = srcs[13];
                String bankcarduserid=srcs[14];
                String bankno=srcs[15];

                prepareStatement.setString(1,userid);
                prepareStatement.setString(2, mobile);
                prepareStatement.setString(3, nickname);
                prepareStatement.setString(4, pid);
                prepareStatement.setString(5,realname);
                prepareStatement.setString(6, username);
                prepareStatement.setString(7, idcard);
                prepareStatement.setString(8, identityuserid);
                prepareStatement.setString(9,identityusername);
                prepareStatement.setString(10, identityrealname);
                prepareStatement.setString(11, bankaddress);
                prepareStatement.setString(12, bankname);
                prepareStatement.setString(13,banknumber);
                prepareStatement.setString(14, phoneno);
                prepareStatement.setString(15, bankcarduserid);
                prepareStatement.setString(16, bankno);

                prepareStatement.addBatch();  // 加入批量处理
                    if(count%2000==0)
                    {
                        prepareStatement.executeBatch(); // 执行批量处理
//                                prepareStatement.executeUpdate();
                        connection.commit();  // 提交
                    }
                    System.out.println(count);
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
