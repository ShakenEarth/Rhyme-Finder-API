
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

/**@author Thomas Lisankie*/

public class RhymeDictionaryAssembler {
	
	public static void main(String[] args){
		ArrayList<Phoneme> phonemes = null;
		//1
		List<String> linesOfDictionary = null;
	//loads all the lines in the CMU Phonemic Dictionary. Each line contains a word and its phonemic translation.
		try{
			linesOfDictionary = Files.readAllLines(Paths.get("cmudict-0.7b_modified.txt"), Charset.defaultCharset());
			
		 	for (String line : linesOfDictionary) {
			 	System.out.println(line);
         	}
		}catch(Exception e){
			
			System.out.println("there was an exception");
		
		}
		
		//2
		//ArrayList for the word names
		ArrayList<String> wordNames = new ArrayList<String>();
		
		/**ArrayList of lists of phonemes that compose a word**/
		ArrayList<ArrayList<Phoneme>> listsOfPhonemesForWords = new ArrayList<ArrayList<Phoneme>>();
		String word = "";
		
		for (int i = 0; i < linesOfDictionary.size(); i++){
			
			String lineBeingExamined = linesOfDictionary.get(i);
			int indexOfCharBeingExamined = 0;
			for (int j = 0; j < lineBeingExamined.length(); j++){
				
				indexOfCharBeingExamined = j;
				char charBeingExamined = lineBeingExamined.charAt(j);
				if(charBeingExamined != ' '){
					
					word = word + charBeingExamined;
					
				}else{
					
					wordNames.add(word);
					word = "";
					break;
					
				}
				
			}
			
			indexOfCharBeingExamined = indexOfCharBeingExamined + 2;
			phonemes = new ArrayList<Phoneme>();
			Phoneme phoneme = new Phoneme();
			String phonemeName = "";
			for (int k = indexOfCharBeingExamined; k < lineBeingExamined.length(); k++){
				
				char charBeingExamined = lineBeingExamined.charAt(k);
				//if the character is a letter
				if(Character.isLetter(charBeingExamined)
						/*this didn't work for whatever reason:
						 * charBeingExamined != ' ' || Character.isDigit(charBeingExamined) == false*/){ 
					
					phonemeName = phonemeName + charBeingExamined;
					System.out.println(phonemeName);
					
				}else if(Character.isDigit(charBeingExamined)){
					
					int stress = Character.getNumericValue(charBeingExamined);
					phoneme.setStress(stress);
					
				}else if(charBeingExamined == ' '){
					
					phoneme.setPhoneme(phonemeName);
					phonemes.add(phoneme);
					phoneme = new Phoneme();
					phonemeName = "";
					
					
				}else{}
				
			}
			
			//this is for the last phoneme
			phoneme.setPhoneme(phonemeName);
			phonemes.add(phoneme);
			phoneme = new Phoneme();
			phonemeName = "";
			
			listsOfPhonemesForWords.add(phonemes);
			
		}
		
		
		for(int h = 0; h < wordNames.size(); h++){
			
			System.out.println(wordNames.get(h));
			ArrayList<Phoneme> listSample = listsOfPhonemesForWords.get(h);
			
			for(int l = 0; l < listSample.size(); l++){
				
				System.out.println(listSample.get(l).getPhoneme());
				
			}
			
		}
		
		//3
		ArrayList<Word> anchorWords = new ArrayList<Word>();
		
		for(int f = 0; f < wordNames.size(); f++){
			
			anchorWords.add(new Word((wordNames.get(f)).toLowerCase(), listsOfPhonemesForWords.get(f)));
			
		}
		
		for(int q = 0; q < wordNames.size(); q++){
			
			System.out.println("Word Name (Anchor):");
			System.out.println(anchorWords.get(q).getWordName());
			System.out.println("Phonemes (Anchor):");
			for(int i = 0; i < anchorWords.get(q).getListOfPhonemes().size(); i++){
				
				System.out.println(anchorWords.get(q).getListOfPhonemes().get(i).getPhoneme() + ", " 
						+ anchorWords.get(q).getListOfPhonemes().get(i).getStress());
				
			}
			
		}
		
		//4
		ArrayList<Word> satelliteWords = new ArrayList<Word>();
		for(int b = 0; b < wordNames.size(); b++){
			
			satelliteWords.add(anchorWords.get(b));
			
		}
		
		for(int q = 0; q < wordNames.size(); q++){
			
			System.out.println("Word Name (Satellite):");
			System.out.println(anchorWords.get(q).getWordName());
			System.out.println("Phonemes (Satellite):");
			for(int i = 0; i < satelliteWords.get(q).getListOfPhonemes().size(); i++){
				
				System.out.println(satelliteWords.get(q).getListOfPhonemes().get(i).getPhoneme() + ", " 
				+ satelliteWords.get(q).getListOfPhonemes().get(i).getStress());
				
			}
			
		}
		
		System.out.println(satelliteWords.get(20).getWordName()); //prints out "aardvarks"
		
		//find Rhyme Value/Percentile for all Words
		for(int i = 0; i < anchorWords.size(); i++){
			
			for(int j = 0; j < satelliteWords.size(); j++){
				
				findRhymeValueAndPercentileForWords(anchorWords.get(i), satelliteWords.get(j));
				
			}
			
		}

	}

