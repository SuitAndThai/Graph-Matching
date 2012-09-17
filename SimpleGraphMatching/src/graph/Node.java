package graph;

import java.util.ArrayList;

public class Node {
	private String label;
	private ArrayList<Node> adjacentNodes;
	
	public void add(Node node) {
		adjacentNodes.add(node);
	}
	
	public ArrayList<Node> getChildren() {
		return this.adjacentNodes;
	}
	
	public Node(String label) {
		this.label = label;
		this.adjacentNodes = new ArrayList<Node>();
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public boolean isLeaf() {
		return this.adjacentNodes.size() == 0;
	}
	
	public String toString() {
		String nodeString = "{";
		for (Node node : adjacentNodes) {
			nodeString += " " + node.toString() + ",";
		}
		nodeString += " }";
		return nodeString;
	}
}
