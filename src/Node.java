import java.io.Serializable;
import java.util.ArrayList;

/**@author Thomas Lisankie*/

public class Node implements Serializable{
	
	private ArrayList<IndexSet> indexSets = new ArrayList<IndexSet>();
	private IndexSet parentIndexSet = null;
	private IndexSet bestSet = null;
	
	public void addIndexSet(IndexSet set){
		
		indexSets.add(set);
		
	}

	public ArrayList<IndexSet> getIndexSets() {
		return indexSets;
	}

	public void setIndexSets(ArrayList<IndexSet> indexSets) {
		this.indexSets = indexSets;
	}

	public IndexSet getParentIndexSet() {
		return parentIndexSet;
	}

	public void setParentIndexSet(IndexSet parentIndexSet) {
		this.parentIndexSet = parentIndexSet;
	}
	
	public IndexSet getBestSet() {
		return bestSet;
	}

	public void setBestSet(IndexSet bestSet) {
		this.bestSet = bestSet;
	}

	public void findBestIndexSetAndSendItUp(){
		
		IndexSet bestSet = null;
		
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
			
			RhymeDictionaryAssembler.debugPrint(bestSet.getIndexes().size()-1);
			RhymeDictionaryAssembler.debugPrint("first " + bestSet.getIndexes().get(bestSet.getIndexes().size()-1));
			RhymeDictionaryAssembler.debugPrint("second " + bestSet.getRhymeValueForSet());
			RhymeDictionaryAssembler.debugPrint(parentIndexSet.getIndexes());
			
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
