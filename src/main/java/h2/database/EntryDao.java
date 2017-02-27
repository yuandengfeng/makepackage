package h2.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import h2.entities.Page;

import java.sql.SQLException;

public class EntryDao {



    private ConnectionSource connectionSource;

    public EntryDao() throws SQLException {
        this.connectionSource = DBConn.getConnection();
    }

    public Dao<Page, String> getPageDao() throws SQLException {

        // instantiate the dao
        Dao<Page, String> pageDao = DaoManager.createDao(connectionSource,Page.class);
        return pageDao;
    }


}
