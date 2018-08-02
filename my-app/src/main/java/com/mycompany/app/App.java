package com.mycompany.app;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {

	private final static String googleURL = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCSISSUjHuG4ToMOfEiZ2I5TkBCFBc9-6g&cx=011892775337292349585:w-jjunqmf6a&q=";
	private final static String http_get = "GET";
	private final static String accept = "application/json";

	public static void main(String[] args) {
		
		HttpURLConnection conn = null;
		
		try 
		{
			Scanner scanner = new Scanner( System.in );//create a scanner
			System.out.print("Enter your search request: "); //get input
			
			String userQuery = getQuery(scanner.nextLine());
			URL url= new URL(googleURL + userQuery);

			//query the google custom search api		
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(http_get);
			conn.setRequestProperty("Accept", accept);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(IOUtils.toString(conn.getInputStream(), "UTF-8")).getAsJsonObject().get("items").getAsJsonArray();
			
			Iterator<JsonElement> iterator = array.iterator();
			
			while(iterator.hasNext())
			{
				JsonObject obj = iterator.next().getAsJsonObject();
				String title = obj.get("title").getAsString();
				
				if(title.toLowerCase().contains(userQuery.toLowerCase()))
				{
					System.out.println("Title is " + title);
					System.out.println("URL is " + obj.get("link").getAsString());
					System.out.println("Description " + obj.get("snippet").getAsString());
					System.out.println("-------------------------------------------------");
				}
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			System.err.println("Caught " + e.getMessage());

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("Caught " + e.getMessage());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("Caught " + e.getMessage());
		}
		finally
		{
			if(conn != null)
				conn.disconnect();
		}
	}

	public static String getQuery(String input) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(input, Charset.defaultCharset().toString()); //replace spaces with + to work as a url
	}

}
