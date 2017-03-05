package com.shakenearth.rhyme_essentials;

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
	public double findRhymePercentileForWords(Word word1, Word word2) {
		
		double rhymePercentile = 0.0;
		Word longerWord = null;
		
		//these conditionals find which word is longer and which is shorter
		if(word1.getListOfPhonemes().size() < word2.getListOfPhonemes().size()){
					
			longerWord = word2;
					
		}else{
					
			longerWord = word1;
					
		}
		
		ArrayList<Double> allRVs = new ArrayList<Double>();
		
		//1 - Find Cartesian product (shorterWord X longerWord)
		CartesianProduct cartesianProduct = new CartesianProduct(word1, word2);
		
		//2 - Calculate RVs
		int echelon = 0;
		while(cartesianProduct.getCartesianProductMatrix().size() != 0){
			
			echelon = cartesianProduct.getCartesianProductMatrix().size() - 1;
			allRVs.add(findBestRV(cartesianProduct, echelon, new ArrayList<Integer>(), 0, 
					cartesianProduct.getCartesianProductMatrix().get(echelon).size(),
					longerWord.getListOfPhonemes().size()));
			
			cartesianProduct.removeTopRow();
			/*resets rhyme values of OrderedPairs to their original value so that previous runthroughs of the findBestRV() method 
			have no effect i.e. it makes sure it has the correct data to work with:*/
			cartesianProduct.resetOrderedPairRVs();
			echelon = 0;
			
		}
		
		//3 - Find Rhyme Percentile
		rhymePercentile = (double) findRhymePercentile(Collections.max(allRVs), longerWord);
		
		return rhymePercentile;
		
	}
	
	private double findBestRV(CartesianProduct cp, int echelon, ArrayList<Integer> indexes, 
			double cumulative, int bound, int lSize){
		
		ArrayList<OrderedPair> currentRow = cp.getCartesianProductMatrix().get(echelon); //echelon number is the same as the current row index.
		
		for(int i = echelon; i < bound; i++){
			
			currentRow.get(i).setRhymeValue(currentRow.get(i).getRhymeValue() + cumulative);
			
		}
		
		OrderedPair bestPairForRow = null;
		int indexToAdd = 0;
		for(int i = echelon; i < bound; i++){
			if(i == echelon){
				
				bestPairForRow = currentRow.get(i);
				indexToAdd = i;
				
			}else{
				
				if(currentRow.get(i).getRhymeValue() > bestPairForRow.getRhymeValue()){
					
					bestPairForRow = currentRow.get(i);
					indexToAdd = i;
					
				}
				
			}
			
		}
		
		indexes.add(indexToAdd);
		
		if(echelon == 0){
			
			bestPairForRow.setIndexes(indexes);
			bestPairForRow.calculateGapPenalty(lSize);
			return bestPairForRow.getRhymeValue();
			
		}else{
			
			echelon = echelon - 1;
			return findBestRV(cp, echelon, indexes, bestPairForRow.getRhymeValue(), indexes.get(indexes.size() - 1), lSize);
			
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
		
		rhymePercentile = (double) rhymeValue / (double)homophonicRhymeValue;
		
		if(rhymePercentile < 0){
			
			rhymePercentile = 0;
			
		}
		
		return rhymePercentile;
		
	}
	
	/**Takes in two Phonemes and finds the amount that should be added to the Rhyme Value based on how well the two Phonemes match.
	 * @return The Rhyme Value between two phonemes*/
	public double findRVBetweenPhonemes(Phoneme p1, Phoneme p2, boolean addWeight, double weight){
		
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