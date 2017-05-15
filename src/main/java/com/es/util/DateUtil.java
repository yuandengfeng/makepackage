package com.es.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/7/28.
 */
public class DateUtil {
    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long dateStartTime(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime() / 1000;
        return ts;
    }

    /*
     * 将时间转换为时间戳(2016-10-19-11)
     */
    public static long dateToStampHour(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime() / 1000;
        return ts;
    }

    /**
     * 时间戳转换成String
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static String stampTodate(Long s) throws ParseException {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(s * 1000);
        return d;
    }

    /*
     * 将时间转换为时间戳(2016-10-19)
     */
    public static long dateToStampDay(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime() / 1000;
        return ts;
    }


    /*
    *获取当天的时间
    * 格式yyyy-MM-dd-hh
    * */
    public static String getDateNowHour() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        String nowTime = dateFormat.format(new Date());
        return nowTime;
    }

    /*
  *获取当前的时间
  * 格式yyyy-MM-dd HH:mm:ss
  * */
    public static String getDateNowss() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateFormat.format(new Date());
        return nowTime;
    }

    /*
  *获取明天的时间
  * 格式yyyy-MM-dd
  * */
    public static String getDayOfTomorrow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String tomorrow = dateFormat.format(cal.getTime());
        return tomorrow;
    }
    /*
  *获取昨天的时间
  * 格式yyyy-MM-dd
  * */
    public static String getDayOfYesterday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String tomorrow = dateFormat.format(cal.getTime());
        return tomorrow;
    }
    //获取距离dateTime多少天的时间，前+ ,后-
    public static String getDay(String dateTime, int i) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime() + i * 3600 * 24 * 1000L;  //L 不加L会正型越界
        return df.format(new Date(time));
    }

    public static void main(String[] args) throws ParseException {

//        System.out.println(Integer.MAX_VALUE);
        String firstday="2016-11-04";
        for(int i=0;i<177;i++)
        {
           String  start = getDay(firstday,i);
           String  end = getDay(firstday,i+1);
           System.out.println("start  "+start+"  end  "+end);

        }
        String yesterday = DateUtil.getDayOfYesterday();
        System.out.println(yesterday);


//        String yesterday="2016-11-13";
//        SimpleDateFormat LsimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = LsimpleDateFormat.parse(yesterday);
//        long srcstart= date.getTime();
//
////        long srcstart = DateUtil.dateStartTime(yesterday);
//
//        for(int i=0;i<368;i++){
//            long  start = srcstart+ i*3600 * 24* 1000;
//           long end = srcstart + 3600 * 24 * (i+1)*1000;
//            if(i==223){
//                System.out.println("==========="+end);
//            }
//            Date startdate = new Date(start);
//            Date enddate = new Date(end);
//            System.out.println(i+"  start  "+LsimpleDateFormat.format(startdate)+"  end  "+LsimpleDateFormat.format(enddate));
////            System.out.println("start  "+format.format(String.valueOf(start))+"  end  "+format.format(String.valueOf(end)));
//
//        }

//        String tt="4";
//        switch (tt) {
//            case "1":
//                System.out.println(tt);
//                break;
//            case "2":
//                System.out.println(tt);
//                break;
//            case "3":
//                System.out.println(tt);
//                break;
//            case "4":
//                System.out.println(tt);
//                break;
//            case "5":
//                System.out.println(tt);
//                break;
//            case "6":
//                System.out.println(tt);
//                break;
//            default:
//                break;
//        }
    }
}
