package h2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuandengfeng on 2017/2/21.
 */
public class ReadFileTool {


    public static void readFile(String dir) {


        File srcDir = new File(dir);
        // 列出源目录下的所有文件名和子目录名
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 如果是一个单个文件，则直接复制
            if (files[i].isFile()) {
                boolean flag = true;
                System.out.println(files[i]);

                try {
                    List<String> list =FileUtils.readLines(files[i]);
                    System.out.println(WenShuMain.getWenShuData(list.get(1)));

//                    for(int j=0;j<list.size();j++)
//                    {
//
//                    }
                    files[i].delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
      }

        public static void main(String[] args){


            readFile("G:\\坤腾\\网络爬虫\\爬虫ECEL");


       }


    }









