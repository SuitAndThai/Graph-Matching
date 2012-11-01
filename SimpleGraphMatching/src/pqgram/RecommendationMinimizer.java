package pqgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pqgram.edits.Deletion;
import pqgram.edits.Insertion;

import tree.Tree;

public class RecommendationMinimizer {

	public static void minimizeDeletions(List<Insertion> insertions, List<Deletion> deletions, Map<String, String> relabelings) {
		List<Insertion> insertionsToRemove = new ArrayList<Insertion>();
		List<Deletion> deletionsToRemove = new ArrayList<Deletion>();
		Map<String, String> deletedToParent = new HashMap<String, String>();
		// add all unaffected deletions
		for (Deletion deletion : deletions) {
			if (relabelings.containsKey(deletion.getA())) {
				deletion.setA(relabelings.get(deletion.getA()));
			}
			if (relabelings.containsKey(deletion.getB())) {
				deletion.setB(relabelings.get(deletion.getB()));
			}
			if (!relabelings.containsKey(deletion.getB())) { // not a relabeling
				deletedToParent.put(deletion.getB(), deletion.getA()); // add to deleted -> parent mapping
				String newParentLabel = deletion.getA();
				if (deletedToParent.containsKey(newParentLabel)) { // parent has already been deleted
					while (deletedToParent.containsKey(newParentLabel)) {
						newParentLabel = deletedToParent.get(newParentLabel);
					}
					deletion.setA(newParentLabel);
				}
				boolean hasMatchingInsertion = false;
				for (Insertion insertion : insertions) { // check for a matching insertion
					if (insertion.getA().equals(newParentLabel) && insertion.getB().equals(deletion.getB())) { // if the deletion and insertion are inverses, we don't need them
						insertionsToRemove.add(insertion);
						hasMatchingInsertion = true;
					}
				}
				if (!hasMatchingInsertion) {
					deletedToParent.put(deletion.getB(), deletion.getA());
				} else {
					deletionsToRemove.add(deletion);
					deletedToParent.remove(deletion.getB());
				}
			}
		}
		
		insertions.removeAll(insertionsToRemove);
		deletions.removeAll(deletionsToRemove);
	}

	public static void minimizeInsertions(List<Insertion> insertions, List<Deletion> deletions, Map<String, String> relabelings) {
		List<Insertion> insertionsToRemove = new ArrayList<Insertion>();
		List<Deletion> deletionsToRemove = new ArrayList<Deletion>();
		
		Map<String, String> parentToInserted = new HashMap<String, String>();
		Map<String, Insertion> finalInsertions = new HashMap<String, Insertion>();
		// remove all of the deletions and insertions that weren't caught above
		for (Insertion insertion : insertions) {
			if (!relabelings.containsKey(insertion.getB())) { // not a relabeling
				parentToInserted.put(insertion.getB(), insertion.getA()); // add to parent -> inserted mapping
				finalInsertions.put(insertion.getB(), insertion);
				if (parentToInserted.containsKey(insertion.getA())) {
					boolean hasMatchingDeletion = false;
					for (Deletion deletion : deletions) { // check for a matching deletion
						if (deletion.getA().equals(parentToInserted.get(insertion.getA())) && deletion.getB().equals(insertion.getB())) {
							deletionsToRemove.add(deletion);
							insertionsToRemove.add(insertion);
							hasMatchingDeletion = true;
							if (finalInsertions.containsKey(insertion.getA())) {
								Insertion parentInsertion = finalInsertions.get(insertion.getA());
								parentInsertion.addInheritedChild(insertion.getB());
								
								while (finalInsertions.containsKey(parentInsertion.getA())) { // Richard understands this
									parentInsertion = finalInsertions.get(parentInsertion.getA());
									parentInsertion.addInheritedChild(insertion.getB());
								}
							}
						}
					}
					if (!hasMatchingDeletion) {
						parentToInserted.put(insertion.getB(), parentToInserted.get(insertion.getA()));
						finalInsertions.put(insertion.getB(), insertion);
					}
				}
			}
		}
	
		insertions.removeAll(insertionsToRemove);
		deletions.removeAll(deletionsToRemove);
	}

	public static Map<String, String> getRelabelings(List<Insertion> insertions, List<Deletion> deletions, Tree sourceTree, Tree targetTree) {
		List<Insertion> insertionsToRemove = new ArrayList<Insertion>();
		List<Deletion> deletionsToRemove = new ArrayList<Deletion>();
		
		Map<String, String> relabelings = new HashMap<String, String>();
		for (Insertion insertion : insertions) {
			String insertedOn = insertion.getA();
			String inserted = insertion.getB();
			int insertedPosition = insertion.getStart();
			for (Deletion deletion : deletions) {
				String deletedFrom = deletion.getA();
				String deleted = deletion.getB();
				int deletedPosition = deletion.getPosition();
				if (relabelings.containsKey(deletedFrom)) {
					deletedFrom = relabelings.get(deletedFrom);
				}
				if (relabelings.containsKey(deleted)) {
					deleted = relabelings.get(deleted);
				}
				Tree deletedTreeInSource = sourceTree.find(deleted);
				Tree insertedTreeInSource = sourceTree.find(inserted);
				Tree deletedTreeInTarget = targetTree.find(deleted);
				Tree insertedTreeInTarget = targetTree.find(inserted);
				boolean labelIsSomewhereElseInSource = false;
				if (deletedTreeInSource != null) {
					labelIsSomewhereElseInSource = (deletedTreeInSource.getParent() == null) ? deletedTreeInSource.isDescendant(insertedTreeInSource) : deletedTreeInSource.getParent().isDescendant(insertedTreeInSource);
				}
				boolean labelIsSomewhereElseInTarget = false;
				if (insertedTreeInTarget != null) {
					labelIsSomewhereElseInTarget = (insertedTreeInTarget.getParent() == null) ? insertedTreeInTarget.isDescendant(deletedTreeInTarget) : insertedTreeInTarget.getParent().isDescendant(deletedTreeInTarget);
				}
				if (insertedOn.equals(deletedFrom) && (insertedPosition == deletedPosition) && !labelIsSomewhereElseInSource && !labelIsSomewhereElseInTarget) {
					relabelings.put(deleted, inserted);
					insertionsToRemove.add(insertion);
					deletionsToRemove.add(deletion);
				}
			}
		}
		
		insertions.removeAll(insertionsToRemove);
		deletions.removeAll(deletionsToRemove);
	
		return relabelings;
	}


}
