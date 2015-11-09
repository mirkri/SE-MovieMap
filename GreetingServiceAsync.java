package com.client;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getTableData(String query, AsyncCallback<ArrayList<String>> callback);
}
