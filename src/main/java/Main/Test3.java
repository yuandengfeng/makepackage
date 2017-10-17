package Main;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public class Test3 {
    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
//        System.out.println(System.getProperty("user.dir"))

//        DateFormat df = new SimpleDateFormat("yyMMDDHHmmss");
//        Date today = new Date();
//        String id="11";
//        Random random=new Random();
//
//        int result=random.nextInt((int)Math.pow(10,12-id.length()));
//        System.out.println(id+result);

//        System.out.println(id+getRandomStringByLength(12-id.length()));

        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("D");
        // 并集
        list1.addAll(list2);
        System.out.println(list1);
        list2.clear();
        System.out.println(list1);

    }
}
