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
	
	public int[] getRow(int i) {
		return mat[i];
	}
	
	public int[] getCol(int i) {
		int[] col = new int[rows];
		for (int j = 0; j < rows; j++) {
			col[j] = mat[j][i];
		}
		
		return col;
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
		Matrix colDeleted = deleteCol(i);
		Matrix rowDeleted = colDeleted.deleteRow(i);

		return rowDeleted;
	}

	public Matrix deleteCol(int i) {
		Matrix m = new Matrix(rows, cols - 1);

		for (int col = 0; col < i; col++) {
			for (int row = 0; row < rows; row++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		// skips the column that we've deleted

		for (int col = i; col < cols - 1; col++) {
			for (int row = 0; row < rows; row++) {
				m.setElement(row, col, getElement(row, col + 1));
			}
		}

		return m;
	}

	public Matrix deleteRow(int i) {
		Matrix m = new Matrix(rows - 1, cols);

		for (int row = 0; row < i; row++) {
			for (int col = 0; col < cols; col++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		// skips the row that we've deleted

		for (int row = i; row < rows - 1; row++) {
			for (int col = 0; col < cols; col++) {
				m.setElement(row, col, getElement(row + 1, col));
			}
		}

		return m;
	}
}
