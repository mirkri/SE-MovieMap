package com.client;

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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MovieMap implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private Button query = new Button("executeQuery");
	private ListBox languageSelection = new ListBox();
	private ListBox countrySelection = new ListBox();
	private VerticalPanel FilterPanel = new VerticalPanel();
	private Filter filter = new Filter();

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

		// Adds button to addPanel from HorizontalPanel
		addPanel.add(button);

		// Adds addPanel from HorizontalPanel (=button) and dataTable to
		// mainPanel from VerticalPanel
		// Meaning: mainPanel contains the Table, and underneath it the button
		// (wrapped in addPanel)
		mainPanel.add(dataTable);
		mainPanel.add(addPanel);

		// Add mainPanel to RootPanel with id=mainPanel -> we use it in the
		// .html for the div element for more control over our layout.
		RootPanel.get("mainPanel").add(mainPanel);

		// Handle click on button
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
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
}
