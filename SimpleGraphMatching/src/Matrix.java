import java.util.Random;

public class Matrix {

	private int[][] mat;
	private int numRows;
	private int numColumns;

	// assumes square
	public Matrix(int size) {
		this(size, size);
	}

	public Matrix(int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		mat = new int[numRows][numColumns];
	}

	public void setElement(int row, int col, int value) {
		mat[row][col] = value;
	}

	public int getElement(int row, int col) {
		return mat[row][col];
	}

	public String toString() {
		String s = "";
		s += "numRows = " + numRows + " numCols = " + numColumns + "\n";

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
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
		int[] col = new int[numRows];
		for (int j = 0; j < numRows; j++) {
			col[j] = mat[j][i];
		}
		
		return col;
	}

	public void print() {
		System.out.println(toString());
	}

	public int getNumberOfRows() {
		return numRows;
	}

	public int getNumberOfColumns() {
		return numColumns;
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
		int r = getNumberOfRows();
		int c = getNumberOfColumns();

		Matrix m = new Matrix(r, c);

		for (int row = 0; row < r; row++) {
			for (int col = 0; col < c; col++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		return m;
	}

	public Matrix delete(int i) {
		Matrix colDeleted = deleteColumn(i);
		Matrix rowDeleted = colDeleted.deleteRow(i);

		return rowDeleted;
	}

	public Matrix deleteColumn(int i) {
		Matrix m = new Matrix(numRows, numColumns - 1);

		for (int col = 0; col < i; col++) {
			for (int row = 0; row < numRows; row++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		// skips the column that we've deleted

		for (int col = i; col < numColumns - 1; col++) {
			for (int row = 0; row < numRows; row++) {
				m.setElement(row, col, getElement(row, col + 1));
			}
		}

		return m;
	}

	public Matrix deleteRow(int i) {
		Matrix m = new Matrix(numRows - 1, numColumns);

		for (int row = 0; row < i; row++) {
			for (int col = 0; col < numColumns; col++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		// skips the row that we've deleted

		for (int row = i; row < numRows - 1; row++) {
			for (int col = 0; col < numColumns; col++) {
				m.setElement(row, col, getElement(row + 1, col));
			}
		}

		return m;
	}
}
