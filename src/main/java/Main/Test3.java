package Main;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

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
    public static List<String> getLines(String file) {
        File f=new File(file);
        List<String> lines=null;
        try {
            lines= FileUtils.readLines(f,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void main(String[] args) throws IOException {

        String str="{\"gameprogress\":\"7\",\"itemlist\":\"[]\",\"towerlist\":\"[]\",\"syntinfolist\":\"[]\",\"dailymissiontime\":\"636616542780000000\",\"dailymissionlist\":\"[{\"MissionID\":\"Mission001\",\"RewardID\":\"Item001\",\"RewardCount\":4,\"Count\":21,\"GetReward\":false,\"FinishCount\":0},{\"MissionID\":\"Mission004\",\"RewardID\":\"Item001\",\"RewardCount\":6,\"Count\":20,\"GetReward\":false,\"FinishCount\":0},{\"MissionID\":\"Mission003\",\"RewardID\":\"Item003\",\"RewardCount\":1,\"Count\":7,\"GetReward\":false,\"FinishCount\":0}]\",\"activereward\":\"[]\",\"signinfo\":\"{\"SignCount\":5,\"CurrentCount\":0,\"SignTime\":636813311850000000,\"CurrentMonth\":\"2018y12m\",\"Enable\":false}\",\"signinfolist\":\"[]\",\"rewardlist\":\"[]\",\"otherlist\":\"[{\"ID\":\"a1\",\"CurrentCount\":1616,\"Level\":3,\"Finished\":true},{\"ID\":\"a2\",\"CurrentCount\":150,\"Level\":3,\"Finished\":true},{\"ID\":\"a3\",\"CurrentCount\":1331,\"Level\":3,\"Finished\":false},{\"ID\":\"a4\",\"CurrentCount\":1388,\"Level\":2,\"Finished\":false}]\",\"othertext\":\"{\"CardList\":[],\"TowerList\":null,\"TelescopeList\":null,\"NextUpdateTime\":636813304451810000}\",\"id\":\"2eb88c07bcfa4c9c88bcf38bb26080b5\"}";
        JSONObject data =JSONObject.fromObject(str);
        System.out.println(data.getString("dailymissionlist"));
        /**
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
         **/

    }
}
