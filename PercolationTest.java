import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {

    @Test
    public void init() {
        Percolation perc = new Percolation(1);
        assertNotNull(perc);
    }

    @Test
    public void initWithZeroThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Percolation perc = new Percolation(0);
        });
    }

    @Test
    public void initWithNegativeThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Percolation perc = new Percolation(-1);
        });
    }

    @Test
    public void afterInitSiteIsClosedn1() {
        Percolation perc = new Percolation(1);
        assertEquals(false, perc.isOpen(0, 0));
    }

    @Test
    public void switchSiteFromOpenToCloseVerifyOpen() {
        Percolation perc = new Percolation(1);
        assertEquals(false, perc.isOpen(1, 1));
        perc.open(1, 1);
        assertEquals(true, perc.isOpen(1, 1));
    }

    @Test
    public void switchSiteFromOpenToCloseVerifyOpenN2() {
        Percolation perc = new Percolation(2);
        assertEquals(false, perc.isOpen(1, 1));
        assertEquals(false, perc.isOpen(2, 2));
        perc.open(1, 1);
        assertEquals(true, perc.isOpen(1, 1));
        assertEquals(false, perc.isOpen(2, 2));
        perc.open(2, 2);
        assertEquals(true, perc.isOpen(1, 1));
        assertEquals(true, perc.isOpen(2, 2));
    }

    @Test
    public void switchSiteFromOpenToCloseVerifyOpenNonEqualRowCol() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isOpen(1, 2));
        assertEquals(false, perc.isOpen(2, 1));
        perc.open(2, 1);
        assertEquals(false, perc.isOpen(1, 2));
        assertEquals(true, perc.isOpen(2, 1));
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
        assertEquals(false, perc.isFull(2, 2));
    }

    @Test
    public void openSiteAtTopIsFull() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(1, 2));
        perc.open(1, 2);
        assertEquals(true, perc.isFull(1, 2));
    }

    @Test
    public void openSiteWithStraightLineToTopIsFull2212() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(2, 2));
        perc.open(2, 2);
        assertEquals(false, perc.isFull(2, 2));
        perc.open(1, 2);
        assertEquals(true, perc.isFull(2, 2));
    }

    @Test
    public void windyWayToTopIsFull() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.isFull(3, 3));
        perc.open(3, 3);
        perc.open(3, 2);
        perc.open(2, 2);
        perc.open(2, 1);
        perc.open(1, 1);
        assertEquals(true, perc.isFull(3, 3));
    }

    @Test
    public void oneCellInitDoesNotPerc() {
        Percolation perc = new Percolation(1);
        assertEquals(false, perc.percolates());
    }

    @Test
    public void oneOpenCellPerc() {
        Percolation perc = new Percolation(1);
        perc.open(1, 1);
        assertEquals(true, perc.percolates());
    }

    @Test
    public void threeCellInitDoesNotPerc() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.percolates());
    }

    @Test
    public void threeCellWithPathPerc() {
        Percolation perc = new Percolation(3);
        assertEquals(false, perc.percolates());
        perc.open(1, 1);
        assertEquals(false, perc.percolates());
        perc.open(3, 3);
        assertEquals(false, perc.percolates());
        perc.open(2, 2);
        assertEquals(false, perc.percolates());
        perc.open(2, 1);
        assertEquals(false, perc.percolates());
        perc.open(3, 2);
        assertEquals(true, perc.percolates());
    }

    @Test
    public void invalidOpenCellTooFarLeft() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.open(1, -1);
        });
    }
    @Test
    public void invalidOpenCellTooFarLeft0() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.open(1, 0);
        });
    }

    @Test
    public void invalidOpenCellTooFarRight() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.open(1, 4);
        });
    }

    @Test
    public void invalidOpenCellTooFarUp() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.open(-1, 1);
        });
    }
    @Test
    public void invalidOpenCellTooFarUp0() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.open(0, 1);
        });
    }

    @Test
    public void invalidOpenCellTooFarDown() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.open(4, 1);
        });
    }

    @Test
    public void invalidFullCellTooFarLeft() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.isFull(1, -1);
        });
    }
    @Test
    public void invalidFullCellTooFarLeft0() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.isFull(1, 0);
        });
    }

    @Test
    public void invalidFullCellTooFarRight() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.isFull(1, 4);
        });
    }

    @Test
    public void invalidFullCellTooFarUp() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.isFull(-1, 1);
        });
    }
    @Test
    public void invalidFullCellTooFarUp0() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.isFull(0, 1);
        });
    }

    @Test
    public void invalidFullCellTooFarDown() {
        Percolation perc = new Percolation(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perc.isFull(4, 1);
        });
    }
}