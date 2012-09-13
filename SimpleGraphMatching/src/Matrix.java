import java.util.Random;

public class Matrix {

	private int[][] mat;
	private int rows;
	private int cols;

	// assumes square
	public Matrix(int size) {
		mat = new int[size][size];
		rows = size;
		cols = size;
	}

	public Matrix(int r, int c) {
		rows = r;
		cols = c;
		mat = new int[rows][cols];
	}

	public void setElement(int row, int col, int value) {
		mat[row][col] = value;
	}

	public int getElement(int row, int col) {
		return mat[row][col];
	}

	public String toString() {
		String s = "";
		s += "numRows = " + rows + " numCols = " + cols + "\n";

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				s += mat[row][col] + " ";
			}
			s += "\n";
		}

		return s;
	}

	public void print() {
		System.out.println(toString());
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public static Matrix generateRandomSquare(int size) {
		Random rand = new Random();
		Matrix m = new Matrix(size);

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				m.setElement(row, col, rand.nextInt(2));
			}
		}

		return m;
	}

	public Matrix clone() {
		int r = getRows();
		int c = getCols();

		Matrix m = new Matrix(r, c);

		for (int row = 0; row < r; row++) {
			for (int col = 0; col < c; col++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		return m;
	}

	public Matrix delete(int i) {
		Matrix rowDeleted = deleteRow(i);
		Matrix colDeleted = rowDeleted.deleteCol(i);

		return colDeleted;
	}

	private Matrix deleteCol(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private Matrix deleteRow(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
