
import com.shakenearth.rhyme_essentials.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FindRPBetweenWords {
	
	public static void main(String[] args){
		
		System.out.println("Enter first word:");
		Scanner scanner = new Scanner(System.in);
		String string1 = scanner.nextLine();
		ArrayList<String> firstStrings = new ArrayList<String>();
		String wordToAdd1 = "";
		for(int i = 0; i < string1.length(); i++){
			
			if(string1.charAt(i) != ' '){
				
				wordToAdd1 = wordToAdd1 + string1.charAt(i);
				
			}else{
				
				firstStrings.add(wordToAdd1);
				wordToAdd1 = "";
				
			}
			
		}
		firstStrings.add(wordToAdd1);
		
		System.out.println("Enter second word:");
		String string2 = scanner.nextLine();
		ArrayList<String> secondStrings = new ArrayList<String>();
		String wordToAdd2 = "";
		for(int i = 0; i < string2.length(); i++){
			
			if(string2.charAt(i) != ' '){
				
				wordToAdd2 = wordToAdd2 + string2.charAt(i);
				
			}else{
				
				secondStrings.add(wordToAdd2);
				wordToAdd2 = "";
				
			}
			
		}
		secondStrings.add(wordToAdd2);
		
		RhymeFinder.buildWords();
		
		ArrayList<Word> firstWords = new ArrayList<Word>(), secondWords = new ArrayList<Word>();
		
		for(int w = 0; w < firstStrings.size(); w++){
			
			for(int i = 0; i < RhymeFinder.anchors.size(); i++){
				
				if(RhymeFinder.anchors.get(i).getWordName().equalsIgnoreCase(firstStrings.get(w))){
					
					firstWords.add(RhymeFinder.anchors.get(i));
					break;
					
				}
				
			}
			
		}
		
		for(int w = 0; w < secondStrings.size(); w++){
			
			for(int i = 0; i < RhymeFinder.anchors.size(); i++){
				
				if(RhymeFinder.anchors.get(i).getWordName().equalsIgnoreCase(secondStrings.get(w))){
					
					secondWords.add(RhymeFinder.anchors.get(i));
					break;
					
				}
				
			}
			
		}
		
		//create super-list of phonemes for each sentence entered
		ArrayList<Phoneme> firstListOfPhonemes = new ArrayList<Phoneme>(), secondListOfPhonemes = new ArrayList<Phoneme>();
		
		for(int w = 0; w < firstWords.size(); w++){
			
			for(int p = 0; p < firstWords.get(w).getListOfPhonemes().size(); p++){
				
				firstListOfPhonemes.add(firstWords.get(w).getListOfPhonemes().get(p));
				
			}
			
		}
		Word word1 = new Word(string1, firstListOfPhonemes);
		
		for(int w = 0; w < secondWords.size(); w++){
			
			for(int p = 0; p < secondWords.get(w).getListOfPhonemes().size(); p++){
				
				secondListOfPhonemes.add(secondWords.get(w).getListOfPhonemes().get(p));
				
			}
			
		}
		Word word2 = new Word(string2, secondListOfPhonemes);
		
		System.out.println(RhymeFinder.findRhymeValueAndPercentileForWords(word1, word2));
		
	}

}
