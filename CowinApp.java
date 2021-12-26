package com.sarvna;

import java.util.*;
import java.sql.*;

class CowinApp{

	private CowinDB db = null;
	private Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws SQLException{
		
		Scanner sc = new Scanner(System.in);
		String username = "root";
		String password = "PsSarvna";
		String database = "cowin";
			
		CowinApp ca = new CowinApp();
		ca.db = new CowinDB(username, password, database);

		try{
			
			if(! ca.db.makeConnection()){
				System.out.println("Database Connection not established !!!");
				return;
			}

			while(true){
				ca.options();
				int loginChoice = sc.nextInt();
				sc.nextLine();

				switch(loginChoice){
					case 1:
					{
						System.out.print("\t Enter username : ");
						String customerName = sc.nextLine();

						System.out.print("\t Enter password : ");
						int[] user = ca.db.validatePassword(customerName , sc.nextLine());
						if((user!=null) && user[0] >= 1){
							ca.userLogin(user);
						}else{
							break;
						}
						break;
					} 
					case 2:
					{
						ca.addCustomer();
						break;
					}
					case 3:
					{
						System.out.println("Thanks.");
						return;
					}
					default:
					{
						System.out.println("Enter Valid Choice.");
						break;
					}
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}finally{
			ca.db.closeConnection();
		}
	}

	public void options(){
		System.out.println("------------- Login Options --------------");
		System.out.println("\t1. Existing Customer (Login).");
		System.out.println("\t2. New Customer (SignUp).");
		System.out.println("\t3. Exit.");
		System.out.print("\nEnter your choice : ");
	}

	public void userLogin(int[] user) throws SQLException{
		switch(user[1]){
			case 1:
			{
				Customer cusObj = db.getCustomer(user[0]);
				boolean repeat = true;
				while(repeat){
					Customer.options();
					int customerChoice = sc.nextInt();
					sc.nextLine();
					switch(customerChoice){
						case 1:
						{
							registerMember(cusObj);
							break;
						}
						case 2:
						{
							changePassword(cusObj);
							break;
						}
						case 3:
						{
							db.printMembers(cusObj.getUserId());
							break;
						}
						case 4:
						{
							scheduleAppointment(cusObj);
							break;
						}
						case 5:
						{
							db.printCustAppointments(cusObj.getUserId());
							break;
						}
						case 6:
						{
							repeat = false;
							break;						
						}
					}
				}
				break;
			}
			case 2:
			{
				Admin admin = db.getCowinAdmin(user[0]);
				boolean repeat = true;
				while(repeat){
					Admin.options();
					int adminChoice = sc.nextInt();
					sc.nextLine();
					switch(adminChoice){
						case 1:
						{
							addHospital();
							break;
						}
						case 2:
						{
							addVaccine();
							break;
						}
						case 3:
						{
							addHospAdmin();
							break;
						}
						case 4:
						{
							changePassword(admin);
							break;
						}
						case 5:
						{
							repeat = false;
							return;		
						}
					}
				}
				break;
			} 
			case 3:
			{
				HospitalAdmin hospAdminObj = db.getHospAdmin(user[0]);
				boolean repeat = true;
				while(repeat){
					HospitalAdmin.options();
					int hospAdminChoice = sc.nextInt();
					sc.nextLine();
					switch(hospAdminChoice){
						case 1:
						{
							changePassword(hospAdminObj);
							break;
						}
						case 2:
						{
							updateVaccineSlot(hospAdminObj);
							break;
						}
						case 3:
						{
							db.printVaccineSlots(hospAdminObj.getHospId());
							System.out.print("\nEnter Vaccine slot ID : ");
							int vaccSlotId = sc.nextInt();
							sc.nextLine();
							db.printVaccAppointments(vaccSlotId);
							break;
						}
						case 4:
						{
							repeat = false;
							return;		
						}
					}
				}
				break;
			}
		}
	}

	// User Methods

	public void changePassword(User obj) throws SQLException{

		System.out.print("Enter Old Password : ");
		String oldPwd = sc.nextLine();
		System.out.print("Enter New Password : ");
		String newPwd = sc.nextLine();

		if(db.changePassword(obj.getUserName(), oldPwd, newPwd)){
			obj.setPassword(newPwd);
			System.out.println("** Password Changed Successfully");
		}else{
			System.out.println("** failed to update password.");
		}
	}
	
	public void addCustomer() throws SQLException{

		System.out.print("Enter New userName : ");
		String userName = sc.nextLine();
		System.out.print("Enter New Password : ");
		String password = sc.nextLine();
		System.out.print("Enter Full Name :  ");
		String fullName = sc.nextLine();
		System.out.print("Enter Contact number : ");
		String contact = sc.nextLine();

		Customer newCust = new Customer(userName,fullName,password,contact);
		if(db.addCustomer(newCust)){
			System.out.println("\n** " + userName + " Added Successfully.");
		}
	}

	// Customer Methods

	public void registerMember(Customer obj) throws SQLException{

		System.out.print("\nEnter Member name : ");
		String name = sc.nextLine();
		System.out.print("Enter Aadhar Number : ");
		String aadhar = sc.nextLine();
		System.out.print("Enter D.O.B (YYYY-MM-DD): ");
		String dob = sc.nextLine();
		System.out.print("Enter age : ");
		int age = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter Gender (M,F) : ");
		String gender = sc.nextLine();

		Member newMemb = new Member(name,aadhar,dob,age,gender);

		if( (newMemb != null) && db.addMember(obj.getUserName(),newMemb)){
			System.out.println("** Member Registered Succesfully.");
		}else{
			System.out.println("!! Member Registration Failed.");
		}
	}

	public void scheduleAppointment(Customer cusObj) throws SQLException{

		System.out.print("\n\nEnter pincode : ");
		String pincode = sc.nextLine();

		db.printHospitals(pincode);

		System.out.print("\nEnter HospitalId to view Vaccine slots : ");
		int hospId = sc.nextInt();
		sc.nextLine();

		db.printVaccineSlots(hospId);

		System.out.print("\n\nEnter Vaccine-Date-Slot : ");
		int vaccSlotId = sc.nextInt();
		sc.nextLine();

		db.printMembers(cusObj.getUserId());

		System.out.print("\n\nEnter Member Id to book slot : ");
		int memberId = sc.nextInt();
		sc.nextLine();

		Appointment newAppoint = new Appointment(memberId,vaccSlotId,hospId,"NOT VACCINATED");
		
		if(db.addAppointment(newAppoint)){
			System.out.println("\n** Appointment booked Succesfully.");
		}else{
			System.out.println("\n** Appointment booking Failed.");
		}
	}
	
	// Cowin Admin Methods

	public void addHospital() throws SQLException{

		System.out.print("Enter Hospital Name : ");
		String hospName = sc.nextLine();
		System.out.print("Enter Hospital Pincode : ");
		String pincode = sc.nextLine();
		System.out.print("Enter Hospital Location : ");
		String location = sc.nextLine();
		System.out.print("Enter Hospital Contact: ");
		String contact = sc.nextLine();
		Hospital newHospital = new Hospital(hospName,pincode,location,contact);
		if(db.addHospital(newHospital)){
			System.out.println("** Hospital Added Successfully.");
		}else{
			System.out.println("** failed to add hospital.");
		}
		return;
	}

	public void addHospAdmin() throws SQLException{

		db.printHospitals();

		System.out.print("Enter Hospital Id : ");
		int hospId = sc.nextInt();
		sc.nextLine();

		System.out.print("Enter Hospital Admin userName : ");
		String username = sc.nextLine();
		System.out.print("Enter Hospital Admin Password  : ");
		String password = sc.nextLine();
		System.out.print("Enter Hospital Admin fullName  : ");
		String fullName = sc.nextLine();
		System.out.print("Enter Hospital Admin contact  : ");
		String contact = sc.nextLine();
		
		HospitalAdmin newHospAdmin =  new HospitalAdmin(username,password,fullName,contact,hospId);
		
		if(db.addHospAdmin(newHospAdmin)){
			System.out.println("** Hospital Admin Added Successfully.");
		}else{
			System.out.println("** failed to add hospital admin.");
		}
	}

	public void addVaccine() throws SQLException{

		System.out.print("Enter Vaccine Name : ");
		String vaccName = sc.nextLine();
		System.out.print("Enter Side Effects : ");
		String sideEffects = sc.nextLine();
		System.out.print("Enter Price  : ");
		double price = sc.nextDouble();
		sc.nextLine();
		
		Vaccine newVacc = new Vaccine(vaccName,sideEffects,price);

		if(db.addVaccine(newVacc)){
			System.out.println("** Vaccine Added Successfully.");
		}else{
			System.out.println("** failed to add Vaccine.");
		}

	}	

	// Hospital Admin Methods

	public void updateVaccineSlot(HospitalAdmin hospAdmin) throws SQLException{

		db.printVaccines();

		System.out.print("Enter Vaccine Id : ");
		int vacc_id = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter Date (YYYY-MM-DD): ");
		String date = sc.nextLine();
		System.out.print("Enter Slot  : ");
		String slot = sc.nextLine();
		System.out.print("Enter no of Dose  : ");
		int noOfDose = sc.nextInt();
		sc.nextLine();
		int hospId = hospAdmin.getHospId();

		VaccineSlot vacc_slot = new VaccineSlot(vacc_id, hospId,date, slot, noOfDose);
		if(db.addVaccineSlot(vacc_slot)){
			System.out.println("** Vaccine Slot Added Successfully.");
		}else{
			System.out.println("** Failed to add Vaccine Slot.");
		}
	}
}