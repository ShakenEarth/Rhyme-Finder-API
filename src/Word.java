import java.util.*;

/**@author Thomas Lisankie*/

public class Word {
	
	private String wordName = "";
	private ArrayList<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	
	public Word(String wordName, ArrayList<Phoneme> phonemes) {
		
		this.setWordName(wordName);
		setListOfPhonemes(phonemes);
		
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

}
