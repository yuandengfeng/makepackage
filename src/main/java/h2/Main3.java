package h2;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import h2.database.DBConn;
import h2.entities.WenShuData;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuandengfeng on 2017/2/21.
 */
public class Main3 {


    public static WenShuData getWenShuData(String str)
    {
//        "date","level1","level2","docno","totalnum","note","flag","cpyyd","ajlx","cprj","ajmc","spcx","ah","fymc"
        try{
            String[] data=str.split(",");
            WenShuData wenShuData = new WenShuData();
            wenShuData.setWENSHUID(data[3].substring(1,data[3].length()-1)); //docno
            wenShuData.setAJLX(data[8].substring(1,data[8].length()-1));  //ajlx
            wenShuData.setDRRQ(data[0].substring(1,data[0].length()-1));  //date
            wenShuData.setAJMC(data[10].substring(1,data[10].length()-1)); //ajmc
            wenShuData.setAH(data[12].substring(1,data[12].length()-1));   //ah
            wenShuData.setFYMC(data[13].substring(1,data[13].length()-1));   //fymc
            wenShuData.setSPCX(data[11].substring(1,data[11].length()-1));  //spcx
            wenShuData.setCPRQ(data[9].substring(1,data[9].length()-1));    //cprj
            wenShuData.setCPYZYW(data[7].substring(1,data[7].length()-1)); //cpyyd
            return wenShuData;
        }catch (Exception ex){
            System.out.println(str);
        }
        return null;

    }


    public static void main(String[] args){

        try {
            ConnectionSource connectionSource = DBConn.getConnection();

            TableUtils.createTableIfNotExists(connectionSource, WenShuData.class);

            Dao<WenShuData,String> pageDao = DaoManager.createDao(connectionSource,WenShuData.class);

//            WenShuData wenShuData = new WenShuData();
//            wenShuData.setWENSHUID("e6ef526d-369c-49bc-ab3b-25892d197a9f"); //docno
//            wenShuData.setAJLX("民事案件");  //ajlx
//            wenShuData.setDRRQ("2017-01-06");  //date
//            wenShuData.setAJMC("上诉人王志浩、张勇与被上诉人曹建波合同纠纷民事裁定书"); //ajmc
//            wenShuData.setAH("（2016）云07民终35号");   //ah
//            wenShuData.setFYMC("云南省丽江市中级人民法院");   //fymc
//            wenShuData.setSPCX("二审");  //spcx
//            wenShuData.setCPRQ("2016-01-26");    //cprj
//            //cpyyd
//            wenShuData.setCPYZYW("本院认为，上诉人李某某以非法占有为目的，采用虚构事实、隐瞒真相的方法骗取他人财物，价值达40000元，数额较大，其行为已触犯刑律，构成诈骗罪。上诉人李某某以身体有病需外出医治，与原审判决的定罪量刑无因果关系；认为原审量刑过重，但二审期间并未提交相关可从轻或减轻处罚的证据，其认罪、悔罪等情节原审法院在量刑时已充分予以考虑，本院不再支持，故请求依法改判的上诉理由不能成立，本院不予支持。原审法院根据上诉人李某某的犯罪事实、性质、情节及社会危害程度所作出的刑事判决，认定事实清楚，证据确实充分，定性准确，量刑恰当，审判程序合法。依照《中华人民共和国刑事诉讼法》第二百二十五条第一款（一）项之规定，裁定如下");
//            pageDao.createIfNotExists(wenShuData);



            String dir="/home/cc/";

            File srcDir = new File(dir);
            // 列出源目录下的所有文件名和子目录名
            File[] files = srcDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果是一个单个文件，则直接复制
                if (files[i].isFile()) {

//                    new WenShuThread(files[i]);
                    int flag = 0;
                    System.out.println(files[i]);

                    try {
                        List<String> list = FileUtils.readLines(files[i]);
//                        System.out.println(WenShuMain.getWenShuData(list.get(1)));
                        System.out.println(list.size());
                        int count =0;
                        for(int j=1;j<list.size();j++)
                        {
                            if(getWenShuData(list.get(j)) != null){
                                try{
                                   flag= pageDao.create(getWenShuData(list.get(j)));
                                    System.out.println(count++);
                                }catch (Exception e){
                                    System.out.println(list.get(j));
                                }

                            }


                        }
                        if(flag==1){
                            files[i].delete();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

//            QueryBuilder<WenShuData,String> queryBuilder = pageDao.queryBuilder();
//            queryBuilder.where().eq("AJLX","民事案件");
//
//            PreparedQuery<WenShuData> preparedQuery = queryBuilder.prepare();
//
//            List<WenShuData> pages = pageDao.query(preparedQuery);
//
//            System.out.println(JSONArray.fromObject(pages).toString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
