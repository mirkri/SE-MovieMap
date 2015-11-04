package com.server;

import com.client.GreetingService;
import com.client.Filter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	
    private ArrayList <String> executeQuery(String query) {
    ArrayList <String> queryResult = new ArrayList <String> ();

    Connection connection = null;
    ResultSet resultSet = null;
    ResultSetMetaData rsmd = null;
    Statement statement = null;
				
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.GoogleDriver");
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
        }
				
        try
        {
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?user=root");
            connection = DriverManager.getConnection("jdbc:mysql://62.203.130.55:8088/moviemaptest", "SEUser", "SE2015DB");
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
        }
    
        if (connection != null)
        {
            try
            {
                statement = connection.createStatement();
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
            }
        }
        
        if (statement != null)
        {
            try
            {
                resultSet = statement.executeQuery(query);
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
            }
        }
        
        try
        {
            rsmd = resultSet.getMetaData();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
        }
        
        try
        {
            queryResult.add(rsmd.getColumnCount() + "");
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
        }
        
        try
        {
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                queryResult.add(rsmd.getColumnName(i));
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
        }
        
        try
        {
            while (resultSet.next())
            {
                for (int i=1; i<=rsmd.getColumnCount(); i++)
                {
                    queryResult.add(resultSet.getString(i));
                }
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
        }
        
        return queryResult;
    }
	
	public ArrayList<String> getTableData(Filter filter) {
		String finalQuery = "";
        String selectAllFromTable = "SELECT * FROM movies1";
        String where = "WHERE";
        
        //Define the where statement
        //Visual Data or not?
        if(filter.isVisual()) {
        	
        } else {
        	if("".equalsIgnoreCase(filter.getName())) {
        		boolean hadPrevious = false;
			    //Language filter
			    if(filter.getLanguage() != null) {
			    	where = where + " genre LIKE '%" + filter.getLanguage() + "%'";
			    	hadPrevious = true;
			    }
			    //Genre filter
			    if(filter.getGenre() != null) {
			    	if(hadPrevious) {
			    		where = where + " AND genre LIKE '%" + filter.getGenre() + "%'";
			    	} else {
			    		where = where + " genre LIKE '%" + filter.getGenre() + "%'";
			    		hadPrevious = true;
			    	}
			    }
			    //Length filter
			    int lengthCode = filter.getLength();
			    if(lengthCode != 0) {
			    	if(hadPrevious) {
				    	if(lengthCode == 1) {
				    		where = where + " AND (length < 60 AND length > 0)";
				    	}else if(lengthCode == 2) {
				    		where = where + " AND (length < 120 AND length > 59)";
				    	}else {
				    		where = where + " AND length > 119";
				    	}
			    	} else {
			    		if(lengthCode == 1) {
				    		where = where + " (length < 60 AND length > 0)";
				    	}else if(lengthCode == 2) {
				    		where = where + " (length < 120 AND length > 59)";
				    	}else {
				    		where = where + " length > 119";
				    	}
			    		hadPrevious = true;
			    	}	
			    }
			    //Origin filter
			    if(filter.getCountry() != null) {
			    	if(hadPrevious) {
			    		where = where + " AND origin LIKE '%" + filter.getCountry() + "%'";
			    	} else {
			    		where = where + " origin LIKE '%" + filter.getCountry() + "%'";
			    		hadPrevious = true;
			    		}
			    }
			    //Year filter
			    if(hadPrevious) {
			    	where = where + " AND (year < " + (filter.getYearEnd() + 1) + " AND year > " + (filter.getYearStart() + 1) + ")";
			    } else {
			    	where = where + " (year < " + (filter.getYearEnd() + 1) + " AND year > " + (filter.getYearStart() + 1) + ")";
			    	hadPrevious = true;
			    }
			    
			    
			    //define final query
			    finalQuery = selectAllFromTable + " " + where;
        	} else {
        		where = where + " name LIKE '%" + filter.getName() + "%'";
        		//define final query
			    finalQuery = selectAllFromTable + " " + where;
        	}
        }
        
		return executeQuery(finalQuery);
	}
	

}
