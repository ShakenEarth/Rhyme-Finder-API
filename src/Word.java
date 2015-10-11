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
	
	public Word(String wordName, ArrayList<Phoneme> phonemes) {
		
		this.setWordName(wordName);
		setListOfPhonemes(phonemes);
		
	}
	
	public void addWord(int index, double rhymePercentile){
		
		if(wordsThisRhymesWith.size() == 0){
			
			wordsThisRhymesWith.add(new Point2D.Double(index, rhymePercentile));
			
		}else{
			
			for(int i = 0; i < wordsThisRhymesWith.size(); i++){
				
				//do process to put Point in proper place based on RP
				
			}
			
		}
		
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

}
