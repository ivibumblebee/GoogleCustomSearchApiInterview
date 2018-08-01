package com.mycompany.app;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class App {

	public static void main(String[] args) {

	  try {
        Scanner scanner = new Scanner( System.in );//create a scanner
        System.out.print("Enter your search request: "); //get input
				String queryhere = scanner.nextLine(); // from console input
				queryhere = queryhere.replaceAll(" ", "+"); //replace spaces with + to work as a url
				URL url= new URL("https://www.googleapis.com/customsearch/v1?key=AIzaSyCSISSUjHuG4ToMOfEiZ2I5TkBCFBc9-6g&cx=011892775337292349585:w-jjunqmf6a&q="+queryhere);
		//query the google custom search api		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		//get results from an input stream
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
		
		String output;
		String fullitem=""; //here the full results are stored
		ArrayList titles = new ArrayList( ); //list of all the titles
		ArrayList links= new ArrayList( ); //list of all the links
		ArrayList snippets = new ArrayList( ); //list of all the details about the website
		while ((output = br.readLine()) != null) { //runs while there is still another line
					fullitem+=output;
					}
					
		String[] parts = fullitem.split("\"items\": "); //splits the string at items where the relevant information starts
		fullitem=parts[1]; //takes the string after items
		Pattern pattern1 = Pattern.compile("\"customsearch#result\",   \"title\":(.*?),   \"htmlTitle\""); //set up the pattern to search after title and before htmlTitle
		Pattern pattern2 = Pattern.compile(",   \"link\":(.*?),   \"displayLink\""); //set up the pattern to search between link and displayLink
		Pattern pattern3 = Pattern.compile(",   \"snippet\":(.*?),   \"htmlSnippet\":"); //set up the pattern to search between snippet and htmlSnippet
		if (fullitem!=null){
			Matcher matcher1 = pattern1.matcher(fullitem); //search for the title
			Matcher matcher2 = pattern2.matcher(fullitem); //search for links 
			Matcher matcher3 = pattern3.matcher(fullitem); //search for the snippets 

			while (matcher1.find()) {
				titles.add(matcher1.group(1)); //when a title is found add it to the list
			}
			while (matcher2.find()) {
				links.add(matcher2.group(1)); //when a link is found add it to the list
			}
			while (matcher3.find()) {
				snippets.add(matcher3.group(1)); //when a snippet is found add it to the list
			}	 
		}
		else{
			System.out.println("null input");
		}
		//System.out.println(titles.size() +",  " +links.size()+", "+snippets.size());
	  for (int i=0; i<Math.min(Math.min(titles.size(), links.size()), snippets.size());i++){//loop through the results
			//print out the results
			System.out.println("Title: "+titles.get(i));
			System.out.println("Link: "+links.get(i));
			System.out.println("Info: "+snippets.get(i)+"\n");
		}



		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

	}

}
