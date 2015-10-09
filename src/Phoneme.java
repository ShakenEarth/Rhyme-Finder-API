
/**
 * Class for representing phonemes
 * @author Thomas Lisankie
 */
public class Phoneme {
	
	private boolean isAVowelPhoneme ;
	private String phoneme = "";
	private int stress = -1; //-1 just means there isn't an assigned stress
	
	public String getPhoneme() {
		return phoneme;
	}

	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
		
		if(this.phoneme.equals("AA") || this.phoneme.equals("AE") || this.phoneme.equals("AH") || this.phoneme.equals("AO") 
				|| this.phoneme.equals("AW") || this.phoneme.equals("AY") || this.phoneme.equals("EH") || this.phoneme.equals("ER") 
				|| this.phoneme.equals("EY") || this.phoneme.equals("IH") || this.phoneme.equals("IY") || this.phoneme.equals("NG") 
				|| this.phoneme.equals("OW") || this.phoneme.equals("OY") || this.phoneme.equals("UH") || this.phoneme.equals("UW")){
			
			setIsAVowelPhoneme(true);
			
		}
	}

	public boolean isAVowelPhoneme() {
		return isAVowelPhoneme;
	}
	
	public void setIsAVowelPhoneme(boolean isAVowel) {
		this.isAVowelPhoneme = isAVowel;
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
