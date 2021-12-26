package com.sarvna;

class VaccineSlot{

	private int vaccSlotId;
	private int vaccId;
	private int hospId;
	private int availableDose;
	private String dateOfDose;
	private String slot;

	public VaccineSlot(int vaccId, int hospId, String dateOfDose,String slot, int availableDose){
		
		this.vaccId = vaccId;
		this.hospId = hospId;
		this.dateOfDose = dateOfDose;
		this.slot = slot;
		this.availableDose = availableDose;
	}

	// Getters
	
	public int getVaccSlotId(){
		return this.vaccSlotId;
	}
	public int getVaccId(){
		return this.vaccId;
	}
	public int getHospId(){
		return this.hospId;
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
	public void setVaccId(int vaccId){
		this.vaccId = vaccId;
	}	
	public void setHospId(int hospId){
		this.hospId = hospId;
	}	
	public void setAvailableDose(int availableDose){
		this.availableDose = availableDose;
	}	
	public void setDateOfDose(String dateOfDose){
		this.dateOfDose = dateOfDose;
	}	
	public void setSlot(String slot){
		this.slot = slot;
	}
}