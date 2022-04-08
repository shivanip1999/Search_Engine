package Search;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

import Trie.Web_Crawler;
import Trie.Trie;

public class WordSearch {
	
	private static Trie<ArrayList<Integer>> trie;
	private final String Regexpattern = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	private static HashSet<String> webpages;

	private static LinkedList<String> suggestion = new LinkedList<>(); 
	
	//create trie and returns the link pages
	public HashSet<String> createTrie(String urlName) throws IOException {
		Hash hash =  new Hash();
		Web_Crawler crawler = new Web_Crawler();
		 		
		trie = new Trie<ArrayList<Integer>>();
		
		HashSet<String> wordsunrequired = hash.savepages("unwanted.txt");		
	
		webpages = crawler.getPagelinks(urlName, 1);
		
		
		HashSet<String> temp = null;
		String text;
		String wrd;
		String[] wordsplit;
		
		Iterator<String> linkIterator = null;
		Iterator<String> wordIterator = null;
		
		linkIterator = webpages.iterator();
		
		int i = 0;
		while(linkIterator.hasNext()) {
			String s1 = linkIterator.next();
			text = Web_Crawler.HTML_To_Text(s1);
			
			if(text.length() == 0) {
				continue;
			}
			
			text = text.toLowerCase();
			wordsplit = text.split(Regexpattern);
			
			for(String s: wordsplit) {
				suggestion.add(s);
			}
			
			suggestion.removeAll(wordsunrequired);
			
			temp = new HashSet<String>(Arrays.asList(wordsplit));
			temp.remove(wordsunrequired);
			
			wordIterator = temp.iterator();
			
			while(wordIterator.hasNext()) {
				wrd = (String) wordIterator.next();
				ArrayList<Integer> arrList = trie.wordSearch(wrd);
				
				
				if (arrList == null) {
					//storing words in trie
					trie.put(wrd, new ArrayList<Integer>(Arrays.asList(i)));
				} else {
					arrList.add(i);
				}
			}

			i++;
		}
		
		return webpages;
	}
	
	
	// returns the index of the web pages
	
	public String[] search (String[] index) {
		
		int[] count = new int[webpages.size()];
		List<String> links = new ArrayList<String>(webpages);
		
		ArrayList<Integer> tmp = null;
		for (int i = 0; i < index.length; i++) {
			//search the word in trie by using wordsearch
			tmp = trie.wordSearch(index[i].toLowerCase());//tmp stored the links position
			
			if (tmp != null) {
				for (int k = 0; k < tmp.size(); k++) {
					count[tmp.get(k)]++;
				}
			} else {
				System.out.println("The word <" + index[i] + "> is not present!" );
				suggestWords(index[i]);				
				return null;
			}
		}
		
		
		/*Answers stores the indexes of the webPages*/ 
		
		ArrayList<String> webPages = new ArrayList<String>();
		for (int m = 0; m < count.length; ++m) {
			if (count[m] == index.length) {
				webPages.add(links.get(m));
			}
		}
		return webPages.toArray(new String[0]);
	}
	
	// function to provide suggestions on the base of spell checking
	
	public static void suggestWords(String s) {
			
		int maxDistance = 10000;
		String suggest = "No Suggestions!";
		
		ArrayList<String> suggestList = new ArrayList<String>() ; 
		
		for(String suggest1: suggestion) {
			int distance = SpellChecker.editDistance(s, suggest1);
			if(distance < maxDistance) {
				suggest = suggest1;
				maxDistance = distance;
				if( distance < 10000 )
					suggestList.add(suggest1);
			}
		}
		
		System.out.println("Did you mean " +suggest+ "? OR");
		Collections.reverse(suggestList) ;
		if(suggestList != null)
		{
			for(String suggestwords: suggestList)
				System.out.println(suggestwords);
		}
	}
	
}
