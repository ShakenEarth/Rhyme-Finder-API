import java.awt.geom.Point2D;
import java.util.*;

/**
 * Class for representing words. This includes a word's spelling and its phonemic translation (sequence of phonemes).
 * @author Thomas Lisankie
 */

public class Word {
	
	private String wordName = "";
	private ArrayList<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	private ArrayList<Point2D.Double> wordsThisRhymesWith = new ArrayList<Point2D.Double>();
	private int numOfSyllables = 0;
	
	public Word(String wordName, ArrayList<Phoneme> phonemes) {
		
		//RhymeDictionaryAssembler.debugPrint("INITIAL SIZE: " + wordsThisRhymesWith.size());
		this.setWordName(wordName);
		setListOfPhonemes(phonemes);
		
	}
	
	public void addWord(int index, double rhymePercentile){
		
		
		Point2D.Double wordToBeInserted = new Point2D.Double(index, rhymePercentile);
		wordsThisRhymesWith.add(wordToBeInserted);
		
		//TODO add some sort of way to sort
		/*
		if(wordsThisRhymesWith.size() == 0){
			
			RhymeDictionaryAssembler.debugPrint("First word to be added");
			wordsThisRhymesWith.add(wordToBeInserted);
			RhymeDictionaryAssembler.debugPrint("First word just added");
			
		}else{
			
			RhymeDictionaryAssembler.debugPrint("Words already in array");
			//goes through every word until it reaches a proper place to be inserted
			for(int i = 0; i < wordsThisRhymesWith.size(); i++){
				//RhymeDictionaryAssembler.debugPrint("Things");
				Point2D.Double wordBeingExamined = wordsThisRhymesWith.get(i);
				
				if(i+1 != wordsThisRhymesWith.size()){
					//RhymeDictionaryAssembler.debugPrint("otherThings");
					if(wordToBeInserted.getY() < wordBeingExamined.getY() && wordToBeInserted.getY() > wordsThisRhymesWith.get(i+1).getY()){
						
						int listSize = wordsThisRhymesWith.size();
						for(int j = listSize; j > j - i; j--){
							//this the music that saved my life
							System.out.println("j: " + j);
							if(j == listSize){
								
								wordsThisRhymesWith.add(wordsThisRhymesWith.get(j-1));
								
							}else if(j-1 > -1){
								
								wordsThisRhymesWith.set(j, wordsThisRhymesWith.get(j-1));
								
							}else{
								
								break;
								
							}
							
						}
						break;
						
					}
					
				}else{
					
					//RhymeDictionaryAssembler.debugPrint("otherotherThings");
					wordsThisRhymesWith.add(wordToBeInserted);
					break;
					
				}
				
			}
				
		}
		*/
		
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public ArrayList<Phoneme> getListOfPhonemes() {
		return listOfPhonemes;
	}

	public void setListOfPhonemes(ArrayList<Phoneme> listOfPhonemes) {
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

}
