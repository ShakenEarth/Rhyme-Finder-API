import java.util.*;

public class IndexSet {
	
	private ArrayList<Integer> indexes = new ArrayList<Integer>();
	private double rhymeValueForSet = 0.0;
	private Node childNode;
	
	public IndexSet(int index, double RVBetweenPhonemes) {
		
		this.addIndex(index, RVBetweenPhonemes);
		
	}
	
	public void addIndex(int index, double RVBetweenPhonemes){
		
		indexes.add(index);
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
	}

}
