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
	
	@Test
	public void testRichardsStupidExample() {
		sourceTree = new Tree("a");
		Tree b = new Tree("b");
		Tree c = new Tree("c");
		Tree d = new Tree("d");
		Tree e = new Tree("e");
		Tree f = new Tree("f");
		Tree g = new Tree("g");
		Tree h = new Tree("h");
		Tree i = new Tree("i");
		sourceTree.addChild(b);
		sourceTree.addChild(c);
		b.addChild(d);
		d.addChild(e);
		d.addChild(f);
		c.addChild(g);
		g.addChild(h);
		g.addChild(i);
		

		targetTree = new Tree("g");
		Tree b2 = new Tree("b");
		Tree c2 = new Tree("c");
		Tree d2 = new Tree("d");
		Tree e2 = new Tree("e");
		Tree f2 = new Tree("f");
		Tree a2 = new Tree("a");
		Tree h2 = new Tree("h");
		Tree i2 = new Tree("i");
		targetTree.addChild(h2);
		targetTree.addChild(i2);
		h2.addChild(a2);
		a2.addChild(b2);
		a2.addChild(c2);
		i2.addChild(d2);
		d2.addChild(e2);
		d2.addChild(f2);
		
		TestUtilities.getEditsAndAssertSize(sourceTree, targetTree, 4);
	}

}
