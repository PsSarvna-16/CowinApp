package com.sarvna;

class Member{
	
	private static int id =0;
	private int memberId;
	private String name;
	private String aadharNumber;
	private int age;
	private String dateOfBirth;
	private String gender;
	
	Member(String name,String aadharNumber,String dob,int age,String gender){
		this.name = name;
		this.aadharNumber = aadharNumber;
		this.dateOfBirth = dob;
		this.gender = gender;
		this.memberId = id++;
		this.age = age;
	}

	public String toString(){
		return String.format("%d         %-13s%-15s     %-12s  %-6s",memberId,name,aadharNumber,dateOfBirth,gender);
	}

	// Getters
	public String getName(){
		return this.name;
	}
	public String getAadhar(){
		return this.aadharNumber;
	}
	public String getDob(){
		return this.dateOfBirth;
	}
	public int getAge(){
		return this.age;
	}
	public String getGender(){
		return this.gender;
	}
}












		/*String s = "------------- Member - " + memberId + " ------------\n";
		s += " Name : " + name + "\n";
		s += " Aadhar Number : " + aadharNumber+ "\n";
		s += " Date of Birth : " + dateOfBirth+ "\n";
		s += " Gender : " + gender+ "\n";
		return s;*/