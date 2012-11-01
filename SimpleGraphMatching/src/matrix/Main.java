package matrix;

import simmatrix.Matcher;

public class Main {
	public static void main(String[] args) {
		int n = 50;
		AdjacencyMatrix m = RandomMatrix.generateRandomAdjacencyMatrix(n);
		// Matrix m = makeT1();

		String s = Maple.toMapleCode(m);
		System.out.println(s);
		System.exit(0);

		System.out.println("Starting Matrix:");
		System.out.println(m);
		AdjacencyMatrix m2 = m.delete(n - 1);
		AdjacencyMatrix m3 = m2.delete(n - 2);

		System.out.println("A submatrix:");
		System.out.println(m3);

		SquareMatrix S = Matcher.getSimilarityMatrix(m, m3);
		System.out.println("Similarity matrix");
		System.out.println(S);

		int[] matches = Matcher.match(m, m3);
		int score = 0;
		for (int i = 0; i < matches.length; i++) {
			score += S.getElement(i, matches[i]);
		}
		System.out.println("Similarity score: " + score);
		System.out.println("Index matches");
		for (int i = 0; i < matches.length; i++) {
			System.out.print(matches[i] + " ");
		}
		System.out.println();
	}

	public static SquareMatrix makeT1() {

		String[] labels = { "a1", "a2", "e", "b1", "b2", "c" };
		AdjacencyMatrix m = new AdjacencyMatrix(6, labels);

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				m.setElement(i, j, 0);
			}
		}

		m.setElement(0, 1, 1);
		m.setElement(0, 4, 1);
		m.setElement(0, 5, 1);
		m.setElement(1, 2, 1);
		m.setElement(1, 3, 1);

		return m;
	}

	public static SquareMatrix makeT2() {
		String[] labels = { "a1", "a2", "e", "b1", "b2", "d" };
		AdjacencyMatrix m = new AdjacencyMatrix(6, labels);

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				m.setElement(i, j, 0);
			}
		}

		m.setElement(0, 1, 1);
		m.setElement(0, 4, 1);
		m.setElement(0, 5, 1);
		m.setElement(1, 2, 1);
		m.setElement(1, 3, 1);

		return m;
	}
}
