package h2.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.h2.tools.Server;

import java.sql.SQLException;


public class DBConn {

	private static ConnectionSource connectionSource;

//    private static Server server= null;    //创建本地h2数据库服务端

	private DBConn() {

    }

	public static ConnectionSource getConnection() throws SQLException{

//        if(server==null){
//            try {
//                server = Server.createTcpServer(
//                        "-tcpPort", "9123", "-tcpAllowOthers").start();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

		if(connectionSource==null||!connectionSource.isOpen()){
			// this uses h2 by default but change to match your database
//			String databaseUrl = "jdbc:h2:mem:test";
//            String databaseUrl = "jdbc:h2:~/data/test13;AUTO_SERVER=TRUE;";
//            String databaseUrl = "jdbc:h2:tcp://localhost:9123/~/data/test44;AUTO_RECONNECT=TRUE";
            String databaseUrl = "jdbc:h2:tcp://192.168.0.251/~/data/wenshu;AUTO_RECONNECT=TRUE";
//            String databaseUrl = "jdbc:h2:tcp://192.168.0.251/home/kt/databaseh2/wenshu3;AUTO_RECONNECT=TRUE";
            // create a connection source to our database
			connectionSource = new JdbcConnectionSource(databaseUrl,"sa","sa");
		}
		return connectionSource;
	}

}
