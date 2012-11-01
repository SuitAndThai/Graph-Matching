package test;

import org.junit.Test;

import tree.Tree;

public class PQGramRecommendationInsertionTest {
	int p = 2;
	int q = 3;
	
	private Tree targetTree;
	private Tree sourceTree;

	@Test
	public void testInsertLeaf() {
		targetTree = new Tree("a");
		targetTree.addChild(new Tree("b"));
		targetTree.addChild(new Tree("c"));
		targetTree.addChild(new Tree("d"));
		
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		sourceTree.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 1);
	}
	
	@Test
	public void testInsertRoot() {
		targetTree = new Tree("a");
		Tree child1 = new Tree("b");
		targetTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		sourceTree = new Tree("b");
		sourceTree.addChild(new Tree("c"));
		sourceTree.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 1);
	}
	
	@Test
	public void testInsertNonLeafNonRoot() {
		targetTree = new Tree("a");
		Tree child1 = new Tree("b");
		targetTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("c"));
		sourceTree.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 1);
	}
	
	@Test
	public void testInsertParentAndChild() {
		targetTree = new Tree("a");
		Tree child1 = new Tree("b");
		targetTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("d"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	
	@Test
	public void testInsertSiblings() {
		targetTree = new Tree("a");
		Tree child1 = new Tree("b");
		Tree child2 = new Tree("c");
		targetTree.addChild(child1);
		targetTree.addChild(child2);
		child1.addChild(new Tree("d"));
		child1.addChild(new Tree("e"));
		child2.addChild(new Tree("f"));
		child2.addChild(new Tree("g"));
		
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("d"));
		sourceTree.addChild(new Tree("e"));
		sourceTree.addChild(new Tree("f"));
		sourceTree.addChild(new Tree("g"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	
	@Test
	public void testInsertSiblingsWithExtraThings() {
		targetTree = new Tree("a");
		Tree child1 = new Tree("b");
		Tree child2 = new Tree("c");
		targetTree.addChild(new Tree("z"));
		targetTree.addChild(child1);
		targetTree.addChild(child2);
		targetTree.addChild(new Tree("y"));
		child1.addChild(new Tree("d"));
		child1.addChild(new Tree("e"));
		child2.addChild(new Tree("f"));
		child2.addChild(new Tree("g"));
		
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("z"));
		sourceTree.addChild(new Tree("d"));
		sourceTree.addChild(new Tree("e"));
		sourceTree.addChild(new Tree("f"));
		sourceTree.addChild(new Tree("g"));
		sourceTree.addChild(new Tree("y"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	
	@Test
	public void testInsertParentAndChildWithChildren() {
		targetTree = new Tree("a");
		Tree child1 = new Tree("b");
		targetTree.addChild(child1);
		Tree child2 = new Tree("c");
		child1.addChild(child2);
		child1.addChild(new Tree("e"));
		child2.addChild(new Tree("d"));
		
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("d"));
		sourceTree.addChild(new Tree("e"));
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 2);
	}
	

	
	@Test
	public void testDeleteOriginalAndInsertAllNewNodes() {
		targetTree = new Tree("a");
		Tree b = new Tree("b");
		Tree c = new Tree("c");
		Tree d = new Tree("d");
		Tree e = new Tree("e");
		Tree f = new Tree("f");
		Tree g = new Tree("g");
		Tree h = new Tree("h");
		Tree i = new Tree("i");
		targetTree.addChild(b);
		targetTree.addChild(c);
		b.addChild(d);
		d.addChild(e);
		d.addChild(f);
		c.addChild(g);
		g.addChild(h);
		g.addChild(i);
		

		sourceTree = new Tree("z");
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 9);
	}

}
