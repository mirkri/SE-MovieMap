package com.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TestGetDataFromDatabase extends GWTTestCase{

	
	
	@Test
	public void testGetData() {
		GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
		
		Filter filter = new Filter();
		filter.setName("Avatar 2");
		
		final ArrayList <String> compareList = new ArrayList <String> ();
		compareList.add(7 + "");
		compareList.add("25813358");
		compareList.add("Avatar 2");
		compareList.add("2015");
		compareList.add("0");
		compareList.add("English Language");
		compareList.add("United States of America");
		compareList.add("Science Fiction, Action, Fantasy, Adventure");
		greetingService.getTableData(filter.generateQuery(), new AsyncCallback<ArrayList <String>>()
				{
						public void onFailure(Throwable caught)	
						{}
						public void onSuccess(ArrayList <String> result)
						{
							assertEquals(compareList,result);
						}
				});
		
	}

	@Override
	public String getModuleName() {
		return "com.moviemap";
	}

}

