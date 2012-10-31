package pqgram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pqgram.edits.Deletion;
import pqgram.edits.Edit;
import pqgram.edits.Insertion;
import pqgram.edits.PositionalEdit;
import pqgram.edits.Relabeling;
import tree.Tree;

public class PQGramRecommendation {

	public static List<Edit> getEdits(Index I1, Index I2, Tree sourceTree, Tree targetTree) {
		Index common = I1.intersect(I2);
		Index missing = I2.difference(common);
		Index extra = I1.difference(common);

		Map<String, Tree> built = new HashMap<String, Tree>();
		Map<String, String> childToParent = new HashMap<String, String>();

		getCommonTrees(common, built, childToParent);

		List<Deletion> deletions = getDeletions(extra, built, childToParent);
		System.out.println("Deletions");
		Utilities.printList(deletions);

		List<Insertion> insertions = getInsertions(missing, built, childToParent);
		System.out.println("\nInsertions");
		Utilities.printList(insertions);

		Map<String, String> relabelings = RecommendationMinimizer.getRelabelings(insertions, deletions, sourceTree, targetTree);
		
		RecommendationMinimizer.minimizeDeletions(insertions, deletions, relabelings);

		RecommendationMinimizer.minimizeInsertions(insertions, deletions, relabelings);
		
		// Build up edits
		
		List<Edit> edits = new ArrayList<Edit>();
		
		// add all relabelings
		for (String oldName : relabelings.keySet()) {
			if (!oldName.equals(relabelings.get(oldName))) {
				edits.add(new Relabeling(oldName, relabelings.get(oldName)));
			}
		}
		
		edits.addAll(insertions);
		edits.addAll(deletions);

		System.out.println("\nTotal Edits:");
		Utilities.printList(edits);

		return edits;
	}
	
	private static List<Deletion> getDeletions(Index extra, Map<String, Tree> built, Map<String, String> childToParent) {
		List<PositionalEdit> posEdits = getPositionalEdits(extra, built, childToParent);
		
		List<Deletion> deletions = new ArrayList<Deletion>();
		for (PositionalEdit posEdit : posEdits) {
			deletions.add(new Deletion(posEdit.getA(), posEdit.getB(), posEdit.getPosition()));
		}
		return deletions;
	}
	
	private static List<Insertion> getInsertions(Index missing, Map<String, Tree> built, Map<String, String> childToParent) {
		List<PositionalEdit> posEdits = getPositionalEdits(missing, built, childToParent);
		
		List<Insertion> insertions = new ArrayList<Insertion>();
		for (PositionalEdit posEdit : posEdits) {
			insertions.add(new Insertion(posEdit.getA(), posEdit.getB(), posEdit.getPosition(), posEdit.getPosition()));
		}
		return insertions;
	}

	private static List<PositionalEdit> getPositionalEdits(Index pieces, Map<String, Tree> built, Map<String, String> childToParent) {
		pieces = pieces.clone();
		built = Utilities.cloneMap(built);
		childToParent = Utilities.cloneMap(childToParent);
		
		List<PositionalEdit> edits = new ArrayList<PositionalEdit>();
		
		// each 2,3-Gram looks like (ancestor, parent, child1, child2, child3)
		for (Tuple<String> tup : pieces.getAllElements()) {
			Tree ancestor = getTree(tup.get(0), built);
			Tree parent = getTree(tup.get(1), built);
			int position = addChildToParent(ancestor, parent, childToParent);
			if (position >= 0) {
				edits.add(new PositionalEdit(ancestor.getLabel(), parent.getLabel(), position));
			}
			for (int i = 2; i < tup.size(); i++) {				
				String currentLabel = tup.get(i);
				Tree currentTree = getTree(currentLabel, built);
				position = addChildToParent(parent, currentTree, childToParent);
				if (position >= 0) {
					edits.add(new PositionalEdit(parent.getLabel(), currentLabel, position));
				}
			}
		}
		
		Collections.reverse(edits);
		for (PositionalEdit edit : edits) {
			Tree parent = built.get(edit.getA());
			parent.deleteChild(edit.getPosition());
		}
		Collections.reverse(edits);
		return edits;
	}
	
	// if tree has already been constructed, grab it; otherwise create it and add it
	private static Tree getTree(String label, Map<String, Tree> builtTrees) {
		if (builtTrees.containsKey(label)) {
			return builtTrees.get(label);
		}
		Tree tree = new Tree(label);
		builtTrees.put(label, tree);
		return tree;
	}
	
	// hook up a child to its parent if not already done and return position; return -1 if already done
	private static int addChildToParent(Tree parent, Tree child, Map<String, String> childToParent) {
		if (!childToParent.containsKey(child.getLabel())) {
			childToParent.put(child.getLabel(), parent.getLabel());
			return parent.addChild(child);
		}
		return -1;
	}

	private static Set<Tree> getCommonTrees(Index common, Map<String, Tree> built, Map<String, String> childToParent) {
		Set<Tree> commonTrees = new HashSet<Tree>();
		for (Tuple<String> tup : common.getAllElements()) {
			Tree ancestor = getTree(tup.get(0), built);
			commonTrees.add(ancestor);
			Tree parent = getTree(tup.get(1), built);
			addChildToParent(parent, ancestor, childToParent);
			for (int i = 2; i < tup.size(); i++) {				
				String currentLabel = tup.get(i);
				Tree currentTree = getTree(currentLabel, built);
				commonTrees.add(currentTree);
				addChildToParent(parent, currentTree, childToParent);
				commonTrees.remove(currentTree);
			}
		}
		return commonTrees;
	}
}