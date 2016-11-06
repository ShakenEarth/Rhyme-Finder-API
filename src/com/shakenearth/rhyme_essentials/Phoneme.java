
package com.shakenearth.rhyme_essentials;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class for representing phonemes. Includes a text representation of the phoneme itself, whether or not the phoneme is a vowel phoneme, 
 * and the stress placed during the pronunciation of the phoneme (-1 if phoneme is a consonant).
 * @author Thomas Lisankie
 */
public class Phoneme implements Serializable {
	
	private ArrayList<Integer> features = new ArrayList<Integer>(); //this needs to be replaced by some sort of a way of keeping track of phonetic features.
	private String phoneme = "";
	private int stress = -1; //-1 just means there isn't an assigned stress
	private boolean isAVowelPhoneme = false;
	
	public Phoneme(String phonemeString) {
		
		this.phoneme = phonemeString;
		
		if(phonemeString.endsWith("0") || phonemeString.endsWith("1") || phonemeString.endsWith("2") || phonemeString.endsWith("3") || phonemeString.endsWith("4") 
				|| phonemeString.endsWith("5")){
			
			String stressText = phonemeString.substring(phonemeString.length()-1);
			String thePhoneme = phonemeString.substring(0, phonemeString.length()-1);
			this.phoneme = thePhoneme;
			stress = Integer.parseInt(stressText);
			isAVowelPhoneme = true;
			
		}
		
		System.out.println("features in RhymeFinder: " + RhymeFinder.getFeatures());
		features = RhymeFinder.getFeatures().get(phoneme);
		
	}

	public String getPhoneme() {
		return phoneme;
	}

	/**
	 * Sets the text representation of the phoneme and determines whether or not the phoneme is a vowel.
	 */
	public void setPhoneme(String phoneme) {
		
		this.phoneme = phoneme;
		
		if(phoneme.endsWith("0") || phoneme.endsWith("1") || phoneme.endsWith("2") || phoneme.endsWith("3") || phoneme.endsWith("4") 
				|| phoneme.endsWith("5")){
			
			String stressText = phoneme.substring(phoneme.length()-1);
			String thePhoneme = phoneme.substring(0, phoneme.length()-1);
			this.phoneme = thePhoneme;
			stress = Integer.parseInt(stressText);
			isAVowelPhoneme = true;
			
		}
		
		features = RhymeFinder.getFeatures().get(phoneme);
		
	}

	public ArrayList<Integer> getFeatures() {
		return features;
	}
	
	public void setFeatures(ArrayList<Integer> features) {
		this.features = features;
	}

	public int getStress() {
		return stress;
	}

	public void setStress(int stress) {
		this.stress = stress;
	}

	public boolean isEqualTo(Phoneme p2) {
		
		if(this.getPhoneme().equals(p2.getPhoneme())){
			
			return true;
			
		}else{
		
			return false;
			
		}
	}

	public boolean isAVowelPhoneme() {
		return isAVowelPhoneme;
	}

	public void setAVowelPhoneme(boolean isAVowelPhoneme) {
		this.isAVowelPhoneme = isAVowelPhoneme;
	}

}
