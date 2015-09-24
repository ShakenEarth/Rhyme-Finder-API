import java.util.*;

public class Word {
	
	String wordName = "";
	ArrayList<Phoneme> listOfPhonemes = new ArrayList<Phoneme>();
	
	public Word(String wordName, ArrayList<Phoneme> phonemes) {
		
		this.wordName = wordName;
		listOfPhonemes = phonemes;
		
	}

}
