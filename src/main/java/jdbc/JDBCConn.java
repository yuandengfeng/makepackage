package jdbc;

import java.sql.*;


public class JDBCConn {

	public static String getInsertSQL(DPIInfo dpi) {

		String sql = "insert into dpiinfo.dpiinfor(id,utctime,src_ip,src_port,des_ip,des_port,url,d_host,d_connection,accept,user_agent,referer,apt_encoding,apt_language,cookie,if_modified,x_apple_c_type) "
				+ "values ("
				+ 0
				+ ",\'"
				+ dpi.getUtc_time()
				+ "\',\'"
				+ dpi.getSrc_ip()
				+ "\',"
				+ dpi.getSrc_port()
				+ ",\'"
				+ dpi.getDes_ip()
				+ "\',"
				+ dpi.getDes_port()
				+ ",\'"
				+ dpi.getUrl()
				+ "\',\'"
				+ dpi.getHost()
				+ "\',\'"
				+ dpi.getConnection()
				+ "\',\'"
				+ dpi.getAccept()
				+ "\',\'"
				+ dpi.getUser_agent()
				+ "\',\'"
				+ dpi.getReferer()
				+ "\',\'"
				+ dpi.getApt_encoding()
				+ "\',\'"
				+ dpi.getApt_language()
				+ "\',\'"
				+ dpi.getCookie()
				+ "\',\'"
				+ dpi.getIf_modified()
				+ "\',\'"
				+ dpi.getX_apple_c_type() + "\');";
		return sql;
	}

	public static void insert(String sql) {
		try {
			String url = "jdbc:mysql://localhost/dpiinfo";
			String user = "root";
			String pwd = "123";
			// 加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// 建立到MySQL的连接
			Connection conn = DriverManager.getConnection(url, user, pwd);
			// 执行SQL语句
			Statement stmt = conn.createStatement();// 创建语句对象，用以执行sql语言
			stmt.execute(sql);
			conn.close();
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}
	}
	
	public static void insert(DPIInfo dpi) {
		try {
			String url = "jdbc:mysql://localhost/dpiinfo";
			String user = "root";
			String pwd = "123";
			String sql = "insert into dpiinfo.dpiinfor(id,utctime,src_ip,src_port,des_ip,des_port,url,d_host,d_connection,accept,user_agent,referer,apt_encoding,apt_language,cookie,if_modified,x_apple_c_type) "
					+ "values (0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// 建立到MySQL的连接
			Connection conn = DriverManager.getConnection(url, user, pwd);
			// 执行SQL语句
			PreparedStatement ps = conn.prepareStatement(sql);
		    ps.setDate(1, new Date(dpi.getUtc_time()*1000));
		    ps.setString(2, dpi.getSrc_ip());
		    ps.setInt(3, dpi.getSrc_port());
		    ps.setString(4, dpi.getDes_ip());
		    ps.setInt(5, dpi.getDes_port());
		    ps.setString(6, dpi.getUrl());
		    ps.setString(7, dpi.getHost());
		    ps.setString(8, dpi.getConnection());
		    ps.setString(9, dpi.getAccept());
		    ps.setString(10, dpi.getUser_agent());
		    ps.setString(11, dpi.getReferer());
		    ps.setString(12, dpi.getApt_encoding());
		    ps.setString(13, dpi.getApt_language());
		    ps.setString(14, dpi.getCookie());
		    ps.setString(15, dpi.getIf_modified());
		    ps.setString(16, dpi.getX_apple_c_type());
		    
		    ps.executeUpdate();
		    
			conn.close();
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}
	}

	public static void main(String[] args) {
		try {
			String url = "jdbc:mysql://localhost/dpiinfo";
			String user = "root";
			String pwd = "123";
			// 加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// 建立到MySQL的连接
			Connection conn = DriverManager.getConnection(url, user, pwd);
			// 执行SQL语句
			Statement stmt = conn.createStatement();// 创建语句对象，用以执行sql语言
			ResultSet rs = stmt.executeQuery("select * from dpiinfor");
			// 处理结果集
			while (rs.next()) {
				String name = rs.getString("utctime");
				System.out.println(name);
			}
			rs.close();// 关闭数据库
			conn.close();
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}
	}
}
