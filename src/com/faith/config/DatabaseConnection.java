package com.faith.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

static Connection connection = null;
	
	private static Properties loadPropertiesFile() throws IOException {
		Properties prop = new Properties();
		//jdbc properties thread loaded
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
		return prop;
	}

	public static Connection getDataBaseConnection() throws Exception{
		try{
			//existing connection is here
			if(connection!=null){
				return connection;
			}
			//Accessing and loading properties file for new connection
			Properties newProp = loadPropertiesFile();
			String driveClass = newProp.getProperty("MYSQLJDBC.MYSQL_DRIVER");
			String url = newProp.getProperty("MYSQLJDBC.MYSQL_URL");
			String username = newProp.getProperty("MYSQLJDBC.USER");
			String password = newProp.getProperty("MYSQLJDBC.PASS");
			//String usessl = newProp.getProperty("MYSQLJDBC.USESSL");
			//establish connection
			try{
				//step1---Register JDBC driver
				Class.forName(driveClass);
				//step2--getConnection
				connection = DriverManager.getConnection(url+"?usessl",username,password);
				return connection;
			}catch(ClassNotFoundException e){
				throw new Exception("\t\t\tNo database..........");
			}
		}catch(SQLException err){
			throw new RuntimeException("\t\t\tError in connection + err");
	}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		//testing connection
		connection = DatabaseConnection.getDataBaseConnection();
		System.out.println("\t\t\tConnected to the database...");
	}

}
