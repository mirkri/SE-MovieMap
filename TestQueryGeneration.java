package com.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQueryGeneration {
	
	private String selectAllFromTable = "SELECT * FROM movies1";

	@Test
	public void testQueryName() {
		Filter filter = new Filter();
		filter.setName("Test Query By Name");
		assertEquals(selectAllFromTable + " WHERE name LIKE '%Test Query By Name%'",filter.generateQuery());
	}
	
	@Test
	public void testQueryYear() {
		Filter filter = new Filter();
		filter.setYearStart(2010);
		filter.setYearEnd(2015);
		assertEquals(selectAllFromTable + " WHERE (year < 2016 AND year > 2009)",filter.generateQuery());
	}
	
	@Test
	public void testQueryLanguageYear() {
		Filter filter = new Filter();
		filter.setYearStart(1920);
		filter.setYearEnd(2000);
		filter.setLanguage("Test Language");
		assertEquals(selectAllFromTable + " WHERE language LIKE '%Test Language%' AND (year < 2001 AND year > 1919)",filter.generateQuery());
	}
	
	@Test
	public void testQueryCountryYear() {
		Filter filter = new Filter();
		filter.setYearStart(2011);
		filter.setYearEnd(2011);
		filter.setCountry("Test Country");
		assertEquals(selectAllFromTable + " WHERE origin LIKE '%Test Country%' AND (year < 2012 AND year > 2010)",filter.generateQuery());
	}
	
	@Test
	public void testQueryGenreYear() {
		Filter filter = new Filter();
		filter.setYearStart(2014);
		filter.setYearEnd(2020);
		filter.setGenre("Test Genre");
		assertEquals(selectAllFromTable + " WHERE genre LIKE '%Test Genre%' AND (year < 2021 AND year > 2013)",filter.generateQuery());
	}
	
	@Test
	public void testQueryLengthYear() {
		Filter filter = new Filter();
		filter.setYearStart(2011);
		filter.setYearEnd(2011);
		filter.setLength(2);
		assertEquals(selectAllFromTable + " WHERE (length < 120 AND length > 59) AND (year < 2012 AND year > 2010)",filter.generateQuery());
	}
	
	@Test
	public void testQueryLanguageCountryGenreYear() {
		Filter filter = new Filter();
		filter.setYearStart(2007);
		filter.setYearEnd(2013);
		filter.setGenre("Test Genre");
		filter.setLanguage("Test Language");
		filter.setCountry("Test Country");
		assertEquals(selectAllFromTable + " WHERE language LIKE '%Test Language%' AND genre LIKE '%Test Genre%' AND origin LIKE '%Test Country%' AND (year < 2014 AND year > 2006)",filter.generateQuery());
	}
	

}
