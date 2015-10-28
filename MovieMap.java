package com.;

import com.???.client.GreetingService;
import com.???.client.GreetingServiceAsync;

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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MovieMap implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable dataTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private Button query = new Button("executeQuery");


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		//Create datatable
		dataTable.setText(0,0,"Name");
		dataTable.setText(0,1,"Length");
		dataTable.setText(0,2,"Language");
		dataTable.setText(0,3,"Origin");
				
		// Add styles to elements datatable.
		dataTable.setCellPadding(6);
		
        //Add button to horizontalpanel
		addPanel.add(query);
		
        //Add buttonpanel and datatable to mainPanel
		mainPanel.add(dataTable);
		mainPanel.add(addPanel);
		
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
	}
	
	private void clicked() {
		greetingService.getTableData(new AsyncCallback<ArrayList <String>>()
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
		
		if(!(data != null)) return;
		int columnsCount= Integer.parseInt(data.get(0));
		int cnt=1;
		for(int i=0;i< ((data.size()-1)/columnsCount);i++){
				for(int c=0;c<columnsCount;c++){
					dataTable.setText(i,c,data.get(cnt));
					cnt++;
                }
		}
	}
    
}
