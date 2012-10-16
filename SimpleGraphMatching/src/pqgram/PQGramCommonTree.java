package pqgram;

import graph.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PQGramCommonTree {
	public static Tree commonTree(ArrayList<String[]> I1, ArrayList<String[]> I2) {
		ArrayList<String[]> intersection = Main.multiIntersection(I1, I2);
		ArrayList<String[]> difference = Main.multiDifference(I2, intersection);
		
		Set<Tree> trees = new HashSet<Tree>();
		Map<String, Tree> built = new HashMap<String, Tree>();
		Set<List<String>> hookedUp = new HashSet<List<String>>();
		for (String[] tup : intersection) {
			Tree prev = null;
			for (int i = 0; i < 5; i++) {
				if (tup[i].equals(PQGramIndexMaker.STAR_LABEL)) continue;
				Tree t;
				if (built.containsKey(tup[i])) {
					t = built.get(tup[i]);
				} else {
					t = new Tree(tup[i]);
					built.put(tup[i], t);
					trees.add(t);
				}
				if (i == 1 && prev != null) {
					if (!hookedUp.contains(list(prev.getLabel(), t.getLabel()))) {
						prev.addChild(t);
						trees.remove(t);
						hookedUp.add(list(prev.getLabel(), t.getLabel()));
					}
				}
				prev = t;
			}
		}
		for (Tree t : trees) {
			System.out.println(t);
		}
		
		for (String[] tup : difference) {
			Tree prev = null;
			for (int i = 0; i < 5; i++) {
				if (tup[i].equals(PQGramIndexMaker.STAR_LABEL)) continue;
				Tree t;
				if (built.containsKey(tup[i])) {
					t = built.get(tup[i]);
				} else {
					t = new Tree(tup[i]);
					built.put(tup[i], t);
					System.out.println("Add " + t.getLabel());
					trees.add(t);
				}
				if (i == 1 && prev != null) {
					if (!hookedUp.contains(list(prev.getLabel(), t.getLabel()))) {
						prev.addChild(t);
						trees.remove(t);
						hookedUp.add(list(prev.getLabel(), t.getLabel()));
						System.out.println("Add " + t.getLabel() + " to " + prev.getLabel());
					}
				}
				prev = t;
			}
		}
		for (Tree t : trees) {
			System.out.println(t);
		}
		
		
		return null;
//		int numPQGrams = intersection.size();
//		Set<Tree> trees = new HashSet<Tree>();
//		Tree root = null;
//		Tree child = null;
//		Tree newChild = null;
//		Map<String, Tree> builtTrees = new HashMap<String, Tree>();
//		Set<List<String>> hookedUp = new HashSet<List<String>>();
//		boolean foundRoot = false;
//		Main.printI(intersection);
//		if (numPQGrams > 0) {
//			for (int i = 0; i < numPQGrams; i++) {
//				if ((intersection.get(i)[0].equals(PQGramIndexMaker.STAR_LABEL)) && (!foundRoot)) {
//					root = new Tree(intersection.get(i)[1]);
//					builtTrees.put(list(intersection.get(i)[0], intersection.get(i)[1]), root);
//					trees.add(child);
//					foundRoot = true;
//				}
//				else if (!builtTrees.containsKey(list(intersection.get(i)[0], intersection.get(i)[1]))) {
//					child = new Tree(intersection.get(i)[1]);
//					builtTrees.put(list(intersection.get(i)[0],intersection.get(i)[1]), child);
//					trees.add(child);
//				}
//				for (int j = 2; j < 5; j++) {
//					if (!intersection.get(i)[j].equals(PQGramIndexMaker.STAR_LABEL)) {
//						if (builtTrees.containsKey(list(intersection.get(i)[1],intersection.get(i)[j]))) {
//							if (!hookedUp.contains(list(intersection.get(i)[1],intersection.get(i)[j]))) {
//								child = builtTrees.get(list(intersection.get(i)[0],intersection.get(i)[1]));
//								newChild = builtTrees.get(list(intersection.get(i)[1],intersection.get(i)[j]));
//								child.addChild(newChild);
//								trees.remove(newChild);
//								hookedUp.add(list(intersection.get(i)[1],intersection.get(i)[j]));
//							}
//						}
//					}
//				}
//			}
//		}
//		for (Tree t : trees) {
//			System.out.println(t);
//			System.out.println("---");
//		}
//		Main.printI(difference);
//		int numDifferences = difference.size();
//		if (numDifferences > 0) {
//			for (int i = 0; i < numDifferences; i++) {
//				if (!builtTrees.containsKey(list(difference.get(i)[0], difference.get(i)[1]))) {
//					child = new Tree(difference.get(i)[1]);
//					builtTrees.put(list(difference.get(i)[0],difference.get(i)[1]), child);
//					System.out.println("Adding " + difference.get(i)[0] + " " + difference.get(i)[1]);
//				}
//				for (int j = 2; j < 5; j++) {
//					if (difference.get(i)[j] != PQGramIndexMaker.STAR_LABEL) {
//						if (!builtTrees.containsKey(list(difference.get(i)[1], difference.get(i)[j]))) {
//							child = new Tree(difference.get(i)[j]);
//							builtTrees.get(list(difference.get(i)[0],difference.get(i)[1])).addChild(child);
//							System.out.println("Adding " + difference.get(i)[j] + " to " + difference.get(i)[0] + " " + difference.get(i)[1]);
//							builtTrees.put(list(difference.get(i)[1],difference.get(i)[j]), child);
//						}
//					}
//				}
//			}
//		}
//		for (Tree t : trees) {
//			System.out.println(t);
//			System.out.println("---");
//		}
//		return root;
	}
	
	public static List<String> list(String str1, String str2) {
		return new ArrayList<String>(Arrays.asList(str1, str2));
	}

	public static void main(String[] args) {
		int p = 2;
		int q = 3;
		ArrayList<String[]> I1 = PQGramIndexMaker.pqGramIndex(Main.makeT1(), p,
				q);
		ArrayList<String[]> I2 = PQGramIndexMaker.pqGramIndex(Main.makeT2(), p,
				q);
		Tree common = commonTree(I1, I2);
		System.out.println(common);
	}
}