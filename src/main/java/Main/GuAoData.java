package Main;

import FileUtil.CompressUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */
public class GuAoData {

    public static HashMap<String,String> getHashMap() throws IOException{
        File cityarray=new File("F:\\test\\data\\20170728_soybeanRNA数据注释信息表.txt");
        List<String> list= FileUtils.readLines(cityarray);
        HashMap<String,String> re=new HashMap<String,String>();
        for (String str:list){
            if (!str.startsWith("\t")){
                String[] ss=str.split("\t");
//                System.out.println(ss[0]+"\t"+ss[1]);
                re.put(ss[0],ss[1]);
            }
        }
        return re;
    }
    public static void getImgs(String path,HashMap<String,String> re) throws IOException {

        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File tmp = files[i];
            if (tmp.isDirectory()) {
                File[] data = tmp.listFiles();
                for (int j = 0; j < data.length; j++) {
                    if(data[j].isDirectory()){
                        File[] f = data[j].listFiles();
                        for (int k = 0; k < f.length; k++) {
                            System.out.println(f[k].getAbsolutePath());
                            List<String>list=FileUtils.readLines(new File(f[k].getAbsolutePath()));
                            String headstr=list.get(0);
                            String[] head=headstr.split("\t");
                            String newStr=headstr;
                            for(String str:head){
                                if (re.containsKey(str)){
                                    newStr=newStr.replace(str,re.get(str));
                                }
                            }
                           list.set(0,newStr);
                           FileUtils.writeLines(new File(f[k].getAbsolutePath().replace("src","newFlod")),list,true);
                        }
                    }else{
                        System.out.println(data[j].getAbsolutePath());
                        List<String>list=FileUtils.readLines(new File(data[j].getAbsolutePath()));
                        String headstr=list.get(0);
                        String[] head=headstr.split("\t");
                        String newStr=headstr;
                        for(String str:head){
                            if (re.containsKey(str)){
                                newStr=newStr.replace(str,re.get(str));
                            }
                        }
                        list.add(0,newStr);
                        FileUtils.writeLines(new File(data[j].getAbsolutePath().replace("src","newFlod")),list,true);
                    }

                }
            }
        }

    }
    public static String findBankType(String bankName){
        if(bankName.contains("工商")){
            return "0102";
        }else if(bankName.contains("农业")){
            return "0103";
        }else if(bankName.contains("中国银行")){
            return "0104";
        }else if(bankName.contains("建设")){
            return "0105";
        }else if(bankName.contains("交通")){
            return "0301";
        }else if(bankName.contains("招商")){
            return "0308";
        }else if(bankName.contains("兴业")){
            return "0309";
        }else if(bankName.contains("民生")){
            return "0305";
        }else if(bankName.contains("发展")){
            return "0306";
        }else if(bankName.contains("平安")){
            return "0307";
        }else if(bankName.contains("浦东")){
            return "0310";
        }else if(bankName.contains("华夏")){
            return "0304";
        }else if(bankName.contains("上海农村")){
            return "0322";
        }else if(bankName.contains("城市商业")){
            return "0313";
        }else if(bankName.contains("农村商业")){
            return "0314";
        }else if(bankName.contains("恒丰")){
            return "0315";
        }else if(bankName.contains("邮政")){
            return "0403";
        }else if(bankName.contains("光大")){
            return "0303";
        }else if(bankName.contains("中信")){
            return "0302";
        }else if(bankName.contains("浙商")){
            return "0316";
        }else if(bankName.contains("渤海")){
            return "0318";
        }else if(bankName.contains("徽商")){
            return "0319";
        }else if(bankName.contains("信用合作")){
            return "0402";
        }else if(bankName.contains("村镇")){
            return "0320";
        }
        return "";
    }

    public static void main(String[] args) throws IOException {

        System.out.println(findBankType(""));

//        相应字段替换并生产到对应的文件夹
        /**
        HashMap<String,String> re=getHashMap();
        getImgs("F:\\test\\data\\src",re);
         */

//        生产压缩文件
        /**
        File file = new File("F:\\test\\data\\newFlod\\comparison_differ.new");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            CompressUtil.zip(files[i].getAbsolutePath(), files[i].getAbsolutePath()+".zip", null);
        }
         */


    }
}
