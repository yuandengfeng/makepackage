package com.es;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ReadFile {


    public static void readFile(String detectIp)
    {
//         InputStreamReader  in = null;
        FileReader djfr =null;
        BufferedReader djbufr=null;

        try {

//                in = new InputStreamReader(new FileInputStream(detectIp),"GB2312");
//                djbufr = new BufferedReader(in);;
            djfr = new FileReader(detectIp);
            djbufr = new BufferedReader(djfr);
            String dj;
            String djline;

            while((dj=djbufr.readLine())!=null)
            {

                System.out.println(dj);

            }
        } catch (Exception ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
//                  in.close();
                djfr.close();
                djbufr.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
//        readFile("detectIp");

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
