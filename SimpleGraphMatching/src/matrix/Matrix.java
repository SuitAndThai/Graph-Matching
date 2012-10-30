package matrix;

public class Matrix {
	protected int numRows;
	protected int numColumns;
	protected int[][] rows;

	public Matrix(int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.rows = new int[numRows][numColumns];
	}

	public void setElement(int row, int col, int value) {
		this.rows[row][col] = value;
	}

	public int getElement(int row, int col) {
		return this.rows[row][col];
	}

	public int[] getRow(int i) {
		return this.rows[i];
	}

	public int[] getColumn(int i) {
		int[] col = new int[this.numRows];
		for (int j = 0; j < this.numRows; j++) {
			col[j] = this.rows[j][i];
		}

		return col;
	}

	public int getNumberOfRows() {
		return this.numRows;
	}

	public int getNumberOfColumns() {
		return this.numColumns;
	}

	public Matrix delete(int i) {
		Matrix colDeleted = deleteColumn(i);
		Matrix rowDeleted = colDeleted.deleteRow(i);

		return rowDeleted;
	}

	public Matrix deleteColumn(int i) {
		Matrix m = new Matrix(this.numRows, this.numColumns - 1);

		for (int col = 0; col < i; col++) {
			for (int row = 0; row < this.numRows; row++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		// skips the column that we've deleted

		for (int col = i; col < this.numColumns - 1; col++) {
			for (int row = 0; row < this.numRows; row++) {
				m.setElement(row, col, getElement(row, col + 1));
			}
		}

		return m;
	}

	public Matrix deleteRow(int i) {
		Matrix m = new Matrix(this.numRows - 1, this.numColumns);

		for (int row = 0; row < i; row++) {
			for (int col = 0; col < this.numColumns; col++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		// skips the row that we've deleted

		for (int row = i; row < this.numRows - 1; row++) {
			for (int col = 0; col < this.numColumns; col++) {
				m.setElement(row, col, getElement(row + 1, col));
			}
		}

		return m;
	}

}
