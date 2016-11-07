package com.shakenearth.rhyme_essentials;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

/**
 * This class contains a set of methods to find how well two words rhyme with one another.
 *@author Thomas Lisankie
 */
public class RhymeFinder {
	
	public final static boolean DEBUGGING = false, SAMPLESIZE = false;
	
	private Hashtable<String, String> dictionary = null;
	private Hashtable<String, Integer> structureReference = null;
	private ArrayList<String> wordList = null;
	private static Hashtable<String, ArrayList<Integer>> features = null;
	
	/**
	 * Creates a new RhymeFinder object.
	 *@param pathToDict The path to the CMU Dictionary (or any stylistic equivalent) to be loaded.
	 */
	public RhymeFinder(String pathToDict, String pathToFeatureSet){
		
		buildWords(pathToDict, pathToFeatureSet);
		
	}
	
	/**
	 * builds the list of Word objects that can be compared to one another.
	 * @param path The path to the CMU Dictionary (or any stylistic equivalent) to be loaded.*/
	public void buildWords(String path, String featureSetPath){ 
		
		//1
		List<String> linesOfDictionary = null;
		List<String> linesOfFeatureSet = null;
		//loads all the lines in the CMU Phonemic Dictionary. Each line contains a word and its phonemic translation.
		try{
			
			linesOfDictionary = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			
		}catch(Exception e){
			
			debugPrint("there was an exception");
		
		}
		
		//loads lines of feature set
		try{
			
			linesOfFeatureSet = Files.readAllLines(Paths.get(featureSetPath), Charset.defaultCharset());
			
		}catch(Exception e){
			
			debugPrint("there was an exception");
		
		}
		
		setDictionary(new Hashtable<String, String>());
		setStructureReference(new Hashtable<String, Integer>());
		setWordList(new ArrayList<String>());
		setFeatures(new Hashtable<String, ArrayList<Integer>>());
		
		for(int l = 0; l < linesOfDictionary.size(); l++){
			
			String[] components = linesOfDictionary.get(l).split("  ");
			
			if(components.length != 2){
				
				System.out.println("The lines aren't separated by two spaces.");
				break;
				
			}
			
			if(components[0].equals("#")){
				
				getStructureReference().put(components[1], l - getStructureReference().size());
				
			}else{
				
				String lowerCaseWord = components[0].toLowerCase();
				
				getWordList().add(lowerCaseWord);
				
				dictionary.put(lowerCaseWord, components[1]);
				
			}
			
		}
		
		//import phonemes and their features
		for(int l = 0; l < linesOfFeatureSet.size(); l++){
			
			String[] components = linesOfFeatureSet.get(l).split("  ");
			//System.out.println(components[0] + " " + components[1]);
			
			if(components.length != 2){
				
				System.out.println("The lines aren't separated by two spaces.");
				break;
				
			}
			
			String[] features = components[1].split(" ");
			ArrayList<Integer> featureInts = new ArrayList<Integer>();
			
			for(int i = 0; i < features.length; i++){
				
				featureInts.add(Integer.parseInt(features[i]));
				
			}
			
			getFeatures().put(components[0], featureInts);
			
		}
		
	}

