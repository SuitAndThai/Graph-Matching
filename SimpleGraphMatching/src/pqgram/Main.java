package pqgram;

import graph.Graph;
import graph.Tree;
import graph.Tree;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	private static String STAR_LABEL = null;
	
	private static void printI(ArrayList<String[]> I) {
		for (String[] tup : I) {
			for (int i = 0; i < tup.length; i++) {
				if (tup[i] == null) {
					System.out.print("*" + " ");
				} else {
					System.out.print(tup[i] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Tree g = makeT1();
		Tree g2 = makeT2();
		int p = 2;
		int q = 3;
		System.out.println(dist(g, g2, p, q));
	}
	
	public static double dist(Tree T1, Tree T2, int p, int q) {
		ArrayList<String[]> index = pqGramIndex(T1, p, q);
		System.out.println("T1 pq-Gram (p = " + p + ", q = " + q + ")");
		printI(index);
		ArrayList<String[]> index2 = pqGramIndex(T2, p, q);
		System.out.println("T2 pq-Gram (p = " + p + ", q = " + q + ")");
		printI(index2);
		ArrayList<String[]> mUnion = multiUnion(index, index2);
		System.out.println("u: " + mUnion.size());
		ArrayList<String[]> mIntersection = multiIntersection(index, index2);
		System.out.println("i: " + mIntersection.size());
		return (mUnion.size() - 2.0 * mIntersection.size()) / (mUnion.size() - mIntersection.size());
	}
	
	private static String join(String[] strs, String delim) {
		String jStr = strs[0];
		for (int i = 1; i < strs.length; i++) {
			jStr += delim + strs[i];
		}
		return jStr;
	}
	
	private static ArrayList<String[]> multiIntersection(ArrayList<String[]> I1, ArrayList<String[]> I2) {
		if (I2.size() < I1.size()) {
			return multiIntersection(I2, I1);
		}
		ArrayList<String> I2strings = new ArrayList<String>(I1.size());
		for (String[] tup : I2) {
			I2strings.add(join(tup, ","));
		}
		ArrayList<String[]> mIntersection = new ArrayList<String[]>(I1.size());
		for (String[] tup : I1) {
			String tupStr = join(tup, ",");
			if (I2strings.contains(tupStr)) {
				I2strings.remove(tupStr);
				mIntersection.add(tup);
			}
		}
		return mIntersection;
	}
	
	private static ArrayList<String[]> multiUnion(ArrayList<String[]> I1, ArrayList<String[]> I2) {
		ArrayList<String[]> mUnion = new ArrayList<String[]>(I1.size() + I2.size());
		mUnion.addAll(I1);
		mUnion.addAll(I2);
		return mUnion;
	}

	private static ArrayList<String[]> pqGramIndex(Tree t, int p, int q) {
		ArrayList<String[]> I = new ArrayList<String[]>();
		String[] stem = new String[p];
		Arrays.fill(stem, STAR_LABEL);
		I = index(t, p, q, I, t, stem);
		return I;
	}

	private static ArrayList<String[]> index(Tree g, int p, int q,
			ArrayList<String[]> I, Tree a, String[] stem) {
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

	private static Tree makeT1() {
		Tree v1 = new Tree("a");
		Tree v2 = new Tree("a");
		Tree v3 = new Tree("e");
		Tree v4 = new Tree("b");
		Tree v5 = new Tree("b");
		Tree v6 = new Tree("c");

		v1.addChild(v2);
		v1.addChild(v5);
		v1.addChild(v6);

		v2.addChild(v3);
		v2.addChild(v4);

		return v1;
	}
	
	private static Tree makeT2() {
		Tree w5 = new Tree("a");
		Tree w1 = new Tree("a");
		Tree w3 = new Tree("b");
		Tree w6 = new Tree("d");
		Tree w7 = new Tree("e");
		Tree w9 = new Tree("b");
		
		w5.addChild(w1);
		w5.addChild(w3);
		w5.addChild(w6);
		
		w1.addChild(w7);
		w1.addChild(w9);
		
		return w5;
	}
}
