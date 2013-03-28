
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 
 * @author Michael Mariano
 * @version: MARCH 2013 v2.0
 * @description:Word Finder/Solver
 * 
 *
 */
public class WordFinder {

	private static class Word
	{
		private String value;
		private int points;

		private Word(String word)
		{
			this.value=word;
			this.points=generatePoint(word);
		}
		private int generatePoint(String str)
		{
			char [] word= str.toCharArray();
			int totalPoints=0;
			for(int i=0;i<word.length;i++)
			{
				totalPoints+=letterVal.get(Character.toString(word[i]).toLowerCase());
			}
			return totalPoints;
		}

		private String getWord(){
			return this.value;
		}
		
		private int getPoints(){
			return this.points;
		}

	}
	//List of all possible words and points
	private static HashMap<String,Integer> wordScore=new HashMap<String,Integer>();
	
	//Stores the numerical values of all the letters
	private static HashMap<String,Integer> letterVal= new HashMap<String,Integer>();

	public static void main(String []args)
	{
		try{
			Scanner keyboard= new Scanner(System.in);

			System.out.print("Enter your tiles up to 20 letters:");
			String wordAttempt=keyboard.next();

			while(wordAttempt.length()>20){
				System.out.print("Incorrect input!: Please enter up to 20 letters:");
				wordAttempt=keyboard.next();
			}

			int wordLen=wordAttempt.length();
			boolean [] used= new boolean[wordLen];

			//generates the dictionary
			Set<String>dictionary= makeDictionary(wordLen);

			//generate the letter values Words With Friends
			generateLetterScoreWordsWithFriends();

			//generates all permutations up to two letters
			for(int i=0;i<wordLen-1;i++){
				permuteStringSolve(i,"",used,wordAttempt,dictionary);
			}

			System.out.println("Here is a list of all possible words you can make with these letters:");
			System.out.println("---------------------------------------------------------------------");

			ArrayList<Map.Entry<String,Integer>> wordsFound= new ArrayList<Map.Entry<String,Integer>>(wordScore.entrySet());

			Collections.sort(wordsFound, new Comparator<Map.Entry<String,Integer>>() {
				@Override
				public int compare(Map.Entry<String,Integer> num1, Map.Entry<String,Integer> num2) {
					return num2.getValue()-num1.getValue();
				}
			});

			for(Map.Entry<String,Integer> wrdPnt:wordsFound){
				System.out.println(String.format("%-11s+ %s" , wrdPnt.getKey(), wrdPnt.getValue() ) );
			}
		}
		catch(Exception e){
			System.out.print(e);
		}
	}
	/**
	 * Generates the value of each letter in Words with Friends
	 */
	public static void generateLetterScoreWordsWithFriends()
	{
		letterVal.put("a",1);
		letterVal.put("b",4);
		letterVal.put("c",4);
		letterVal.put("d",2);
		letterVal.put("e",1);
		letterVal.put("f",4);
		letterVal.put("g",3);
		letterVal.put("h",3);
		letterVal.put("i",1);
		letterVal.put("j",10);
		letterVal.put("k",5);
		letterVal.put("l",2);
		letterVal.put("m",4);
		letterVal.put("n",2);
		letterVal.put("o",1);
		letterVal.put("p",4);
		letterVal.put("q",10);
		letterVal.put("r",1);
		letterVal.put("s",1);
		letterVal.put("t",1);
		letterVal.put("u",2);
		letterVal.put("v",5);
		letterVal.put("w",4);
		letterVal.put("x",8);
		letterVal.put("y",3);
		letterVal.put("z",10);
	}
	/**
	 * Creates the Dictionary as a HashSet for quick lookup
	 * @param wordLen-The length of the word input by the user
	 * @return-The HashSet, which is the dictionary
	 * @throws FileNotFoundException
	 */
	public static Set<String> makeDictionary(int wordLen) throws FileNotFoundException
	{
		Set<String> dictionary = new HashSet<String>();
		Scanner filescan = new Scanner(new File("src/enable1.txt"));
		while (filescan.hasNext()){
			String word= filescan.nextLine();
			if(word.length()<=wordLen)
				dictionary.add(word.toLowerCase());
		}
		return dictionary;
	}

	/**
	 * Computes all the permutations of the given characters in the String and checks if they are words
	 * @param level-The number of characters
	 * @param permuted-The permuted string
	 * @param used-which letters where already used
	 * @param original-The original string to be permuted
	 * @param dictionary-The dictionary to look-up the word
	 */
	public static void permuteStringSolve(int level, String permuted,boolean used[], String original,Set <String>dictionary) 
	{
		int length = original.length();
		if (level == length){
			if(dictionary.contains(permuted)){
				if(wordScore.get(permuted)==null){
					Word wrd=new Word(permuted);
					wordScore.put(wrd.getWord(),wrd.getPoints());
				}
			}
		}
		else{
			for (int i = 0; i < length; i++){
				if (!used[i]){
					used[i] = true;
					permuteStringSolve(level + 1, permuted + original.charAt(i),used, original,dictionary);
					used[i] = false;
				}
			}
		}
	}

}


