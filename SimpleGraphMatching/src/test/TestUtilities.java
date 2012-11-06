package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import pqgram.PQGram;
import pqgram.PQGramRecommendation;
import pqgram.Utilities;
import pqgram.edits.Edit;
import tree.Tree;

public class TestUtilities {
	private static final int P = 2;
	private static final int Q = 3;
	
	public static void getEditsAndAssertSize(Tree sourceTree, Tree targetTree, int size) {
		List<Edit> edits = PQGramRecommendation.getEdits(PQGram.getProfile(sourceTree, P, Q), PQGram.getProfile(targetTree, P, Q), sourceTree, targetTree);
//		Utilities.printList(edits);
		assertEquals(size, edits.size());
	}
}