	/**This method goes through the entire process of finding how well two words rhyme with one another.
	 * How well two words rhyme is given by the Rhyme Percentile returned. The higher the Rhyme Percentile, the better the two words rhyme.
	 * @return Rhyme Percentile between two Words*/
	public double findRhymeValueAndPercentileForWords(Word anchor, Word satellite) {
		
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
	
	/**This method is called when two words have differing phonemic lengths (contain the same number of phonemes).
	 * Ideal Rhyme Value is just the rhyme value before spacing between phoneme matches is taken into account.
	 * @return Ideal Rhyme Value between two Words*/
	private double idealRhymeValue(Word anchor, Word satellite){
		
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
	
	/**This method is called when two words have the same phonemic lengths (contain the same number of phonemes).
	 * 1. Iterate through each phoneme in one of the words and compare it to its corresponding phoneme in the other word, adding the resulting points awarded to the total Rhyme Value along the way.
	 * 2. Find Homophonic Rhyme Value (as previously defined)
	 * 3. Divide Rhyme Value by Homophonic Rhyme Value and multiply by 100
	 * @return Regular Rhyme Value between two Words*/
	private double regularRhymeValue(Word anchor, Word satellite){
		
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
	 * actual Rhyme Value matches with the homophonic RV
	 * @return Rhyme Percentile between two Words*/
	private double findRhymePercentile(double rhymeValue, Word longerWord){
		
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
	
	/**Takes in two Phonemes and finds the amount that should be added to the Rhyme Value based on how well the two Phonemes match.
	 * @return The Rhyme Value between two phonemes*/
	private double findRVBetweenPhonemes(Phoneme p1, Phoneme p2, boolean addWeight, double weight){
		
		ArrayList<Integer> p1Features = p1.getFeatures();
		ArrayList<Integer> p2Features = p2.getFeatures();
		ArrayList<Integer> biggerList = null;
		
		if(p1Features.size() >= p2Features.size()){
			
			biggerList = p1Features;
			
		}else{
			
			biggerList = p2Features;
			
		}
		
		//contains just the features that the phonemes share
		ArrayList<Integer> commonFeatures = new ArrayList<Integer>(p1Features);
		commonFeatures.retainAll(p2Features);
		
		int difference = biggerList.size() - commonFeatures.size();
		
		if(p1.isAVowelPhoneme() && p2.isAVowelPhoneme()){
			
			int stressDifference = Math.abs(p1.getStress() - p2.getStress());
			return 5.0 - (1*difference) - stressDifference;
			
		}else if(p1.isAVowelPhoneme() == false && p2.isAVowelPhoneme() == false){
			
			int commonFeaturesSize = commonFeatures.size();
			double specialDifference = 0; /*this is used for keeping track of differences that need different values to be subtracted
			as opposed to the standard amount*/
			
			if(p1.getPhoneme().equals(p2.getPhoneme()) == false){ /*This is here so that when homophonic rhyme value is being 
			calculated, the homophonic rhyme value won't differ according to feature sets and thus rhyme percentile won't be 
			altered based on the order in which the words were entered*/
					
				if(commonFeatures.contains(9) == false){ //difference in voicing
					
					specialDifference = specialDifference + 0.1;
					commonFeaturesSize = commonFeaturesSize - 1;
					
				}
				
				if(commonFeatures.contains(2)){ //difference in sonority
					
					specialDifference = specialDifference + 1;
					commonFeaturesSize = commonFeaturesSize - 1;
					
				}
				
			}
			
			difference = biggerList.size() - commonFeaturesSize;
			
			return 2.0 - (0.15*difference) - specialDifference;
			
		}else{ /*this is a bit different because we're starting at the assumption that they won't have much in common so it's structured
		for rewarding common features rather than punishing for differences*/
			
			//run same sonority and voicing tests but perhaps with different amounts rewarded for each
			
			int commonFeaturesSize = commonFeatures.size();
			double specialDifference = 0; /*this is used for keeping track of differences that need different values to be subtracted
			as opposed to the standard amount*/
			
			if(commonFeatures.contains(9) == false){ //difference in voicing
				
				specialDifference = specialDifference + 0.1;
				commonFeaturesSize = commonFeaturesSize - 1;
				
			}
			
			if(commonFeatures.contains(2)){ //difference in sonority
				
				specialDifference = specialDifference + 1;
				commonFeaturesSize = commonFeaturesSize - 1;
				
			}
			
			difference = biggerList.size() - commonFeaturesSize;
			
			return 0.1*commonFeaturesSize + specialDifference;
			
		}
		
	}
	
	/**To be used with Ideal Rhyme Value. Finds the amount that should be subtracted from the Ideal Rhyme Value based on the number of 
	 * spaces between phonemes.
	 * @return The number to subtract from Ideal Rhyme Value*/
	private double findDeductionForIndexSet(RVIndexPair bestSet, Word longerWord){
		
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
	
	public void debugPrint(Object x){
		
		if(DEBUGGING == true){
			
			System.out.println(x);
			
		}
		
	}

	/**Returns the trie of the CMU Dictionary
	 * @return A trie of the CMU Dictionary*/
	public Hashtable<String, String> getDictionary() {
		return dictionary;
	}

	/**
	 * Sets this object's trie of the CMU Dictionary
	 * @param trie a RhymeDictionaryTrie*/
	public void setDictionary(Hashtable<String, String> dictionary) {
		this.dictionary = dictionary;
	}

	public Hashtable<String, Integer> getStructureReference() {
		return structureReference;
	}

	public void setStructureReference(Hashtable<String, Integer> structureReference) {
		this.structureReference = structureReference;
	}

	public ArrayList<String> getWordList() {
		return wordList;
	}

	public void setWordList(ArrayList<String> wordList) {
		this.wordList = wordList;
	}

	public static Hashtable<String, ArrayList<Integer>> getFeatures() {
		return features;
	}

	public static void setFeatures(Hashtable<String, ArrayList<Integer>> featureList) {
		features = featureList;
	}
	
}