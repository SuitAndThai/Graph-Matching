package test;

import org.junit.Test;

import tree.Tree;

public class PQGramRecommendationMixedTest {	
	private Tree sourceTree;
	private Tree targetTree;

	@Test
	public void testRelabelLeafAndInsertLeaf() {
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		sourceTree.addChild(new Tree("c"));
		
		targetTree = new Tree("a");
		targetTree.addChild(new Tree("z"));
		targetTree.addChild(new Tree("d"));
		targetTree.addChild(new Tree("c"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}

	@Test
	public void testRelabelAndInsertOntoLeaf() {
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		
		targetTree = new Tree("a");
		Tree child = new Tree("c");
		targetTree.addChild(child);
		child.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}

	@Test
	public void testDeleteLeafAndInsertLeaf() {
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		sourceTree.addChild(new Tree("c"));
		
		targetTree = new Tree("a");
		Tree child = new Tree("c");
		targetTree.addChild(child);
		child.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}

	@Test
	public void testRelabelRootAndInsertLeaf() {
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		sourceTree.addChild(new Tree("c"));
		
		targetTree = new Tree("z");
		targetTree.addChild(new Tree("b"));
		Tree child = new Tree("c");
		targetTree.addChild(child);
		child.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}

	@Test
	public void testRelabelRootAndDeleteLeaf() {
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		sourceTree.addChild(new Tree("c"));
		
		targetTree = new Tree("z");
		targetTree.addChild(new Tree("b"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	
	@Test
	public void testRelabelNonLeafNonRootAndDeleteLeaf() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		child1.addChild(new Tree("c"));
		sourceTree.addChild(child1);
		sourceTree.addChild(new Tree("d"));
		
		targetTree = new Tree("a");
		targetTree.addChild(new Tree("z"));
		targetTree.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}

}
