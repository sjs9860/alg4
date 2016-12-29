/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: WeightedQuickUnionUF.java
 *
 *  This program 3-by-3 percolation prototype, then open sites (1, 3), (2, 3), 
 *  (2, 1), (3, 3), (3, 1). Test status of given sites and the whole prototype.
 *  
 *  Author: White Shi
 *  Date:   Dec. 23 2016
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // DIRECTION representing left, right, down, up
    private static final int[][] DIRECTION = { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
    // map for n by n sites, each location with true for the site is open
    private boolean[][] map;
    // disjoint set with for n*n+2 elements, n*n sites, 1 super head, 1 super buttom;
    private WeightedQuickUnionUF dj;
    // size of map
    private int sizeN;
    // number of opened sites
    private int numOfOpened;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "The size of metrix should be great or equal to 0");
        }
        sizeN = n;
        numOfOpened = 0;
        map = new boolean[n + 1][n + 1];
        dj = new WeightedQuickUnionUF(n * n + 2);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        numOfOpened++;
        map[row][col] = true;
        int indexCurr = (row - 1) * sizeN + col;
        if (indexCurr <= sizeN) {
            dj.union(0, indexCurr);
        }
        if (!percolates() && indexCurr > sizeN * (sizeN - 1) && indexCurr <= sizeN * sizeN) {
            dj.union(indexCurr, sizeN * sizeN + 1);
        }
        for (int[] d : DIRECTION) {
            int x = row + d[0];
            int y = col + d[1];
            if (x > 0 && x <= sizeN && y > 0 && y <= sizeN && isOpen(x, y)) {
                int index = (x - 1) * sizeN + y;
                dj.union(index, indexCurr);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return map[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = (row - 1) * sizeN + col;
        return map[row][col] && dj.connected(index, 0);
    }
    
    // number of open sites
    public int numberOfOpenSites() {
    	return numOfOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return dj.connected(0, sizeN * sizeN + 1);
    }

    // private helper to check param's validation
    private void validate(int row, int col) {
        if (row < 1 || row > sizeN) {
            throw new IndexOutOfBoundsException("row " + row
                    + " should out of [0, " + (sizeN - 1) + "]");
        }
        if (col < 1 || col > sizeN) {
            throw new IndexOutOfBoundsException("column" + col
                    + " should out of [0, " + (sizeN - 1) + "]");
        }
    }

    // test client
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        System.out.println(p.numberOfOpenSites());
        System.out.println("Is (1,2) open? " + p.isOpen(1, 2));
        p.open(1, 3);
        p.open(2, 3);
        System.out.println(p.numberOfOpenSites());
        System.out.println("Is (2,3) open? " + p.isOpen(2, 3));
        System.out.println("Is (2,3) full? " + p.isFull(2, 3));
        p.open(2, 1);
        System.out.println(p.numberOfOpenSites());
        System.out.println("Is (2,1) open? " + p.isOpen(2, 1));
        System.out.println("Is (2,1) full? " + p.isFull(2, 1));
        p.open(3, 3);
        System.out.println(p.numberOfOpenSites());
        System.out.println("Is 3*3 percolated? " + p.percolates());
        p.open(3, 1);
        System.out.println(p.numberOfOpenSites());
        System.out.println("Is (3,1) open? " + p.isOpen(2, 1));
        System.out.println("Is (3,1) full? " + p.isFull(2, 1));        
    }
}
