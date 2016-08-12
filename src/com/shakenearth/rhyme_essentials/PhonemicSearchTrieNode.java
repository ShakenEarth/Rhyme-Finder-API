package com.shakenearth.rhyme_essentials;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A node implementation for a simple trie (http://en.wikipedia.org/wiki/Trie)
 * Note: this implementation is not thread safe
 * 
 * @author Parth Parekh
 * @author Thomas Lisankie
 **/
public class PhonemicSearchTrieNode {
	// to be set by Trie implementor
	private boolean isFinalPhoneme;
	private String phonemeName; //the name of this node
	private ArrayList<WordName> wordNames; //the value this node is holding
	private int depth;
	private Map<String, PhonemicSearchTrieNode> childrenMap;

	// creates empty root trie node
	public PhonemicSearchTrieNode() {
	}

	// this should be called by addChild only
	private PhonemicSearchTrieNode(String phoneme) {
		
		this.phonemeName = phoneme;
		wordNames = new ArrayList<WordName>();
		childrenMap = null;
		
	}

	/**
	 * adds child to the existing Trie node, if there is no child with given character value present already
	 * 
	 * @param charValue
	 * @return returns true if the add is successful, false if there was already a child with that charValue
	 */
	public boolean addChild(String phoneme) {
		// only create children when you're adding first child
		if (childrenMap == null) {
			
			childrenMap = new TreeMap<String, PhonemicSearchTrieNode>();
			
		}
		
		this.setFinalPhoneme(false);
		
		if (childrenMap.containsKey(phoneme)) {
			
			return false;
			
		}
		
		childrenMap.put(phoneme, new PhonemicSearchTrieNode(phoneme).setDepth(this.depth + 1));
		
		//TODO Need to fix this so words aren't added to every child
		
		return true;
		
	}

	/**
	 * removes child from the existing Trie node
	 * 
	 * @param charValue
	 * @return returns true if the remove was successful, false if there was no child found with that charValue
	 */
	public boolean removeChild(String phoneme) {
		// return false if there are no children or children does not contain
		// the character to be removed
		if (childrenMap == null || !childrenMap.containsKey(phoneme)) {
			return false;
		}
		childrenMap.remove(phoneme);
		return true;
	}

	/**
	 * returns the child TrieNode if it exists
	 * 
	 * @param phoneme
	 * @return returns TrieNode object for child if it exists, null otherwise
	 */
	public PhonemicSearchTrieNode getChild(String phoneme) {
		// return null if there are no children
		if (childrenMap == null) {
			return null;
		}
		return childrenMap.get(phoneme);
	}

	/**
	 * returns Set of all the children char values of current TrieNode
	 * 
	 * @return returns Set of all the Character objects if it exists, null otherwise
	 */
	public Set<String> getChildrenValues() {
		// return null if there are no children
		if (childrenMap == null) {
			return null;
		}
		return childrenMap.keySet();
	}

    /**
   	 * returns Set of all the children nodes of current TrieNode
   	 *
   	 * @return returns Set of all the TrieNode objects if it exists, null otherwise
   	 */
   	public Set<PhonemicSearchTrieNode> getChildrenNodes() {
   		// return null if there are no children
   		if (childrenMap == null) {
   			return null;
   		}
        Set<PhonemicSearchTrieNode> trieNodes = new HashSet<PhonemicSearchTrieNode>();
        for (String phoneme : childrenMap.keySet()) {
            trieNodes.add(getChild(phoneme));
        }
   		return trieNodes;
   	}

	public boolean isFinalPhoneme() {
		return isFinalPhoneme;
	}

	public void setFinalPhoneme(boolean isFinalPhoneme) {
		this.isFinalPhoneme = isFinalPhoneme;
	}

	public int getDepth() {
		return depth;
	}

	public PhonemicSearchTrieNode setDepth(int depth) {
		this.depth = depth;
		return this;
	}

	@Override
	public int hashCode() {
		// need to think of something better
		return phonemeName.hashCode();
	}

	public ArrayList<WordName> getWordNames() {
		return wordNames;
	}

	public void setWordNames(ArrayList<WordName> wordNames) {
		this.wordNames = wordNames;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("nodeValue: " + phonemeName + "; isFinalChar: "
				+ isFinalPhoneme + "; Word: "
						+ wordNames + "; depth: " + depth + "; children: ");
		if (childrenMap != null) {
			return toString.append(childrenMap.keySet().toString()).toString();
		}
		return toString.append("no children").toString();
	}

	public String getPhonemeName() {
		return phonemeName;
	}

	public void setPhonemeName(String phonemeName) {
		this.phonemeName = phonemeName;
	}
}