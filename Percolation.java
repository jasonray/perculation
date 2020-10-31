import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private WeightedQuickUnionUF qf;
    private boolean[][] siteState;
    private int[][] qfIndex;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
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

        //if adjacent cells are also open, then union them
        unionIfLeftOpen(siteIndex, row, col);
        unionIfRightOpen(siteIndex, row, col);
        unionIfAboveOpen(siteIndex, row, col);
        unionIfBelowOpen(siteIndex, row, col);
        
        //if cell is top row, join to virtual top
        unionIfTop(row, siteIndex);

        //if cell is bottom row, join to virtual bottom
        unionIfBottom(row, siteIndex);
    }

    private boolean isTopRow(int row) {
        return row == 0;
    }

    private boolean isBottomRow(int row) {
        return row == (siteState.length - 1);
    }

    private void unionIfTop(int siteRow, int siteIndex) {
        if (isTopRow(siteRow)) {
            qf.union(siteIndex, virtualTopIndex);
        }
    }

    private void unionIfBottom(int siteRow, int siteIndex) {
        if (isBottomRow(siteRow)) {
            qf.union(siteIndex, virtualBottomIndex);
        }
    }

    private void unionIfLeftOpen(int siteIndex, int siteRow, int siteCol) {
        unionIfNeighborOpen(siteIndex, siteRow, siteCol - 1);
    }

    private void unionIfRightOpen(int siteIndex, int siteRow, int siteCol) {
        unionIfNeighborOpen(siteIndex, siteRow, siteCol + 1);
    }

    private void unionIfAboveOpen(int siteIndex, int siteRow, int siteCol) {
        unionIfNeighborOpen(siteIndex, siteRow + 1, siteCol);
    }

    private void unionIfBelowOpen(int siteIndex, int siteRow, int siteCol) {
        unionIfNeighborOpen(siteIndex, siteRow - 1, siteCol);
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
        if (row < 0 || row >= siteState.length) return true;
        if (col < 0 || col >= siteState[row].length) return true;
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
