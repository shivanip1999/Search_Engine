package Search;

public class SpellChecker {
		
		// calculating distance between 2 words using Edit Distance Algorithm
		public static int editDistance(String word1, String word2) {
			
			int wordlen1 = word1.length();
			int wordlen2 = word2.length();
		 
			int[][] distanceArray = new int[wordlen1 + 1][wordlen2 + 1];
		 
			// Initialize first column
			for (int i = 0; i <= wordlen1; i++) 
				distanceArray[i][0] = i;
			
			// Initialize first row
			for (int j = 0; j <= wordlen2; j++) 
				distanceArray[0][j] = j;
			
			// Apply Edit Distance Algorithm 
			for (int i = 0; i < wordlen1; i++) 
			{
				char char1 = word1.charAt(i);
				for (int j = 0; j < wordlen2; j++) 
				{
					char char2 = word2.charAt(j);
		 
					//checking last 2 characters for its equality
					if (char1 == char2) 
					{
						//update distanceArray
						distanceArray[i + 1][j + 1] = distanceArray[i][j];
					} 
					// Find the minimum cost from all the sub - problems
					else 
					{
						int replace = distanceArray[i][j] + 1;
						int insert = distanceArray[i][j + 1] + 1;
						int delete = distanceArray[i + 1][j] + 1;
						
						int min = 0;
						
						if(replace > insert)
							min = insert;
						else
							min = replace;
						
						if(delete < min)
							min = delete;
						
						distanceArray[i + 1][j + 1] = min;
					}
				}
			}
		 
			return distanceArray[wordlen1][wordlen2];
		}
		
}

