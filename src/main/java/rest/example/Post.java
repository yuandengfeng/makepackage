package rest.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class Post extends Thread {
    HttpClient client = HttpClients.createDefault();


    public void run(){
//        http://fanyi.youdao.com/openapi.do?keyfrom=jcckxyteswqt&key=1290319334&type=data&doctype=xml&version=1.1&q=welcome
        HttpPost post = new HttpPost("http://fanyi.youdao.com/openapi.do");
        try {
            List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            parameters.add(new BasicNameValuePair("keyfrom","jcckxyteswqt"));
            parameters.add(new BasicNameValuePair("key","1290319334"));
            parameters.add(new BasicNameValuePair("type","data"));
            parameters.add(new BasicNameValuePair("doctype","xml"));
            parameters.add(new BasicNameValuePair("version","1.1"));
            parameters.add(new BasicNameValuePair("q","welcome"));


            post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            HttpResponse response= client.execute(post);
            response.getEntity();
            HttpEntity entity = response.getEntity();
            String result= EntityUtils.toString(entity, "UTF-8");
            System.out.println( result);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Post().run();
    }


}
