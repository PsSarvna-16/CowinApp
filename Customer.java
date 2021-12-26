package com.sarvna;

import java.util.*;

class Customer extends User{

	private int customerId;
	private int dosesTaken = 0;
	private int membersCount = 0;

	Customer(String userName, String password, String fullName, String contact){
		
		super(userName, fullName, password, contact);
	}

	// Setters

	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	public void setDosesTaken(int dosesTaken){
		this.dosesTaken = dosesTaken;
	}
	public void setMembersCount(int membersCount){
		this.membersCount = membersCount;
	}

	// Getters

	public int getCustomerId(){
		return this.customerId;
	}
	public int getDosesTaken(){
		return this.dosesTaken;
	}
	public int getMembersCount(){
		return this.membersCount;
	}

	public static void options(){

		System.out.println("\n------------- Customer --------------");
		System.out.println("\t1. Add Member.");
		System.out.println("\t2. Change Password.");
		System.out.println("\t3. View Member Details.");
		System.out.println("\t4. Schedule Appointment.");
		System.out.println("\t5. View Vaccination Appointments.");
		System.out.println("\t6. Logout.");
		System.out.print("\nEnter your choice : ");
	}
}