package com.sarvna;

class HospitalAdmin extends User{
	private static int id =0;
	private int hospAdminId;
	private Hospital myHospital;

	HospitalAdmin(String userName,String fullName, String password,String contact,Hospital myHospital){
		super(userName, fullName, password, contact);
		this.hospAdminId = id++;
		this.myHospital = myHospital;
	}

	public Hospital getHospital(){
		return this.myHospital;
	}
	
	public static void options(){
		System.out.println("------------- Hosp Admin --------------");
		System.out.println("\t1. Change Password.");
		System.out.println("\t2. Update Vaccine Count.");
		System.out.println("\t3. Print Vaccine-Slot Appointments.");
		System.out.println("\t4. Logout");
		System.out.print("\nEnter your choice : ");
	}
}
