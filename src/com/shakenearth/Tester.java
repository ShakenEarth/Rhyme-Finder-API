package com.shakenearth;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.shakenearth.rhyme_essentials.*;

public class Tester {
	
	public static void main(String[] args){
		
		final int TESTING = 0;
		
		if(TESTING == 0){ //comparing two words and/or phrases
			
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter first word: ");
			String firstWordSpelling = reader.nextLine();
			
			System.out.println("Enter second word: ");
			String secondWordSpelling = reader.nextLine();
			reader.close();
			
			RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt", 
					"/Users/thomas/Desktop/Dev/rap-writer/src/features.txt");
			
			String[] firstWordComponents = firstWordSpelling.split(" ");
			String[] secondWordComponents = secondWordSpelling.split(" ");
			
			String firstWordPhonemeString = "";
			String secondWordPhonemeString = "";
			
			for(int i = 0; i < firstWordComponents.length; i++){
				
				firstWordPhonemeString = firstWordPhonemeString + finder.getDictionary().get(firstWordComponents[i].toLowerCase()) + " ";
				
			}
			
			for(int i = 0; i < secondWordComponents.length; i++){
				
				secondWordPhonemeString = secondWordPhonemeString + finder.getDictionary().get(secondWordComponents[i].toLowerCase()) + " ";
				
			}
			
			Word firstWord = new Word(firstWordSpelling, firstWordPhonemeString);
			Word secondWord = new Word(secondWordSpelling, secondWordPhonemeString);
			
			System.out.println(finder.findRhymeValueAndPercentileForWords(firstWord, secondWord) * 100 + "%");
			
		}else if(TESTING == 1){ //finding rhyming words for a specific word or phrase
			
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Enter a word to find rhymes for: ");
			String wordSpelling = reader.nextLine();
			reader.close();
			
			RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt", 
					"/Users/thomas/Desktop/Dev/rap-writer/src/features.txt");
			
			String[] wordComponents = wordSpelling.split(" ");
			String wordPhonemeString = "";
			
			for(int i = 0; i < wordComponents.length; i++){
				
				wordPhonemeString = wordPhonemeString + finder.getDictionary().get(wordComponents[i].toLowerCase()) + " ";
				
			}
			
			Word firstWord = new Word(wordSpelling, wordPhonemeString);
			String vowelString = firstWord.getVowelPhonemesAsString();
			System.out.println(vowelString);
			int beginningIndex = finder.getStructureReference().get(vowelString);
			boolean nextStructFound = false;
			System.out.println(beginningIndex);
			
			int currentIndex = beginningIndex;
			
			while(nextStructFound == false){
				
				currentIndex = currentIndex + 1;
				
				String currentWord = finder.getWordList().get(currentIndex);
				Word newWord = new Word(currentWord, finder.getDictionary().get(currentWord));
				
				if(newWord.getVowelPhonemesAsString().equals(vowelString) == false){
					
					break;
					
				}else{
					
					Word secondWord = new Word(currentWord, finder.getDictionary().get(currentWord));
					
					System.out.println(currentWord + ", " + finder.findRhymeValueAndPercentileForWords(firstWord, secondWord) * 100 + "%");
					
				}
				
			}
			
		}
		
	}

}
