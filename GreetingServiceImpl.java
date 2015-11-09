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

import com.google.appengine.api.rdbms.AppEngineDriver;
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
            DriverManager.registerDriver(new AppEngineDriver());
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
        }
				
        try
        { 
        	connection = DriverManager.getConnection("jdbc:google:rdbms://moviemapgroup01:movies/moviedb?user=root");
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
        	e.printStackTrace();
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
	
	public ArrayList<String> getTableData(String query) {
		return executeQuery(query);
	}
	

}
