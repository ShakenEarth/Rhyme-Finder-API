
package com.shakenearth.rhyme_essentials;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Used to store indexes of phonemes in the longer word that have some sort of rhyme value with a phoneme in the shorter word
 * @author Thomas Lisankie*/

public class Node implements Serializable{
	
	private ArrayList<RVIndexPair> indexSets = new ArrayList<RVIndexPair>();
	private RVIndexPair parentIndexSet = null;
	private RVIndexPair bestSet = null;
	
	public void addIndexSet(RVIndexPair set){
		
		indexSets.add(set);
		
	}

	public ArrayList<RVIndexPair> getIndexSets() {
		return indexSets;
	}

	public void setIndexSets(ArrayList<RVIndexPair> indexSets) {
		this.indexSets = indexSets;
	}

	public RVIndexPair getParentIndexSet() {
		return parentIndexSet;
	}

	public void setParentIndexSet(RVIndexPair parentIndexSet) {
		this.parentIndexSet = parentIndexSet;
	}
	
	public RVIndexPair getBestSet() {
		return bestSet;
	}

	public void setBestSet(RVIndexPair bestSet) {
		this.bestSet = bestSet;
	}

	public void findBestIndexSetAndSendItUp(){
		
		RVIndexPair bestSet = null;
		
		//finds which index set has highest RV
		
		for(int i = 0; i < indexSets.size(); i++){
			
			if(i == 0){
				
				bestSet = indexSets.get(i);
				
			}else{
				
				if(indexSets.get(i).getRhymeValueForSet() > bestSet.getRhymeValueForSet()){
					
					bestSet = indexSets.get(i);
					
				}
				
			}
			
		}
		
		//bestSet has been found
		
		this.setBestSet(bestSet);
		
		if(parentIndexSet != null){
			
			/*RhymeFinder.debugPrint(bestSet.getIndexes().size()-1);
			RhymeFinder.debugPrint("first " + bestSet.getIndexes().get(bestSet.getIndexes().size()-1));
			RhymeFinder.debugPrint("second " + bestSet.getRhymeValueForSet());
			RhymeFinder.debugPrint(parentIndexSet.getIndexes());*/
			
			parentIndexSet.addIndexes(bestSet.getIndexes(), bestSet.getRhymeValueForSet());
			
		}else{
			
			
			
		}
		
	}
	
	@Override
	public String toString(){
		
		String nodeInfo = "";
		
		for(int i = 0; i < indexSets.size(); i++){
			
			nodeInfo = nodeInfo + indexSets.get(i).toString() + "; ";
			
		}
			
		return nodeInfo;
		
	}

}
