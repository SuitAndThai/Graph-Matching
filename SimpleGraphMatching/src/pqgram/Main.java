package pqgram;

import graph.Tree;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Tree g = makeT1();
		Tree g2 = makeT2();
		int p = 2;
		int q = 3;
		System.out.println(dist(g, g2, p, q));
	}

	@SuppressWarnings("unused")
	public static void printI(ArrayList<String[]> I) {
		for (String[] tup : I) {
			for (int i = 0; i < tup.length; i++) {
				System.out.print(tup[i] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static double dist(Tree T1, Tree T2, int p, int q) {
		ArrayList<String[]> index = PQGramIndexMaker.pqGramIndex(T1, p, q);
		ArrayList<String[]> index2 = PQGramIndexMaker.pqGramIndex(T2, p, q);
		ArrayList<String[]> mUnion = multiUnion(index, index2);
		ArrayList<String[]> mIntersection = multiIntersection(index, index2);
		return 1 - (2.0 * mIntersection.size()) / mUnion.size();
	}

	private static String join(String[] strs, String delim) {
		String jStr = strs[0];
		for (int i = 1; i < strs.length; i++) {
			jStr += delim + strs[i];
		}
		return jStr;
	}
	
	public static ArrayList<String[]> multiDifference(ArrayList<String[]> I1, ArrayList<String[]> I2) {
		ArrayList<String> I2strings = new ArrayList<String>(I1.size());
		for (String[] tup : I2) {
			I2strings.add(join(tup, ","));
		}
		ArrayList<String[]> difference = new ArrayList<String[]>();
		for (String[] tup : I1) {
			String tupStr = join(tup, ",");
			if (I2strings.contains(tupStr)) {
				I2strings.remove(tupStr);
			} else {
				difference.add(tup);
			}
		}
		return difference;
	}
	
	public static ArrayList<String[]> multiIntersection(
			ArrayList<String[]> I1, ArrayList<String[]> I2) {
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

	private static ArrayList<String[]> multiUnion(ArrayList<String[]> I1,
			ArrayList<String[]> I2) {
		ArrayList<String[]> mUnion = new ArrayList<String[]>(I1.size()
				+ I2.size());
		mUnion.addAll(I1);
		mUnion.addAll(I2);
		return mUnion;
	}

	public static Tree makeT1() {
		Tree v1 = new Tree("a0");
		Tree v2 = new Tree("a1");
		Tree v3 = new Tree("e0");
		Tree v4 = new Tree("b0");
		Tree v5 = new Tree("b1");
		Tree v6 = new Tree("d0");

		v1.addChild(v2);
		v1.addChild(v5);
		v1.addChild(v6);

		v2.addChild(v3);
		v2.addChild(v4);

		return v1;
	}

	public static Tree makeT2() {
		Tree w5 = new Tree("a0");
		Tree w1 = new Tree("a1");
		Tree w3 = new Tree("c1");
		Tree w6 = new Tree("d0");
		Tree w7 = new Tree("e0");
		Tree w9 = new Tree("b0");

		w5.addChild(w1);
		w5.addChild(w3);
		w5.addChild(w6);

		w1.addChild(w7);
		w1.addChild(w9);

		return w5;
	}
}
