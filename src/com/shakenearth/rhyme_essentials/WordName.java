package com.shakenearth.rhyme_essentials;

public class WordName {
	
	private String wordName = null;
	
	public WordName(String wordName){
		
		this.setWordName(wordName);
		
	}
	
	public int hashCode(){
		
		return wordName.hashCode();
		
	}
	
	public boolean equals(Object o){
		
		if(wordName.equals(o)){
			
			return true;
			
		}else{
			
			return false;
			
		}
		
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

}
