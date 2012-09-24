package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import matrix.Matrix;

public class Tree {
	private String label;
	private ArrayList<Tree> children;

	public Tree(String label) {
		this.label = label;
		this.children = new ArrayList<Tree>();
	}

	public void addChild(Tree tree) {
		this.children.add(tree);
	}

	public String getLabel() {
		return this.label;
	}

	public ArrayList<Tree> getChildren() {
		return this.children;
	}

	public boolean isLeaf() {
		return this.children.size() == 0;
	}
	
	public int getNumNodes() {
		int numNodes = 1;
		for (Tree t : this.children) {
			numNodes += t.getNumNodes();
		}
		return numNodes;
	}
	
	public List<String> getLabels() {
		List<String> labels = new ArrayList<String>();
		labels.add(this.label);
		for (Tree t : this.children) {
			labels.addAll(t.getLabels());
		}
		return labels;
	}
	
	public List<Tree> getTrees() {
		List<Tree> trees = new ArrayList<Tree>();
		trees.add(this);
		for (Tree t : this.children) {
			trees.addAll(t.getTrees());
		}
		return trees;
	}

	public String toString() {
		StringBuilder treeString = new StringBuilder();
		treeString.append(this.label);
		if (this.children.size() > 0) {
			treeString.append("\n");
		}
		for (Tree child : this.children) {
			treeString.append(this.label+":"+child.toString());
			treeString.append("\n");
		}
		return treeString.toString();
	}

	public static Tree makeRandomTree(int numNodes, String[] labels) {
		Random rand = new Random();

		Tree t = new Tree(labels[rand.nextInt(labels.length)]);

		if (numNodes > 1) {
			numNodes--;
			int numChildren = 1 + rand.nextInt((int) Math
					.max(1, numNodes / 2.0));
			for (int i = 0; i < numChildren - 1; i++) {
				int numDescendants = rand.nextInt((numNodes + 1) / numChildren);
				numNodes -= (numDescendants + 1);
				Tree tchild = makeRandomTree(numDescendants + 1, labels);
				t.addChild(tchild);
			}
			t.addChild(makeRandomTree(numNodes, labels));
		}

		return t;
	}

	public static Matrix getMatrix(Tree t) {
		List<Tree> trees = t.getTrees();
		int numNodes = trees.size();
		String[] labels = new String[trees.size()];
		for (int i = 0; i < numNodes; i++) {
			labels[i] = trees.get(i).getLabel(); 
		}
		Matrix m = new Matrix(numNodes, (String[]) labels);
		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numNodes; j++) {
				if (trees.get(i).getChildren().contains(trees.get(j))) {
					m.setElement(i, j, 1);
				} else {
					m.setElement(i, j, 0);
				}
			}
		}
		return m;
	}
}
