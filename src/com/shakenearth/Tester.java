package com.shakenearth;

import java.util.*;

import com.shakenearth.rhyme_essentials.*;

public class Tester {
	
	public static void main(String[] args){
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a word: ");
		String wordSpelling = reader.nextLine();
		reader.close();
		
		RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt");
		System.out.println(finder.getDictionary().get(wordSpelling));
		Word word = new Word(wordSpelling, finder.getDictionary().get(wordSpelling));
		System.out.println("Initial Syllable Count: " + word.getNumOfSyllables());
		
		for(int i = 0; i < word.getListOfSyllables().size(); i++){
			
			System.out.println("Syllable " + (i+1) + ": ");
			word.getListOfSyllables().get(i).printListOfPhonemes();
			
		}
		
	}

}
