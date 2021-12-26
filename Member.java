package com.sarvna;

class Member{

	private int memberId;
	private String name;
	private String aadharNumber;
	private String dateOfBirth;
	private int age;
	private String gender;
	
	Member(String name, String aadharNumber, String dateOfBirth, int age, String gender){
		
		this.name = name;
		this.aadharNumber = aadharNumber;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.gender = gender;
	}

	// Getters

	public int getMemberId(){
		return this.memberId;
	}
	public String getMemberName(){
		return this.name;
	}
	public String getAadharNumber(){
		return this.aadharNumber;
	}
	public String getDateOfBirth(){
		return this.dateOfBirth;
	}
	public int getAge(){
		return this.age;
	}
	public String getGender(){
		return this.gender;
	}

	// Setters

	public void setMemberId(int memberId){
		this.memberId = memberId;
	}
	public void setMemberName(String name){
		this.name = name;
	}
	public void setAadharNumber(String aadharNumber){
		this.aadharNumber = aadharNumber;
	}
	public void setDateOfBirth(String dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	public void setAge(int age){
		this.age = age;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
}