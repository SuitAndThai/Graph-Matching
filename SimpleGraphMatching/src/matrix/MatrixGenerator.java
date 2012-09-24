package matrix;


public class MatrixGenerator {
	public static Matrix randomMatrix(int size) {
		Matrix m = Matrix.generateRandomSquare(size);
		return m;
		// Random rand = new Random();
		// return RandomMatrices.createRandom(size, size, rand);
		// DenseMatrix64F A = RandomMatrices.createSymmetric(5, -2, 3, rand);
		// return A;
	}
}
