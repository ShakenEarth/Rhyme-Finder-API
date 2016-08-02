
package com.shakenearth.rhyme_essentials;

import java.io.Serializable;


/**
 * Class for representing phonemes. Includes a text representation of the phoneme itself, whether or not the phoneme is a vowel phoneme, 
 * and the stress placed during the pronunciation of the phoneme (-1 if phoneme is a consonant).
 * @author Thomas Lisankie
 */
public class Phoneme implements Serializable {
	
	private boolean isAVowelPhoneme;
	private String phoneme = "";
	private int stress = -1; //-1 just means there isn't an assigned stress
	
	public Phoneme(String phoneme) {
		
		this.phoneme = phoneme;
		
		if(phoneme.endsWith("0") || phoneme.endsWith("1") || phoneme.endsWith("2") || phoneme.endsWith("3") || phoneme.endsWith("4") 
				|| phoneme.endsWith("5")){
			
			String stressText = phoneme.substring(phoneme.length()-1);
			String thePhoneme = phoneme.substring(0, phoneme.length()-1);
			this.phoneme = thePhoneme;
			stress = Integer.parseInt(stressText);
			
		}
		
		if(this.phoneme.equals("AA") || this.phoneme.equals("AE") || this.phoneme.equals("AH") || this.phoneme.equals("AO") 
				|| this.phoneme.equals("AW") || this.phoneme.equals("AY") || this.phoneme.equals("EH") || this.phoneme.equals("ER") 
				|| this.phoneme.equals("EY") || this.phoneme.equals("IH") || this.phoneme.equals("IY") || this.phoneme.equals("OW")
				|| this.phoneme.equals("OY") || this.phoneme.equals("UH") || this.phoneme.equals("UW")){
			
			setIsAVowelPhoneme(true);
			
		}
		
	}

	public String getPhoneme() {
		return phoneme;
	}

	/**
	 * Sets the text representation of the phoneme and determines whether or not the phoneme is a vowel.
	 */
	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
		
		if(this.phoneme.equals("AA") || this.phoneme.equals("AE") || this.phoneme.equals("AH") || this.phoneme.equals("AO") 
				|| this.phoneme.equals("AW") || this.phoneme.equals("AY") || this.phoneme.equals("EH") || this.phoneme.equals("ER") 
				|| this.phoneme.equals("EY") || this.phoneme.equals("IH") || this.phoneme.equals("IY") || this.phoneme.equals("NG") 
				|| this.phoneme.equals("OW") || this.phoneme.equals("OY") || this.phoneme.equals("UH") || this.phoneme.equals("UW")
				|| this.phoneme.equals("AR") || this.phoneme.equals("EL") || this.phoneme.equals("OL") || this.phoneme.equals("OR") 
				|| this.phoneme.equals("ALE") || this.phoneme.equals("EAR")){
			
			setIsAVowelPhoneme(true);
			
		}
	}

	public boolean isAVowelPhoneme() {
		return isAVowelPhoneme;
	}
	
	public void setIsAVowelPhoneme(boolean isAVowel) {
		this.isAVowelPhoneme = isAVowel;
	}
	
	public boolean isALongVowelPhoneme() {
		
		if(getPhoneme().equals("AO") || getPhoneme().equals("AW") || getPhoneme().equals("AY") || getPhoneme().equals("EY") || getPhoneme().equals("IY")
				|| getPhoneme().equals("OW") || getPhoneme().equals("OY") || getPhoneme().equals("UW") || getPhoneme().equals("OL") || getPhoneme().equals("OR")
				|| getPhoneme().equals("ALE") || getPhoneme().equals("EAR") ){
			
			return true;
			
		}else{
			
			return false;
			
		}
		
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

}
