package com.shakenearth;

import java.util.*;

import com.shakenearth.rhyme_essentials.*;

public class Tester {
	
	public static void main(String[] args){
		
		RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt");
		List<Phoneme> nemes = finder.getTrie().getWord("ababa").getListOfPhonemes();
		
		for(int i = 0; i < nemes.size(); i++){
			
			System.out.println(nemes.get(i).getPhoneme());
			
		}
		
	}

}
