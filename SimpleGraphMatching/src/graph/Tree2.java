package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import matrix.Matrix;

public class Tree2 {
	private String label;
	private ArrayList<Tree2> children;

	public Tree2(String label) {
		this.label = label;
		this.children = new ArrayList<Tree2>();
	}

	public void addChild(Tree2 tree) {
		this.children.add(tree);
	}

	public String getLabel() {
		return this.label;
	}

	public ArrayList<Tree2> getChildren() {
		return this.children;
	}

	public boolean isLeaf() {
		return this.children.size() == 0;
	}
	
	public int getNumNodes() {
		int numNodes = 1;
		for (Tree2 t : this.children) {
			numNodes += t.getNumNodes();
		}
		return numNodes;
	}
	
	public List<String> getLabels() {
		List<String> labels = new ArrayList<String>();
		labels.add(this.label);
		for (Tree2 t : this.children) {
			labels.addAll(t.getLabels());
		}
		return labels;
	}
	
	public List<Tree2> getTrees() {
		List<Tree2> trees = new ArrayList<Tree2>();
		trees.add(this);
		for (Tree2 t : this.children) {
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
		for (Tree2 child : this.children) {
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
			int numChildren = Math.min(numNodes, 2 + rand.nextInt((int) Math
					.max(1, numNodes / 10)));
			numNodes -= numChildren;
			for (int i = 0; i < numChildren - 1; i++) {
				int numDescendants = rand.nextInt(Math.max(1, (numNodes + 1) / numChildren));
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
