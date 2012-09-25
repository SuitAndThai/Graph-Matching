package benchmarking;

import static org.junit.Assert.fail;
import graph.Tree;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

import matrix.Matcher;
import matrix.Matrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphMatchingBenchmarking {
	private static int P = 2;
	private static int Q = 3;
	
	private long getCpuTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadCpuTime( ) : 0L;
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
	
	public long[] getTimes(int n) throws InterruptedException {
		Tree t1 = Tree.makeRandomTree(n, makeLabels(n/2));
		Tree t2 = Tree.makeRandomTree(n, makeLabels(n/2));
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
		
		return times;
	}
	
	@Test
	public void benchmarkTen() {
		benchmark(100);
	}

	private void benchmark(int n) {
		long[] times;
		try {
			times = getTimes(n);
			System.out.println("pq time: " + times[0]);
			System.out.println("sim time: " + times[1]);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
