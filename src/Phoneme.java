
public class Phoneme {
	
	private boolean isAVowelPhoneme = false;
	private String phoneme = "";
	private int stress = -1; //-1 just means there isn't an assigned stress
	
	public String getPhoneme() {
		return phoneme;
	}

	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
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

}
