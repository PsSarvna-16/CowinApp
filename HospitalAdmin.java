package com.sarvna;

class HospitalAdmin extends User{

	private int hospId;

	HospitalAdmin(String userName, String password, String fullName, String contact, int hospId){
		super(userName, password, fullName, contact);
		this.hospId = hospId;
	}

	// Getters

	public int getHospId(){
		return this.hospId;
	}

	// Setters

	public void setHospId(int hospId){
		this.hospId = hospId;
	}
	
	public static void options(){
		System.out.println("\n------------- Hosp Admin --------------");
		System.out.println("\t1. Change Password.");
		System.out.println("\t2. Update Vaccine Slot.");
		System.out.println("\t3. Print Vaccine-Slot Appointments.");
		System.out.println("\t4. Logout");
		System.out.print("\nEnter your choice : ");
	}
}