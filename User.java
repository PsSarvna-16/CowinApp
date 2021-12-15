package com.sarvna;

class User{

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

	public boolean validatePassword(String password){
		return this.password.equals(password);
	}

	public boolean changePassword(String oldPwd, String newPwd){
		if(this.password.equals(oldPwd)){
			this.password = newPwd;
			return true;
		}
		return false;
	}
}