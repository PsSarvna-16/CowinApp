package com.sarvna;

class Appointment{

	private static int id=0;
	private int appointmentId;
	private Vaccine vaccine;
	private Member member;
	private String status;

	Appointment(Vaccine vaccine,Member member){
		this.vaccine = vaccine;
		this.member = member;
		this.appointmentId = id++;
	}

	public int getAppointmentId(){
		return this.appointmentId;
	}

	public String toString(){
		return appointmentId + "\n" + vaccine + "\nMember Details: \n" + member + "\n";
	}
}