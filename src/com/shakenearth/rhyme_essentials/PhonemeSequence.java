package com.shakenearth.rhyme_essentials;

import java.util.ArrayList;
import java.util.List;

public abstract class PhonemeSequence {
	
	private List<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	
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
