import java.util.ArrayList;

public class Matcher {
	
	public static int[] matchNodes(Matrix S) {
		// Number of rows <= Number of columns
		int numRows = S.getRows();
		int numCols = S.getCols();
		
		int[] matches = new int[numRows];
		ArrayList<Integer> tabu = new ArrayList<Integer>();
		
		int bestIndex;
		for (int i = 0; i < numRows; i++) {
			bestIndex = 0;
			while (tabu.contains(bestIndex)) {
				bestIndex++;
			}
			for (int j = 0; j < numCols; j++) {
				if (!tabu.contains(j)) {
					if (S.getElement(i, j) > S.getElement(i, bestIndex)) {
						bestIndex = j;
					}
				}
			}
			matches[i] = bestIndex;
			tabu.add(bestIndex);
		}
		
		return matches;
	}

	public static Matrix getSimilarityMatrix(Matrix A, Matrix B) {
		if (B.getRows() < A.getRows()) {
			return getSimilarityMatrix(B, A);
		}
		Matrix S = new Matrix(A.getRows(), B.getCols());
		
		int score;
		for (int row = 0; row < A.getRows(); row++) {
			for (int col = 0; col < B.getCols(); col++) {
				score = getSimilarityScore(A.getRow(row), A.getCol(row), B.getRow(col), B.getCol(col));
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
	
	private static int getSimilarityScore(int[] Arow, int[] Acol, int[] Brow, int[] Bcol) {
		int helperScore = 2;
		int hurterScore = -1;
		
		int ArowSum = vectorSum(Arow);
		int AcolSum = vectorSum(Acol);
		int BrowSum = vectorSum(Brow);
		int BcolSum = vectorSum(Bcol);
		
		int minRowSum = Math.min(ArowSum, BrowSum);
		int minColSum = Math.min(AcolSum, BcolSum);
		int rowDiff = Math.abs(ArowSum - BrowSum);
		int colDiff = Math.abs(AcolSum - BcolSum);
		
		return helperScore * (minRowSum + minColSum) + hurterScore * (rowDiff + colDiff);
	}

}
