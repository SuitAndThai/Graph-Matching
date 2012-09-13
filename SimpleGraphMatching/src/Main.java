public class Main {
	public static void main(String[] args) {
		Matrix m = MatrixGenerator.randomMatrix(5);

		System.out.println("Starting Matrix:");
		m.print();
		Matrix m2 = m.delete(4);
		Matrix m3 = m2.delete(3);

		System.out.println("A submatrix:");
		m3.print();

		System.out.println("Is it a subgraph? " + Matcher.isSubMatrix(m, m3));
	}
}
