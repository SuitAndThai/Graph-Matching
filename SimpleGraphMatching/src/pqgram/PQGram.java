package pqgram;

import java.util.Arrays;

import tree.Tree;

public class PQGram {
	public static String STAR_LABEL = "**";

	public static double getDistance(Tree T1, Tree T2, int p, int q) {
		Profile profile = PQGram.getProfile(T1, p, q);
		Profile profile2 = PQGram.getProfile(T2, p, q);
		Profile mUnion = profile.union(profile2);
		Profile mIntersection = profile.intersect(profile2);
		return 1 - (2.0 * mIntersection.size()) / mUnion.size();
	}

	/*
	 * Multiset of label-tuples of all pq-grams
	 */
	public static Profile getProfile(Tree t, int p, int q) {
		Profile profile = new Profile();
		String[] stem = new String[p];
		Arrays.fill(stem, STAR_LABEL);
		profile = getLabelTuples(t, p, q, profile, t, stem);
		return profile;
	}

	private static Profile getLabelTuples(Tree g, int p, int q, Profile profile, Tree a, String[] stem) {
		String[] base = new String[q];
		Arrays.fill(base, STAR_LABEL);
		stem = shift(stem, a.getLabel());
		if (a.isLeaf()) {
			profile.add(concatenate(stem, base));
		} else {
			for (Tree c : a.getChildren()) {
				base = shift(base, c.getLabel());
				profile.add(concatenate(stem, base));
				profile = getLabelTuples(g, p, q, profile, c, stem);
			}
			for (int k = 1; k < q; k++) {
				base = shift(base, STAR_LABEL);
				profile.add(concatenate(stem, base));
			}
		}
		return profile;
	}

	private static String[] concatenate(String[] stem, String[] base) {
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
