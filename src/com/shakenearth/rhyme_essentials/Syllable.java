package com.shakenearth.rhyme_essentials;
import java.util.*;

public class Syllable extends PhonemeSequence {

	private List<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	
	public Syllable(List<Phoneme> phonemes){
		
		listOfPhonemes = phonemes;
		
	}
	
}
