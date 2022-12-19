package com.faith.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.faith.bean.User;
import com.faith.config.DatabaseConnection;

public class UserDaoImplementation implements IuserDaoService{
	static String SEARCH_USER = "SELECT * FROM users WHERE userName = ? AND passWord = ?";
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	
	public boolean checkCredentials(User user) throws Exception {
		//open connection
		connection = DatabaseConnection.getDataBaseConnection();
		//statement = connection.prepareStatement(SEARCH_USER);
		statement = connection.prepareCall("{call sp_loginuser(?,?)}");//calling stored procedure
		statement.setString(1, user.getUserName());
		statement.setString(2, user.getPassWord());
		
		//execute query
		resultSet = statement.executeQuery();
		if(resultSet.next()){
			String dbPass = resultSet.getString("passWord");
			if(dbPass.equals(user.getPassWord())){
				return true;
			}
		}else{
			DateFormat dform = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date logDate = new Date();
			FileWriter logFile = new FileWriter("logfile.txt",true);
			BufferedWriter bw = new BufferedWriter(logFile);
			bw.write("\n"+dform.format(logDate)+" Username : "+user.getUserName()+" Password : "+user.getPassWord());
			bw.close();
			System.out.println("\n\t\t\tUser not found...");
		}
		return false;
	}


	public int getRoleIdFrom(User user) throws Exception {
		int currentRoleId = 0;
		//open connection
		connection = DatabaseConnection.getDataBaseConnection();
		//statement
		statement = connection.prepareStatement(SEARCH_USER);
		statement.setString(1, user.getUserName());
		statement.setString(2, user.getPassWord());
		//execute query
		resultSet = statement.executeQuery();
		if(resultSet.next()){
			currentRoleId = resultSet.getInt("roleId");
			return currentRoleId;
		}else{
			System.out.println("\n\t\t\tUser not found...");
		}
		return currentRoleId;
	}

}
