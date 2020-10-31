import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF qf;
    private boolean[][] siteState;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        siteState = new boolean[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                siteState[row][col] = false;
            }
        }

        // the extra +2 is for tracking the virtual top and virtual bottom
        int numberOfCells = n * n + 2;
        qf = new WeightedQuickUnionUF(n * n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        siteState[row][col] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return siteState[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int openSitesCount = 0;
        for (boolean[] siteStateRow : siteState) {
            for (boolean siteState : siteStateRow) {
                if (siteState) openSitesCount++;
            }
        }
        return openSitesCount;
    }

    public boolean percolates() {
        return false;
    }

}
