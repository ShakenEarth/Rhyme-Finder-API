import java.util.*;


public class IndexSet {
	
	private ArrayList<Integer> indexes = new ArrayList<Integer>();
	private double rhymeValueForSet = 0.0;
	
	public IndexSet(int index, double RVBetweenPhonemes, Word shorterWord, Word longerWord) {
		
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

}
