package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
			treeString.append(this.label + ":" + child.toString());
			treeString.append("\n");
		}
		return treeString.toString();
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
	
	public static int[] getRandomPartition(int n, int numPartitions) {
		int[] partitions = new int[numPartitions];
		for (int i = 0; i < partitions.length; i++) {
			partitions[i] = 1;
		}
		Random rand = new Random();
		for (int j = 0; j < n; j++) {
			partitions[(rand.nextInt(partitions.length) + rand.nextInt(partitions.length)) % numPartitions]++;
		}
		return partitions;
	}

	public static Tree makeRandomTree(int numNodes, String[] labels) {
		int minNumChildren = 1;
		int maxNumChildren = 30;
		
		Random rand = new Random();
		Tree t = new Tree(labels[rand.nextInt(labels.length)]);

		numNodes--;

		if (numNodes > 0) {
			int numChildren;
			if (numNodes <= minNumChildren) {
				numChildren = numNodes;
			} else {
				numChildren = Math.min(numNodes, minNumChildren) + rand.nextInt(Math.min(numNodes, maxNumChildren) - Math.min(numNodes, minNumChildren));
			}
			numNodes -= numChildren;
			int[] numDescendants = getRandomPartition(numNodes, numChildren);
			for (int i = 0; i < numChildren; i++) {
				t.addChild(makeRandomTree(numDescendants[i], labels));
			}
		}

		return t;
	}
}
