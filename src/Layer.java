import java.io.Serializable;
import java.util.ArrayList;

public class Layer implements Serializable {
	
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public Layer(){
		
		
		
	}
	
	public Layer(ArrayList<Node> nodes){
		
		this.setNodes(nodes);
		
	}

	public ArrayList<Node> getNodes() {
		
		return nodes;
		
	}

	public void setNodes(ArrayList<Node> nodes) {
		
		this.nodes = nodes;
		
	}

}
