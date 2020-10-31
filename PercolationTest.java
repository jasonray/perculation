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
}