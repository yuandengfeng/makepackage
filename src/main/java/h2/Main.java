package h2;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import h2.database.DBConn;
import h2.entities.Page;
import h2.entities.WenShuData;
import net.sf.json.JSONArray;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yuandengfeng on 2017/2/21.
 */
public class Main {


    public static void main(String[] args){
                try {
            ConnectionSource connectionSource = DBConn.getConnection();

            Dao<WenShuData,String> pageDao = DaoManager.createDao(connectionSource,WenShuData.class);

//            QueryBuilder<WenShuData,String> queryBuilder = pageDao.queryBuilder();
//            queryBuilder.where().eq("WENSHUID","c9f4238a-8fbd-4fe2-a586-4c368669c23f");
//            PreparedQuery<WenShuData> preparedQuery = queryBuilder.prepare();
//            List<WenShuData> wenShuDatas = pageDao.query(preparedQuery);

              List<WenShuData> wenShuDatas =pageDao.queryForAll().subList(0,1000);
            System.out.println(wenShuDatas.size());
//              System.out.println(JSONArray.fromObject(wenShuDatas).toString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
