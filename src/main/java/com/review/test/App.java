package com.review.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Review Test
 *
 */
public class App 
{
	
	
	public static String[] getMovieTitles(String substr) {
		String[] filterStr = substr.split(" ");
		String paramStr = "";
		if(filterStr.length==1) {
			paramStr = "Title="+filterStr[0];
		}else {
			paramStr = "Title="+filterStr[0]+"&page="+filterStr[1];
		}
		String jsonResult = jsonRequest(paramStr);
	
		JSONObject obj = new JSONObject(jsonResult);
	
		JSONArray movieTitles = obj.getJSONArray("data");
		String[] titles = new String[movieTitles.length()];
		for (int i = 0; i < movieTitles.length(); i++) {
            JSONObject jb = movieTitles.getJSONObject(i);
            titles[i] = jb.getString("Title");
        
        
        }
	
		return titles;
	}
	
	public static String jsonRequest(String substr) {
		String json = "";
		try {

    		URL url = new URL("https://jsonmock.hackerrank.com/api/movies/search/?"+substr);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Accept", "application/json");

    		if (conn.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			(conn.getInputStream())));

    		String output;
    		//System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			//System.out.println(output);
    			json = output;
    		}

    		conn.disconnect();

    	  } catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  }
		return json;
	}
	
    public static void main( String[] args )
    {
    	String substr = "Maze";
    	String[] movieTitles = getMovieTitles(substr);
    	Stream<String> stream = Arrays.stream(movieTitles).sorted();
    	stream.forEach(title->System.out.println(title));
        //createJSON(true);
    }
}
