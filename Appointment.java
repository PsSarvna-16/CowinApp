package com.sarvna;

class Appointment{

	private int appointmentId;
	private int memberId;
	private int vaccSlotId;
	private int hospId;
	private int vaccinatedBy;
	private String status;

	Appointment(int memberId,int vaccSlotId,int hospId,String status){
		
		this.memberId = memberId;
		this.vaccSlotId = vaccSlotId;
		this.hospId = hospId;
		this.status = status;
	}

	// Getters

	public int getAppointmentId(){
		return this.appointmentId;
	}
	public int getMemberId(){
		return this.memberId;
	}
	public int getVaccSlotId(){
		return this.vaccSlotId;
	}
	public int getHospId(){
		return this.hospId;
	}
	public int getVaccinatedBy(){
		return this.vaccinatedBy;
	}
	public String getStatus(){
		return this.status;
	}

	// Setters

	public void setAppointmentId(int appointmentId){
		this.appointmentId = appointmentId;
	}
	public void setMemberId(int memberId){
		this.memberId = memberId;
	}
	public void setVaccSlotId(int vaccSlotId){
		this.vaccSlotId = vaccSlotId;
	}
	public void setHospId(int hospId){
		this.hospId = hospId;
	}
	public void setVaccinatedBy(int vaccinatedBy){
		this.vaccinatedBy = vaccinatedBy;
	}
	public void setStatus(String status){
		this.status = status;
	}
}