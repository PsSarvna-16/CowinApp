package com.sarvna;

import java.util.*;

class Customer extends User{

	private static int id =0;
	private int customerId;
	private int dosesTaken;
	private int membersCount;
	ArrayList<Member> members;
	ArrayList<Integer> appointmentIds;
	ArrayList<Integer> certificateIds;

	Customer(String userName,String fullName, String password,String contact){
			super(userName, fullName, password, contact);
			this.customerId = id++;
			this.membersCount = 0;
			this.members = new ArrayList<Member>(4);
			this.certificateIds = new ArrayList<Integer>();
			this.appointmentIds = new ArrayList<Integer>();
	}

	public void addAppointment(int appointmentId){
		appointmentIds.add(appointmentId);
	}

	public ArrayList<Integer> getAppointmentIds(){
		return this.appointmentIds;
	}

	public Member getMember(int memberId){
		return members.get(memberId);
	}

	public Member registerMember(String name,String aadharNumber,String dob,int age,String gender){
		if(membersCount < 4){
			Member newMemb = new Member(name,aadharNumber,dob,age,gender);
			members.add(newMemb);
			membersCount++;
			return newMemb;
		}else{
			System.out.println("One Customer can add Maximum of 4-Members only");
			return null;
		}
	}

	public void printMembers(){
		Iterator itr = members.iterator();
		System.out.println("\n\n------------------------ Members -------------------------------\n");
		System.out.println("memberId  Name         AadharNumber        dateOfBirth  Gender");
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
	}

	public static void options(){
		System.out.println("------------- Customer --------------");
		System.out.println("\t1. Add Member.");
		System.out.println("\t2. Change Password.");
		System.out.println("\t3. View Member Details.");
		System.out.println("\t4. Schedule Appointment.");
		System.out.println("\t5. View Vaccination Appointments.");
		System.out.println("\t6. Logout.");
		System.out.print("\nEnter your choice : ");
	}
}