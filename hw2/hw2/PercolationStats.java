package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int size;
    private int runs;
    private double[] results;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        runs = T;
        results = new double[runs];
        size = N;
        for (int i = 0; i < runs; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int r1 = StdRandom.uniform(size);
                int r2 = StdRandom.uniform(size);
                p.open(r1, r2);
            }
            results[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }
    public double stddev() {
        return StdStats.stddev(results);
    }
    public double confidenceLow() {
        return (mean() - 1.96 * stddev() / Math.sqrt(runs));
    }
    public double confidenceHigh() {
        return (mean() + 1.96 * stddev() / Math.sqrt(runs));
    }

}
