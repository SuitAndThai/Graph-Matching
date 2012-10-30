package matrix;

public class Maple {
	/* 
	 * this code is intended for triangular matrices to write the code to create
	 * trees for maple
	 */
	public static String toMapleCode(SquareMatrix m, int n) {
		String output = "";

		if (1 == n) {
			output += "restart; with(GraphTheory):\n";
		}
		output += "G" + n + ":=Graph(<";

		for (int col = 0; col < m.getNumberOfColumns(); col++) {
			output += "<";
			for (int row = 0; row < m.getNumberOfRows(); row++) {
				output += ("" + (m.getElement(row, col) | m
						.getElement(col, row)));

				if (row < m.getNumberOfRows() - 1) {
					output += ",";
				}
			}
			output += ">";

			if (col < m.getNumberOfColumns() - 1) {
				output += "|";
			}
		}

		output += ">):\n";
		output += "DrawGraph(G" + n + ");\n";

		return output;
	}

}
