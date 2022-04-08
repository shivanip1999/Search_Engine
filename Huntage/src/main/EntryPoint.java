package main;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import Search.URLranking;
import Search.WordSearch;

public class EntryPoint {

	private static final String url = "https://www.google.com/";
	private static final String Regex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	
	public static void main(String [] args) throws IOException {
		
		WordSearch searchEngine = new WordSearch();
		System.out.println("-------------------------- Welcome to Huntage --------------------------");
		
		System.out.println("\n");
		System.out.println("Using \"" +url+ "\" to crawl web pages from!");
		System.out.println("Creating Trie.... Wait for a while!");
		HashSet<String> trie = searchEngine.createTrie(url);  // create hash table using trie
		System.out.println("Trie created. \n\n");
		
		boolean flag = true; // flag to determine whether to continue searching 
		Scanner sc = new Scanner(System.in);
		
		String searchWord;
		
		while(flag) {
			
			System.out.printf("\tEnter the word to hunt: ");
			//Storing input from the user
			searchWord = sc.next();
			
			if(!searchWord.equals(null)) {
				
				//Split word using Regular expression
				
				String [] splitWord = searchWord.split(Regex);
				String[] allSearchedPages = searchEngine.search(splitWord);
				
				try {
					if (allSearchedPages != null) {
						
						//this map will store unsorted links
						Map<String, Integer> unsortedLinks = null;
						unsortedLinks = new HashMap<>();
						
						//storing the links with its word occurrence
						for (String url : allSearchedPages) {
							unsortedLinks.put(url, URLranking.WordOcuurence(url, searchWord));
						}
						
						LinkedHashMap<String, Integer> reverseMap = new LinkedHashMap<>();
						
						unsortedLinks.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> reverseMap.put(x.getKey(), x.getValue()));
						
						System.out.println("\n");

						
						// to print the page ranks, word occurrence & search link
						System.out.println("| Rank | ---------- | Word Frequency | --> | Link in which the word appeared |");	
				        System.out.println("\n");
				       
				        int count = 1;
				        
				        for (Map.Entry<String, Integer> putin : reverseMap.entrySet()) {
				        	if(count > 10)
				        		break;
				            System.out.println("| "+count+" | ---------- | "+ putin.getValue() + " | --> | " + putin.getKey()+" |");
				            count++;
				        }
				        
				        System.out.println("\n\n");
				        
				        // prompt to choose to continue or exit the web search engine
				        System.out.println("Press 'Y' to continue hunting or 'N' to Exit Huntage...");
				        
				        while(true) {
				        	String inpute = sc.next();
				        	if(inpute.equals("Y")||inpute.equals("y")) {
					        	break;
					        }
				        	else if(inpute.equals("N")||inpute.equals("n")) {
				        		flag = false;
					        	System.out.println("Thankyou for using Huntage!...\n Hope to see you hunt words soon!");
					        	System.exit(1);
					        	sc.close();
				        	}
				        	else {
				        		
				        		System.out.println("\n Sorry! This character is out of my Huntage zone! \nPlease input valid character. \n Press 'Y' to continue hunting or 'N' to Exit Huntage...");
				        	}
				        }
					}
					
				
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
		}
		else 
		{
			System.out.println(searchWord+" not Found, Please enter correct word.");
			continue;
		}	
		}
	}
}