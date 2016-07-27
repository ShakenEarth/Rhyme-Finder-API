package com.shakenearth.rhyme_essentials;
import java.util.*;

public class Syllable extends PhonemeSequence {

	private List<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	
	public Syllable(List<Phoneme> phonemes){
		
		listOfPhonemes = phonemes;
		
	}
	
	public void printListOfPhonemes(){
		
		for(int i = 0; i < listOfPhonemes.size(); i++){
			
			System.out.println(listOfPhonemes.get(i).getPhoneme() + " ");
			
		}
		
	}
	
	public void setListOfPhonemes(List<Phoneme> listOfPhonemes) {
		this.listOfPhonemes = listOfPhonemes;
	}
	
	public List<Phoneme> getListOfPhonemes() {
		return listOfPhonemes;
	}
	
}
