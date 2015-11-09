package com.client;

import static org.junit.Assert.*;

import com.google.gwt.junit.client.GWTTestCase;

import org.junit.Test;

public class TestSlider extends GWTTestCase {

	int min = 1900;
	int max = 2015;
	int[] defaultValues = {2010,2012};
	
	//Slider rangeSlider = new Slider(null, min, max, defaultValues);
	//SliderEvent e = new SliderEvent(null, rangeSlider, defaultValues);
	
	@Test
	public void testSliderEvent() {
		assertEquals(defaultValues, new SliderEvent(null, new Slider(null, min, max, defaultValues), defaultValues).getValues());
	}

	@Override
	public String getModuleName() {
		return "com.moviemap";
	}

}
