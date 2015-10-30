package com.client;

public class filter {
	private String name;
	private String language;
	private String country;
	private String[] genre;
	private int yearStart;
	private int yearEnd;
	private int length; // Codes für Längen (1=(0 bis 60), 2=(61bis120), 3=(121bis)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String[] getGenre() {
		return genre;
	}
	public void setGenre(String[] genre) {
		this.genre = genre;
	}
	public int getYearStart() {
		return yearStart;
	}
	public void setYearStart(int yearStart) {
		this.yearStart = yearStart;
	}
	public int getYearEnd() {
		return yearEnd;
	}
	public void setYearEnd(int yearEnd) {
		this.yearEnd = yearEnd;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
}
