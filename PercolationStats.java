/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class PercolationStats {
    private int size;
    private int numberOfSimulationsThatHaveBeenRun = 0;
    private double[] results;

    // test client (see below)
    public static void main(String[] args) {
        int n = 2;
        int trials = 4;

        PercolationStats simulator = new PercolationStats(n, trials);
    }

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("invalid size");
        if (trials <= 0) throw new IllegalArgumentException("invalid number of trials");

        this.size = n * n;
        this.results = new double[trials];

        for (int trialNumber = 1; trialNumber <= trials; trialNumber++) {
            System.out.println("begin simulation " + trialNumber);
            runOneSimulation(n);
        }
    }

    private void runOneSimulation(int n) {
        Percolation perc = new Percolation(n);
        java.util.Random random = new java.util.Random();

        int loopNumber = 0;
        do {
            loopNumber++;
            int nextRow = random.nextInt(n) + 1;
            int nextCol = random.nextInt(n) + 1;
            perc.open(nextRow, nextCol);
        } while (!perc.percolates());

        recordSimulationResult(perc.numberOfOpenSites());
    }

    private void recordSimulationResult(int numberOfOpenSites) {
        double percentageOfOpenSites = (double) numberOfOpenSites / (double) this.size;

        this.results[numberOfSimulationsThatHaveBeenRun] = percentageOfOpenSites;
        this.numberOfSimulationsThatHaveBeenRun++;

        System.out.println(
                "simulation complete [" + percentageOfOpenSites + "][" + this.mean() + "][" + this
                        .stddev() + "][ " + this.confidenceLo() + " - " + this.confidenceHi()
                        + "]");
    }

    // sample mean of percolation threshold
    public double mean() {
        double sumOfResults = 0;
        for (int i = 0; i < this.numberOfSimulationsThatHaveBeenRun; i++) {
            sumOfResults = sumOfResults + this.results[i];
        }
        return sumOfResults / this.numberOfSimulationsThatHaveBeenRun;
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

}
