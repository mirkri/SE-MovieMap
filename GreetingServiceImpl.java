package com.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.client.GreetingService;
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
	
	public ArrayList<String> getTableData() {
        String tableName = "movies1";
		return executeQuery("SELECT name, length, language, origin FROM " + tableName + " WHERE year=2000");
	}

}
