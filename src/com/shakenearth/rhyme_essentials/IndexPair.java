package com.shakenearth.rhyme_essentials;

import java.util.ArrayList;

public class IndexPair {
	
	private int shorterWordPhoneme = 0, longerWordPhoneme = 0;
	private double rhymeValue = 0.0;
	
	public IndexPair(Phoneme p1, Phoneme p2){
		
		setRhymeValue(findRVBetweenPhonemes(p1, p2));
		
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

	public int getShorterWordPhoneme() {
		return shorterWordPhoneme;
	}

	public void setShorterWordPhoneme(int shorterWordPhoneme) {
		this.shorterWordPhoneme = shorterWordPhoneme;
	}

	public int getLongerWordPhoneme() {
		return longerWordPhoneme;
	}

	public void setLongerWordPhoneme(int longerWordPhoneme) {
		this.longerWordPhoneme = longerWordPhoneme;
	}

	public double getRhymeValue() {
		return rhymeValue;
	}

	public void setRhymeValue(double rhymeValue) {
		this.rhymeValue = rhymeValue;
	}

}
