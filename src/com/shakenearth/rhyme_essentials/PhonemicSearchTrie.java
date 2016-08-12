package com.shakenearth.rhyme_essentials;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class PhonemicSearchTrie implements Trie, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected PhonemicSearchTrieNode trieRoot;
	private Hashtable<WordName, String> dictionary = null;
	
    public PhonemicSearchTrie(Hashtable<WordName, String> dictionary) {
    	
        trieRoot = new PhonemicSearchTrieNode();
        this.dictionary = dictionary;
        
    }
	
	public boolean addWord(Word word) { //adding VALUES
		
		if (word==null || word.getWordNameObj() == null) {
			
            return false;
            
        }
        
        List<Syllable> syllables = word.getListOfSyllables();
        PhonemicSearchTrieNode tempRoot = trieRoot;
        
        for (int i = syllables.size() - 1; i >= 0; i--) {
        	
        	Syllable syllable = syllables.get(i);
            tempRoot.addChild(syllable.getVowelPhoneme().getPhoneme());
            tempRoot = tempRoot.getChild(syllable.getVowelPhoneme().getPhoneme());
            
        }
        
        tempRoot.getWordNames().add(word.getWordNameObj());
        tempRoot.setFinalPhoneme(true);
        return true;
		
	}
	
	public ArrayList<WordName> getWordsWithSimilarVowelStructure(WordName wordName){ //used to fetch words with same vowel structure
		
		ArrayList<WordName> similarWordNames = null;
		Word word = new Word(wordName, dictionary.get(wordName));
		PhonemicSearchTrieNode currentNode = trieRoot;
		
		
		for(int i = word.getListOfSyllables().size()- 1; i >= 0; i--){
			
			String phoneme = word.getListOfSyllables().get(i).getVowelPhoneme().getPhoneme();
			ArrayList<PhonemicSearchTrieNode> children = new ArrayList<PhonemicSearchTrieNode>(currentNode.getChildrenNodes());
			boolean foundPhoneme = false;
			
			for(int j = 0; j < children.size(); j++){
				
				foundPhoneme = false;
				
				PhonemicSearchTrieNode child = children.get(j);
				String childPhoneme = child.getPhonemeName();
				
				if(childPhoneme.equals(phoneme)){
					
					currentNode = child;
					foundPhoneme = true;
					break;
					
				}
				
			}
			
			if(foundPhoneme == false){
				
				break;
				
			}
			
			
		}
		
		similarWordNames = currentNode.getWordNames();
		
		return similarWordNames;
		
	}

	public boolean removeWord(Word word) {
		
		return false;
	}

	public boolean hasWord(Word word) {
		
		return false;
	}

	public char[] getNextCharacters(String prefixString) {
		
		return null;
	}
	
	@Override
	public String toString(){
		
		String string = "";
		
		return string;
		
	}

}