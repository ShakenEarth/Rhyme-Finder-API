
package com.shakenearth.rhyme_essentials;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class RhymeDictionaryTrie implements Trie, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected RhymeDictionaryTrieNode trieRoot;
    public RhymeDictionaryTrie() {
    	
        trieRoot = new RhymeDictionaryTrieNode();
        
    }
	
	public boolean addWord(Word word) {
		
		if (word==null || word.getWordName().isEmpty()) {
			
            return false;
            
        }
        
        char[] wordCharArr = word.getWordName().toCharArray();
        RhymeDictionaryTrieNode tempRoot = trieRoot;
        
        for (char charValue : wordCharArr) {
        	
            tempRoot.addChild(charValue);
            tempRoot = tempRoot.getChild(charValue);
            
        }
        
        tempRoot.setWord(word);
        tempRoot.setFinalChar(true);
        return true;
		
	}
	
	public Word getWord(String wordName){
		
		Word word = null;
		RhymeDictionaryTrieNode currentNode = trieRoot;
		
		for(int i = 0; i < wordName.length(); i++){
			
			char current = wordName.charAt(i);
			ArrayList<RhymeDictionaryTrieNode> children = new ArrayList<RhymeDictionaryTrieNode>(currentNode.getChildrenNodes());
			boolean foundChar = false;
			
			for(int j = 0; j < children.size(); j++){
				
				foundChar = false;
				
				RhymeDictionaryTrieNode child = children.get(j);
				Character childChar = child.getCharValue();
				
				if(childChar.equals(current)){
					
					currentNode = child;
					foundChar = true;
					break;
					
				}
				
			}
			
			if(foundChar == false){
				
				break;
				
			}
			
			
		}
		
		word = currentNode.getWord();
		
		return word;
		
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
		
		String string = trieRoot.getChild('a').getChild('r').getChild('e').toString();
		
		return string;
		
	}

}
