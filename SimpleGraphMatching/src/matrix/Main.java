package matrix;

public class Main {
	public static void main(String[] args) {
		int n = 100;
		 Matrix m = Matrix.generateRandomSquare(n);
//		Matrix m = makeT1();

		System.out.println("Starting Matrix:");
		m.print();
		 Matrix m2 = m.delete(n - 1);
		 Matrix m3 = m2.delete(n - 2);
//		Matrix m3 = makeT2();

		System.out.println("A submatrix:");
		m3.print();

		Matrix S = Matcher.getSimilarityMatrix(m, m3);
		System.out.println("Similarity matrix");
		S.print();
		
		System.out.println("Rowsums");
		for (int i = 0; i < S.getNumberOfRows(); i++) {
			System.out.println(Matcher.vectorSum(S.getRow(i)));
		}

		int[] matches = Matcher.matchNodes(S);
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

	public static Matrix makeT1() {

		String[] labels = { "a1", "a2", "e", "b1", "b2", "c" };
		Matrix m = new Matrix(6, labels);

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

	public static Matrix makeT2() {
		String[] labels = { "a1", "a2", "e", "b1", "b2", "d" };
		Matrix m = new Matrix(6, labels);

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
