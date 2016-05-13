
package com.shakenearth.rhyme_essentials;

import java.io.Serializable;
import java.util.*;

/**Used to store an index of a phoneme in the longer word that has some sort of rhyme value with a phoneme in the shorter word.
 * When the best rhyme value is trying to be found of a child node, the rhyme value for an RVIndexPair is increased by the highest RV in
 * the child node.
 * @author Thomas Lisankie*/

public class RVIndexPair implements Serializable {
	
	private ArrayList<Integer> indexes = new ArrayList<Integer>();
	private double rhymeValueForSet = 0.0;
	private Node childNode;
	
	public RVIndexPair(int index, double RVBetweenPhonemes){
		
		this.getIndexes().add(index);
		this.setRhymeValueForSet(getRhymeValueForSet() + RVBetweenPhonemes);
		
	}
	
	public void addIndexes(ArrayList<Integer> indexesToAdd, double RVBetweenPhonemes){
		
		for(int i = 0; i < indexesToAdd.size(); i++){
			
			indexes.add(indexesToAdd.get(i));
			
		}
		
		rhymeValueForSet = rhymeValueForSet + RVBetweenPhonemes;
		
	}
	
	public ArrayList<Integer> getIndexes() {
		
		return indexes;
		
	}
	public void setIndexes(ArrayList<Integer> indexes) {
		
		this.indexes = indexes;
		
	}
	public double getRhymeValueForSet() {
		
		return rhymeValueForSet;
		
	}
	public void setRhymeValueForSet(double rhymeValueForSet) {
		
		this.rhymeValueForSet = rhymeValueForSet;
		
	}

	public Node getChildNode() {
		return childNode;
	}

	public void attachChildNode(Node childNode) {
		this.childNode = childNode;
		this.childNode.setParentIndexSet(this);
	}
	
	public String toString(){
		
		String indexSetInfo = "INDEX SET INFO: ";
		
		for(int i = 0; i < indexes.size(); i++){
			
			indexSetInfo = indexSetInfo + "Index " + i + ": " + indexes.get(i) + ", ";
			
		}
		
		indexSetInfo = indexSetInfo + "RV for Set: " + getRhymeValueForSet();
			
		return indexSetInfo;
		
	}

}
