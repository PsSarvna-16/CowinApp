package com.sarvna;

import java.util.*;
import java.sql.*;

class CowinApp{

	public static void main(String[] args) throws SQLException{
			
		Scanner sc = new Scanner(System.in);

		String username = "root";
		String password = "PsSarvna";
		String database = "cowin";
			
		CowinDB db = new CowinDB(username, password, database);

		try{
			
			if(! db.makeConnection()){
				System.out.println("Database Connection not established !!!");
				return;
			}

			HashMap<String,Customer> customers = new HashMap<String,Customer>();
			HashMap<Integer,Hospital> hospitals = new HashMap<Integer,Hospital>();
			HashMap<Integer,Appointment> appointments = new HashMap<Integer,Appointment>();
			HashMap<String,HospitalAdmin> hospAdmin = new HashMap<String,HospitalAdmin>();
			ArrayList<String> vaccines = new ArrayList<String>();
	/*
			HardCoded Values
			Admin:
				password  - admin
			Customer:
				customerName - Sarvna
				password  - user 
			HospitalAdmin:
				adminName - hospadmin
				password - admin

			hospital:
				Apollo , 625022
			slots:
				"Covaxin","01/01/21","Morning",25,252.0
				"Covishield","01/01/21","Evening",25,360.0
			Vaccines:
				Covaxin,CoviShield,SputnikV
	*/

			Admin admin = new Admin("cowinadmin","CowinAdmin","admin","809891XX36");
			customers.put("Sarvna",new Customer("Sarvna","Saravana Kumar","user","809891XX36"));
			Hospital newHospital = new Hospital("Apollo","625022","Madurai","044-859687");
			newHospital.updateVaccineSlot("Covaxin","01/01/21","Morning",25,252.0);
			newHospital.updateVaccineSlot("Covishield","01/01/21","Evening",25,360.0);
			hospitals.put(newHospital.getHospitalId(),newHospital);
			hospAdmin.put("hospadmin",new HospitalAdmin("hospadmin","HospAdmin","admin","8098919936",newHospital));
			vaccines.add("Covaxin");
			vaccines.add("CoviShield");
			vaccines.add("SputnikV");
			
			boolean repeat = true;
			while(true){
				options();
				int loginChoice = sc.nextInt();
				sc.nextLine();
				switch(loginChoice){
					case 1:
					{	
						System.out.print("\t Enter username : ");
						String customerName = sc.nextLine();
						Customer cusObj = null;
						if(customers.containsKey(customerName)){
							cusObj = customers.get(customerName);
						}

						System.out.print("\t Enter password : ");
						if(! db.validatePassword("customer",customerName , sc.nextLine())){
							break;
						}
						repeat = true;
						while(repeat){
							Customer.options();
							int customerChoice = sc.nextInt();
							switch(customerChoice){
								case 1:
								{
									registerMember(cusObj,db);
									break;
								}
								case 2:
								{
									changePassword(cusObj,db);
									break;
								}
								case 3:
								{
									db.printMembers(cusObj.getUserId());
									break;
								}
								case 4:
								{
									scheduleAppointment(hospitals,cusObj,appointments,db);
									break;
								}
								case 5:
								{
									printAppointments(appointments,cusObj.getAppointmentIds());
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
						System.out.print("\t Enter username : ");
						String adminName = sc.nextLine();
						System.out.print("\t Enter password : ");
						if( ! db.validatePassword("cowinAdmin", adminName, sc.nextLine())){
							break;
						}
						repeat = true;
						while(repeat){
							Admin.options();
							int adminChoice = sc.nextInt();
							sc.nextLine();
							switch(adminChoice){
								case 1:
								{
									addHospital(hospitals, db);
									break;
								}
								case 2:
								{
									addHospitalAdmin(hospitals,hospAdmin,db);
									break;
								}
								case 3:
								{
									System.out.print("Enter Vaccine Name : ");
									String vaccineName = sc.nextLine();
									vaccines.add(vaccineName);
									System.out.println("\n** " + vaccineName + " Added Successfully.\n");
									break;
								}
								case 4:
								{
									changePassword(admin,db);
									break;
								}
								case 5:
								{
									repeat = false;
									break;
								}
							}
						}
						break;
					}
					case 3:
					{
						System.out.print("\t Enter username : ");
						String hospAdminName = sc.nextLine();
						HospitalAdmin hospAdminObj = null;
						if(hospAdmin.containsKey(hospAdminName)){
							hospAdminObj = hospAdmin.get(hospAdminName);
						}
						System.out.print("\t Enter password : ");
						if(! db.validatePassword("hospAdmin", hospAdminName,sc.nextLine())){
							break;
						}
						Hospital adminHospital = hospAdminObj.getHospital();
						repeat = true;
						while(repeat){
							HospitalAdmin.options();
							int hospAdminChoice = sc.nextInt();
							switch(hospAdminChoice){
								case 1:
								{
									changePassword(hospAdminObj,db);
									break;
								}
								case 2:
								{
									updateVaccine(adminHospital,vaccines);
									break;
								}
								case 3:
								{
									printVaccineSlotAppointments(appointments,adminHospital);
									break;
								}
								case 4:
								{
									repeat = false;
									break;
								}
							}
						}
						break;
					}
					case 4:
					{
						addCustomer(customers, db);
						break;
					}
					case 5:
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
			db.closeConnection();
		}
	}

	// methods

	public static void options(){
		System.out.println("------------- Login Options --------------");
		System.out.println("\t1. Customer.");
		System.out.println("\t2. Cowin Admin.");
		System.out.println("\t3. Hospital Admin.");
		System.out.println("\t4. New Customer (SignUp).");
		System.out.println("\t5. Exit.");
		System.out.print("\nEnter your choice : ");
	}

	public static void addCustomer(HashMap<String,Customer> customers, CowinDB db) throws SQLException{
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter New userName : ");
		String userName = sc.nextLine();
		System.out.print("Enter New Password : ");
		String password = sc.nextLine();
		System.out.print("Enter Full Name :  ");
		String fullName = sc.nextLine();
		System.out.print("Enter Contact number : ");
		String contact = sc.nextLine();
		if(customers.containsKey(userName)){
			System.out.println("\n !! Username already Exist.");
			return;
		}
		Customer newCust = new Customer(userName,fullName,password,contact);
		customers.put(userName, newCust);
		if(db.addCustomer(newCust)){
			System.out.println("\n** " + userName + " Added Successfully.");
		}
	}

	public static void addHospital(HashMap<Integer,Hospital> hospitals,CowinDB db) throws SQLException{

		Scanner sc = new Scanner(System.in);

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
			hospitals.put(newHospital.getHospitalId(),newHospital);
			System.out.println("** Hospital Added Successfully.");
		}else{
			System.out.println("** failed to add hospital.");
		}
		return;
	}

	public static void addHospitalAdmin(HashMap<Integer,Hospital> hospitals,HashMap<String,HospitalAdmin> hospAdmin,CowinDB db) throws SQLException{

		Scanner sc = new Scanner(System.in);

		Hospital newHospital = null;

		Iterator itr = hospitals.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry<Integer,Hospital> map = (Map.Entry<Integer,Hospital>)itr.next();
			Hospital hosp  = (Hospital)map.getValue();
			System.out.println(hosp.getHospitalId() + ". " + hosp.getName());
		}

		System.out.print("Enter Hospital Id : ");
		int hospID = sc.nextInt();
		sc.nextLine();
		
		if(hospitals.containsKey(hospID)){
			newHospital = hospitals.get(hospID);
		}else{
			System.out.println("Invalid Hospital Name !!!");
			return;
		}

		System.out.print("Enter Hospital Admin userName : ");
		String username = sc.nextLine();
		System.out.print("Enter Hospital Admin Password  : ");
		String password = sc.nextLine();
		System.out.print("Enter Hospital Admin fullName  : ");
		String fullName = sc.nextLine();
		System.out.print("Enter Hospital Admin contact  : ");
		String contact = sc.nextLine();
		
		HospitalAdmin newHospAdmin =  new HospitalAdmin(username,fullName,password,contact,newHospital);
		
		if(db.addHospitalAdmin(newHospAdmin)){
			hospAdmin.put(username,newHospAdmin);
			System.out.println("** Hospital Admin Added Successfully.");
		}else{
			System.out.println("** failed to add hospital admin.");
		}
	}

	public static void changePassword(User obj, CowinDB db) throws SQLException{
		
		Scanner sc = new Scanner(System.in);

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

	// Customer

	public static void registerMember(Customer obj,CowinDB db) throws SQLException{
		Scanner sc = new Scanner(System.in);

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

		Member newMemb = obj.registerMember(name,aadhar,dob,age,gender);

		if( (newMemb != null) && db.addMember(obj.getUserName(),newMemb)){
			System.out.println("** Member Registered Succesfully.");
		}else{
			System.out.println("!! Member Registration Failed.");
		}
	}

	public static void scheduleAppointment(HashMap<Integer,Hospital> hospitals,Customer cusObj,HashMap<Integer,Appointment> appointments,CowinDB db) throws SQLException{
		
		Scanner sc = new Scanner(System.in);

		System.out.print("\n\nEnter pincode : ");
		String pincode = sc.nextLine();

		db.printHospitals(pincode);

		System.out.print("\nEnter HospitalId to view Vaccine slots : ");
		int hospId = sc.nextInt();
		sc.nextLine();

		db.printVaccineSlots(hospId);

		System.out.print("\n\nEnter Vaccine-Date-Slot : ");
		int vacc_slot_id = sc.nextInt();
		sc.nextLine();

		db.printMembers(cusObj.getUserId());

		System.out.print("\n\nEnter Member Id to book slot : ");
		int memberId = sc.nextInt();
		sc.nextLine();

		Appointment newAppoint = new Appointment(memberId,vacc_slot_id,hospId,"NOT VACCINATED");
		
		if(db.addAppointment(newAppoint)){
			System.out.println("\n** Appointment booked Succesfully.");
		}else{
			System.out.println("\n** Appointment booking Failed.");
		}

		
	}

	public static void printAppointments(HashMap<Integer,Appointment> appointments,ArrayList<Integer> appointmentIds){
		try{
			Iterator itr = appointmentIds.iterator();
			while(itr.hasNext()){
				System.out.println(appointments.get(itr.next()));
			}
		}catch(NullPointerException e){
			return;
		}
	}

	// Hospital Admin
	public static void printVaccineSlotAppointments(HashMap<Integer,Appointment> appointments,Hospital hosp){
		Scanner sc = new Scanner(System.in);
		HashMap<String,ArrayList<Integer>> appointmentIds = hosp.getAppointmentIds();
		
		Iterator itr = appointmentIds.keySet().iterator();
		while(itr.hasNext()){
				System.out.println(itr.next());
		}
		System.out.print("\n\nEnter Vaccine-Date-Slot : ");
		String vaccinedateslot = sc.nextLine();

		printAppointments(appointments,appointmentIds.get(vaccinedateslot));
	}

	public static void updateVaccine(Hospital hosp,ArrayList vaccines){
		Scanner sc = new Scanner(System.in);


		Iterator itr = vaccines.iterator();
		while(itr.hasNext()){
				System.out.println(itr.next());
		}

		System.out.print("\n\nEnter Vaccine name : ");
		String vaccineName = sc.nextLine();
		if(! vaccines.contains(vaccineName)){
			System.out.println("Invalid Vaccination name");
			return;
		}
		System.out.print("Enter date : ");
		String date = sc.nextLine();
		System.out.print("Enter slot : ");
		String slot = sc.nextLine();
		System.out.print("Enter no of doses : ");
		int doses = sc.nextInt();
		System.out.print("Enter price : ");
		double price = sc.nextDouble();

		hosp.updateVaccineSlot(vaccineName,date,slot,doses,price);
		System.out.println("** Vaccine Slot Added Successfully");
	}
}