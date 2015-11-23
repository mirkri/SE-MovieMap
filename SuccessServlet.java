package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

@SuppressWarnings("serial")
public class SuccessServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyString = req.getParameter("blob-key");
		
		BlobKey blobkey = new BlobKey(keyString);
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BlobstoreInputStream(blobkey)));
		List<String> queries = new ArrayList<String>();
		while((line = reader.readLine()) != null) {
			String query = processLineToQuery(line);
			queries.add(query);
		}
		reader.close();
		
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
		  	  for(String query: queries) {
		  		  PreparedStatement stmt = conn.prepareStatement(query);
		  		  int success = 2;
		  		  success = stmt.executeUpdate();
		  		  if (success == 1) {
		  			  out.println("<html><head></head><body>Success! Redirecting in 3 seconds...</body></html>");
		  		  } else if (success == 0) {
		  			  out.println("<html><head></head><body>Failure! Please try again! Redirecting in 3 seconds...</body></html>");
		  		  }
		  	  }
		          
		    } finally {
		        conn.close();
		    }
        } catch (SQLException e) {
   
        }
        req.getRequestDispatcher("/WEB-INF/uploadSuccess.jsp").forward(req, resp);

		
	}
	
	private String processLineToQuery(String line) throws IOException {
		List<String> cleanedLine = cleanLine(line);
		String query = generateQuery(cleanedLine);
		return query;
	}
	
	private List<String> cleanLine(String line) {
		List<String> cleaned = new ArrayList<String>();
		line = line.replaceAll("\t\t", "\t a \t");
		StringTokenizer stk = new StringTokenizer(line,"\t");
		List<String> list = new ArrayList<String>();
		while(stk.hasMoreTokens()) {
			list.add(stk.nextToken());
		}
		if(list.size() != 0) {
			cleaned.add(list.get(0));
		}else return list;
		for(int i = 2; i < 9 && i < list.size() ; ++i) {
			switch (i) {
			case 2: cleaned.add(list.get(i));
					break;
			case 3: if(list.get(i).length() > 3) {
					cleaned.add(list.get(i).substring(0,4));
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
		return cleaned;
	}
	
	private String generateQuery(List<String> list) {
		if(list.size() != 0) {
			String query = "INSERT INTO moviesnew (id,name,year,length,language,origin,genre) VALUES (";
			String values = "";
			for(String val: list) {
				values += ",\'" + val + "\'";
			}
			query += values.substring(1) + ")";
			return query;
		} else return "";
	}

}
