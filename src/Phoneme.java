
public class Phoneme {
	
	private boolean isAVowel = false;
	private String phoneme = "";
	private int stress;
	
	public String getPhoneme() {
		return phoneme;
	}

	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
	}

	public boolean isAVowel() {
		return isAVowel;
	}
	
	public void setIsAVowel(boolean isAVowel) {
		this.isAVowel = isAVowel;
	}

	public int getStress() {
		return stress;
	}

	public void setStress(int stress) {
		this.stress = stress;
	}

}
