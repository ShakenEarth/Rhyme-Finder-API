
package com.shakenearth.rhyme_essentials;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

/**@author Thomas Lisankie*/

public class RhymeFinder {
	
	public final static boolean DEBUGGING = false, SAMPLESIZE = false, CREATE_DICTIONARY = true;
	
	public static ArrayList<Word> anchors = null, words;
	private static RhymeDictionaryTrie trie = null;
	
	public static void main(String[] args){
		
		buildWords();
		
		//find Rhyme Value/Percentile for all Words
		
		if(CREATE_DICTIONARY == true){
			
			for(int i = 0; i < words.size(); i++){
				
				debugPrint("Anchor Index: " + i);
				Word currentWord = words.get(i);
				
				for(int j = 0; j < words.size(); j++){
					
					debugPrint("Satellite Index: " + j);
					
					double rhymePercentile = 0.0;
					
					if(j != i){
						
						rhymePercentile = findRhymeValueAndPercentileForWords(words.get(i), words.get(j));
					
					}
					
					if(rhymePercentile >= 0.4){
						
						currentWord.addWordThisRhymesWith(j, rhymePercentile);
						
					}
					
				}
				
				//save each word
				try{
					
			         FileOutputStream fileOut = new FileOutputStream("/Users/thomas/Google Drive/Rhyme Machine Resources/Words/" + i + ".prhyme");
			         
			         ObjectOutputStream out = new ObjectOutputStream(fileOut);
			         
			         out.writeObject(currentWord);
			         
			         out.close();
			         
			         fileOut.close();
			         
			         System.out.printf("correctly saved, ");
			         
			      }catch(IOException ex)
			      {
			          ex.printStackTrace();
			      }
				
				currentWord = null;
				
				System.out.println("done with '" + words.get(i).getWordName() + "'");
				
				for(int k = 0; k < words.get(i).getWordsThisRhymesWith().size(); k++){
					
					debugPrint(words.get(i).getWordsThisRhymesWith().get(k).getX() + ", " + words.get(i).getWordsThisRhymesWith().get(k).getY());
					
				}
				
			}
			
		}
				
				//now that the rhyme dictionary has been created, it's necessary to organize the words into a tree structure
		
	}
	
