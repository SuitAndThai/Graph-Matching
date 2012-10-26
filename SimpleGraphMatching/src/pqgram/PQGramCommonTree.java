package pqgram;

import graph.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PQGramCommonTree {

	public static <T> void printList(List<T> list) {
		for (T element : list) {
			System.out.println(element);
		}
	}

	public static Set<Tree> commonTree(ArrayList<String[]> I1,
			ArrayList<String[]> I2) {
		ArrayList<String[]> common = Main.multiIntersection(I1, I2);
		ArrayList<String[]> missing = Main.multiDifference(I2, common);
		ArrayList<String[]> extra = Main.multiDifference(I1, common);

		Set<Tree> trees = new HashSet<Tree>();
		Map<String, Tree> built = new HashMap<String, Tree>();
		Set<List<String>> hookedUp = new HashSet<List<String>>();

		buildCommonTree(common, trees, built, hookedUp);

		List<Triple<String, String, Integer>> deletions = getTriples(extra,
				built, hookedUp);
		System.out.println("Deletions");
		printList(deletions);

		List<Triple<String, String, Integer>> insertions = getTriples(missing,
				built, hookedUp);
		System.out.println("\nInsertions");
		printList(insertions);

		List<Edit> totalEdits = getRelabelings(insertions, deletions);

		System.out.println("\nTotal Edits:");
		printList(totalEdits);

		return trees;
	}

	private static List<Edit> getRelabelings(
			List<Triple<String, String, Integer>> insertions,
			List<Triple<String, String, Integer>> deletions) {
		List<Edit> insertionList = new ArrayList<Edit>();
		Map<String, String> relabelings = new HashMap<String, String>();
		for (Triple<String, String, Integer> insertion : insertions) {
			boolean didRelabel = false;
			String insertionParent = insertion.getA();
			String insertionChild = insertion.getB();
			for (Triple<String, String, Integer> deletion : deletions) {
				String parent = deletion.getA();
				String child = deletion.getB();
				int position = deletion.getC();
				if (relabelings.containsKey(parent)) {
					parent = relabelings.get(parent);
				}
				if (relabelings.containsKey(child)) {
					child = relabelings.get(child);
				}
				if (insertion.getA().equals(parent)
						&& insertion.getC().equals(position)) {
					// TODO: before we relabel a node, check if the new label is
					// in this node's descendants
					relabelings.put(child, insertion.getB());
					didRelabel = true;
				}
			}
			if (!didRelabel)
				insertionList
						.add(new Insertion(insertionParent, insertionChild));
		}

		List<Edit> editList = new ArrayList<Edit>();

		// add all relabelings
		for (String oldName : relabelings.keySet()) {
			if (!oldName.equals(relabelings.get(oldName)))
				editList.add(new Relabeling(oldName, relabelings.get(oldName)));
		}

		// add all unaffected deletions
		for (Triple<String, String, Integer> deletion : deletions) {
			if (!relabelings.containsKey(deletion.getB())) {
				editList.add(new Deletion(deletion.getA(), deletion.getB()));
			}
		}

		// add all unaffected insertions
		editList.addAll(insertionList);

		return editList;
	}

	private static void buildCommonTree(ArrayList<String[]> commons,
			Set<Tree> trees, Map<String, Tree> built, Set<List<String>> hookedUp) {
		for (String[] tup : commons) {
			Tree prev = null;
			for (int i = 0; i < 5; i++) {
				if (tup[i].equals(PQGramIndexMaker.STAR_LABEL))
					continue;
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
	}

	public static List<String[]> cloneList(List<String[]> list) {
		List<String[]> clone = new ArrayList<String[]>(list.size());
		for (String[] item : list)
			clone.add(item.clone());
		return clone;
	}

	public static Map<String, Tree> cloneMap(Map<String, Tree> map) {
		Map<String, Tree> map2 = new HashMap<String, Tree>();

		Set<Entry<String, Tree>> set1 = map.entrySet();
		for (Entry<String, Tree> e : set1) {
			map2.put(e.getKey(), e.getValue());
		}

		return map2;
	}

	public static Set<List<String>> cloneSet(Set<List<String>> set) {
		Set<List<String>> set2 = new HashSet<List<String>>();

		for (List<String> list : set) {
			set2.add(list);
		}

		return set2;
	}

	private static List<Triple<String, String, Integer>> getTriples(
			ArrayList<String[]> commonsO, Map<String, Tree> builtO,
			Set<List<String>> hookedUpO) {
		List<String[]> commons = cloneList(commonsO);
		Map<String, Tree> built = cloneMap(builtO);
		Set<List<String>> hookedUp = cloneSet(hookedUpO);
		int position;
		List<Triple<String, String, Integer>> triples = new ArrayList<Triple<String, String, Integer>>();
		for (String[] tup : commons) {
			Tree prev = null;
			for (int i = 0; i < 5; i++) {
				if (tup[i].equals(PQGramIndexMaker.STAR_LABEL))
					continue;
				Tree t;
				if (built.containsKey(tup[i])) {
					t = built.get(tup[i]);
				} else {
					t = new Tree(tup[i]);
					built.put(tup[i], t);
				}
				if (i == 1 && prev != null) {
					if (!hookedUp.contains(list(prev.getLabel(), t.getLabel()))) {
						position = prev.addChild(t);
						triples.add(new Triple<String, String, Integer>(prev
								.getLabel(), t.getLabel(), position));
						hookedUp.add(list(prev.getLabel(), t.getLabel()));
					}
				}
				prev = t;
			}
		}
		Collections.reverse(triples);
		for (Triple<String, String, Integer> triple : triples) {
			Tree parent = built.get(triple.getA());
			int pos = triple.getC();
			parent.deleteChild(pos);
		}
		Collections.reverse(triples);
		return triples;
	}

	@SuppressWarnings("unused")
	private static void buildTargetTree(ArrayList<String[]> commons,
			Set<Tree> trees, Map<String, Tree> built, Set<List<String>> hookedUp) {
		int position;
		List<Triple<String, String, Integer>> insertions = new ArrayList<Triple<String, String, Integer>>();
		for (String[] tup : commons) {
			Tree prev = null;
			for (int i = 0; i < 5; i++) {
				if (tup[i].equals(PQGramIndexMaker.STAR_LABEL))
					continue;
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
						position = prev.addChild(t);
						insertions.add(new Triple<String, String, Integer>(prev
								.getLabel(), t.getLabel(), position));
						trees.remove(t);
						hookedUp.add(list(prev.getLabel(), t.getLabel()));
						System.out.println("Add " + t.getLabel() + " to "
								+ prev.getLabel());
					}
				}
				prev = t;
			}
		}
		for (Triple<String, String, Integer> triple : insertions) {
			System.out.println(triple);
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

		System.out.println("I1 Tree");
		Main.printI(I1);

		System.out.println("I2 Tree");
		Main.printI(I2);

		Set<Tree> common = commonTree(I1, I2);
		System.out.println("\nCommon Sub-trees");
		System.out.println(common);
	}
}