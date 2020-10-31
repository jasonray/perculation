import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
class PercolationTest {

    @Test
    public void init() {
        Percolation perc = new Percolation(0);
        assertNotNull(perc);
    }

    @Test
    public void afterInitSiteIsClosedn1() {
        Percolation perc = new Percolation(1);
        assertEquals(false, perc.isOpen(0, 0));
    }

    @Test
    public void switchSiteFromOpenToCloseVerifyOpen() {
        Percolation perc = new Percolation(1);
        assertEquals(false, perc.isOpen(0, 0));
        perc.open(0, 0);
        assertEquals(true, perc.isOpen(0, 0));
    }

    @Test
    public void switchSiteFromOpenToCloseVerifyOpenN2() {
        Percolation perc = new Percolation(2);
        assertEquals(false, perc.isOpen(0, 0));
        assertEquals(false, perc.isOpen(1, 1));
        perc.open(0, 0);
        assertEquals(true, perc.isOpen(0, 0));
        assertEquals(false, perc.isOpen(1, 1));
        perc.open(1, 1);
        assertEquals(true, perc.isOpen(0, 0));
        assertEquals(true, perc.isOpen(1, 1));
    }

    @Test
    public void switchSiteFromOpenToCloseVerifyOpenNonEqualRowCol() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isOpen(0, 1));
        assertEquals(false, perc.isOpen(1, 0));
        perc.open(1, 0);
        assertEquals(false, perc.isOpen(0, 1));
        assertEquals(true, perc.isOpen(1, 0));
    }

    @Test
    public void numberOfOpenSitesAfterInitIs0() {
        Percolation perc = new Percolation(3);
        assertEquals(0, perc.numberOfOpenSites());
    }

    @Test
    public void checkOpenSitesAfterSwitch() {
        Percolation perc = new Percolation(3);
        assertEquals(0, perc.numberOfOpenSites());
        perc.open(1, 2);
        assertEquals(1, perc.numberOfOpenSites());
    }

    @Test
    public void checkOpenSitesAfterTwoSwitch() {
        Percolation perc = new Percolation(3);
        assertEquals(0, perc.numberOfOpenSites());
        perc.open(1, 2);
        assertEquals(1, perc.numberOfOpenSites());
        perc.open(2, 1);
        assertEquals(2, perc.numberOfOpenSites());
    }

    @Test
    public void closedSiteNotFull() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(1, 1));
    }

    @Test
    public void closedSiteAtTopNotFull() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(0, 0));
    }

    @Test
    public void openSiteAtTopIsFull() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(0, 0));
        perc.open(0, 0);
        assertEquals(true, perc.isFull(0, 0));
    }

    @Test
    public void openSiteWithStraightLineToTopIsFull1101() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(1, 1));
        perc.open(1, 1);
        assertEquals(false, perc.isFull(1, 1));
        perc.open(0, 1);
        assertEquals(true, perc.isFull(1, 1));
    }

    @Test
    public void openSiteWithStraightLineToTopIsFull0111() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(1, 1));
        perc.open(0, 1);
        assertEquals(false, perc.isFull(1, 1));
        perc.open(1, 1);
        assertEquals(true, perc.isFull(1, 1));
    }

    @Test
    public void openSiteWithStraightLineToTopIsFull220212() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(2, 2));
        perc.open(2, 2);
        assertEquals(false, perc.isFull(2, 2));
        perc.open(0, 2);
        assertEquals(false, perc.isFull(2, 2));
        perc.open(1, 2);
        assertEquals(true, perc.isFull(2, 2));
    }

    @Test
    public void windyWayToTopIsFull() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(2, 2));
        perc.open(2, 2);
        perc.open(2, 1);
        perc.open(1, 1);
        perc.open(1, 0);
        perc.open(0, 0);
        assertEquals(true, perc.isFull(2, 2));
    }
}