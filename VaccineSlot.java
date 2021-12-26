package com.sarvna;

class VaccineSlot{

	private int vaccSlotId;
	private int vaccId;
	private int hospitalId;
	private int availableDose;
	private String dateOfDose;
	private String slot;

	VaccineSlot(int vaccId,int hospitalId,String dateOfDose,String slot,int availableDose){
		
		this.vaccId = vaccId;
		this.hospitalId = hospitalId;
		this.dateOfDose = dateOfDose;
		this.slot = slot;
		this.availableDose = availableDose;
	}
	// getters
	public int getVaccSlotId(){
		return this.vaccSlotId;
	}
	public int getVaccId(){
		return this.vaccId;
	}
	public int getHospitalId(){
		return this.hospitalId;
	}
	public int getAvailableDose(){
		return this.availableDose;
	}
	public String getDateOfDose(){
		return this.dateOfDose;
	}
	public String getSlot(){
		return this.slot;
	}

	// Setters
	public void setVaccSlotId(int vaccSlotId){
		this.vaccSlotId = vaccSlotId;
	}
}