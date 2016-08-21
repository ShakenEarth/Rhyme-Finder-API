package com.shakenearth.rhyme_essentials;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class PhonemicSearchTrie implements Trie, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected PhonemicSearchTrieNode trieRoot;
	private Hashtable<WordName, String> dictionary = null;
	int searchCounter = 0;
	
    public PhonemicSearchTrie(Hashtable<WordName, String> dictionary) {
    	
        trieRoot = new PhonemicSearchTrieNode();
        this.dictionary = dictionary;
        
    }
	
	public boolean addWord(Word word) { //adding VALUES
		
		if (word==null || word.getWordNameObj() == null) {
			
            return false;
            
        }
        
        List<Phoneme> vowelPhonemes = word.getVowelPhonemes();
        PhonemicSearchTrieNode tempRoot = trieRoot;
        
        for (int i = vowelPhonemes.size() - 1; i >= 0; i--) {
        	
        	Phoneme phoneme = vowelPhonemes.get(i);
            tempRoot.addChild(phoneme.getPhoneme());
            tempRoot = tempRoot.getChild(phoneme.getPhoneme());
            
        }
        
        tempRoot.getWordNames().add(word.getWordNameObj());
        tempRoot.setFinalPhoneme(true);
        return true;
		
	}
	
	public ArrayList<WordName> getWordsWithSimilarVowelStructure(WordName wordName){ //used to fetch words with same vowel structure
		
		ArrayList<WordName> similarWordNames = null;
		Word word = new Word(wordName, dictionary.get(wordName));
		PhonemicSearchTrieNode currentNode = trieRoot;
		
		
		for(int i = word.getVowelPhonemes().size()- 1; i >= 0; i--){
			
			String phoneme = word.getVowelPhonemes().get(i).getPhoneme();
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
		
		dfs(trieRoot, 0);
		System.out.println(searchCounter);
		return string;
		
	}

	public String dfs(PhonemicSearchTrieNode root, int layer){       
	    //Avoid infinite loops
		String trieString = "";
		
	    if(root == null) {
	    	
	    	return trieString;
	    
	    }
	    
	    searchCounter = searchCounter + 1;
	    
	    trieString = trieString + "\n";
	    
	    for(int i = 0; i < layer; i++){
	    	
	    	trieString = trieString + "\t";
	    	
	    }
	    
	    trieString = trieString + root.getPhonemeName();
	    System.out.println(trieString);
	    
	    root.visited = true;
	
	    //for every child
	    PhonemicSearchTrieNode[] childNodes = new PhonemicSearchTrieNode[root.getChildrenNodes().size()];
	    root.getChildrenNodes().toArray(childNodes);
	    for(PhonemicSearchTrieNode n: childNodes){
	    	
	        //if childs state is not visited then recurse
	    	if(n != null){
		    	
		        if(n.visited == false){
		        	
		            dfs(n, layer + 1);
		            
		        }
		        
	    	}else{
	    		layer = layer - 1;
	    		break;
	    		
	    	}
	        
	    }
	    
	    return trieString;
	    
	}

}