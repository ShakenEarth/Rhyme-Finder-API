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
	
	public void addPhoneme(Phoneme p){
		
		if(p.getPhoneme().equals("N")){
			
			p.setPhoneme("M");
			
		}
		
		listOfPhonemes.add(p);
		
	}
	
	public void setListOfPhonemes(List<Phoneme> listOfPhonemes) {
		this.listOfPhonemes = listOfPhonemes;
	}
	
	public List<Phoneme> getListOfPhonemes() {
		return listOfPhonemes;
	}
	
}
