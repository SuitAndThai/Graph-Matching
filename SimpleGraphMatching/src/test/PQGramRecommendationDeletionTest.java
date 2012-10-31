package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import pqgram.PQGram;
import pqgram.PQGramRecommendation;
import pqgram.edits.Edit;
import tree.Tree;

public class PQGramRecommendationDeletionTest {
	int p = 2;
	int q = 3;
	
	private Tree sourceTree;
	private Tree targetTree;

	@Test
	public void testDeleteLeaf() {
		sourceTree = new Tree("a");
		sourceTree.addChild(new Tree("b"));
		sourceTree.addChild(new Tree("c"));
		sourceTree.addChild(new Tree("d"));
		
		targetTree = new Tree("a");
		targetTree.addChild(new Tree("b"));
		targetTree.addChild(new Tree("d"));
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(1, edits.size());
	}
	
	@Test
	public void testDeleteRoot() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		targetTree = new Tree("b");
		targetTree.addChild(new Tree("c"));
		targetTree.addChild(new Tree("d"));
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(1, edits.size());
	}
	
	@Test
	public void testDeleteNonLeafNonRoot() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		targetTree = new Tree("a");
		targetTree.addChild(new Tree("c"));
		targetTree.addChild(new Tree("d"));
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(1, edits.size());
	}
	
	@Test
	public void testDeleteParentAndChild() {
		sourceTree = new Tree("a");
		Tree child1 = new Tree("b");
		sourceTree.addChild(child1);
		child1.addChild(new Tree("c"));
		child1.addChild(new Tree("d"));
		
		targetTree = new Tree("a");
		targetTree.addChild(new Tree("d"));
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(2, edits.size());
	}
	
	@Test
	public void testDeleteSiblings() {
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
		targetTree.addChild(new Tree("d"));
		targetTree.addChild(new Tree("e"));
		targetTree.addChild(new Tree("f"));
		targetTree.addChild(new Tree("g"));
		
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.pqGramIndex(sourceTree, p, q), PQGram.pqGramIndex(targetTree, p, q), sourceTree, targetTree);
		assertEquals(2, edits.size());
	}

}
