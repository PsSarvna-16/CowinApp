package com.sarvna;

class Admin extends User{

	Admin(String userName, String password, String fullName, String contact){
		super(userName, password, fullName, contact);
	}

	public static void options(){
		
		System.out.println("\n------------- Admin --------------");
		System.out.println("\t1. Add Hospital.");
		System.out.println("\t2. Add Vaccine.");
		System.out.println("\t3. Add Hospital Admin");
		System.out.println("\t4. Change Password.");
		System.out.println("\t5. Logout.");
		System.out.print("\nEnter your choice : ");
	}
}