	/**This method goes through the entire process of finding how well two words rhyme with one another.
	 * How well two words rhyme is given by the Rhyme Percentile returned. The higher the Rhyme Percentile, the better they rhyme.*/
	public static double findRhymeValueAndPercentileForWords(Word anchor, Word satellite) {
		
		double rhymeValue = 0.0;
		double rhymePercentile = 0.0;
		Word word = null;
		if(anchor.getListOfPhonemes().size() == satellite.getListOfPhonemes().size()){
			
			for(int s = 0; s < anchor.getListOfPhonemes().size(); s++){
				
				rhymeValue = rhymeValue + findRVBetweenPhonemes(anchor.getListOfPhonemes().get(s), 
						satellite.getListOfPhonemes().get(s));
				
			}
			
			/*rhyme percentile for words of same phonemic length uses the anchor word as the denominator. This is to keep the focus on
			 * the anchor word which is the focus word*/
			rhymePercentile = findRhymePercentile(rhymeValue, anchor); 
			
		}else{//do ideal Rhyme Value process
			
			Word shorterWord = null;
			Word longerWord = null;
			
			//these conditionals find which word is longer and which is shorter
			if(anchor.getListOfPhonemes().size() < satellite.getListOfPhonemes().size()){
				
				shorterWord = anchor;
				longerWord = satellite;
				
			}else{
				
				shorterWord = satellite;
				longerWord = anchor;
				
			}
			
			double idealRhymeValue = 0.0;
			ArrayList<IndexSet> listOfIndexSets = new ArrayList<IndexSet>();
			boolean firstSearch = true;
			for(int t = 0; t < shorterWord.getListOfPhonemes().size(); t++){
				
				Phoneme shorterWordPhoneme = shorterWord.getListOfPhonemes().get(t);
				if(firstSearch == true){
					
					for(int u = 0; u < longerWord.getListOfPhonemes().size(); u++){
						
						Phoneme longerWordPhoneme = longerWord.getListOfPhonemes().get(t);
						double RVBetweenPhonemes = findRVBetweenPhonemes(shorterWordPhoneme, longerWordPhoneme);
						if(RVBetweenPhonemes > 0){
							
							listOfIndexSets.add(new IndexSet(u, RVBetweenPhonemes));
							
							
						}
						
					}
					
					firstSearch = false;
					
				}else{
					
					for(int v = 0; v < listOfIndexSets.size(); v++){
						
						//okay, I'm gonna stop here for the night. Some of the code may be whack since I was really tired.
						//Also, fix this phenomenon http://i.imgur.com/jxjip2O.jpg
						
						int startIndexBeingExamined = listOfIndexSets.get(v).getIndexes().get(listOfIndexSets.size()-1);
						for(int w = startIndexBeingExamined; 
								w < longerWord.getListOfPhonemes().size() - startIndexBeingExamined - 1; w++){
							
							Phoneme longerWordPhoneme = longerWord.getListOfPhonemes().get(t);
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return rhymePercentile;
		
	}
	
	/**Finds the Rhyme Value that a word has with itself (homophonic Rhyme Value) and then finds the percentage that the 
	 * actual Rhyme Value matches with the homophonic RV*/
	public static double findRhymePercentile(double rhymeValue, Word longerWord){
		
		double homophonicRhymeValue = 0.0;
		double rhymePercentile = 0.0;
		
		for(int i = 0; i < longerWord.getListOfPhonemes().size(); i++){
			
			homophonicRhymeValue = homophonicRhymeValue + 
					findRVBetweenPhonemes(longerWord.getListOfPhonemes().get(i), longerWord.getListOfPhonemes().get(i));
			
		}
		
		rhymePercentile = rhymeValue / homophonicRhymeValue;
		
		return rhymePercentile;
		
	}
	
	/**Takes in two Phonemes and finds the amount that should be added to the Rhyme Value based on how well the two Phonemes match.*/
	public static double findRVBetweenPhonemes(Phoneme p1, Phoneme p2){
		
		if(p1.isAVowelPhoneme() && p2.isAVowelPhoneme()){
			
			if(p1.isEqualTo(p2)){
				
				return 2.0;
				
			}else{
				
				return 1.0;
				
			}
		}else if(!p1.isAVowelPhoneme() && !p1.isAVowelPhoneme()){
			
			if(p1.isEqualTo(p2)){
				
				return 1.0;
				
			}else{
				
				return 0.5;
				
			}
			
		}else{
			
			return 0.0;
			
		}
		
	}
}
