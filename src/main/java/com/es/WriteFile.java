package com.es;


import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/8/12.
 */
public class WriteFile {
    static FileWriter  fw = null;
    static  BufferedWriter bufw =null;

    public static void writeResultFile(String filename,String text)
    {
        try {
            fw = new FileWriter(filename,true);
            bufw = new BufferedWriter(fw);
            //指定字符串编码格式
//            String str = new String(text.getBytes(),"ANSI");
            bufw.write(text);
//             bufw.write(str);
            bufw.newLine();
            bufw.flush();

        } catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    public static void main(String[] args){

        writeResultFile("G:/辽宁移动/打击报表匹配导出/整理/ok/ccc","ccccccccccc");
    }

}
