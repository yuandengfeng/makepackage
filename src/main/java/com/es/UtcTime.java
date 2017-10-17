package com.es;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuandengfeng on 2017/2/10.
 */

//UTC时间转化
public class UtcTime {

    public static void main(String[] args) {
//        2017-02-10T01:46:48.400Z
        String time="2017-02-10 05:13:46 ";
        System.out.println(getDateString(time));
    }

    public static String getDateString(String time) {
//        String time="2017-02-10 05:13:46 ";
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat utc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'"); //输出UTC时间格式
        Date date = null;
        try {
            date=sdf.parse(time);
//            date=new Date();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(date);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,-8); //8小时时间差

        return utc.format(calendar.getTime());

    }

}
