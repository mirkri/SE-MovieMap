package com.client;

import com.client.GreetingService;
import com.client.GreetingServiceAsync;
import com.client.RangeSlider;
import com.client.Slider;
import com.client.SliderEvent;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
	private VerticalPanel FilterPanel = new VerticalPanel();
	private Filter filter = new Filter();
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable dataTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	
	private Label m_rangeSliderLabel;
	private RangeSlider m_rangeSlider;
	
	private int yearStart;
	private int yearEnd;
	
	
	private Button query = new Button("Search");

	// Constructor. Creates new Instance of Flextable
	// Flextable automatically resizes on demand - there is no explicit size
	private final FlexTable dataTable = new FlexTable();
	// Constructors. Creates new Instances of Panels
	// HorizontalPanel grows to the right if items are added
	// VerticalPanel grows downwards if items are added
	private final HorizontalPanel addPanel = new HorizontalPanel();
	private final VerticalPanel mainPanel = new VerticalPanel();

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {

		// Sets Table Headers (Columns)
		// Method "setText" is inherited from HTMLTable class
		dataTable.setText(0, 0, "ID");
		dataTable.setText(0, 1, "Name");
		dataTable.setText(0, 2, "Year");
		dataTable.setText(0, 3, "Length");
		dataTable.setText(0, 4, "Language");
		dataTable.setText(0, 5, "Origin");
		dataTable.setText(0, 6, "Genre");

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
        mainPanel.add(rangeLabel);
        mainPanel.add(m_rangeSliderLabel);
        mainPanel.add(m_rangeSlider);
        
        //Add button to horizontalpanel
      	addPanel.add(query);
      		
      	//Add buttonpanel to mainPanel
      	mainPanel.add(addPanel);
        
        
        //Add dataTable to mainPanel
        mainPanel.add(dataTable);
        
		
        //Add mainPanel to RootPanel with id=root
		RootPanel.get("root123").add(mainPanel);
		
		
		
        //Handle click on button
		query.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				clicked();
			}
		});
        
        //Add a new list for Language selection
        languageSelection.addItem("Choose Language");
        languageSelection.addItem("English");
        languageSelection.addItem("German");
        //Set itemcount to 1 to make it a dropdown instead of showing all items
        languageSelection.setVisibleItemCount(1);
        
        //Add a new list for Country selection
        countrySelection.addItem("Choose Country");
        countrySelection.addItem("GB");
        countrySelection.addItem("Germany");
        //Set itemcount to 1 to make it a dropdown instead of showing all items
        countrySelection.setVisibleItemCount(1);
        
        // Add listboxes to the root panel.
        
        FilterPanel.setSpacing(10);
        FilterPanel.add(languageSelection);
        FilterPanel.add(countrySelection);

        RootPanel.get("languageDropdown").add(FilterPanel);
        RootPanel.get("countryDropdown").add(FilterPanel);
        
        
	}

	private void clicked() {
		greetingService.getTableData(filter, new AsyncCallback<ArrayList <String>>()
		{
				public void onFailure(Throwable caught)	
				{
					Window.alert(caught.getMessage());
				}
				public void onSuccess(ArrayList <String> result)
				{
					Window.alert("success");
					dataTable.removeAllRows();
					fillTable(result);
				}
		});
	}

	private void fillTable(ArrayList<String> data) {
		if (!(data != null))
			return;
		int columnsCount = Integer.parseInt(data.get(0));
		int entry = 1;
		for (int i = 0; i < ((data.size() - 1) / columnsCount); i++) {
			for (int c = 0; c < columnsCount; c++) {
				dataTable.setText(i, c, data.get(entry));
				entry++;
			}
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
    
}
