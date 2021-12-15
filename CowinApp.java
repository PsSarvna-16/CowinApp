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

			Admin admin = new Admin("admin","CowinAdmin","admin","809891XX36");
			customers.put("Sarvna",new Customer("Sarvna","Saravana Kumar","user","809891XX36"));
			Hospital newHospital = new Hospital("Apollo","625022");
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
									cusObj.printMembers();
									break;
								}
								case 4:
								{
									scheduleAppointment(hospitals,cusObj,appointments);
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
									addHospital(hospitals, hospAdmin);
									break;
								}
								case 2:
								{
									System.out.print("Enter Vaccine Name : ");
									String vaccineName = sc.nextLine();
									vaccines.add(vaccineName);
									System.out.println("\n** " + vaccineName + " Added Successfully.\n");
									break;
								}
								case 3:
								{
									changePassword(admin);
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
									changePassword(hospAdminObj);
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
		if(db.addCustomer(userName,password,fullName,contact)){
			System.out.println("\n** " + userName + " Added Successfully.");
		}
	}

	public static void addHospital(HashMap<Integer,Hospital> hospitals,HashMap<String,HospitalAdmin> hospAdmin){

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter Hospital Name : ");
		String hospName = sc.nextLine();
		System.out.print("Enter Hospital Pincode : ");
		String pincode = sc.nextLine();
		Hospital newHospital = new Hospital(hospName,pincode);
		hospitals.put(newHospital.getHospitalId(),newHospital);

		System.out.print("Enter Hospital Admin userName : ");
		String hospAdminName = sc.nextLine();
		System.out.print("Enter Hospital Admin Password  : ");
		String hospAdminPwd = sc.nextLine();
		HospitalAdmin newHospAdmin =  new HospitalAdmin(hospAdminName,hospAdminName,hospAdminPwd,"809891XX36",newHospital);
		hospAdmin.put(hospAdminName,newHospAdmin);
		
		System.out.println("** Hospital Added Successfully.");
	}

	public static void changePassword(User obj){
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter Old Password : ");
		String oldPwd = sc.nextLine();
		System.out.print("Enter New Password : ");
		String newPwd = sc.nextLine();

		if(obj.changePassword(oldPwd,newPwd)){
			System.out.println("** Password Changed Successfully");
		}else{
			System.out.println("!! Invalid password");
		}
	}

	// Customer

	public static void registerMember(Customer obj){
		Scanner sc = new Scanner(System.in);

		System.out.print("\nEnter Member name : ");
		String name = sc.nextLine();
		System.out.print("Enter Aadhar Number : ");
		String aadhar = sc.nextLine();
		System.out.print("Enter D.O.B : ");
		String dob = sc.nextLine();
		System.out.print("Enter Gender : ");
		String gender = sc.nextLine();

		if(obj.registerMember(name,aadhar,dob,gender)){
			System.out.println("** Member Registered Succesfully.");
		}else{
			System.out.println("!! Member Registration Failed.");
		}
	}


	public static void scheduleAppointment(HashMap<Integer,Hospital> hospitals,Customer cusObj,HashMap<Integer,Appointment> appointments){
		
		Scanner sc = new Scanner(System.in);

		System.out.print("\n\nEnter pincode : ");
		String pincode = sc.nextLine();

		System.out.println("hospitalId        hospitalName        pinCode");
		Iterator itr = hospitals.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry<Integer,Hospital> map = (Map.Entry<Integer,Hospital>)itr.next();
			Hospital hosp  = (Hospital)map.getValue();
			if(hosp.getPinCode().equals(pincode)){
				System.out.println(hosp);
			}
		}

		System.out.print("\nEnter HospitalId to view Vaccine slots : ");
		int hospitalId = sc.nextInt();
		sc.nextLine();

		Hospital hosp = hospitals.get(hospitalId);
		hosp.showVaccineSlots();

		System.out.print("\n\nEnter Vaccine-Date-Slot : ");
		String vaccinedateslot = sc.nextLine();

		cusObj.printMembers();

		System.out.print("\n\nEnter Member Id to book slot : ");
		int memberId = sc.nextInt();

		Member member = cusObj.getMember(memberId);

		Appointment newAppoint = hosp.bookAppointment(member,vaccinedateslot);
		int appointmentId = newAppoint.getAppointmentId();
		cusObj.addAppointment(appointmentId);
		appointments.put(appointmentId, newAppoint);
		System.out.println("\n** Appointment booked Succesfully.");
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