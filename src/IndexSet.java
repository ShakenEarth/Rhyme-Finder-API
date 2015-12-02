import java.io.Serializable;
import java.util.*;

/**@author Thomas Lisankie*/

public class IndexSet implements Serializable {
	
	private ArrayList<Integer> indexes = new ArrayList<Integer>();
	private double rhymeValueForSet = 0.0;
	private Node childNode;
	
	public IndexSet(int index, double RVBetweenPhonemes){
		
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
