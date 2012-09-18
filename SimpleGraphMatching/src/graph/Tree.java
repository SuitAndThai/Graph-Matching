package graph;

public class Tree extends Graph {
	private Node root;
	
	public Tree(Node root) {
		this.root = root;
	}
	
	public Node getRoot() {
		return this.root;
	}
}
