import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

public class Main {
	public static void main(String[] args) {
		Matrix m = MatrixGenerator.randomMatrix(5);

		System.out.println("Starting Matrix:");
		m.print();
		Matrix m2 = m.clone();
		System.out.println("A submatrix:");
		m2.print();
	}
}
