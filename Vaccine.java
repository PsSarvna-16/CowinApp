package com.sarvna;

class Vaccine{

	private int vaccId;
	private String vaccName;
	private String sideEffects;
	private double price;

	Vaccine(String vaccName, String sideEffects, double price){
		
		this.vaccName = vaccName;
		this.sideEffects = sideEffects;
		this.price = price;
	}

	// Getters

	public int getVaccId(){
		return this.vaccId;
	}
	public String getVaccName(){
		return this.vaccName;
	}
	public String getSideEffects(){
		return this.sideEffects;
	}
	public double getPrice(){
		return this.price;
	}

	// Setters

	public void setVaccId(int vaccId){
		this.vaccId = vaccId;
	}
	public void setVaccName(String vaccName){
		this.vaccName = vaccName;
	}
	public void setSideEffects(String sideEffects){
		this.sideEffects = sideEffects;
	}
	public void setPrice(double price){
		this.price = price;
	}

}