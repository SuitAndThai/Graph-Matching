package tree;

import java.util.Random;

public class RandomTree {
	
	private static Tree makeRandomTree(int numNodes, String[] labels, Tree root) {
		int minNumChildren = 1;
		int maxNumChildren = 30;
		
		Random rand = new Random();
		Tree t = new Tree(root, labels[rand.nextInt(labels.length)]);

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
				t.addChild(makeRandomTree(numDescendants[i], labels, t));
			}
		}

		return t;
	}
	
	/*
	 * Build a random tree given the number of nodes and label set
	 */
	public static Tree makeRandomTree(int numNodes, String[] labels) {
		return makeRandomTree(numNodes, labels, null);
	}
	
	/*
	 * Partition the remaining number of nodes among the n children
	 */
	private static int[] getRandomPartition(int n, int numPartitions) {
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

}
