
package com.shakenearth.rhyme_essentials;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;

/**
 * Class for representing words. This includes a word's spelling and its phonemic translation (sequence of phonemes).
 * @author Thomas Lisankie
 */

public class Word implements Serializable{
	
	private String wordName = "";
	private List<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	private ArrayList<Point2D.Double> wordsThisRhymesWith = new ArrayList<Point2D.Double>();
	private int numOfSyllables = 0;
	
	public Word(String wordName, List<Phoneme> phonemes) {
		
		//RhymeDictionaryAssembler.debugPrint("INITIAL SIZE: " + wordsThisRhymesWith.size());
		this.setWordName(wordName);
		setListOfPhonemes(phonemes);
		
		for(int i = 0; i < getListOfPhonemes().size(); i++){
			
			if(getListOfPhonemes().get(i).isAVowelPhoneme() == true){
				
				setNumOfSyllables(getNumOfSyllables() + 1);
				
			}
			
		}
		
	}
	
	public void addWordThisRhymesWith(int index, double rhymePercentile){
		
		
		Point2D.Double wordToBeInserted = new Point2D.Double(index, rhymePercentile);
		
		wordsThisRhymesWith.add(wordToBeInserted);
		
		Collections.sort(wordsThisRhymesWith, new Point2DCompare());
		
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
	
	public void printListOfPhonemes(){
		
		for(int i = 0; i < getListOfPhonemes().size(); i++){
			
			RhymeDictionaryAssembler.debugPrint(getListOfPhonemes().get(i).getPhoneme() + ", " 
					+ getListOfPhonemes().get(i).getStress());
			
		}
		
	}

	public int getNumOfSyllables() {
		return numOfSyllables;
	}

	public void setNumOfSyllables(int numOfSyllables) {
		this.numOfSyllables = numOfSyllables;
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

}
