package matrix;

import java.util.ArrayList;

public class Matcher {

	public static int[] match(Matrix A, Matrix B) {
		Matrix S = Matcher.getSimilarityMatrix(A, B);
		int[] matches = Matcher.matchNodes(S);
		return matches;
	}

	public static int[] matchNodes(Matrix S) {
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

//		int bestIndex;
//		for (int i = 0; i < numRows; i++) {
//			bestIndex = 0;
//			while (tabuRows.contains(bestIndex)) {
//				bestIndex++;
//			}
//			for (int j = 0; j < numCols; j++) {
//				if (!tabuRows.contains(j)) {
//					if (S.getElement(i, j) > S.getElement(i, bestIndex)) {
//						bestIndex = j;
//					}
//				}
//			}
//			matches[i] = bestIndex;
//			tabuRows.add(bestIndex);
//		}

		return matches;
	}

	public static Matrix getSimilarityMatrix(Matrix A, Matrix B) {
		if (B.getNumberOfRows() < A.getNumberOfRows()) {
			return getSimilarityMatrix(B, A);
		}

		String[] temp = { "" };
		Matrix S = new Matrix(A.getNumberOfRows(), B.getNumberOfColumns(), temp);

		int score;
		for (int row = 0; row < A.getNumberOfRows(); row++) {
			for (int col = 0; col < B.getNumberOfColumns(); col++) {
				score = getSimilarityScore(A.getRow(row), A.getCol(row),
						B.getRow(col), B.getCol(col), A.getLabel(row),
						B.getLabel(col));
				S.setElement(row, col, score);
			}
		}

		return S;
	}

	public static int vectorSum(int[] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}

	// label matching is not counted yet
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
