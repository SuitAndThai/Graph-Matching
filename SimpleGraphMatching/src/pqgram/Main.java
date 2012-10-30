package pqgram;


import tree.Tree;

public class Main {

	public static void main(String[] args) {
		Tree g = makeT1();
		Tree g2 = makeT2();
		int p = 2;
		int q = 3;
		System.out.println(dist(g, g2, p, q));
	}

	public static void printI(Index I) {
		for (Tuple<String> tup : I.getAllElements()) {
			for (int i = 0; i < tup.size(); i++) {
				System.out.print(tup.get(i) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static double dist(Tree T1, Tree T2, int p, int q) {
		Index index = PQGramIndexMaker.pqGramIndex(T1, p, q);
		printI(index);
		Index index2 = PQGramIndexMaker.pqGramIndex(T2, p, q);
		printI(index2);
		Index mUnion = index.union(index2);
		printI(mUnion);
		Index mIntersection = index.intersect(index2);
		printI(mIntersection);
		return 1 - (2.0 * mIntersection.size()) / mUnion.size();
	}

	public static Tree makeT2() {
		Tree v1 = new Tree("a0");
		Tree v2 = new Tree("a1");
		Tree v3 = new Tree("e0");
		Tree v4 = new Tree("b0");
		Tree v5 = new Tree("b1");
		Tree v6 = new Tree("c0");

		v1.addChild(v2);
		v1.addChild(v5);
		v1.addChild(v6);

		v2.addChild(v3);
		v2.addChild(v4);

		return v1;
	}

	public static Tree makeT1() {
		Tree v1 = new Tree("a0");
		Tree v2 = new Tree("a1");
//		Tree v3 = new Tree("e0");
		Tree v4 = new Tree("b0");
		Tree v5 = new Tree("b1");
		Tree v6 = new Tree("c0");
		

		v1.addChild(v2);
		v1.addChild(v4);
		v1.addChild(v5);
		v1.addChild(v6);


		return v1;
	}

	public static Tree makeT3() {
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
}
