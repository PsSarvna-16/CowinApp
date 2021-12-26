package com.sarvna;

class User{

	private int userId;
	private String userName;
	private String password;
	private String fullName;
	private String contact;

	User(String userName,String fullName, String password,String contact){
		
		this.userName = userName;
		this.fullName = fullName;
		this.password = password;
		this.contact = contact;
	}

	// Getters
	public String getPassword(){
		return this.password;
	}

	public String getUserName(){
		return this.userName;
	}

	public int getUserId(){
		return this.userId;
	}

	public String getFullName(){
		return this.fullName;
	}
	public String getContact(){
		return this.contact;
	}

	// Setters
	public void setPassword(String password){
		this.password = password;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}
}