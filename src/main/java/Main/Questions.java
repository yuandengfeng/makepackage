package Main;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/14 0014.
 */
public class Questions {
    public static List<String> getQuestions(String file) {
        File f=new File(file);
        List<String> sns=null;
        try {
            sns= FileUtils.readLines(f,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sns;
    }
    public static void insertQuestions(List<String> questions) {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.20.83:3306/yunpos619?useUnicode=true&characterEncoding=utf-8", "yunpos619", "yunpos619");
            connection.setAutoCommit(false); //设置是否自动提交
            String insert_sql = "insert into fuyrate (code,rate,add) values (?,?,?)";
            prepareStatement = connection.prepareStatement(insert_sql);
            int count = 0;
            for (String str : questions) {
                count++;
                System.out.println(str);
                String[] srcs = str.split("\t");
                String code = srcs[0];
                String rate=srcs[1];
                String add = srcs[2];
                prepareStatement.setString(1, code);
                prepareStatement.setString(2, rate);
                prepareStatement.setString(3, add);

                prepareStatement.addBatch();  // 加入批量处理
                if (count % 1000 == 0) {
                    prepareStatement.executeBatch(); // 执行批量处理
                    connection.commit();  // 提交
                }
                System.out.println(count);
            }
            prepareStatement.executeBatch(); // 执行批量处理
            connection.commit();  // 提交
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                prepareStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        List<String> questions=getQuestions("E:\\坤腾\\a开票程序\\富友\\rate.lib");
//        for(String str:questions){
//            int a=str.split("\t").length;
//            if(a>3){
//            System.out.println();
//            }
//        }
        insertQuestions(questions);
    }


}
