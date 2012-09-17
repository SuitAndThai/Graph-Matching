package graph;

public class Graph {
	
	private Node root;
	
	public Graph(Node root) {
		this.root = root;
	}
	
	public Node getRoot() {
		return this.root;
	}

	public String toString() {
		return this.root.toString();
	}

}
