package com.sarvna;

class HospitalAdmin extends User{

	private int hospitalId;
	private Hospital myHospital;

	HospitalAdmin(String userName,String fullName, String password,String contact,int hospitalId){
		super(userName, fullName, password, contact);
		this.hospitalId = hospitalId;
	}

	public int getHospitalId(){
		return this.hospitalId;
	}
	public void setHospitalId(int hospitalId){
		this.hospitalId = hospitalId;
	}
	
	public static void options(){
		System.out.println("------------- Hosp Admin --------------");
		System.out.println("\t1. Change Password.");
		System.out.println("\t2. Update Vaccine Slot.");
		System.out.println("\t3. Print Vaccine-Slot Appointments.");
		System.out.println("\t4. Logout");
		System.out.print("\nEnter your choice : ");
	}
}
