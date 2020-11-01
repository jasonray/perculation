/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int n;
    private int size;
    private int numberOfSimulationsThatHaveBeenRun = 0;
    private double[] results;

    // test client (see below)
    public static void main(String[] args) {
        int n;
        int trials;

        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        else {
            n = 3;
            trials = 10;
        }
        System.out.println("n:" + n);
        System.out.println("t:" + trials);

        PercolationStats simulator = new PercolationStats(n, trials);
    }

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("invalid size");
        if (trials <= 0) throw new IllegalArgumentException("invalid number of trials");

        this.n = n;
        this.size = n * n;
        this.results = new double[trials];

        for (int trialNumber = 1; trialNumber <= trials; trialNumber++) {
            // System.out.println("begin simulation " + trialNumber);
            runOneSimulation(n);
        }
    }

    private void runOneSimulation(int n) {
        Percolation perc = new Percolation(n);

        int loopNumber = 0;
        do {
            loopNumber++;
            int nextRow = StdRandom.uniform(n) + 1;
            int nextCol = StdRandom.uniform(n) + 1;
            // System.out.println("open [" + nextRow + ", " + nextCol + "]");
            perc.open(nextRow, nextCol);
        } while (!perc.percolates());

        recordSimulationResult(perc.numberOfOpenSites());
    }

    private void recordSimulationResult(int numberOfOpenSites) {
        double percentageOfOpenSites = (double) numberOfOpenSites / (double) this.size;

        this.results[numberOfSimulationsThatHaveBeenRun] = percentageOfOpenSites;
        this.numberOfSimulationsThatHaveBeenRun++;

        this.outputStats();
    }

    // sample mean of percolation threshold
    public double mean() {
        double sumOfResults = 0;
        for (int i = 0; i < this.numberOfSimulationsThatHaveBeenRun; i++) {
            sumOfResults = sumOfResults + this.results[i];
        }
        return sumOfResults / this.numberOfSimulationsThatHaveBeenRun;
    }

    public double max() {
        double trackingMax = results[0];
        for (int i = 0; i < this.numberOfSimulationsThatHaveBeenRun; i++) {
            trackingMax = Math.max(trackingMax, this.results[i]);
        }
        return trackingMax;
    }

    public double min() {
        double trackingMax = results[0];
        for (int i = 0; i < this.numberOfSimulationsThatHaveBeenRun; i++) {
            trackingMax = Math.min(trackingMax, this.results[i]);
        }
        return trackingMax;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = this.mean();
        double standardDeviation = 0;
        for (int i = 0; i < this.numberOfSimulationsThatHaveBeenRun; i++) {
            double result = this.results[i];
            standardDeviation += Math.pow(result - mean, 2);
        }
        return Math.sqrt(standardDeviation / this.numberOfSimulationsThatHaveBeenRun);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidence(false);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidence(true);
    }

    // true to calculate high, false to calculate low
    private double confidence(boolean high) {
        // mean +/- (1.96 * stddev ) / sqrt of # of simulations
        double rightSide = 1.96 * this.stddev() / Math
                .sqrt(this.numberOfSimulationsThatHaveBeenRun);
        if (high)
            return this.mean() + rightSide;
        else
            return this.mean() - rightSide;
    }

    public void outputStats() {
        System.out.println("Matrix Size       : " + this.n);
        System.out.println("Trials            : " + this.numberOfSimulationsThatHaveBeenRun);
        System.out.println("mean              : " + this.mean());
        System.out.println("stddev            : " + this.stddev());
        System.out.println(
                "range             : " + "[" + this.min() + ", " + this
                        .max() + "]");
        System.out.println(
                "95% confidence    : " + "[" + this.confidenceLo() + ", " + this
                        .confidenceHi() + "]");
    }

}
