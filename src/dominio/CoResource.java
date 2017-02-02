package dominio;

import java.io.IOException;

public class CoResource {
	private String country;
	private int year;
	private float quantity;
	
	public CoResource() {
		this.country = "";
		this.year = -1;
		this.quantity = -1;		
	}
	
	public CoResource(String country, int year, float quantity) {
		this.country = country;
		this.year = year;
		this.quantity = quantity;		
	}
	
	public CoResource(String country) {
		this();
		this.setCountry(country);
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public float getQuantity() {
		return this.quantity;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
}
