package Trie;
import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Web_Crawler {
	 
	
	private static final int Max_depth = 4; //set maximum depth for crawling
	
	private static final int Max_Page = 100;	//set maximum page limit for crawling 	
	
	private static HashSet<String> urls; 
	
	public Web_Crawler(){
		urls = new HashSet<String>(); // calling constructor
	}

	public static String HTML_To_Text(String url){ //method to convert html files into text.
		try {
			Document document = Jsoup.connect(url).get();  //getting urls and connect to url using jsoup library.
			String text = document.body().text();// converting html to text.
			return text;		
		}
		catch(Exception e){
			return "Error while converting html files to text"; 
			
		}
	}
	
	
	public HashSet<String> getPagelinks(String url,int init_depth){
		
		if((!urls.contains(url) && (init_depth <= Max_depth))){ //check weather the url present in maximum limit.
			try{
				urls.add(url);
				
				Document document = Jsoup.connect(url).get();
				Elements urlsFromPage= document.select("a[href]");//fetching links by filtering.
				
				                           //print crawler links
				if(urls.size() >= Max_Page) // exit if page limit over
					return urls;
			
			
				init_depth++;
			
				for(Element e: urlsFromPage){
					getPagelinks(e.attr("abs:href"), init_depth); //store filtered url.
				}			
			}
			catch(IOException IOex){}		
		}
		return urls;
	}
}
