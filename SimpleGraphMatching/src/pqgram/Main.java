package pqgram;


import tree.Tree;

public class Main {

	public static void main(String[] args) {
		Tree t1 = makeT1();
		Tree t2 = makeT2();
		int p = 2;
		int q = 3;
		System.out.println("Distance: " + PQGram.dist(t1, t2, p, q));
		System.out.println();
		PQGramRecommendation.getEdits(PQGram.pqGramIndex(t1, p, q), PQGram.pqGramIndex(t2, p, q), t1, t2);
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
		Tree v2 = new Tree("a2");
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
}
