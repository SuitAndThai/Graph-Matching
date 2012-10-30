package pqgram;


import java.util.Arrays;

import tree.Tree;

public class PQGramIndexMaker {
	public static String STAR_LABEL = "**";

	public static Index pqGramIndex(Tree t, int p, int q) {
		Index I = new Index();
		String[] stem = new String[p];
		Arrays.fill(stem, STAR_LABEL);
		I = index(t, p, q, I, t, stem);
		return I;
	}

	private static Index index(Tree g, int p, int q, Index I, Tree a, String[] stem) {
		String[] base = new String[q];
		Arrays.fill(base, STAR_LABEL);
		stem = shift(stem, a.getLabel());
		if (a.isLeaf()) {
			I.add(concat(stem, base));
		} else {
			for (Tree c : a.getChildren()) {
				base = shift(base, c.getLabel());
				I.add(concat(stem, base));
				I = index(g, p, q, I, c, stem);
			}
			for (int k = 1; k < q; k++) {
				base = shift(base, STAR_LABEL);
				I.add(concat(stem, base));
			}
		}
		return I;
	}

	private static String[] concat(String[] stem, String[] base) {
		String[] result = new String[stem.length + base.length];
		for (int i = 0; i < stem.length; i++) {
			result[i] = stem[i];
		}
		for (int i = stem.length; i < result.length; i++) {
			result[i] = base[i - stem.length];
		}
		return result;
	}

	private static String[] shift(String[] arr, String label) {
		String[] newArr = new String[arr.length];
		for (int i = 1; i < arr.length; i++) {
			newArr[i - 1] = arr[i];
		}
		newArr[arr.length - 1] = label;
		return newArr;
	}
}