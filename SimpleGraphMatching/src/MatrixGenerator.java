import java.util.Random;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.RandomMatrices;

public class MatrixGenerator {
	static Matrix randomMatrix(int size) {
		Matrix m = Matrix.generateRandomSquare(5);
		return m;
		// Random rand = new Random();
		// return RandomMatrices.createRandom(size, size, rand);
		// DenseMatrix64F A = RandomMatrices.createSymmetric(5, -2, 3, rand);
		// return A;
	}
}
