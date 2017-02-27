package h2.database;


import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import h2.entities.Page;

import java.sql.SQLException;

public class DBInit {

    public static void init() throws SQLException {
        ConnectionSource connectionSource = DBConn.getConnection();
        TableUtils.createTableIfNotExists(connectionSource, Page.class);

    }



}
