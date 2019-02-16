package rest.html;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2019/2/14 0014.
 */

/**
 * @author ydf
 * @com kt
 * @create 2019-02-14 上午 11:25
 * 抓取统计局(http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html)网站上的省市区区划代码
 **/
public class TaxCodeFromHtml {

    public static void main(String[] args) {
////////        /**-----------1--------------
//        String tt="441900000000,东莞市,442000000000,中山市";
//        String[] tts=tt.split(",");
//        File file=new File("F:\\shuiwu\\广东省.csv");
//        String provicence="440000000000,广东省,";
//        int count1 = 0;
//        int count2 = 1;
//        for (String str : tts) {
//            String cityone = tts[count1] + "," + tts[count1 + 1] + ",";
//            System.out.println("count1===" + count1);
//            getCountry(file,provicence,cityone,"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/"+tts[count1].substring(0,2)+"/"+tts[count1].substring(0,4)+".html");
//            count1 = count2*2;
//            count2++;
//        }
////        String city="460400000000,儋州市";
////        getCountry(file,provicence,city,"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/46/4604.html");
////         **/


//        /**-----------2------------
        File file = new File("F:\\shuiwu\\新疆维吾尔自治区.csv");
        String provicence = "650000000000,新疆维吾尔自治区,";
        String urlCity = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/"+provicence.substring(0,2)+".html";
        String urlCountry = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";
        getCountryFromCity(file, provicence, urlCity, urlCountry);
//         **/
    }

    public static void getCountryFromCity(File file, String provience, String urlCity, String urlCountry) {
        final String city = urlCity;
        try {
            Document doc = Jsoup.connect(city).get();
            Elements container = doc.getElementsByClass("citytr");
            Document containerDoc = Jsoup.parse(container.toString());
            Element element = containerDoc.body();
            String text = element.text();
            System.out.println("text==="+text);
            String[] tts = text.split(" ");
            int count1 = 0;
            int count2 = 1;
            for (String str : tts) {
                if(count1+1>tts.length)
                    break;
                String cityone = tts[count1] + "," + tts[count1 + 1] + ",";
                System.out.println("count1===" + count1);
                getCountry(file, provience, cityone, urlCountry + tts[count1].substring(0, 2) + "/" + tts[count1].substring(0, 4) + ".html");
                count1 = count2 * 2;
                count2++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getCity(File file, String provience, String url) {
        final String city = url;
        try {
            Document doc = Jsoup.connect(city).get();
            Elements container = doc.getElementsByClass("citytr");
            Document containerDoc = Jsoup.parse(container.toString());
            Element element = containerDoc.body();
            String text = element.text();
//            System.out.println("text==" + text);
            String[] arr = text.split(" ");
            int count = 0;
            for (String str : arr) {
                count++;
//                System.out.println(count);
                if (count % 2 == 0) {
                    System.out.println(str);
                } else if (count < arr.length) {
                    System.out.println(str + ",");
                } else {
                    System.out.println(str);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getCountry(File file, String provience, String city, String url) {
        final String countytr = url;
        try {
            Document doc = doc = Jsoup.connect(countytr).get();
            Elements container = doc.getElementsByClass("countytr"); //区县
//            Elements container = doc.getElementsByClass("towntr"); //乡镇街道
            Document containerDoc = Jsoup.parse(container.toString());
            Element element = containerDoc.body();
            String text = element.text();
//            System.out.println("text==" + text);
            String[] arr = text.split(" ");
            int count = 0;
            for (String str : arr) {
                count++;
//                System.out.println(count);
                if (count % 2 == 0) {
                    FileUtils.writeStringToFile(file, str + "\n", "UTF-8", true);
                    System.out.println(provience + city + str);
                } else if (count < arr.length) {
                    FileUtils.writeStringToFile(file, provience + city, "UTF-8", true);
                    FileUtils.writeStringToFile(file, str + ",", "UTF-8", true);
                    System.out.println(str + ",");
                } else {
                    FileUtils.writeStringToFile(file, str + "\n", "UTF-8", true);
                    System.out.println(provience + city + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
