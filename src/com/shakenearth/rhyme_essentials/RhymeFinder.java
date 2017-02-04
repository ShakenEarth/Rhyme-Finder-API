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
	public double findRhymePercentileForWords(Word anchor, Word satellite) {
		
		double rhymePercentile = 0.0;
		
		if(anchor.getListOfPhonemes().size() == satellite.getListOfPhonemes().size()){
			
			/*rhyme percentile for words of same phonemic length uses the anchor word as the denominator. This is to keep the focus on
			 * the anchor word which is the focus word*/
			
			rhymePercentile = regularRhymeValue(anchor, satellite);
			System.out.println("NEW METHOD RESULT: " + newFindRhymePercentileForWords(anchor, satellite)*100 + "%");
			System.out.println("NEW NEW METHOD RESULT: " + newNewFindRhymePercentileForWords(anchor, satellite)*100 + "%");
			
		}else{//do ideal Rhyme Value process
			
			rhymePercentile = idealRhymeValue(anchor, satellite);
			System.out.println("NEW METHOD RESULT: " + newFindRhymePercentileForWords(anchor, satellite)*100 + "%");
			System.out.println("NEW NEW METHOD RESULT: " + newNewFindRhymePercentileForWords(anchor, satellite)*100 + "%");
			
		}
		
		return rhymePercentile;
		
	}
	
	public double newNewFindRhymePercentileForWords(Word word1, Word word2){
		
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
		ArrayList<ArrayList<OrderedPair>> cartesianProduct = findCartesianProduct(word1, word2);
		
		//2 - Calculate RVs
		int echelon = 0;
		while(cartesianProduct.size() != 0){
			
			//print REF CP:
			
			for(int i = 0; i < cartesianProduct.size(); i++){
				
				ArrayList<OrderedPair> currentRow = cartesianProduct.get(i);
				
				for(int j = 0; j < echelon; j++){
					
					System.out.print("          ");
					
				}
				
				for(int j = echelon; j < currentRow.size(); j++){
					
					OrderedPair pair = currentRow.get(j);
					
					if(pair.getShorterWordPhoneme().length()  == 2 && pair.getLongerWordPhoneme().length()  == 2){
						
						System.out.print(pair + ", " + pair.getRhymeValue() + "  ");
						
					}else if(pair.getShorterWordPhoneme().length()  != pair.getLongerWordPhoneme().length()){
						
						System.out.print(pair + ", " + pair.getRhymeValue() + "   ");
						
					}else if(pair.getShorterWordPhoneme().length()  == 1 && pair.getLongerWordPhoneme().length()  == 1){
						
						System.out.print(pair + ", " + pair.getRhymeValue() + "    ");
						
					}
					
				}
				
				System.out.println();
				echelon = echelon + 1;
				
			}
			
			//end REF CP print
			
			allRVs.add(newFindBestRV(cartesianProduct, 0.0, new ArrayList<Integer>(), longerWord.getListOfPhonemes().size()));
			
			if(cartesianProduct.size() == 0){
				
				break;
				
			}
			
			cartesianProduct.remove(0);
			
		}
		
		//3 - Find Rhyme Percentile
		
		rhymePercentile = (double) findRhymePercentile(Collections.max(allRVs), longerWord);
		
		return rhymePercentile;
		
	}
	
	
	private ArrayList<ArrayList<OrderedPair>> findCartesianProduct(Word word1, Word word2){
		
		Word shorterWord = null;
		Word longerWord = null;
		
		System.out.println("Cartesian Product of Newest Method: \n");
		
		//these conditionals find which word is longer and which is shorter
		if(word1.getListOfPhonemes().size() < word2.getListOfPhonemes().size()){
			
			shorterWord = word1;
			longerWord = word2;
			
		}else{
			
			shorterWord = word2;
			longerWord = word1;
			
		}
		
		ArrayList<ArrayList<OrderedPair>> cartesianProduct = new ArrayList<ArrayList<OrderedPair>>();
		
		//creates Cartesian product (shorterWord X longerWord)
		for(int s = 0; s < shorterWord.getListOfPhonemes().size(); s++){
			
			ArrayList<OrderedPair> currentRow = new ArrayList<OrderedPair>();
			
			for(int l = 0; l < longerWord.getListOfPhonemes().size(); l++){
				
				OrderedPair newOrderedPair = new OrderedPair(shorterWord.getListOfPhonemes().get(s), longerWord.getListOfPhonemes().get(l), l);
				currentRow.add(newOrderedPair);
				
				//print the Ordered Pairs and make it look pretty
				
				if(newOrderedPair.getShorterWordPhoneme().length()  == 2 && newOrderedPair.getLongerWordPhoneme().length()  == 2){
					
					System.out.print(newOrderedPair + "  ");
					
				}else if(newOrderedPair.getShorterWordPhoneme().length()  != newOrderedPair.getLongerWordPhoneme().length()){
					
					System.out.print(newOrderedPair + "   ");
					
				}else if(newOrderedPair.getShorterWordPhoneme().length()  == 1 && newOrderedPair.getLongerWordPhoneme().length()  == 1){
					
					System.out.print(newOrderedPair + "    ");
					
				}
				
				//End print of ordered pairs
				
			}
			
			System.out.println();
			cartesianProduct.add(currentRow);
			
		}
		
		System.out.println();
		return cartesianProduct;
		
	}
	
	private double newFindBestRV(ArrayList<ArrayList<OrderedPair>> matrix, double addition, ArrayList<Integer> indexes, int lSize){
		
		OrderedPair bestPair = null;
		int echelonIndex = matrix.size() - 1;
		int index = echelonIndex;
		System.out.println("New Index: " + index);
		
		ArrayList<OrderedPair> currentRow = matrix.get(echelonIndex);
		System.out.println("Current Row: " + currentRow);
		
		// add addition to an OrderedPair's RV
		for(int i = 0; i < currentRow.size(); i++){
					
			currentRow.get(i).setRhymeValue(currentRow.get(i).getRhymeValue() + addition);
					
		}
		
		for(int i = echelonIndex; i < currentRow.size(); i++){
			
			if(i == echelonIndex){
				
				bestPair = currentRow.get(echelonIndex);
				
			}else{
				
				if(currentRow.get(i).getRhymeValue() > bestPair.getRhymeValue()){
					
					bestPair = currentRow.get(i);
					index = echelonIndex + (currentRow.size() - echelonIndex);
					
				}
				
			}
			
		}
		
		System.out.println("Best Pair: " + bestPair);
		
		indexes.add(index);
		
		matrix.remove(matrix.size() - 1);
		
		if(matrix.size() == 0){
			
			bestPair.setIndexes(indexes);
			System.out.println();
			bestPair.calculateGapPenalty(lSize);
			return bestPair.getRhymeValue();
			
		}else{
			
			return newFindBestRV(matrix, bestPair.getRhymeValue(), indexes, lSize);
			
		}
		
	}
	
	public double newFindRhymePercentileForWords(Word anchor, Word satellite){
		
		double rhymePercentile = 0.0;
		
		//find modified Cartesian product
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
		
		ArrayList<ArrayList<OrderedPair>> cartesianProducts = new ArrayList<ArrayList<OrderedPair>>(); /*row echelon form of the
		 Cartesian Product*/
		
		for(int s = 0; s < shorterWord.getListOfPhonemes().size(); s++){
			
			ArrayList<OrderedPair> cartes = new ArrayList<OrderedPair>(); //new row
			
			for(int l = s; l < longerWord.getListOfPhonemes().size(); l++){
				
				cartes.add(new OrderedPair(shorterWord.getListOfPhonemes().get(s), longerWord.getListOfPhonemes().get(l), l));
				
			}
			
			cartesianProducts.add(cartes);
			
		}
		
		System.out.println("Full Cartesian Product: " + cartesianProducts);
		
		double rhymeValue = findBestRV(cartesianProducts, 0.0, new ArrayList<Integer>(), longerWord.getListOfPhonemes().size());
		
		rhymePercentile = (double) findRhymePercentile(rhymeValue, longerWord);
		
		return rhymePercentile;
		
	}
	
	private double findBestRV(ArrayList<ArrayList<OrderedPair>> cartesianProducts, double addition, ArrayList<Integer> indexes, int lSize) {
		
		OrderedPair bestPair = null;
		ArrayList<OrderedPair> currentRow = cartesianProducts.get(cartesianProducts.size()-1);
		System.out.println("Cartesian Products: " + cartesianProducts);
		
		// add addition to an OrderedPair's RV
		for(int i = 0; i < currentRow.size(); i++){
			
			currentRow.get(i).setRhymeValue(currentRow.get(i).getRhymeValue() + addition);
			
		}
		
		int startingIndex = cartesianProducts.get(0).size() - currentRow.size();
		int index = startingIndex;
		System.out.println("Old Index: " + index);
		
		for(int i = 0; i < currentRow.size(); i++){
			
			if(i == 0){
				
				bestPair = currentRow.get(0);
				
			}else{
				
				if(currentRow.get(i).getRhymeValue() > bestPair.getRhymeValue()){
					
					bestPair = currentRow.get(i);
					index = startingIndex + i;
					
				}
				
			}
			
		}
		
		indexes.add(index);
		
		cartesianProducts.remove(cartesianProducts.size() - 1);
		
		if(cartesianProducts.size() == 0){
			
			bestPair.setIndexes(indexes);
			bestPair.calculateGapPenalty(lSize);
			return bestPair.getRhymeValue();
			
		}else{
			
			return findBestRV(cartesianProducts, bestPair.getRhymeValue(), indexes, lSize);
			
		}
		
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
			
			newWord = new Word(satellite.getWordName(), shortenedListOfPhonemes);
			
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
			
			if(anchorOrSatellite == true){
				
				return idealRhymeValue(newWord, satellite);
				
			}else{
				
				return idealRhymeValue(anchor, newWord);
				
			}
			
		}
		
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
		
		double idealRhymeValue = 0.0;
		
		boolean firstSearch = true;
		boolean foundStartingIndex = false;
		ArrayList<Layer> layers = new ArrayList<Layer>();
		ArrayList<Node> nodesForThisLayer = new ArrayList<Node>();
		
		int pastLayerNum = 0;
		
		// find all possible sequence lineups for the two phonemic sequences
		for(int s = 0; s < shorterWord.getListOfPhonemes().size(); s++){
			
			double weightTowardsWordEnd = 0.1;
			
			if(firstSearch == true){
				
				Node startNode = new Node();
				
				for(int l = 0; l < longerWord.getListOfPhonemes().size(); l++){
					
					double RVBetweenPhonemes = findRVBetweenPhonemes(shorterWord.getListOfPhonemes().get(s), longerWord.getListOfPhonemes().get(l), true, l * weightTowardsWordEnd);
					
					if(RVBetweenPhonemes > 1){
						
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
								
								if(RVBetweenPhonemes > 1){
									
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
				
				bestSet = nodeBeingExamined.getBestSet();
				
			}
			
		}
		
		System.out.println(bestSet.getIndexes());
		
		idealRhymeValue = bestSet.getRhymeValueForSet();
		
		double rhymeValue = idealRhymeValue;
		
		//subtract specing to get actual rhyme value
		
		rhymeValue = rhymeValue - findDeductionForIndexSet(bestSet, longerWord);
		
		return (double) findRhymePercentile(rhymeValue, longerWord);
		
	}
	
	/**Finds the Rhyme Value that a word has with itself (homophonic Rhyme Value) and then finds the percentage that the 
	 * actual Rhyme Value matches with the homophonic RV
	 * @return Rhyme Percentile between two Words*/
	private double findRhymePercentile(double rhymeValue, Word longerWord){
		
		double homophonicRhymeValue = 0.0;
		double rhymePercentile = 0.0;
		
		double weightTowardsWordEnd = 0.1;
		System.out.println("OLD: ");
		
		for(int i = 0; i < longerWord.getListOfPhonemes().size(); i++){
			
			homophonicRhymeValue = homophonicRhymeValue + 
					findRVBetweenPhonemes(longerWord.getListOfPhonemes().get(i), longerWord.getListOfPhonemes().get(i), true, i*weightTowardsWordEnd);
			
		}
		debugPrint("RV: " + rhymeValue);
		debugPrint("HRV: " + homophonicRhymeValue);
		
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
		
		System.out.println("DEDUCTION: " + deduction);
		
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