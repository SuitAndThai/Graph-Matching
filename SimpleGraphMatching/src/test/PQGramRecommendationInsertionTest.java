package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import pqgram.PQGram;
import pqgram.PQGramRecommendation;
import pqgram.edits.Edit;
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
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree); 
		assertEquals(1, edits.size());
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
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(1, edits.size());
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
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(1, edits.size());
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
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(2, edits.size());
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
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(2, edits.size());
	}

}
