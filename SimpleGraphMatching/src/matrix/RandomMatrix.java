package matrix;

import java.util.Random;

public class RandomMatrix {

	public static AdjacencyMatrix generateRandomAdjacencyMatrix(int size) {
		Random rand = new Random();

		String[] randomLabels = new String[size];

		for (int i = 0; i < size; i++) {
			randomLabels[i] = Integer.toString(rand.nextInt(size / 2));
		}

		AdjacencyMatrix m = new AdjacencyMatrix(size, randomLabels);

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				m.setElement(row, col, rand.nextInt(2));
			}
		}

		return m;
	}

}
