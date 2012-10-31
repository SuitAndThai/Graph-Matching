package pqgram;


import tree.Tree;

public class Main {

	public static void main(String[] args) {
		Tree t1 = makeT1();
		System.out.println(t1);
		Tree t2 = makeT2();
		System.out.println(t2);
		int p = 2;
		int q = 3;
		System.out.println("Distance: " + PQGram.dist(t1, t2, p, q));
		System.out.println();
		PQGramRecommendation.getEdits(PQGram.pqGramIndex(t1, p, q), PQGram.pqGramIndex(t2, p, q), t1, t2);
	}
	
	public static Tree makeT2() {
		Tree root = new Tree("b");
		
//		Tree child1 = new Tree("b");
//		root.addChild(child1);
		root.addChild(new Tree("c"));
		root.addChild(new Tree("d"));
		
		return root;
	}

	public static Tree makeT1() {
		Tree root = new Tree("a");
		
		Tree child1 = new Tree("b");
		root.addChild(child1);
		
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		return root;
	}
}
