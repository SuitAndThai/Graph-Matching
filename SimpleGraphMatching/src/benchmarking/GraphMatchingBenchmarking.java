package benchmarking;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

import matrix.AdjacencyMatrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pqgram.PQGram;
import simmatrix.Matcher;
import tree.RandomTree;
import tree.Tree;

public class GraphMatchingBenchmarking {
	private static int NUM_ALGORITHMS = 2;

	/*
	 * "User time" is the time spent running your application's own code.
	 * "System time" is the time spent running OS code on behalf of your
	 * application (such as for I/O).
	 * 
	 * "CPU time" is user time plus system time. It's the total time spent using
	 * a CPU for your application.
	 * 
	 * Source: http://bit.ly/SdeC9h
	 */
	private long getCpuTime() {
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		return bean.isCurrentThreadCpuTimeSupported() ? bean
				.getCurrentThreadCpuTime() : 0L;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private String[] makeLabels(int n) {
		String[] labels = new String[n];
		Random rand = new Random();
		for (int i = 0; i < labels.length; i++) {
			labels[i] = "" + rand.nextInt(n);
		}
		return labels;
	}

	private long getPQGramTime(Tree t1, Tree t2) {
		int P = 2;
		int Q = 3;
		long startTime = getCpuTime();
		PQGram.getDistance(t1, t2, P, Q);
		return getCpuTime() - startTime;
	}

	private long getSimilarityMatrixTime(AdjacencyMatrix m1, AdjacencyMatrix m2) {
		long startTime = getCpuTime();
		Matcher.match(m1, m2);
		return getCpuTime() - startTime;
	}

	private long[] getTimes(int n) throws InterruptedException {
		long[] times = new long[NUM_ALGORITHMS];

		Tree t1 = RandomTree.makeRandomTree(n, makeLabels(n / 2));
		Tree t2 = RandomTree.makeRandomTree(n, makeLabels(n / 2));

		times[0] = getPQGramTime(t1, t2);

		AdjacencyMatrix m1 = Tree.getMatrix(t1);
		AdjacencyMatrix m2 = Tree.getMatrix(t2);

		times[1] = getSimilarityMatrixTime(m1, m2);

		return times;
	}

	@Test
	public void benchmarkHundred() {
		benchmark(100);
	}

	private void benchmark(int n) {
		long[] times;
		try {
			times = getTimes(n);
			System.out.println("pq time: " + times[0]);
			System.out.println("sim time: " + times[1]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
