package graph;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Node> nodes;

	public ArrayList<Node> getNodes() {
		return this.nodes;
	}
	
	public class Node {
		private String label;
		private ArrayList<Node> adjacentNodes;

		public Node(String label) {
			this.label = label;
			this.adjacentNodes = new ArrayList<Node>();
		}

		public void add(Node node) {
			this.adjacentNodes.add(node);
		}

		public ArrayList<Node> getChildren() {
			return this.adjacentNodes;
		}

		public String getLabel() {
			return this.label;
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

}
