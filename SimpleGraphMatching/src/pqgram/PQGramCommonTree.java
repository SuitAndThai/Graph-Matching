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
		buildTrees(intersection, trees, built, hookedUp);
		System.out.println("Edits");
		buildTrees(difference, trees, built, hookedUp);
		for (Tree t : trees) {
			System.out.println(t);
		}
		
		return null;
	}

	private static void buildTrees(ArrayList<String[]> commons,
			Set<Tree> trees, Map<String, Tree> built, Set<List<String>> hookedUp) {
		for (String[] tup : commons) {
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