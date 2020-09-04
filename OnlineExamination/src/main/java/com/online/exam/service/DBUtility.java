package com.online.exam.service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtility {

	private static Connection conn ;
	private static Properties prop = new Properties();  
	
	static {
		loadSystemProperties();
	}
	
	private static void loadSystemProperties() {
		
		 FileReader reader;
		try {
			reader = new FileReader("instance.properties");
			  prop.load(reader);  
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	private static void initializeConnection() {
		try {
			String dbProvider = prop.getProperty("db.provider");
			String url = prop.getProperty("db.url");
			String username = prop.getProperty("db.username");
			String pwd = prop.getProperty("db.password");
			if(dbProvider.equals("MySQL")) {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Initializing DB connection to URL - "+url);
				conn = DriverManager.getConnection(  url,username,pwd);  
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void executeInsert(Connection conn, String sql) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static Connection getConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				initializeConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
