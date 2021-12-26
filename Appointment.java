package com.sarvna;

class Appointment{

	private static int id=0;
	private int appointmentId;
	private int memberId;
	private int vacc_slot_id;
	private int hospitalId;
	private String status;
	private int vaccinatedBy;

	Appointment(int memberId,int vacc_slot_id,int hospitalId,String status){
		
		this.memberId = memberId;
		this.vacc_slot_id = vacc_slot_id;
		this.hospitalId = hospitalId;
		this.status = status;
	}

	public int getAppointmentId(){
		return this.appointmentId;
	}
	public int getMemberId(){
		return this.memberId;
	}
	public int getVaccSlotId(){
		return this.vacc_slot_id;
	}
	public int getHospitalId(){
		return this.hospitalId;
	}
	public String getStatus(){
		return this.status;
	}
	public int getVaccinatedBy(){
		return this.vaccinatedBy;
	}



	public void setAppointmentId(int appointmentId){
		this.appointmentId = appointmentId;
	}
	public void setVaccinatedBy(int vaccinatedBy){
		this.vaccinatedBy = vaccinatedBy;
	}
}