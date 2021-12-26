package com.sarvna;

import java.util.*;

class Hospital{
	
	private int hospId;
	private String hospName;
	private String pinCode;
	private String location;
	private String contact;

	Hospital(String hospName, String pinCode, String location, String contact){
		
		this.hospName = hospName;
		this.pinCode = pinCode;
		this.location = location;
		this.contact = contact;
	}

	// Getters
	public int getHospId(){
		return this.hospId;
	}
	public String getPincode(){
		return this.pinCode;
	}
	public String getHospName(){
		return this.hospName;
	}
	public String getLocation(){
		return this.location;
	}
	public String getContact(){
		return this.contact;
	}

	// Setters
	public void setHospId(int hospId){
		this.hospId = hospId;
	}	
	public void setPincode(String pinCode){
		this.pinCode = pinCode;
	}	
	public void setHospNAme(String hospName){
		this.hospName = hospName;
	}	
	public void setLocation(String location){
		this.location = location;
	}	
	public void setContact(String contact){
		this.contact = contact;
	}
}