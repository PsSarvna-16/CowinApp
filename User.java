package com.sarvna;

class User{

	private int userId;
	private String userName;
	private String password;
	private String fullName;
	private String contact;

	User(String userName, String password, String fullName, String contact){
		
		this.userName = userName;
		this.fullName = fullName;
		this.password = password;
		this.contact = contact;
	}

	// Getters

	public int getUserId(){
		return this.userId;
	}
	public String getUserName(){
		return this.userName;
	}
	public String getPassword(){
		return this.password;
	}
	public String getFullName(){
		return this.fullName;
	}
	public String getContact(){
		return this.contact;
	}

	// Setters
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setFullName(String fullName){
		this.fullName = fullName;
	}
	public void setContact(String contact){
		this.contact = contact;
	}
}