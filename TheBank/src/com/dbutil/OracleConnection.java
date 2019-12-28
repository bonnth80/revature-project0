package com.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {
	@SuppressWarnings("unused")
	private static Connection connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin@localhost:1521:xe";
		String username = "bonnth";
		String password = "clandestine";
		return connection=DriverManager.getConnection(url,username,password);
	}
}
