package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.rdbms.AppEngineDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class SuccessServlet.
 */
@SuppressWarnings("serial")
public class SuccessServlet extends HttpServlet{
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyString = req.getParameter("blob-key");
		
		BlobKey blobkey = new BlobKey(keyString);
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BlobstoreInputStream(blobkey)));
		List<String> values = new ArrayList<String>();
		while((line = reader.readLine()) != null) {
			String value = processLineToQuery(line);
			if (!value.isEmpty()) values.add(value);
		}
		reader.close();
		
		int numOf1000 = values.size() / 1000 + 1;
		List<String> queries = new ArrayList<String>(numOf1000);
		int counter = 0;
		for(int i = 0;i < numOf1000; ++i) {
			String tmp = "INSERT INTO moviesnew (id,name,year,length,language,origin,genre) VALUES (";
			queries.add(tmp);
		}
		for(int i = 0;i < numOf1000; ++i) {
			for(int j = 0; j < 1000; ++j) {
				while(counter < values.size()) {
					String newQuery = queries.get(counter / 1000) + values.get(counter) + "), (";
					queries.set(counter++ / 1000, newQuery);
				}
			}
		}
		for(int i = 0;i < numOf1000; ++i) {
			String tmp = queries.get(i);
			tmp = tmp.substring(0, tmp.length() - 3);
			queries.set(i, tmp);
		}
		
		
		PrintWriter out = resp.getWriter();
		Connection conn = null;
		try {
            DriverManager.registerDriver(new AppEngineDriver());
        } catch (SQLException e) {
        }			
        try { 
        	conn = DriverManager.getConnection("jdbc:google:rdbms://moviemapgroup01:movies/moviedb?user=root");
        } catch (SQLException e) {
        }
        try {
		    try {
		    	for (String query: queries) {
		    		System.out.println(query);
			    	PreparedStatement st = conn.prepareStatement(query);
			    	st.executeUpdate();
		    	}
		          
		    } finally {
		        conn.close();
		    }
        } catch (SQLException e) {
   
        }
        req.getRequestDispatcher("/WEB-INF/uploadSuccess.jsp").forward(req, resp);

		
	}
	
	/**
	 * Process line to query.
	 *
	 * @param line the line
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String processLineToQuery(String line) throws IOException {
		String cleanedLine = cleanLine(line);
		//String query = generateQuery(cleanedLine);
		return cleanedLine;
	}
	
	/**
	 * Clean line.
	 *
	 * @param line the line
	 * @return the string
	 */
	private String cleanLine(String line) {
		List<String> cleaned = new ArrayList<String>();
		line = line.replaceAll("\t\t", "\t a \t");
		line = line.replaceAll("'","");
		StringTokenizer stk = new StringTokenizer(line,"\t");
		List<String> list = new ArrayList<String>();
		while(stk.hasMoreTokens()) {
			list.add(stk.nextToken());
		}
		if(list.size() != 0) {
			cleaned.add(list.get(0));
		}else return "";
		for(int i = 2; i < 9 && i < list.size() ; ++i) {
			switch (i) {
			case 2: cleaned.add(list.get(i));
					break;
			case 3: if(list.get(i).length() > 3) {
					cleaned.add(list.get(i).substring(0,4));
					} else {
						cleaned.add(" ");
					}
					break;
			case 4: break;
			case 5: String current = list.get(i);
					if(current.contains(".") && (current.length() == 3 || current.length() == 4)) {
						cleaned.add(current);
					}else cleaned.add("0");
					break;
			case 6: current = list.get(i);
					if (current.contains("/m/") && current.contains(":") && current.contains("anguage")) {
					String result = "";
					String token = "\"";
					StringTokenizer st = new StringTokenizer(current,token);
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						if (!(tmp.contains("/m/") || tmp.equals(", ") || tmp.equals(": ") || tmp.equals("}") || tmp.equals("{}") || tmp.equals("{")))
						result += "," + tmp;
					}
					if(result.length() != 0) {
						cleaned.add(result.substring(1));
					}
					} else cleaned.add(" ");
					break;
			case 7: current = list.get(i);
					if (current.contains("/m/") && current.contains(":")) {
					String result = "";
					String token = "\"";
					StringTokenizer st = new StringTokenizer(current,token);
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						if (!(tmp.contains("/m/") || tmp.equals(", ") || tmp.equals(": ") || tmp.equals("}") || tmp.equals("{}") || tmp.equals("{")))
						result += "," + tmp;
					}
					if(result.length() != 0) {
						cleaned.add(result.substring(1));
					}
					} else cleaned.add(" ");
					break;
			case 8: current = list.get(i);
					if (current.contains("/m/") && current.contains(":")) {
					String result = "";
					String token = "\"";
					StringTokenizer st = new StringTokenizer(current,token);
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						if (!(tmp.contains("/m/") || tmp.equals(", ") || tmp.equals(": ") || tmp.equals("}") || tmp.equals("{}") || tmp.equals("{")))
						result += "," + tmp;
					}
					if(result.length() != 0) {
						cleaned.add(result.substring(1));
					}
					} else cleaned.add(" ");
					break;
			}
		}
		String values = "";
		if (cleaned.size() == 7) {
			for(String val: cleaned) {
				values += ",\'" + val + "\'";
			}
			return values.substring(1);
		} else return values;
		
	}
	
	/**
	 * Generate query.
	 *
	 * @param line the line
	 * @return the string
	 */
	private String generateQuery(String line) {
		if(line.equals("")) {
			String query = "INSERT INTO moviesnew (id,name,year,length,language,origin,genre) VALUES (";
			query += line + ")";
			return query;
		} else return "";
	}

}
