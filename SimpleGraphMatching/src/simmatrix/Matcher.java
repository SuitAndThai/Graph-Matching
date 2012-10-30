package simmatrix;

import java.util.ArrayList;

import matrix.AdjacencyMatrix;
import matrix.SquareMatrix;

public class Matcher {

	public static int[] match(AdjacencyMatrix A, AdjacencyMatrix B) {
		SquareMatrix S = Matcher.getSimilarityMatrix(A, B);
		int[] matches = Matcher.matchNodes(S);
		return matches;
	}

	private static int[] matchNodes(SquareMatrix S) {
		// Number of rows <= Number of columns
		int numRows = S.getNumberOfRows();
		int numCols = S.getNumberOfColumns();

		int[] matches = new int[numRows];
		ArrayList<Integer> tabuRows = new ArrayList<Integer>();
		ArrayList<Integer> tabuCols = new ArrayList<Integer>();

		for (int n = 0; n < matches.length; n++) {
			int bestRow = 0;
			int bestCol = 0;
			int bestScore = -2000;
			int nextScore;
			for (int i = 0; i < numRows; i++) {
				if (tabuRows.contains(i)) {
					continue;
				}
				for (int j = 0; j < numCols; j++) {
					if (tabuCols.contains(j)) {
						continue;
					}
					nextScore = S.getElement(i, j);
					if (nextScore > bestScore) {
						bestRow = i;
						bestCol = j;
						bestScore = nextScore;
					}
				}
			}
			matches[bestRow] = bestCol;
			tabuRows.add(bestRow);
			tabuCols.add(bestCol);
		}

		return matches;
	}

	public static SquareMatrix getSimilarityMatrix(AdjacencyMatrix A, AdjacencyMatrix B) {
		if (B.getNumberOfRows() < A.getNumberOfRows()) {
			return getSimilarityMatrix(B, A);
		}

		SquareMatrix S = new SquareMatrix(A.getNumberOfRows());

		int score;
		for (int row = 0; row < A.getNumberOfRows(); row++) {
			for (int col = 0; col < B.getNumberOfColumns(); col++) {
				score = getSimilarityScore(A.getRow(row), A.getColumn(row),
						B.getRow(col), B.getColumn(col), A.getLabel(row),
						B.getLabel(col));
				S.setElement(row, col, score);
			}
		}

		return S;
	}

	private static int vectorSum(int[] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}

	private static int getSimilarityScore(int[] Arow, int[] Acol, int[] Brow,
			int[] Bcol, String ALabel, String BLabel) {
		int helperScore = 2;
		int hurterScore = 1; // this will become negative later

		int ArowSum = vectorSum(Arow);
		int AcolSum = vectorSum(Acol);
		int BrowSum = vectorSum(Brow);
		int BcolSum = vectorSum(Bcol);

		int minRowSum = Math.min(ArowSum, BrowSum);
		int minColSum = Math.min(AcolSum, BcolSum);
		int rowDiff = Math.abs(ArowSum - BrowSum);
		int colDiff = Math.abs(AcolSum - BcolSum);

		int help = helperScore * (minRowSum + minColSum);
		int hurt = hurterScore * (rowDiff + colDiff);

		int labelHelpScore = 0;

		if (ALabel.equals(BLabel)) {
			labelHelpScore = 1000;
		}

		return (int) (labelHelpScore + 100 * ((help - hurt) / ((double) (help + hurt))));
	}
}
