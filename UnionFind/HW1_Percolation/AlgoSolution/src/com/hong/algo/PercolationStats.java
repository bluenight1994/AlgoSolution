package com.hong.algo;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by GanHong on 10/23/16.
 */
public class PercolationStats {

    private Percolation percolation;
    private int count;
    private double[] res;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        count = trials;
        res = new double[count];
        for(int i = 0; i<count; i++ ) {
            percolation = new Percolation(n);
            int openCount=0;
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n+1 );
                int y = StdRandom.uniform(1, n+1 );
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    openCount++;
                }
            }
            double result = (double) openCount / (n * n);
            res[i] = result;
        }
    }
    public double stddev() {
        return StdStats.stddev(res);
    }

    public double mean() {
        return StdStats.mean(res);
    }

    public double confidenceLo() {
        return mean()-((1.96*stddev())/Math.sqrt(count));
    }

    public double confidenceHi() {
        return mean()+((1.96*stddev())/Math.sqrt(count));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        n = 200;
        trials = 10;
        PercolationStats ps = new PercolationStats(n, trials);
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
