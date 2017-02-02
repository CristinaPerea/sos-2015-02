package dominio;

import java.io.IOException;

public class InversionResource {
	private String country;
	private int year;
	private long inversion;
	
	public InversionResource() {
		this.country = "";
		this.year = -1;
		this.inversion = -1;		
	}
	
	public InversionResource(String country, int year, long inversion) {
		this.country = country;
		this.year = year;
		this.inversion = inversion;		
	}
	
	public InversionResource(String country) {
		this();
		this.setCountry(country);
	}
	
	public void setYear(int year) throws IOException {
		this.year = year;
	}
	
	public void setInversion(long inversion) throws IOException {
		this.inversion = inversion;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public long getInversion() {
		return this.inversion;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
}