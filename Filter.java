package com.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Filter implements IsSerializable {
	
	private String name = null;
	private String language = null;
	private String country = null;
	private String genre = null;
	private int yearStart = 0;
	private int yearEnd = 0;
	private int length = 0; // Codes für Längen (1=(0 bis 60), 2=(61bis120), 3=(121bis)
	//true falls Daten für die Map abgefragt werden 
	private boolean isVisual = false;
	//TODO in Abhängigkeit von Switch Button/ default zum späteren Zeitpunkt sollte true sein
	
	public Filter() {};
	
	
	// Unified setter for all filter variables
	public Filter(Boolean isVisual, String name, String language, String country, String genre, int yearStart, int yearEnd, int length){
		this.name=name;
		this.language=language;
		this.country=country;
		this.genre=genre;
		this.yearStart=yearStart;
		this.yearEnd=yearEnd;
		this.length=length;
		this.isVisual = isVisual;
	}
	
	
	public String generateQuery() {
		String finalQuery = "";
        String selectAllFromTable = "SELECT * FROM moviesnew";
        String where = "WHERE";
        
        //Define the where statement
        //Visual Data or not?
        if(this.isVisual()) {
        	
        } else {
        	if(name == null) {
        		boolean hadPrevious = false;
			    //Language this
			    if(language != null) {
			    	where = where + " language LIKE '%" + language + "%'";
			    	hadPrevious = true;
			    }
			    //Genre this
			    if(genre != null) {
			    	if(hadPrevious) {
			    		where = where + " AND genre LIKE '%" + genre + "%'";
			    	} else {
			    		where = where + " genre LIKE '%" + genre + "%'";
			    		hadPrevious = true;
			    	}
			    }
			    //Length this
			    if(length != 0) {
			    	if(hadPrevious) {
				    	if(length == 1) {
				    		where = where + " AND (length < 60 AND length > 0)";
				    	}else if(length == 2) {
				    		where = where + " AND (length < 121 AND length > 59)";
				    	}else {
				    		where = where + " AND length > 120";
				    	}
			    	} else {
			    		if(length == 1) {
				    		where = where + " (length < 60 AND length > 0)";
				    	}else if(length == 2) {
				    		where = where + " (length < 120 AND length > 59)";
				    	}else {
				    		where = where + " length > 119";
				    	}
			    		hadPrevious = true;
			    	}	
			    }
			    //Origin this
			    if(country != null) {
			    	if(hadPrevious) {
			    		where = where + " AND origin LIKE '%" + country + "%'";
			    	} else {
			    		where = where + " origin LIKE '%" + country + "%'";
			    		hadPrevious = true;
			    		}
			    }
			    //Year this
			    if(hadPrevious) {
			    	where = where + " AND (year < " + (yearEnd + 1) + " AND year > " + (yearStart - 1) + ")";
			    } else {
			    	where = where + " (year < " + (yearEnd + 1) + " AND year > " + (yearStart - 1) + ")";
			    	hadPrevious = true;
			    }
			    
			    
			    //define final query
			    finalQuery = selectAllFromTable + " " + where;
        	} else {
        		where = where + " name LIKE '%" + name + "%'";
        		//define final query
			    finalQuery = selectAllFromTable + " " + where;
        	}
        }
        return finalQuery;
	}
	
	
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
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
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
	
	public void setIsVisual(boolean isVisual) {
		this.isVisual = isVisual;
	}
	
	public boolean isVisual() {
		return isVisual;
	}
	
	public void setAll(Boolean isVisual, String name, String language, String country, String genre, int yearStart, int yearEnd, int length){
		this.isVisual = isVisual;
		this.name=name;
		this.language=language;
		this.country=country;
		this.genre=genre;
		this.yearStart=yearStart;
		this.yearEnd=yearEnd;
		this.length=length;
	}
}
