
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class SampleReader {
	
	public static void main(String[] args){
		
		//1
		List<String> linesOfDictionary = null;
	//loads all the lines in the CMU Phonemic Dictionary. Each line contains a word and its phonemic translation.
		try{
			linesOfDictionary = Files.readAllLines(Paths.get("cmudictOneLiner.txt"), Charset.defaultCharset());
			
		 	for (String line : linesOfDictionary) {
			 	System.out.println(line);
         	}
		}catch(Exception e){
			
			System.out.println("there was an exception");
		
		}
		
		//2
		//ArrayList for the word names
		ArrayList<String> wordNames = new ArrayList<String>();
		
		/**ArrayList of lists of phonemes that compose a word*/
		ArrayList<ArrayList<Phoneme>> listsOfPhonemesForWords = new ArrayList<ArrayList<Phoneme>>();
		String word = "";
		
		for (int i = 0; i < linesOfDictionary.size(); i++){
			
			String wordBeingExamined = linesOfDictionary.get(i);
			int indexOfCharBeingExamined = 0;
			for (int j = 0; j < wordBeingExamined.length(); j++){
				
				indexOfCharBeingExamined = j;
				char charBeingExamined = wordBeingExamined.charAt(j);
				if(charBeingExamined != ' '){
					
					word = word + charBeingExamined;
					
				}else{
					
					wordNames.add(word);
					break;
					
				}
				
			}
			
			indexOfCharBeingExamined = indexOfCharBeingExamined + 2;
			ArrayList<Phoneme> phonemes = new ArrayList<Phoneme>();
			for (int k = indexOfCharBeingExamined; k < wordBeingExamined.length(); k++){
				
				Phoneme phoneme = new Phoneme();
				String phonemeName = "";
				char charBeingExamined = wordBeingExamined.charAt(k);
				if(charBeingExamined != ' ' || Character.isDigit(charBeingExamined) == false){ 
					
					phonemeName = phonemeName + charBeingExamined;
					
				}else if(Character.isDigit(charBeingExamined)){
					
					int stress = charBeingExamined;
					phoneme.setStress(stress);
					
				}else if(charBeingExamined == ' '){
					
					phoneme.setPhoneme(phonemeName);
					phonemes.add(phoneme);
					phoneme = new Phoneme();
					phonemeName = "";
					
					
				}
				
			}
			
			listsOfPhonemesForWords.add(phonemes);
			
		}

	}
}
