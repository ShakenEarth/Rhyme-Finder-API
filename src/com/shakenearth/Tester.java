package com.shakenearth;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.shakenearth.rhyme_essentials.*;

public class Tester {
	
	public static void main(String[] args){
		
		final int TESTING = 3;
		
		if(TESTING == 0){
			
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Enter a word: ");
			String wordSpelling = reader.nextLine();
			reader.close();
			
			RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt");
			WordName name = new WordName(wordSpelling);
			
			Word word = new Word(wordSpelling, finder.getDictionary().get(name));
			System.out.println("Initial Syllable Count: " + word.getNumOfSyllables());
			
			for(int i = 0; i < word.getListOfSyllables().size(); i++){
				
				System.out.println("Syllable " + (i+1) + ": ");
				word.getListOfSyllables().get(i).printListOfPhonemes();
				
			}
			
		}else if(TESTING == 1){
			
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter first word: ");
			String firstWordSpelling = reader.nextLine();
			
			System.out.println("Enter second word: ");
			String secondWordSpelling = reader.nextLine();
			reader.close();
			
			RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt");
			
			Word firstWord = new Word(firstWordSpelling, finder.getDictionary().get(new WordName(firstWordSpelling)));
			Word secondWord = new Word(secondWordSpelling, finder.getDictionary().get(new WordName(secondWordSpelling)));
			
			System.out.println(finder.findRhymeValueAndPercentileForWords(firstWord, secondWord) * 100 + "%");
			
		}else if (TESTING == 2){
			
			List<String> linesOfDictionary = null;
			;
			try{
				linesOfDictionary = Files.readAllLines(Paths.get("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt"), Charset.defaultCharset());
				
			}catch(Exception e){
				
				System.out.println("there was an exception");
			
			}
			
			int[] syllableCounts = new int[20];
			
			for(int l = 0; l < linesOfDictionary.size(); l++){
				
				String[] components = linesOfDictionary.get(l).split("  ");
				
				if(components.length != 3){
					
					System.out.println("The lines aren't separated by two spaces.");
					break;
					
				}
				
				syllableCounts[Integer.parseInt(components[2]) - 1] = syllableCounts[Integer.parseInt(components[2]) - 1] + 1;
				
			}
			
			for(int i = 0; i < syllableCounts.length; i++){
				if(i != 0){
					
					System.out.println(syllableCounts[i] + " words with " + (i+1) + " syllables.");
					
				}else{
					
					System.out.println(syllableCounts[i] + " words with " + (i+1) + " syllable.");
					
				}
				
			}
			
		}else if(TESTING == 3){
			
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Enter a word to find rhymes for: ");
			String wordSpelling = reader.nextLine();
			reader.close();
			
			RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt");
			ArrayList<WordName> returnedWords = finder.getTrie().getWordsWithSimilarVowelStructure(new WordName(wordSpelling));
			
			Word originalWord = new Word(wordSpelling, finder.getDictionary().get(new WordName(wordSpelling)));
			
			for(int i = 0; i < returnedWords.size(); i++){
				
				Word secondWord = new Word(returnedWords.get(i), finder.getDictionary().get(returnedWords.get(i)));
				
				System.out.println(returnedWords.get(i).getWordName() + ", " + finder.findRhymeValueAndPercentileForWords(originalWord, secondWord) * 100 + "%");
				
			}
			
		}
		
	}

}
