package graph;

import matrix.Matrix;

public class Main {

	public static void main(String[] args) {
		int numNodes = 10;
		String[] labels = { "a", "b", "c", "d", "e", "f" };
		Tree t = Tree.makeRandomTree(numNodes, labels);
		System.out.println(t);
		Matrix m = Tree.getMatrix(t);
		m.print();
	}

}
