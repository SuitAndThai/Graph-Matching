package graph;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
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

	private static long getPQAverage(int numNodes, int timesToRun) {
		Tree t1;
		Tree t2;
		long[] totalPQ = new long[timesToRun];
		long totalPQTime = 0;

		for (int i = 0; i < timesToRun; i++) {
			t1 = Tree.makeRandomTree(numNodes, makeLabels(numNodes / 2));
			t2 = Tree.makeRandomTree(numNodes, makeLabels(numNodes / 2));

			long startTime, endTime;

			// pq-Gram
			startTime = getCpuTime();
			pqgram.Main.dist(t1, t2, P, Q);
			endTime = getCpuTime();
			totalPQ[i] = endTime - startTime;
			totalPQTime += totalPQ[i];
		}

		return (totalPQTime / timesToRun);
	}

	private static long getSimAverage(int numNodes, int timesToRun) {
		Tree t1;
		Tree t2;
		Matrix m1;
		Matrix m2;
		long[] totalSim = new long[timesToRun];
		long totalSimTime = 0;

		for (int i = 0; i < timesToRun; i++) {
			t1 = Tree.makeRandomTree(numNodes, makeLabels(numNodes / 2));
			t2 = Tree.makeRandomTree(numNodes, makeLabels(numNodes / 2));

			m1 = Tree.getMatrix(t1);
			m2 = Tree.getMatrix(t2);

			long startTime, endTime;

			// similarity matrix
			startTime = getCpuTime();
			Matcher.match(m1, m2);
			endTime = getCpuTime();
			totalSim[i] = endTime - startTime;
			totalSimTime += totalSim[i];
		}

		return (totalSimTime / timesToRun);
	}

	public static void main(String[] args) {

		int timesToRun = Integer.parseInt(args[0]);

		String pqMapleCode = "";
		pqMapleCode += "pqGramPoints:=[";
		for (int i = 1; i <= 10; i++) {
			pqMapleCode += "[" + i * 100 + ","
					+ getPQAverage(i * 100, timesToRun) + "]";
		}
		pqMapleCode += "];";
		System.out.println(pqMapleCode);

		String simMapleCode = "";
		simMapleCode += "simMatrixPoints:=[";
		for (int i = 1; i <= 10; i++) {
			simMapleCode += "[" + i * 100 + ","
					+ getSimAverage(i * 100, timesToRun) + "]";
		}
		simMapleCode += "];";
		System.out.println(simMapleCode);
	}
}
