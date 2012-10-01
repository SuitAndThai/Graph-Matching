package graph;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

import matrix.Matcher;
import matrix.Matrix;

public class Main {

	private static int P = 2;
	private static int Q = 3;

	private static long getCpuTime() {
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		return bean.isCurrentThreadCpuTimeSupported() ? bean
				.getCurrentThreadCpuTime() : 0L;
	}

	private static String[] makeLabels(int n) {
		String[] labels = new String[n];
		Random rand = new Random();
		for (int i = 0; i < labels.length; i++) {
			labels[i] = "" + rand.nextInt(n);
		}
		return labels;
	}

	public static void main(String[] args) {
		int numNodes = Integer.parseInt(args[0]);

		Tree t1 = Tree.makeRandomTree(numNodes, makeLabels(numNodes / 2));
		Tree t2 = Tree.makeRandomTree(numNodes, makeLabels(numNodes / 2));

		Matrix m1 = Tree.getMatrix(t1);
		Matrix m2 = Tree.getMatrix(t2);

		long[] times = new long[2];
		long startTime, endTime;

		// pq-Gram
		startTime = getCpuTime();
		pqgram.Main.dist(t1, t2, P, Q);
		endTime = getCpuTime();
		times[0] = endTime - startTime;
		System.out.println("*spoiler* pq-time: " + times[0]);

		// similarity matrix
		startTime = getCpuTime();
		Matcher.match(m1, m2);
		endTime = getCpuTime();
		times[1] = endTime - startTime;

		System.out.println("pq time: " + times[0]);
		System.out.println("sim time: " + times[1]);
	}
}
