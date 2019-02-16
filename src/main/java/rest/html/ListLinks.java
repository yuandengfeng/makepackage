package rest.html;/**
 * Created by Administrator on 2019/2/14 0014.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author ydf
 * @com kt
 * @create 2019-02-14 上午 11:11
 **/
public class ListLinks {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/11/1101.html").get();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("tr");
        System.out.println(newsHeadlines.html());
        for (Element headline : newsHeadlines) {
//            headline.
            System.out.println(headline.attr("td")+"------"+headline.absUrl("href"));

        }
    }
}
