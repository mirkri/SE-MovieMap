package com.client;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	
	// TODO: error: invalid use of @return 	  [javadoc] 	 * @return the table data
	/**
	 * Gets the table data.
	 *
	 * @param query the query
	 * @param callback the callback
	 * @return the table data
	 */
	void getTableData(String query, AsyncCallback<ArrayList<String>> callback);
}
