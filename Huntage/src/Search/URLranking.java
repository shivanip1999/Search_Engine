package Search;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class URLranking {
     
	private static String regex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	
	//function calculate the occurrence of searched word in a web page and find page ranking.
	public static int WordOcuurence(String URL, String WORD) throws IOException{
		
		Document web_Pages = Jsoup.connect(URL).get(); // Connects method creates a new connection , and get() methods fetches and parses a HTML file
		String data = web_Pages.body().text();
		Map<String, Word_Element> map_content = new HashMap<String, Word_Element>();
		BufferedReader br_Reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))));
		String temp_Str;
		
		while((temp_Str = br_Reader.readLine()) != null){
			
			String word [] = temp_Str.split(regex); // splits the word and stores word in array of strings
			for (String allwords : word){
				
				if("".equals(allwords)){ 
					
					continue;
				}
				
				Word_Element wordElement = map_content.get(allwords);
				
				if(allwords.equalsIgnoreCase(WORD)){  // if word matches with the searches word
					
					if(wordElement == null){
						
						wordElement = new Word_Element();
						wordElement.word = allwords;
						wordElement.count = 0;
						map_content.put(allwords,wordElement);
					}
					wordElement.count++;
				}
			}
		}
		br_Reader.close();
		SortedSet<Word_Element> sort = new TreeSet<Word_Element>(map_content.values()); // 
		int p = 0;
		int max = 1000;
		
		LinkedList <String> unusedWords = new LinkedList <>();
		try {
			BufferedReader wordbr = new BufferedReader(new FileReader("unwanted"));
			String w;
			while ((w = wordbr.readLine()) != null){
				
				unusedWords.add(w);
			}
			wordbr.close();
		}
		catch (FileNotFoundException e){
			
			System.out.println("Sorry..The word you are trying to hunt, not found! Try another");
		}
		
		for(Word_Element words : sort) {
			if(p >= max){
				
				break;
			}
			if (unusedWords.contains(words.word)){
				
				p++;
				max++;
			}
			else{
				
				p++;
				return words.count;
			}
		}
		return 0;
	}
	
	public static class Word_Element implements Comparable<Word_Element> // class implementing comparable interface
	{
		String word;
		int count;

		@Override
		public int hashCode() {
			return word.hashCode();
		}
		@Override
		public boolean equals(Object object) {
			return word.equals(((Word_Element) object).word);
		}
		@Override
		public int compareTo(Word_Element we) {
			return we.count - count;
		}	
	}
}
