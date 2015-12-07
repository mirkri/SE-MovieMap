package com.client;

import com.client.GreetingService;
import com.client.GreetingServiceAsync;
import com.client.RangeSlider;
import com.client.Slider;
import com.client.SliderEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MovieMap implements EntryPoint, SliderListener {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private ListBox languageSelection = new ListBox();
	private ListBox countrySelection = new ListBox();
	private ListBox genreSelection = new ListBox();
	private ListBox lengthSelection = new ListBox();
	private Filter filter = new Filter();
	final TextBox nameField = new TextBox();
	
	private Label m_rangeSliderLabel;
	private RangeSlider m_rangeSlider;
	private int yearEnd;
	private int yearStart;
	
	//Column with countries
	private ArrayList<String> countriesFromDB = new ArrayList<String>();  
    
    // Arrays for countries and number of movies per country
    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<Integer> moviesPerCountry = new ArrayList<Integer>();

    // Create JS-Arrays to pass to JavaScript
    private JsArrayString countriesJS = toJsArrayString(countries);
    private JsArrayInteger moviesPerCountryJS = toJsArrayInt(moviesPerCountry);
	
	private Button goButton = new Button("Search");

	// Constructor. Creates new Instance of Flextable
	// Flextable automatically resizes on demand - there is no explicit size
	private final FlexTable dataTable = new FlexTable();
	// Constructors. Creates new Instances of Panels
	// HorizontalPanel grows to the right if items are added
	// VerticalPanel grows downwards if items are added
	private final VerticalPanel mainPanel = new VerticalPanel();
	private final VerticalPanel navigationPanel = new VerticalPanel();
    private final HorizontalPanel dropdownPanel = new HorizontalPanel();

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {

		// Add styles to elements of dataTable
		dataTable.setCellPadding(6);
		
		/*
         * Create a RangeSlider with:
         * minimum possible value: oldest decade in database
         * maximum possible value: most recent year in database
         * default: most recent year in database
         */
        Label rangeLabel = new Label("Year:");
        m_rangeSliderLabel = new Label("2015");
        m_rangeSliderLabel.addStyleName("slider-values");
        m_rangeSlider = new RangeSlider("range", 1900, 2015, 2015, 2015);
        m_rangeSlider.addListener(this);
        
      //Add labels and slider to mainPanel
        navigationPanel.add(rangeLabel);
        navigationPanel.add(m_rangeSliderLabel);
        navigationPanel.add(m_rangeSlider);
        
        //Add dropdowns FilterPanel
      	dropdownPanel.setSpacing(10);
        dropdownPanel.add(languageSelection);
        dropdownPanel.add(countrySelection);
        dropdownPanel.add(genreSelection);
        dropdownPanel.add(lengthSelection);
        nameField.setText("Search by name");
        dropdownPanel.add(nameField);
        
        
        //Add button to FilterPanel
      	dropdownPanel.add(goButton);

      	
        //Add dataTable to mainPanel
        mainPanel.add(dataTable);
        
		
        //Add mainPanel to RootPanel with id=root
		RootPanel.get("root123").add(mainPanel);
		RootPanel.get("rootNavigation").add(navigationPanel);
		RootPanel.get("rootDropdown").add(dropdownPanel);
		RootPanel.get("root123").add(mainPanel);
		
        //Handle click on button
		goButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				setFilter();
				queryDatabase();
			}
		});
		
		Scheduler.get().scheduleDeferred(new Command() {
			public void execute () {
				updateGeoChart();
			}
		});
		
		//Fill first few
		languageSelection.addItem("Choose Language");
		languageSelection.addItem("Aboriginal Malay languages");
		languageSelection.addItem("Aceh");
		languageSelection.addItem("Adamawa");
		languageSelection.addItem("Afrikaans");
		languageSelection.addItem("Akan");
		languageSelection.addItem("Albanian");
		languageSelection.addItem("Algonquin");
		languageSelection.addItem("American English");
		languageSelection.addItem("American Sign");
		languageSelection.addItem("Amharic");
		languageSelection.addItem("Ancient");
		languageSelection.addItem("Ancient Greek");
		languageSelection.addItem("Apache");
		languageSelection.addItem("Arabic");
		languageSelection.addItem("Aramaic");
		languageSelection.addItem("Armenien");
		languageSelection.addItem("Assamese");
		languageSelection.addItem("Assyrian");
		
		genreSelection.addItem("Choose Genre");
		genreSelection.addItem("& Literature");
		genreSelection.addItem("Absurdism");
		genreSelection.addItem("Acid western");
		genreSelection.addItem("Action");
		genreSelection.addItem("Action Comedy");
		genreSelection.addItem("Action Thrillers");
		genreSelection.addItem("Action/Adventure");
		genreSelection.addItem("Addiction Drama");
		genreSelection.addItem("Adult");
		genreSelection.addItem("Adventure");
		genreSelection.addItem("Adventure Comedy");
		genreSelection.addItem("Airplanes and airports");
		genreSelection.addItem("Albino bias");
		genreSelection.addItem("Alien Film");
		genreSelection.addItem("Alien Invasion");
		genreSelection.addItem("Americana");
		genreSelection.addItem("Animal Picture");
		genreSelection.addItem("Animals");
		
		countrySelection.addItem("Choose Country");
		countrySelection.addItem("Afghanistan");
		countrySelection.addItem("Albania");
		countrySelection.addItem("Algeria");
		countrySelection.addItem("Argentina");
		countrySelection.addItem("Armenia");
		countrySelection.addItem("Aruba");
		countrySelection.addItem("Australia");
		countrySelection.addItem("Azerbaijan");
		countrySelection.addItem("Bahamas");
		countrySelection.addItem("Bahrain");
		countrySelection.addItem("Bangladesh");
		countrySelection.addItem("Belgium");
		countrySelection.addItem("Bhutan");
		countrySelection.addItem("Bolivia");
		countrySelection.addItem("Bosnia and Herzegovina");
		countrySelection.addItem("Brazil");
		countrySelection.addItem("Bulgaria");
		
		
        //Generate Language dropdown
		generateDropDown("language", languageSelection);
		languageSelection.setVisibleItemCount(1);

		// Generate Country DropDown
		generateDropDown("country", countrySelection);
		countrySelection.setVisibleItemCount(1);

		// Populate Genre DropDown
		generateDropDown("genre", genreSelection);
		genreSelection.setVisibleItemCount(1);
        
		//Generate Length Dropdown
		lengthSelection.addItem("All");
		lengthSelection.addItem("0 to 59min");
		lengthSelection.addItem("60min to 120min");
		lengthSelection.addItem("over 120min");
		lengthSelection.setVisibleItemCount(1);
        
	}
	
	private void generateDropDown(String usage, ListBox listbox) {
		
		// different in database
		String tmp = null;
		if (usage.equals("country")) {
			tmp = "origin";
		} else {
			tmp = usage;
		}
		final String use = tmp;

		final ListBox list = listbox;

		// just for visual
		String s1 = usage.substring(0, 1).toUpperCase() + usage.substring(1);
		final String capitalized = s1;

		greetingService.getTableData("SELECT " + use + " FROM moviesnew", new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				// building result into one String
				StringBuilder sb = new StringBuilder();
				for (String s : result) {
					sb.append(s);
					sb.append(",");
				}
				String wholeThingToString = sb.toString();
				// cleaning String up
				String regex1 = "\\s*\\bLanguage\\b\\s*";
				String regex2 = "\\s*\\b" + use + "\\b\\s*";
				String regex3 = "\\w*\\d\\w* *";
				wholeThingToString = wholeThingToString.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3,
						"");
				// splitting up
				java.util.List<String> singleMenuePoints = Arrays.asList(wholeThingToString.split("\\s*,\\s*"));
				// converting for unique items
				HashSet<String> hs = new HashSet<String>();
				hs.addAll(singleMenuePoints);
				result.clear();
				result.addAll(hs);
				// sorting for user friendliness
				Collections.sort(result);
				// exchange again since db is different. otherwise use original
				// word provided
				// populate list
				for (int i = 19; i < result.size(); i++) {
					String tmp = result.get(i);
					if (!(tmp.equals(" ")) || tmp.isEmpty()) {
						list.addItem(result.get(i));
					}
				}
			}
		});
	}
	
	private void setFilter() {
		//set Language
		if(!languageSelection.isItemSelected(0)) {
			filter.setLanguage(languageSelection.getItemText(languageSelection.getSelectedIndex()));
		} else {
			filter.setLanguage(null);
		}
		//set Country
		if(!countrySelection.isItemSelected(0)) {
			filter.setCountry(countrySelection.getItemText(countrySelection.getSelectedIndex()));
		} else {
			filter.setCountry(null);
		}
		if(!genreSelection.isItemSelected(0)) {
			filter.setGenre(genreSelection.getItemText(genreSelection.getSelectedIndex()));
		} else {
			filter.setGenre(null);
		}
		//set year through timeline
		filter.setYearStart(yearStart);
		filter.setYearEnd(yearEnd);
		switch (lengthSelection.getItemText(lengthSelection.getSelectedIndex())) {
		case "0 to 59min": filter.setLength(1);
			break;
		case "60min to 120min": filter.setLength(2);
			break;
		case "over 120min": filter.setLength(3);
			break;
		default: filter.setLength(0);
		}
		//get search text for name search
		String name = nameField.getText();
		if(!(name.equals("Search by name") || name.equals(""))) {
			filter.setName(nameField.getText());
		}else {
			filter.setName(null);
		}
	}

	private void queryDatabase() {
		greetingService.getTableData(filter.generateQuery(), new AsyncCallback<ArrayList <String>>()
		{
				public void onFailure(Throwable caught)	
				{
					Window.alert(caught.getMessage());
				}
				public void onSuccess(ArrayList <String> result)
				{
					dataTable.removeAllRows();
					fillTable(result);
					updateGeoChart();
				}
		});
	}

	private void fillTable(ArrayList<String> data) {
		if (!(data != null))
			return;
		int columnsCount = Integer.parseInt(data.get(0));
		int entry = 1;
		countriesFromDB.clear();
		
		for (int i = 0; i < ((data.size() - 1) / columnsCount); i++) {
			for (int c = 0; c < columnsCount; c++) {
				dataTable.setText(i, c, data.get(entry));
			//set name/CSS identifier	
			if (i%2==0 && i!=0){	
				dataTable.getCellFormatter().addStyleName(i, c, "cellsAlt");
			}
			else if (i%2==1 && i!=0){
				dataTable.getCellFormatter().addStyleName(i, c, "cells");
			}
			if (i==0){
				dataTable.getCellFormatter().addStyleName(i, c, "cellsHeader");
			
			}
			
			
			
			if (c == 5 && data.get(entry) != "" && data.get(entry) != "origin") {
				// Fill the array with all entries from the column "origin"
				// Leaves out blank entries and the first entry "origin"
				countriesFromDB.add(data.get(entry));
			}
			entry++;
		}
		//Overwrite Titels for DB
		dataTable.setText(0, 0, "ID");
		dataTable.setText(0, 1, "Title");
		dataTable.setText(0, 2, "Year");
		dataTable.setText(0, 3, "Length");
		dataTable.setText(0, 4, "Language");
		dataTable.setText(0, 5, "Origin");
		dataTable.setText(0, 6, "Genre");
		}
	}
	
	@Override
    public void onChange(SliderEvent e)
    {
        //We don't need to do anything, because everything is done in onSlide in this example
    }

	/**
     * Update the rangeSliderLabel when the rangeSlider is moved
     */
    @Override
    public boolean onSlide(SliderEvent e)
    {
        Slider source = e.getSource();
        if (source == m_rangeSlider) {
        	if (e.getValues()[0] == e.getValues()[1]) {
        		m_rangeSliderLabel.setText("" + e.getValues()[0]);
        	} else {
        		m_rangeSliderLabel.setText(e.getValues()[0] + " - " + e.getValues()[1]);
        	}
        	yearStart = e.getValues()[0];
        	yearEnd = e.getValues()[1];
        }
        return true;
    }

    @Override
    public void onStart(SliderEvent e)
    {
        // We are not going to do anything onStart 
    }

    @Override
    public void onStop(SliderEvent e)
    {
        // We are not going to do anything onStop        
    }
    
 // Convert an ArrayList<String> to a JavaScript-Array that holds Strings
    public static JsArrayString toJsArrayString(ArrayList<String> input) {
        JsArrayString jsArrayString = JsArrayString.createArray().cast();
        for (String s : input) {
            jsArrayString.push(s);
        }
        return jsArrayString; 
    }
    // Convert an ArrayList<Integer> to a JavaScript-Array that holds Integer
    public static JsArrayInteger toJsArrayInt(ArrayList<Integer> input) {
        JsArrayInteger jsArrayInteger = JsArrayInteger.createArray().cast();
        for (int s : input) {
            jsArrayInteger.push(s);
        }
        return jsArrayInteger; 
    }
    
	
    private void setMovieCountPerCountry() {
		
		// One occurrence of a country
		ArrayList<String> cleanedCountries = new ArrayList<String>();
		// Index corresponding to country in cleanedCountries-Array
		ArrayList<Integer> countryCount = new ArrayList<Integer>();
		
		// Temporary array
		ArrayList<String> subListTemp = new ArrayList<String>();

		// Iterate through countriesFromDB which holds all entries from the column "origin"
		for (int i = 1; i < countriesFromDB.size(); i++) {
			// Fill all the countries from the array into a sublist
			// Split all entries that have multiple countries into single entries
			String[] subList = countriesFromDB.get(i).split(", ");
			for (int j = 0; j < subList.length; j++) {
				// Fill the temporary array with the entries from the sublist
				subListTemp.add(subList[j]);
			}	
		}
		
		// Assign the temporary array to countriesFromDB-Array
		countriesFromDB = subListTemp;
		
		// Iterate through countriesFromDB-Array
		for (int i = 0; i < countriesFromDB.size(); i++) {
			if (cleanedCountries.isEmpty()) {
				// If empty, add first country from DB-entry to cleaned Array
				cleanedCountries.add(countriesFromDB.get(i));
				countryCount.add(1);
			} else {
				Boolean found = false;
				for (int j = 0; j < cleanedCountries.size(); j++) {
					// Iterate through cleaned Array
					if (cleanedCountries.get(j) == countriesFromDB.get(i)) {
						// If country from DB-entry is the same , add +1 to countryCount at same indexxe
						countryCount.set(j, countryCount.get(j)+1);
						found = true;
					}
				}
				if (!found) {
					// If country from the DB-entry is not found, add it at the end of cleaned Array
					cleanedCountries.add(countriesFromDB.get(i));
					countryCount.add(1);
				}
				found = false;
			}
		}
		
		// Assign arrays to the class-variables
		countries = cleanedCountries;
		moviesPerCountry = countryCount;
		
	}

    // JSNI-method
    private native void publishVariables() /*-{
    	// Creates following variables which can be used by JavaScript
      	$wnd.countries = this.@com.client.MovieMap::countriesJS;
      	$wnd.moviesPerCountry = this.@com.client.MovieMap::moviesPerCountryJS;
    }-*/;
    
    // JSNI-method
    private native void loadMapData() /*-{
    	// Creates following function which can be used by JavaScript
    	$wnd.onGwtReady();
  	}-*/;  
    
    private void updateGeoChart() {
    	// Fill up the arrays "countries" and "moviesPerCountry" with the DB-data
    	setMovieCountPerCountry();
    	
    	// Convert arrays to JS-Arrays
    	countriesJS = toJsArrayString(countries);
    	moviesPerCountryJS = toJsArrayInt(moviesPerCountry);
    	
    	// Publish the new arrays to JavaScript
    	publishVariables();
    	// Reload the GeoChart with the new data
    	loadMapData();
    }
    
}
