package com.sarvna;

class Vaccine{

	private String vaccineName;
	private String sideEffects;
	private String date;
	private String slot;
	private double price;
	private int dosesAvailable;

	Vaccine(String vaccineName,String date,String slot,int doses,double price){
		
		this.vaccineName = vaccineName;
		this.date = date;
		this.slot = slot;
		this.dosesAvailable = doses;
		this.price = price;
	}

	public boolean reduceCount(){
		if(dosesAvailable >0 ){
			dosesAvailable--;
			return true;
		}
		else{
			return false;
		}
	}

	public void printDetails(){
		if(dosesAvailable > 0)
			System.out.printf(vaccineName +"    "+ date + "    " + slot  + "     " + dosesAvailable + "    " + price +"\n");
	}
	public String toString(){
		return "Vaccine Details\nName-" + vaccineName + "     Date-" + date + "    Slot-" + slot + "    Price-" + price + "\n";

	}
}