	public static void buildWords(){ //builds the list of Word objects that can be compared to one another
		
		ArrayList<Phoneme> phonemes = null;
		//1
		List<String> linesOfDictionary = null;
	//loads all the lines in the CMU Phonemic Dictionary. Each line contains a word and its phonemic translation.
		try{
			linesOfDictionary = Files.readAllLines(Paths.get("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt"), Charset.defaultCharset());
			
		}catch(Exception e){
			
			debugPrint("there was an exception");
		
		}
		
		//2 - Sets up resources for dictionary
		//ArrayList for the word names
		ArrayList<String> wordNames = new ArrayList<String>();
		
		/*ArrayList of lists of phonemes that compose a word*/
		ArrayList<ArrayList<Phoneme>> listsOfPhonemesForWords = new ArrayList<ArrayList<Phoneme>>();
		String word = "";
		
		//creates word names
		int spacesToSkip = 2;
		for (int i = 0; i < linesOfDictionary.size(); i++){
			
			spacesToSkip = 2;
			
			String lineBeingExamined = linesOfDictionary.get(i);
			int indexOfCharBeingExamined = 0;
			for (int j = 0; j < lineBeingExamined.length(); j++){
				
				indexOfCharBeingExamined = j;
				char charBeingExamined = lineBeingExamined.charAt(j);
				
				if(charBeingExamined != ' ' && charBeingExamined != '('){
					
					word = word + charBeingExamined;
					
				}else if(charBeingExamined == '('){
					
					spacesToSkip = spacesToSkip + 3;
					wordNames.add(word);
					word = "";
					break;
					
				}else{
					
					wordNames.add(word);
					word = "";
					break;
					
				}
				
			}
			
			indexOfCharBeingExamined = indexOfCharBeingExamined + spacesToSkip;
			phonemes = new ArrayList<Phoneme>();
			Phoneme phoneme = new Phoneme();
			String phonemeName = "";
			//creates list of phonemes
			for (int k = indexOfCharBeingExamined; k < lineBeingExamined.length(); k++){
				
				char charBeingExamined = lineBeingExamined.charAt(k);
				//if the character is a letter
				if(Character.isLetter(charBeingExamined)){ 
					
					phonemeName = phonemeName + charBeingExamined;
					//debugPrint(phonemeName);
					
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
		
		//3 - builds dictionary
		ArrayList<Word> anchorWords = new ArrayList<Word>();
		
		for(int f = 0; f < wordNames.size(); f++){
			//TODO add something to deal with alternative pronunciations of words
			anchorWords.add(new Word((wordNames.get(f)).toLowerCase(), listsOfPhonemesForWords.get(f)));
			
		}
		
		wordNames = null;
		listsOfPhonemesForWords = null;
		
		anchors = anchorWords;
		RhymeFinder.words = anchorWords;
		
		//now put this list of Words into a trie
		setTrie(new RhymeDictionaryTrie());
		
		for(int i = 0; i < anchors.size(); i++){
			
			getTrie().addWord(anchors.get(i));
			
		}
		
		anchors = null;
		
		//System.out.println(getTrie().trieRoot);
		
	}

	/**This method goes through the entire process of finding how well two words rhyme with one another.
	 * How well two words rhyme is given by the Rhyme Percentile returned. The higher the Rhyme Percentile, the better they rhyme.
	 * @return */
	public static double findRhymeValueAndPercentileForWords(Word anchor, Word satellite) {
		
		//System.out.println("---------------------------------------------");
		//System.out.println("Anchor: " + anchor.getWordName() + ", Satellite: " + satellite.getWordName());
		double rhymeValue = 0.0;
		double rhymePercentile = 0.0;
		
		if(anchor.getListOfPhonemes().size() == satellite.getListOfPhonemes().size()){
			
			/*rhyme percentile for words of same phonemic length uses the anchor word as the denominator. This is to keep the focus on
			 * the anchor word which is the focus word*/
			
			rhymePercentile = regularRhymeValue(anchor, satellite); 
			
		}else{//do ideal Rhyme Value process
			
			rhymePercentile = idealRhymeValue(anchor, satellite);
			
		}
		
		//System.out.println("Rhyme Percentile: " + rhymePercentile);
		
		return rhymePercentile;
		
	}
	
	
	
	public static double idealRhymeValue(Word anchor, Word satellite){
		
		debugPrint("IDEAL RHYME VALUE");
		
		debugPrint("Anchor:");
		anchor.printListOfPhonemes();
		debugPrint("Satellite:");
		satellite.printListOfPhonemes();
		
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
		
		debugPrint("Shorter Word: " + shorterWord.getWordName());
		debugPrint("Longer Word: " + longerWord.getWordName());
		
		double idealRhymeValue = 0.0;
		
		//start here
		//too much hatred for too long
		
		boolean firstSearch = true;
		boolean foundStartingIndex = false;
		ArrayList<Layer> layers = new ArrayList<Layer>();
		ArrayList<Node> nodesForThisLayer = new ArrayList<Node>();
		
		int pastLayerNum = 0;
		
		for(int s = 0; s < shorterWord.getListOfPhonemes().size(); s++){
			
			double weightTowardsWordEnd = 0.1;
			
			//firstSearch
			if(firstSearch == true){
				
				debugPrint("firstSearch");
				
				Node startNode = new Node();
				for(int l = 0; l < longerWord.getListOfPhonemes().size(); l++){
					
					double RVBetweenPhonemes = findRVBetweenPhonemes(shorterWord.getListOfPhonemes().get(s), longerWord.getListOfPhonemes().get(l), true, l * weightTowardsWordEnd);
					
					if(RVBetweenPhonemes > 0){
						
						foundStartingIndex = true;
						
						RVIndexPair indexSet = new RVIndexPair(l, RVBetweenPhonemes);
						
						startNode.addIndexSet(indexSet);
						
					}
					
				}
				
				if(foundStartingIndex == true){
					
					nodesForThisLayer.add(startNode);
					layers.add(new Layer(nodesForThisLayer));
					firstSearch = false;
					
				}
				
				nodesForThisLayer = new ArrayList<Node>();
				
				debugPrint(startNode.toString());
				
			}else{
				
				for(int n = 0; n < layers.get(pastLayerNum).getNodes().size(); n++){
					//loop for each node in the previous layer (aka every group of possibilites found)
					
					debugPrint("Layer: " + (pastLayerNum) + ", " + "Node: " + n);
					
					Node nodeBeingExamined = layers.get(pastLayerNum).getNodes().get(n);
					
					for(int i = 0; i < nodeBeingExamined.getIndexSets().size(); i++){
						//loop for the index sets in the node being examined
						
						RVIndexPair setBeingExamined = nodeBeingExamined.getIndexSets().get(i);
						Node childNode = new Node(); //node to be attached to the index set being examined.
						int indexToStartAt = setBeingExamined.getIndexes().get(0);
						debugPrint("setBeingExamined: " + setBeingExamined.toString());
						
						if(indexToStartAt + 1 == longerWord.getListOfPhonemes().size()){
							
							//do nothing
							
						}else{
							
							for(int l = indexToStartAt + 1; l < longerWord.getListOfPhonemes().size(); l++){
								
								double RVBetweenPhonemes = findRVBetweenPhonemes(shorterWord.getListOfPhonemes().get(s), longerWord.getListOfPhonemes().get(l), true, l*weightTowardsWordEnd);
								
								if(RVBetweenPhonemes > 0){
									
									RVIndexPair indexSet = new RVIndexPair(l, RVBetweenPhonemes);
									childNode.addIndexSet(indexSet);
									
								}
								
							}
							
							setBeingExamined.attachChildNode(childNode);
							nodesForThisLayer.add(childNode);
							debugPrint("childNode: " + childNode.toString());
							
						}
						
					}
					
				}
				
				layers.add(new Layer(nodesForThisLayer));
				nodesForThisLayer = new ArrayList<Node>();
				
				pastLayerNum = pastLayerNum + 1;
				
			}
			
		}
		
		//find best path
		
		RVIndexPair bestSet = null;
		Node nodeBeingExamined = null;
		
		for(int l = layers.size()-1; l >= 0; l--){
			
			for(int n = 0; n < layers.get(l).getNodes().size(); n++){
				
				nodeBeingExamined = layers.get(l).getNodes().get(n);
				
				if(nodeBeingExamined.getIndexSets().size()>0){
					
					nodeBeingExamined.findBestIndexSetAndSendItUp();
					
				}
				
			}
			
			if(l == 0 && layers.get(l).getNodes().size() == 1){
				
				debugPrint("LAYER IS 0");
				
				debugPrint("IndexSets in top node: " + nodeBeingExamined.toString());
				
				bestSet = nodeBeingExamined.getBestSet();
				
			}
			/*
			 * I don't have time to be down on myself
			 * I'm on top of things
			 * */
			
			debugPrint("l: " + l);
			
		}
		
		debugPrint("bestSet info: " + bestSet.toString());
		
		idealRhymeValue = bestSet.getRhymeValueForSet();
		
		double rhymeValue = idealRhymeValue;
		
		//subtract specing to get actual rhyme value
		
		debugPrint("deduction: " + findDeductionForIndexSet(bestSet, longerWord));
		
		rhymeValue = rhymeValue - findDeductionForIndexSet(bestSet, longerWord);
		
		return (double) findRhymePercentile(rhymeValue, longerWord);
		
	}
	
	public static double regularRhymeValue(Word anchor, Word satellite){
		
		debugPrint("REGULAR RHYME VALUE");
		
		debugPrint("Anchor:");
		anchor.printListOfPhonemes();
		debugPrint("Satellite:");
		satellite.printListOfPhonemes();
		
		boolean foundConsonantCluster = false;
		boolean anchorOrSatellite = false; //true if anchor, false if satellite.
		
		double rhymeValue = 0.0;
		
		Word newWord = null;
		
		double weightTowardsWordEnd = 0.1;
			
		if(anchor.getListOfPhonemes().get(0).isAVowelPhoneme() == false && anchor.getListOfPhonemes().get(1).isAVowelPhoneme() == false
				&& (!anchor.getListOfPhonemes().get(0).isEqualTo(satellite.getListOfPhonemes().get(0)) && !anchor.getListOfPhonemes().get(1).isEqualTo(satellite.getListOfPhonemes().get(1)))){
			
			foundConsonantCluster = true;
			
			List<Phoneme> shortenedListOfPhonemes = anchor.getListOfPhonemes().subList(1, anchor.getListOfPhonemes().size());
			
			newWord = new Word(anchor.getWordName(), shortenedListOfPhonemes);
			
			anchorOrSatellite = true;
				
		}else if(satellite.getListOfPhonemes().get(0).isAVowelPhoneme() == false && satellite.getListOfPhonemes().get(1).isAVowelPhoneme() == false
				&& (!anchor.getListOfPhonemes().get(0).isEqualTo(satellite.getListOfPhonemes().get(0)) && !anchor.getListOfPhonemes().get(1).isEqualTo(satellite.getListOfPhonemes().get(1)))){
			
			foundConsonantCluster = true;
			
			List<Phoneme> shortenedListOfPhonemes = satellite.getListOfPhonemes().subList(1, anchor.getListOfPhonemes().size());
			
			newWord = new Word(anchor.getWordName(), shortenedListOfPhonemes);
			
			anchorOrSatellite = false;
			
		}
		
		if(foundConsonantCluster == false){
			
			for(int s = 0; s < anchor.getListOfPhonemes().size(); s++){
			
				rhymeValue = (double) rhymeValue + (double)findRVBetweenPhonemes(anchor.getListOfPhonemes().get(s), 
						satellite.getListOfPhonemes().get(s), true, s*weightTowardsWordEnd);
			
			}
			
		}else{
			
			//nothing, it'll be taken care of in the next if-else statement.
			
		}
		
		debugPrint("Rhyme Value:" + rhymeValue);
		
		if(foundConsonantCluster == false){
			
			return (double) findRhymePercentile(rhymeValue, anchor);
			
		}else{
			
			Word longerWord = null;
			
			if(anchor.getListOfPhonemes().size() < satellite.getListOfPhonemes().size()){
				
				longerWord = satellite;
				
			}else{
				
				longerWord = anchor;
				
			}
			
			if(anchorOrSatellite == true){
				
				return idealRhymeValue(newWord, satellite);
				
			}else{
				
				return idealRhymeValue(anchor, newWord);
				
			}
			
		}
		
	}
	
	/**Finds the Rhyme Value that a word has with itself (homophonic Rhyme Value) and then finds the percentage that the 
	 * actual Rhyme Value matches with the homophonic RV*/
	public static double findRhymePercentile(double rhymeValue, Word longerWord){
		
		double homophonicRhymeValue = 0.0;
		double rhymePercentile = 0.0;
		
		double weightTowardsWordEnd = 0.1;
		
		for(int i = 0; i < longerWord.getListOfPhonemes().size(); i++){
			
			homophonicRhymeValue = homophonicRhymeValue + 
					findRVBetweenPhonemes(longerWord.getListOfPhonemes().get(i), longerWord.getListOfPhonemes().get(i), true, i*weightTowardsWordEnd);
			
		}
		debugPrint("RV: " + rhymeValue);
		debugPrint("HRV: " + homophonicRhymeValue);
		
		rhymePercentile = (double) rhymeValue / (double)homophonicRhymeValue;
		
		return rhymePercentile;
		
	}
	
	/**Takes in two Phonemes and finds the amount that should be added to the Rhyme Value based on how well the two Phonemes match.*/
	public static double findRVBetweenPhonemes(Phoneme p1, Phoneme p2, boolean addWeight, double weight){
		
		debugPrint("			In method findRVBetweenPhonemes");
		debugPrint("			p1 (" + p1.getPhoneme() +") is a vowel:" + p1.isAVowelPhoneme());
		debugPrint("			p2 (" + p2.getPhoneme() +") is a vowel:" + p2.isAVowelPhoneme());
		
		if(p1.isAVowelPhoneme() && p2.isAVowelPhoneme()){
			debugPrint("			-Both vowels");
			if(p1.isEqualTo(p2)){
				debugPrint("			--Equal");
				return 5.0 + weight;
				
			}else{
				debugPrint("			--Not equal");
				return 2.5 + weight;
				
			}
			
		}else if(!p1.isAVowelPhoneme() && !p2.isAVowelPhoneme()){
			debugPrint("			-Both consonants");
			if(p1.isEqualTo(p2)){
				debugPrint("			--Equal");
				return 1.0 + weight;
				
			}else{
				debugPrint("			--Not equal");
				return 0.5 + weight;
				
			}
			
		}else{
			debugPrint("			-No reasonable relation");
			return 0.0;
			
		}
		
	}
	
	public static double findDeductionForIndexSet(RVIndexPair bestSet, Word longerWord){
		
		double deduction = 0.0;
		debugPrint(bestSet.toString());
		
		if(bestSet.getIndexes().get(0) > 0){
			
			if(bestSet.getIndexes().get(0) > 1){
				
				deduction = deduction + Math.log10(bestSet.getIndexes().get(0));
				
			}else{
				
				deduction = deduction + 0.25;
				
			}
			
			debugPrint("first index: " + bestSet.getIndexes().get(0));
			debugPrint("DEDUCTION FROM FRONT: " + deduction);
			
		}
		
		if((longerWord.getListOfPhonemes().size() - 1) - bestSet.getIndexes().get(bestSet.getIndexes().size()-1) > 0){
			
			deduction = deduction + Math.log10((longerWord.getListOfPhonemes().size() - 1) - bestSet.getIndexes().get(bestSet.getIndexes().size()-1));
			
		}
		
		for(int i = 0; i < bestSet.getIndexes().size() - 1; i++){
			
			int index1 = bestSet.getIndexes().get(i);
			int index2 = bestSet.getIndexes().get(i + 1);
			
			debugPrint("index subtraction" + (index2 - index1-1));
			
			deduction = deduction + (0.25 * (index2 - index1-1));
			
		}
		
		return deduction;
		
	}
	
	public static void debugPrint(Object x){
		
		if(DEBUGGING == true){
			
			System.out.println(x);
			
		}
		
	}

	public static RhymeDictionaryTrie getTrie() {
		return trie;
	}

	public static void setTrie(RhymeDictionaryTrie trie) {
		RhymeFinder.trie = trie;
	}
	
}
