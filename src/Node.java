import java.util.ArrayList;

public class Node {
	
	private ArrayList<IndexSet> indexSets = new ArrayList<IndexSet>();
	private IndexSet parentIndexSet;
	
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

}
