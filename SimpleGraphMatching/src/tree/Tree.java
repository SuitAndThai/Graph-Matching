package tree;

import java.util.ArrayList;
import java.util.List;

import matrix.AdjacencyMatrix;

public class Tree {
	private String label;
	private Tree parent;
	private List<Tree> children;

	public Tree(Tree parent, String label) {
		this.parent = parent;
		this.label = label;
		this.children = new ArrayList<Tree>();
	}
	
	public Tree(String label) {
		this(null, label);
	}
	
	public Tree() {
		this(null);
	}
	
	public Tree getParent() {
		return this.parent;
	}
	
	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public int addChild(Tree tree) {
		this.children.add(tree);
		tree.setParent(this);
		return this.children.size() - 1;
	}
	
	public void deleteChild(int position) {
		Tree toRemove = this.children.remove(position);
		toRemove.setParent(null);
	}

	public String getLabel() {
		return this.label;
	}

	public List<Tree> getChildren() {
		return this.children;
	}

	public boolean isLeaf() {
		return this.children.size() == 0;
	}
	
	public boolean isParent() {
		return !this.isLeaf();
	}
	
	public boolean isDescendant(Tree tree) {
		if (children.contains(tree)) {
			return true;
		}

		for (Tree t : children) {
			if (t.isDescendant(tree)) {
				return true;
			}
		}

		return false;
	}

	public Tree find(String label) {
		if (this.label.equals(label)) {
			return this;
		} else {
			for (Tree t : this.children) {
				Tree goal = t.find(label);
				if (null != goal) {
					return goal;
				}
			}
		}
		return null;
	}

	public int getNumberOfNodes() {
		int numNodes = 1;
		for (Tree t : this.children) {
			numNodes += t.getNumberOfNodes();
		}
		return numNodes;
	}

	public List<String> getAllLabels() {
		List<String> labels = new ArrayList<String>();
		labels.add(this.label);
		for (Tree t : this.children) {
			labels.addAll(t.getAllLabels());
		}
		return labels;
	}
	
	private List<Tree> getTrees() {
		List<Tree> trees = new ArrayList<Tree>();
		trees.add(this);
		for (Tree t : this.children) {
			trees.addAll(t.getTrees());
		}
		return trees;
	}

	@Override
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
	
	public static AdjacencyMatrix getMatrix(Tree t) {
		List<Tree> trees = t.getTrees();
		int numNodes = trees.size();
		String[] labels = new String[trees.size()];
		for (int i = 0; i < numNodes; i++) {
			labels[i] = trees.get(i).getLabel();
		}
		AdjacencyMatrix m = new AdjacencyMatrix(numNodes, (String[]) labels);
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
