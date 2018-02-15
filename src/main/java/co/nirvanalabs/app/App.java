package co.nirvanalabs.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * 
 *
 */
public class App {

	public static String baseUrl="https://jsonplaceholder.typicode.com/posts";
	
	public static void main(String[] args) throws IOException {

		try {
			String str = Arrays.toString(args);
			System.out.println(str);
			
			if(args.length==0 || !args[0].equals("blogpost") || args.length<2) {
				introText();
				showHelpInfo();
			}else {
				
				if(args[1].equals("create")) {
					createPost(Integer.parseInt(args[2]), args[3], args[4]);
				} else if(args[1].equals("get")) {
					if(args.length==2) {
						getBlogPosts();
					} else {
						getUserPosts( Integer.parseInt(args[2]) );
					}
				} else {
					System.out.println("Invalid arguments.");
					showHelpInfo();
				}		
				
			}
						
		} catch(ArrayIndexOutOfBoundsException ae) {
			System.out.println("Invalid no of arguments provided!");
			showHelpInfo();
			
		}catch (MalformedURLException e1) {
			System.out.println("MalformedURLException Occured:-");
			System.out.println(e1.toString());
		} catch (IOException ioe) {
			System.out.println("Error Occured:-");
			System.out.println(ioe.toString());
		} catch (Exception e) {
			System.out.println("Error Occured:-");
			System.out.println(e.toString());
		}

	}
	

	public static void createPost(int id, String title, String body) throws MalformedURLException, IOException {
		System.out.println("Creating a Post...");
		// JSON query request
		String query = "{'title': '"+title+"','body': '"+body+"','userId': "+id+"}";

		String respStr=sendPost(baseUrl,query);
		
		System.out.println("Ouput:--");
		System.out.println(respStr);

	}

	public static void getUserPosts(int id) throws MalformedURLException,Exception {
		
		System.out.println("Getting user post...");
		String completeUrl= baseUrl+"?userId="+id;
        
        String respStr=sendGet(completeUrl);
		
		System.out.println("Ouput:--");
		System.out.println(respStr);

	}	
	public static void getBlogPosts() throws Exception {
		System.out.println("Getting all the post...");
		String completeUrl=baseUrl ;
        
		String respStr=sendGet(completeUrl);
		
		System.out.println("Ouput:--");
		System.out.println(respStr);


	}
	
	
	// HTTP GET request
	public static String sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
	
	
	public static String sendPost(String completeUrl,String query) throws IOException,MalformedURLException {
		
		query = query!=null ? query.replace("'", "\"") : query;
 
		URL url;
		url = new URL(completeUrl);
		// make connection
		URLConnection urlc = url.openConnection();
		// Content Type
		urlc.setRequestProperty("Content-Type", "application/json");

		// use post mode
		urlc.setDoOutput(true);
		urlc.setAllowUserInteraction(false);

		System.out.println("\nSending 'POST' request to URL : " + url);
		// send query
		PrintStream ps = new PrintStream(urlc.getOutputStream());
		ps.print(query);
		ps.close();

		// get result
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(urlc.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();		
	};
	

	public static void introText() {
		System.out.println("This command line app is build using Rest API From JSONPlaceholder.");
		System.out.println("JSONPlaceholder is a simple fake REST API for testing and prototyping.");		
	}
	
	public static void showHelpInfo() {
		System.out.println("------------Help--------------");		
		System.out.println("Expected inputs are as follows:-");
		System.out.println("1.Create a new blog entry using :- blogpost create userId title body");
		System.out.println("*Eg:- blogpost create 1 blog1 blogbody");
		System.out.println("2.Get all the blog posts that belong to the first user using:- blogpost get 1");
		System.out.println("*Eg:- blogpost get 1");
		System.out.println("3.Get all the blog posts using:- blogpost get");
		System.out.println("*Eg:- blogpost get");	
		System.out.println("------------Help--------------");
	}
	
}
