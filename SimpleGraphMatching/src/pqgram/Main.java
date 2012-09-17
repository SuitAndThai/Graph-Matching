package pqgram;

import graph.Graph;
import graph.Node;

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
	}

	public static void main(String[] args) {
		Graph g = makeT1();
		Graph g2 = makeT2();
		int p = 2;
		int q = 3;
		System.out.println(dist(g, g2, p, q));
	}
	
	public static double dist(Graph T1, Graph T2, int p, int q) {
		ArrayList<String[]> index = pqGramIndex(T1, p, q);
		ArrayList<String[]> index2 = pqGramIndex(T2, p, q);
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

	private static ArrayList<String[]> pqGramIndex(Graph g, int p, int q) {
		ArrayList<String[]> I = new ArrayList<String[]>();
		String[] stem = new String[p];
		Arrays.fill(stem, STAR_LABEL);
		I = index(g, p, q, I, g.getRoot(), stem);
		return I;
	}

	private static ArrayList<String[]> index(Graph g, int p, int q,
			ArrayList<String[]> I, Node a, String[] stem) {
		String[] base = new String[q];
		Arrays.fill(base, STAR_LABEL);
		stem = shift(stem, a.getLabel());
		if (a.isLeaf()) {
			I.add(concat(stem, base));
		} else {
			for (Node c : a.getChildren()) {
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

	private static Graph makeT1() {
		Node v1 = new Node("a");
		Node v2 = new Node("a");
		Node v3 = new Node("e");
		Node v4 = new Node("b");
		Node v5 = new Node("b");
		Node v6 = new Node("c");

		v1.add(v2);
		v1.add(v5);
		v1.add(v6);

		v2.add(v3);
		v2.add(v4);

		return new Graph(v1);
	}
	
	private static Graph makeT2() {
		Node w5 = new Node("a");
		Node w1 = new Node("a");
		Node w3 = new Node("b");
		Node w6 = new Node("d");
		Node w7 = new Node("e");
		Node w9 = new Node("b");
		
		w5.add(w1);
		w5.add(w3);
		w5.add(w6);
		
		w1.add(w7);
		w1.add(w9);
		
		return new Graph(w5);
	}
}
