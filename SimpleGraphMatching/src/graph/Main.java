package graph;

import matrix.Matrix;

public class Main {

	public static void main(String[] args) {
		int numNodes = 100;
		String[] labels = {"a", "b", "c", "d", "e", "f"};
		Tree t = Tree.makeRandomTree(numNodes, labels);
		//System.out.println(t);
		Matrix m = Tree.getMatrix(t);
		System.out.println(m.toMapleCode(1));
	}

}
