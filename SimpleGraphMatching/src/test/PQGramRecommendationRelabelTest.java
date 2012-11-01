package test;

import org.junit.Test;

import tree.Tree;

public class PQGramRecommendationRelabelTest {	
	private Tree sourceTree;
	private Tree targetTree;

	@Test
	public void testRelabelLeaf() {
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		sourceTree.addChild(new Tree("c"));
		sourceTree.addChild(new Tree("d"));
		
		targetTree = new Tree("a");
		targetTree.addChild(new Tree("b"));
		targetTree.addChild(new Tree("e"));
		targetTree.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 1);
	}
	
	@Test
	public void testRelabelRoot() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));		

		targetTree = new Tree("z");
		Tree child2 = new Tree("b");
		targetTree.addChild(child2);
		child2.addChild(new Tree("c"));
		child2.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 1);
	}
	
	@Test
	public void testRelabelNonLeafNonRoot() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));

		targetTree = new Tree("a");
		Tree child2 = new Tree("z");
		targetTree.addChild(child2);
		child2.addChild(new Tree("c"));
		child2.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 1);
	}
	
	@Test
	public void testRelabelParentAndChild() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		targetTree = new Tree("a");
		Tree child2 = new Tree("z");
		targetTree.addChild(child2);
		child2.addChild(new Tree("y"));
		child2.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	
	@Test
	public void testRelabelSiblings() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		Tree child2 = new Tree("c");
		sourceTree.addChild(child1);
		sourceTree.addChild(child2);
		child1.addChild(new Tree("d"));
		child1.addChild(new Tree("e"));
		child2.addChild(new Tree("f"));
		child2.addChild(new Tree("g"));

		targetTree = new Tree("a");
		Tree child3 = new Tree("z");
		Tree child4 = new Tree("y");
		targetTree.addChild(child3);
		targetTree.addChild(child4);
		child3.addChild(new Tree("d"));
		child3.addChild(new Tree("e"));
		child4.addChild(new Tree("f"));
		child4.addChild(new Tree("g"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	
	@Test
	public void testRelabelParentAndChildWithChildren() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		Tree child2 = new Tree("c");
		child1.addChild(child2);
		child1.addChild(new Tree("e"));
		child2.addChild(new Tree("d"));

		targetTree = new Tree("a");
		Tree child3 = new Tree("z");
		targetTree.addChild(child3);
		Tree child4 = new Tree("y");
		child3.addChild(child4);
		child3.addChild(new Tree("e"));
		child4.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	
	@Test
	public void testRelabelAllTheNodes() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		sourceTree.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		child1.addChild(new Tree("e"));
		
		targetTree = new Tree("z");
		Tree child2 = new Tree("y");
		targetTree.addChild(child2);
		targetTree.addChild(new Tree("x"));
		child2.addChild(new Tree("w"));
		child2.addChild(new Tree("v"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 5);
	}

}
