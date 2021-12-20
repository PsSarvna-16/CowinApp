package com.sarvna;

import java.util.*;


class Hospital{
	
	private int hospitalId;
	private String hospitalName;
	private String pinCode;
	private String location;
	private String contact;
	// key - combination of vaccine-date-slot
	HashMap<String,Vaccine> vaccines;
	HashMap<String,ArrayList<Integer>> appointmentIds;

	Hospital(String hospitalName,String pinCode,String location, String contact){
		this.hospitalName = hospitalName;
		this.pinCode = pinCode;
		this.location = location;
		this.contact = contact;
		this.vaccines = new HashMap<String,Vaccine>();
		this.appointmentIds = new HashMap<String,ArrayList<Integer>>();
	}

	// Getters
	public int getHospitalId(){
		return this.hospitalId;
	}

	public String getPincode(){
		return this.pinCode;
	}
	public String getName(){
		return this.hospitalName;
	}
	public String getLocation(){
		return this.location;
	}
	public String getContact(){
		return this.contact;
	}

	// Setters
	public void setHospId(int hospId){
		this.hospitalId = hospId;
	}
	



	public ArrayList<Integer> getAppointmentIds(String vaccineSlot){
		return this.appointmentIds.get(vaccineSlot);
	}
	public HashMap<String,ArrayList<Integer>> getAppointmentIds(){
		return this.appointmentIds;
	}

	public void showVaccineSlots(){
		System.out.println("Vaccine    Date     Slot     dosesAvailable  Price");
		Iterator itr = vaccines.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry<String,Vaccine> map = (Map.Entry<String,Vaccine>)itr.next();
			((Vaccine)map.getValue()).printDetails();
		}
	}

	public Appointment bookAppointment(Member member,String vaccineSlot){
		Vaccine vaccine = (Vaccine)vaccines.get(vaccineSlot);
		vaccine.reduceCount();
		Appointment appoint = new Appointment(vaccine,member);
		ArrayList<Integer> list = appointmentIds.get(vaccineSlot);
		if(list == null){
			list = new ArrayList<Integer>();
		}
		list.add(appoint.getAppointmentId());
		appointmentIds.put(vaccineSlot,list);
		return appoint;	
	}

	public void updateVaccineSlot(String vaccineName,String date,String slot,int doses,double price){
		String key = vaccineName + "-" + date +"-"+ slot;
		vaccines.put(key,new Vaccine(vaccineName,date,slot,doses,price));
	}

	public String toString(){
		return hospitalId + "    " + hospitalName + "    " + pinCode;
	}
}