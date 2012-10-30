package matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjacencyMatrix extends SquareMatrix {
	private ArrayList<String> labels;

	public AdjacencyMatrix(int size, String[] labels) {
		super(size);
		this.labels = new ArrayList<String>(Arrays.asList(labels));
	}

	public String getLabel(int n) {
		return this.labels.get(n);
	}

	public String toString() {
		String s = "";

		s += "numRows = " + this.numRows + " numCols = " + this.numColumns + "labels = [";

		for (String label : this.labels) {
			s += label + ",";
		}
		s += "]\n";

		for (int row = 0; row < this.numRows; row++) {
			for (int col = 0; col < this.numColumns; col++) {
				s += this.rows[row][col] + " ";
			}
			s += "\n";
		}

		return s;
	}

	@Override
	public AdjacencyMatrix clone() {
		int r = getNumberOfRows();
		int c = getNumberOfColumns();

		AdjacencyMatrix m = new AdjacencyMatrix(r, (String[]) this.labels.toArray());

		for (int row = 0; row < r; row++) {
			for (int col = 0; col < c; col++) {
				m.setElement(row, col, getElement(row, col));
			}
		}

		return m;
	}

	@Override
	public AdjacencyMatrix delete(int index) {
		Matrix m = super.delete(index);
		List<String> labels = new ArrayList<String>(this.labels);
		labels.remove(index);
		String[] labelArray = labels.toArray(new String[this.labels.size()-1]);

		AdjacencyMatrix am = new AdjacencyMatrix(m.getNumberOfRows(), labelArray);
		
		for (int i = 0; i < m.getNumberOfRows(); i++) {
			for (int j = 0; j < m.getNumberOfColumns(); j++) {
				am.setElement(i, j, m.getElement(i, j));
			}
		}
		
		return am;
	}
}
