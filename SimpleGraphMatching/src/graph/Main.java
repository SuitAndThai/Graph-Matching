package graph;

import matrix.Matrix;

public class Main {

	public static void main(String[] args) {
		int numNodes = 50;
		String[] labels = {"a", "b", "c", "d", "e", "f"};
		Tree t = Tree.makeRandomTree(numNodes, labels);
		Matrix m = Tree.getMatrix(t);
	}

}
