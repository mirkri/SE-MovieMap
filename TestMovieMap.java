package com.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.client.MovieMap;
import com.client.Slider;
import com.client.SliderEvent;
import com.google.gwt.junit.client.GWTTestCase;

public class TestMovieMap extends GWTTestCase {
	
	int min = 1900;
	int max = 2015;
	int[] defaultValues = {2010,2012};
	
	MovieMap movieMap = new MovieMap();
	
	Slider rangeSlider = new Slider(null, min, max, defaultValues);
	SliderEvent e = new SliderEvent(null, rangeSlider, defaultValues);

	@Test
	public void testSlide() {
		assertTrue(movieMap.onSlide(e));
	}
	
	@Test
	public void testSliderEvent() {
		assertEquals(defaultValues, e.getValues());
	}
	
	@Test
	public void testSetValuesForSlider() {
		
		int[] newValues = {1980,2000};
		rangeSlider.setValues(newValues);
		
		assertEquals(newValues[0], rangeSlider.getValueAtIndex(0));
		assertEquals(newValues[1], rangeSlider.getValueAtIndex(1));
	}
	
	
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "com.moviemap";
	}
}
