package com.faith.bean;

import java.util.Scanner;

public class User {
	//instance variables
	private int userId;
	private String userName;
	private String passWord;
	private int roleId;
	private Scanner input = new Scanner(System.in);
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String userName, String passWord, int roleId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if(userName.matches("^[A-Za-z][A-Za-z0-9_]{5,15}$"))
			this.userName = userName;
		else {
			System.out.println("\n\t\t\tUsername should be 6 characters long and should not begin with numbers..."
					+"\n\n\t\t\tEnter a valid username : ");
		  	setUserName(input.nextLine());
		}
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		if(passWord.length()>=6)
			this.passWord = passWord;
		else {
			System.out.println("\n\t\t\tPassword should be 6 characters long"
					+ "\n\n\t\t\tEnter a valid password : ");
			setPassWord(input.nextLine());
		}
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", passWord=" + passWord + ", roleId=" + roleId + "]";
	}
	
	
}