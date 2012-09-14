public class Main {
	public static void main(String[] args) {
		int n = 4;
		Matrix m = MatrixGenerator.randomMatrix(n);

		System.out.println("Starting Matrix:");
		m.print();
		Matrix m2 = m.delete(n - 1);
		Matrix m3 = m2.delete(n - 2);

		System.out.println("A submatrix:");
		m3.print();

		Matrix S = Matcher.getSimilarityMatrix(m, m3);
		System.out.println("Similarity matrix");
		S.print();
		
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
}
