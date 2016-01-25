
package com.shakenearth.rhyme_essentials;

import java.io.Serializable;
import java.util.ArrayList;

/**@author Thomas Lisankie*/

public class Query implements Serializable{
	
	private ArrayList<Word> wordsForThisQuery = new ArrayList<Word>();
	private String name = null;
	
	public Query(String name){
		
		this.name = name;
		
	}

	public ArrayList<Word> getWordsForThisQuery() {
		return wordsForThisQuery;
	}

	public void setWordsForThisQuery(ArrayList<Word> wordsForThisQuery) {
		this.wordsForThisQuery = wordsForThisQuery;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
