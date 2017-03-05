
package com.shakenearth.rhyme_essentials;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;

/**
 * Class for representing words. This includes a word's spelling, a list of the Phonemes that compose it, and the number of syllables 
 * that make up the word.
 * @author Thomas Lisankie
 */
public class Word implements Serializable{
	
	private String wordName = "";
	private List<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	private ArrayList<Point2D.Double> wordsThisRhymesWith = new ArrayList<Point2D.Double>();
	
	public Word(String wordName, String phonemeString){
		
		this.wordName = wordName;
		String[] phonemes = phonemeString.split(" ");
		for(int i = 0; i < phonemes.length; i++){
			
			listOfPhonemes.add(new Phoneme(phonemes[i]));
			
		}
		
	}
	
	/**Creates a new Word object using the spelling of the word itself as well as a list of the Phonemes that compose it.
	 * @param wordName The spelling of the word
	 * @param phonemes A list of the Phonemes that compose the word*/
	public Word(String wordName, List<Phoneme> phonemes) {
		
		this.setWordName(wordName);
		setListOfPhonemes(phonemes);
		
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public List<Phoneme> getListOfPhonemes() {
		return listOfPhonemes;
	}

	public void setListOfPhonemes(List<Phoneme> listOfPhonemes) {
		this.listOfPhonemes = listOfPhonemes;
	}

	public ArrayList<Point2D.Double> getWordsThisRhymesWith() {
		return wordsThisRhymesWith;
	}

	public void setWordsThisRhymesWith(ArrayList<Point2D.Double> wordsThisRhymesWith) {
		this.wordsThisRhymesWith = wordsThisRhymesWith;
	}
	
	public String toString(){
		
		String wordInfo = wordName + ": ";
		
		for(int i = 0; i < listOfPhonemes.size(); i++){
			
			if(i != listOfPhonemes.size()-1){
				
				wordInfo = wordInfo + listOfPhonemes.get(i).getPhoneme() + ", ";
				
			}else{
				
				wordInfo = wordInfo + listOfPhonemes.get(i).getPhoneme();
				
			}
			
		}
		
		return wordInfo;
		
	}

	public List<Phoneme> getVowelPhonemes() {
		
		List<Phoneme> vowelPhonemes = new ArrayList<Phoneme>();
		
		for(int i = 0; i < listOfPhonemes.size(); i++){
			
			if(listOfPhonemes.get(i).isAVowelPhoneme()){
				
				vowelPhonemes.add(listOfPhonemes.get(i));
				
			}
			
		}
		
		return vowelPhonemes;
		
	}
	
	public String getVowelPhonemesAsString(){
		
		String vowelPhonemeString = "";
		
		List<Phoneme> vowelPhonemes = getVowelPhonemes();
		
		for(int i = 0; i < vowelPhonemes.size(); i++){
			
			vowelPhonemeString = vowelPhonemeString + vowelPhonemes.get(i).getPhoneme() + " ";
			
		}
		
		return vowelPhonemeString;
		
	}

}