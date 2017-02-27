package h2;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import h2.database.DBConn;
import h2.entities.WenShuData;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuandengfeng on 2017/2/21.
 */
public class WenShuThread extends Thread {

    File file =null;

    public WenShuThread(File file) {
        this.file = file;
    }

    @Override
    public void run() {

        try {
        ConnectionSource connectionSource = DBConn.getConnection();

        TableUtils.createTableIfNotExists(connectionSource, WenShuData.class);

        Dao<WenShuData,String> pageDao = DaoManager.createDao(connectionSource,WenShuData.class);
        int flag = 0;
        System.out.println(file);


            List<String> list = FileUtils.readLines(file);
//                        System.out.println(WenShuMain.getWenShuData(list.get(1)));
            System.out.println(list.size());
            int count =0;
            for(int j=1;j<list.size();j++)
            {

                try {
                    flag =  pageDao.create(WenShuMain.getWenShuData(list.get(j)));
                    System.out.println(count++);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if(flag==0){
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
