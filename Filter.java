package com.client;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Filter.
 */
public class Filter implements IsSerializable {
	
	/** The name. */
	private String name = null;
	
	/** The language. */
	private String language = null;
	
	/** The country. */
	private String country = null;
	
	/** The genre. */
	private String genre = null;
	
	/** The year start. */
	private int yearStart = 0;
	
	/** The year end. */
	private int yearEnd = 0;
	
	/** The length. */
	private int length = 0; // Codes für Längen (1=(0 bis 60), 2=(61bis120), 3=(121bis)
	
	/** The is visual. */
	//true falls Daten für die Map abgefragt werden 
	private boolean isVisual = false;
	//TODO in Abhängigkeit von Switch Button/ default zum späteren Zeitpunkt sollte true sein
	
	/**
	 * Instantiates a new filter.
	 */
	public Filter() {};
	
	
	/**
	 * Instantiates a new filter.
	 *
	 * @param isVisual the is visual
	 * @param name the name
	 * @param language the language
	 * @param country the country
	 * @param genre the genre
	 * @param yearStart the year start
	 * @param yearEnd the year end
	 * @param length the length
	 */
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
	
	
	/**
	 * Generate query.
	 *
	 * @return the string
	 */
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
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets the genre.
	 *
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	 * Sets the genre.
	 *
	 * @param genre the new genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * Gets the year start.
	 *
	 * @return the year start
	 */
	public int getYearStart() {
		return yearStart;
	}
	
	/**
	 * Sets the year start.
	 *
	 * @param yearStart the new year start
	 */
	public void setYearStart(int yearStart) {
		this.yearStart = yearStart;
	}
	
	/**
	 * Gets the year end.
	 *
	 * @return the year end
	 */
	public int getYearEnd() {
		return yearEnd;
	}
	
	/**
	 * Sets the year end.
	 *
	 * @param yearEnd the new year end
	 */
	public void setYearEnd(int yearEnd) {
		this.yearEnd = yearEnd;
	}
	
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Sets the checks if is visual.
	 *
	 * @param isVisual the new checks if is visual
	 */
	public void setIsVisual(boolean isVisual) {
		this.isVisual = isVisual;
	}
	
	/**
	 * Checks if is visual.
	 *
	 * @return true, if is visual
	 */
	public boolean isVisual() {
		return isVisual;
	}
	
	/**
	 * Sets the all.
	 *
	 * @param isVisual the is visual
	 * @param name the name
	 * @param language the language
	 * @param country the country
	 * @param genre the genre
	 * @param yearStart the year start
	 * @param yearEnd the year end
	 * @param length the length
	 */
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
