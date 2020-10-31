import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
class PercolationFileTest {

    private Percolation loadPercFromFile(String filename) {
        Percolation perc = null;
        int fileRow = 0;
        try {
            File f = new File(filename);
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                fileRow++;
                String data = reader.nextLine();
                data = data.trim();
                System.out.println("read row " + fileRow + ": " + data);
                if (perc == null) {
                    int matrixSize = Integer.parseInt(data);
                    perc = new Percolation(matrixSize);
                }
                else if (data.length() > 0) {
                    String[] parts = data.split(" ");
                    //the format of the files is not consistent, but there are 2 numeric values with misc " " between them
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[parts.length - 1]);
                    if (perc == null) throw new RuntimeException("Perc unexpected null");
                    //these are 1-based array, and perc expected 0-based array
                    perc.open(row - 1, col - 1);
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to find file " + filename, e);
        }
        return perc;
    }

    private void runTest(String filename, int expectedOpenSites, boolean expectedPercolates) {
        Percolation perc = loadPercFromFile(filename);
        assertEquals(expectedOpenSites, perc.numberOfOpenSites());
        assertEquals(expectedPercolates, perc.percolates());
    }

    @Test
    public void eagle25() {
        runTest("eagle25.txt", 400, true);
    }

    @Test
    public void greeting57() {
        runTest("greeting57.txt", 2522, false);
    }

    @Test
    public void heart25() {
        runTest("heart25.txt", 352, false);
    }

    @Test
    public void input1no() {
        runTest("input1-no.txt", 0, false);
    }

    @Test
    public void input1() {
        runTest("input1.txt", 1, true);
    }

    @Test
    public void input2no() {
        runTest("input2-no.txt", 2, false);
    }

    @Test
    public void input2() {
        runTest("input2.txt", 3, true);
    }

    @Test
    public void input3() {
        runTest("input3.txt", 6, true);
    }

    @Test
    public void input4() {
        runTest("input4.txt", 8, true);
    }

    @Test
    public void input5() {
        runTest("input5.txt", 25, true);
    }

    @Test
    public void input6() {
        runTest("input6.txt", 18, true);
    }

    @Test
    public void input7() {
        runTest("input7.txt", 16, true);
    }

    @Test
    public void input8dups() {
        runTest("input8-dups.txt", 34, true);
    }

    @Test
    public void input8no() {
        runTest("input8-no.txt", 33, false);
    }

    @Test
    public void input8() {
        runTest("input8.txt", 34, true);
    }

    @Test
    public void input10no() {
        runTest("input10-no.txt", 55, false);
    }

    @Test
    public void input10() {
        runTest("input10.txt", 56, true);
    }

    @Test
    public void input20() {
        runTest("input20.txt", 250, true);
    }

    @Test
    public void input50() {
        runTest("input50.txt", 1412, true);
    }

    @Test
    public void java60() {
        runTest("java60.txt", 578, true);
    }

    @Test
    public void jerry47() {
        runTest("jerry47.txt", 1476, true);
    }

    @Test
    public void princeton96() {
        runTest("princeton96.txt", 6260, true);
    }

    @Test
    public void sedgewick60() {
        runTest("sedgewick60.txt", 2408, true);
    }

    @Test
    public void snake13() {
        runTest("snake13.txt", 85, true);
    }

    @Test
    public void snake101() {
        runTest("snake101.txt", 5101, true);
    }

    @Test
    public void wayne98() {
        runTest("wayne98.txt", 5079, true);
    }
}