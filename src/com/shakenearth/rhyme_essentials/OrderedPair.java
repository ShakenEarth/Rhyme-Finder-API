package com.shakenearth.rhyme_essentials;

import java.util.ArrayList;

public class OrderedPair {
	
	private String shorterWordPhoneme = "", longerWordPhoneme = "";
	private ArrayList<Integer> indexes = new ArrayList<Integer>();
	private double rhymeValue = 0.0;
	
	public OrderedPair(Phoneme p1, Phoneme p2, int l){
		
		shorterWordPhoneme = p1.getPhoneme();
		longerWordPhoneme = p2.getPhoneme();
		setRhymeValue(findRVBetweenPhonemes(p1, p2));
		getIndexes().add(l);
		
	}
	
	private double findRVBetweenPhonemes(Phoneme p1, Phoneme p2){
		
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

	public String getShorterWordPhoneme() {
		return shorterWordPhoneme;
	}

	public void setShorterWordPhoneme(String shorterWordPhoneme) {
		this.shorterWordPhoneme = shorterWordPhoneme;
	}

	public String getLongerWordPhoneme() {
		return longerWordPhoneme;
	}

	public void setLongerWordPhoneme(String longerWordPhoneme) {
		this.longerWordPhoneme = longerWordPhoneme;
	}

	public double getRhymeValue() {
		return rhymeValue;
	}

	public void setRhymeValue(double rhymeValue) {
		this.rhymeValue = rhymeValue;
	}
	
	public String toString(){
		
		return "(" + shorterWordPhoneme + ", " + longerWordPhoneme + ")";
		
	}

	public ArrayList<Integer> getIndexes() {
		return indexes;
	}

	public void setIndexes(ArrayList<Integer> indexes) {
		this.indexes = indexes;
	}

	public void calculateGapPenalty(int lSize) {
		
		double deduction = 0.0;
		System.out.println("INDEXES: " + getIndexes());
		
		for(int i = 0; i < getIndexes().size() - 1; i++){
			
			int index1 = getIndexes().get(i);
			int index2 = getIndexes().get(i + 1);
			
			deduction = deduction + (0.25 * (index1 - index2-1));
			
		}
		
		if(getIndexes().get(getIndexes().size() - 1) < 0){
			
			if(getIndexes().get(getIndexes().size() - 1) > 1){
				
				deduction = deduction + Math.log10(getIndexes().get(0));
				
			}else{
				
				deduction = deduction + 0.25;
				
			}
			
		}
		
		if(lSize - getIndexes().get(getIndexes().size()-1) < 0){
			
			deduction = deduction + Math.log10(lSize - getIndexes().get(getIndexes().size()-1));
			
		}
		
		System.out.println("NEW DEDUCTION: " + deduction);
		
		setRhymeValue(getRhymeValue() - deduction);
		
	}

}
