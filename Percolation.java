import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private WeightedQuickUnionUF qf;
    private boolean[][] siteState;
    private int[][] qfIndex;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n;
        siteState = new boolean[n][n];
        qfIndex = new int[n][n];

        int siteIndex = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                siteState[row][col] = false;
                qfIndex[row][col] = siteIndex;
                siteIndex++;
            }
        }

        virtualTopIndex = siteIndex++;
        virtualBottomIndex = siteIndex;

        // the extra +2 is for tracking the virtual top and virtual bottom
        int numberOfCells = n * n + 2;
        qf = new WeightedQuickUnionUF(numberOfCells);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        siteState[row][col] = true;
        int siteIndex = lookupSiteIndex(row, col);

        //if cell to the right, left, up, down is also open then join them
        unionIfNeighborOpen(siteIndex, row, col - 1);
        unionIfNeighborOpen(siteIndex, row, col + 1);
        unionIfNeighborOpen(siteIndex, row + 1, col);
        unionIfNeighborOpen(siteIndex, row - 1, col);

        //if cell is top row, join to virtual top
        if (row == 0) {
            qf.union(siteIndex, virtualTopIndex);
        }

        //if cell is bottom row, join to virtual bottom
        if (row == size - 1) {
            qf.union(siteIndex, virtualBottomIndex);
        }
    }

    private void unionIfNeighborOpen(int siteIndex, int neighborRow, int neighborCol) {
        if (!isCellOutOfBounds(neighborRow, neighborCol)) {
            if (isOpen(neighborRow, neighborCol)) {
                int neighborSiteIndex = lookupSiteIndex(neighborRow, neighborCol);
                qf.union(siteIndex, neighborSiteIndex);
            }
        }
    }

    private boolean isCellOutOfBounds(int row, int col) {
        if (row < 0 || row >= size) return true;
        if (col < 0 || col >= size) return true;
        return false;
    }

    private int lookupSiteIndex(int row, int col) {
        return qfIndex[row][col];
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return siteState[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return qf.find(lookupSiteIndex(row, col)) == qf.find(virtualTopIndex);
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
        return qf.find(virtualTopIndex) == qf.find(virtualBottomIndex);
    }

}
