/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: Percolation.java StdRandom.java StdStats.java
 *
 *  This program uses Monte Carlo simulation to analyze the threshold of 
 *  percolation, and give the result through statistics methods.
 *  
 *  Author: Jian Shi
 *  Date:   Dec. 23 2016
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // array of thresholds
    private double[] thresholds;
    // repeating this computation experiment T times
    private int numTrails;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int numOpened = 0;
        int num = n * n;
        numTrails = trials;
        thresholds = new double[numTrails];
        for (int i = 0; i < numTrails; i++) {
            // Initialize all sites to be blocked.
            Percolation percl = new Percolation(n);
            numOpened = 0;
            // Repeat the following until the system percolates:
            while (!percl.percolates()) {
                // Choose a site uniformly at random among all blocked sites.
                int id = StdRandom.uniform(num) + 1;
                int rem = id % n;
                int row, col;
                if (rem == 0) {
                    row = (id / n);
                    col = n;
                } 
                else {
                    row = (id / n) + 1;
                    col = rem;
                }
                // Open the site.
                if (!percl.isOpen(row, col)) {
                    percl.open(row, col);
                    numOpened++;
                }
            }
            // The fraction of sites that are opened when the system percolates
            // provides an estimate of the percolation threshold.
            thresholds[i] = numOpened * 1.0 / num * 1.0;
        }
    }

    // sample mean of percolation threshold
    // The sample mean x provides an estimate of the percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    // the sample standard deviation s; measures the sharpness of the threshold.
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        if (Double.isNaN(stddev())) {
            return Double.NaN;
        }
        return mean() - stddev() * 1.96 / Math.sqrt(numTrails);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (Double.isNaN(stddev())) {
            return Double.NaN;
        }
        return mean() + stddev() * 1.96 / Math.sqrt(numTrails);
    }

    // test client
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.format("mean\t\t\t = %.16f\n", ps.mean());
        System.out.format("stddev\t\t\t = %.16f\n", ps.stddev());
        System.out.format("95%% confidence interval\t = %.16f , %.16f\n",
                ps.confidenceLo(), ps.confidenceHi());
    }
}
