

public class Percolation {
    private boolean[][] siteState;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        siteState = new boolean[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                siteState[row][col] = false;
            }
        }
        // WeightedQuickUnionUF qf = new WeightedQuickUnionUF(n * n);
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
        return 0;
    }

    public boolean percolates() {
        return false;
    }

}
