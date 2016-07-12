
package com.shakenearth.rhyme_essentials;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;

/**
 * Class for representing words. This includes a word's spelling, a list of the Phonemes that compose it, and the number of syllables 
 * that make up the word.
 * @author Thomas Lisankie
 */
public class Word extends PhonemeSequence implements Serializable{
	
	private String wordName = "";
	private List<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	private List<Syllable> listOfSyllables = new ArrayList<Syllable>();
	private ArrayList<Point2D.Double> wordsThisRhymesWith = new ArrayList<Point2D.Double>();
	private int numOfSyllables = 0;
	
	/**Creates a new Word object using the spelling of the word itself as well as a string of the Phonemes that compose it 
	 * (separated by a space character).
	 * @param wordName The spelling of the word
	 * @param phonemes A string of the Phonemes that compose it (separated by a space character)*/
	public Word(String wordName, String phonemeString){
		
		this.wordName = wordName;
		String[] phonemes = phonemeString.split(" ");
		for(int i = 0; i < phonemes.length; i++){
			
			listOfPhonemes.add(new Phoneme(phonemes[i]));
			if(listOfPhonemes.get(i).isAVowelPhoneme()){
				
				numOfSyllables = numOfSyllables + 1;
				
			}
			
		}
		
		splitIntoSyllables();
		
	}
	
	private void splitIntoSyllables(){
		
		Syllable currentSyllable = null;
		ArrayList<Phoneme> phonemesForCurrentSyllable = new ArrayList<Phoneme>();
		ArrayList<Integer> vowelIndices = new ArrayList<Integer>();
		
		for(int i = 0; i < listOfPhonemes.size(); i++){
			
			Phoneme phonemeBeingExamined = listOfPhonemes.get(i);
			
			if(phonemeBeingExamined.isAVowelPhoneme()){
				
				if(listOfPhonemes.get(i+1).isAVowelPhoneme() == false){ /*if we're not reaching the end of the word and the next phoneme is a consonant 
				follow the consonant rules to see where to split*/
					
					if(i + 2 != listOfPhonemes.size()-1){
						
						if(phonemeBeingExamined.getPhoneme().equals("AA") || phonemeBeingExamined.getPhoneme().equals("AO")
								|| phonemeBeingExamined.getPhoneme().equals("AW") || phonemeBeingExamined.getPhoneme().equals("AY")
								|| phonemeBeingExamined.getPhoneme().equals("EY") || phonemeBeingExamined.getPhoneme().equals("IY")
								|| phonemeBeingExamined.getPhoneme().equals("OW") || phonemeBeingExamined.getPhoneme().equals("OY")
								|| phonemeBeingExamined.getPhoneme().equals("UW")){
							
							phonemesForCurrentSyllable.add(phonemeBeingExamined);
							currentSyllable = new Syllable(phonemesForCurrentSyllable);
							listOfSyllables.add(currentSyllable);
							phonemesForCurrentSyllable = new ArrayList<Phoneme>();
							currentSyllable = null;
							
						}else{
							
							phonemesForCurrentSyllable.add(phonemeBeingExamined);
							phonemesForCurrentSyllable.add(listOfPhonemes.get(i+1));
							i = i + 1;
							currentSyllable = new Syllable(phonemesForCurrentSyllable);
							listOfSyllables.add(currentSyllable);
							phonemesForCurrentSyllable = new ArrayList<Phoneme>();
							currentSyllable = null;
							
						}
						
					}else{/*make sure to include that final consonant as well*/
						
						if(listOfPhonemes.get(i + 2).isAVowelPhoneme() == false){
							
							phonemesForCurrentSyllable.add(phonemeBeingExamined);
							phonemesForCurrentSyllable.add(listOfPhonemes.get(i+1));
							phonemesForCurrentSyllable.add(listOfPhonemes.get(i+2));
							i = i + 2;
							currentSyllable = new Syllable(phonemesForCurrentSyllable);
							listOfSyllables.add(currentSyllable);
							phonemesForCurrentSyllable = new ArrayList<Phoneme>();
							currentSyllable = null;
							
						}else{
							
							phonemesForCurrentSyllable.add(phonemeBeingExamined);
							currentSyllable = new Syllable(phonemesForCurrentSyllable);
							listOfSyllables.add(currentSyllable);
							phonemesForCurrentSyllable = new ArrayList<Phoneme>();
							phonemesForCurrentSyllable.add(listOfPhonemes.get(i+1));
							phonemesForCurrentSyllable.add(listOfPhonemes.get(i+2));
							i = i + 2;
							currentSyllable = new Syllable(phonemesForCurrentSyllable);
							listOfSyllables.add(currentSyllable);
							phonemesForCurrentSyllable = new ArrayList<Phoneme>();
							currentSyllable = null;
							
						}
						
					}
					
				}else{/*it's a vowel so you're gonna have to split*/
					
					phonemesForCurrentSyllable.add(phonemeBeingExamined);
					currentSyllable = new Syllable(phonemesForCurrentSyllable);
					listOfSyllables.add(currentSyllable);
					phonemesForCurrentSyllable = new ArrayList<Phoneme>();
					currentSyllable = null;
					
				}
				
			}else{
				
				phonemesForCurrentSyllable.add(phonemeBeingExamined);
				
			}
			
		}
		
		listOfPhonemes = null;
		
	}
	
	/**Creates a new Word object using the spelling of the word itself as well as a list of the Phonemes that compose it.
	 * @param wordName The spelling of the word
	 * @param phonemes A list of the Phonemes that compose the word*/
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
	
	public List<Syllable> getListOfSyllables() {
		return listOfSyllables;
	}

	public void setListOfSyllables(List<Syllable> listOfSyllables) {
		this.listOfSyllables = listOfSyllables;
	}

	public ArrayList<Point2D.Double> getWordsThisRhymesWith() {
		return wordsThisRhymesWith;
	}

	public void setWordsThisRhymesWith(ArrayList<Point2D.Double> wordsThisRhymesWith) {
		this.wordsThisRhymesWith = wordsThisRhymesWith;
	}
	
	public void printListOfPhonemes(){
		
		for(int i = 0; i < getListOfPhonemes().size(); i++){
			
//			RhymeFinder.debugPrint(getListOfPhonemes().get(i).getPhoneme() + ", " 
//					+ getListOfPhonemes().get(i).getStress());
			
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
