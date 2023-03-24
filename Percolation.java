import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF uf;
    private boolean[][] open;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        open = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                open[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        for(int i = 1; i <= n; i++) {
            uf.union(i, 0);
            uf.union(n*(n-1) + i, n*n + 1);
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + (col - 1) + 1;
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            open[row-1][col-1] = true;
            if (row == 1) {
                uf.union(getIndex(row, col), 0);
            }
            if (row == n) {
                uf.union(getIndex(row, col), n * n + 1);
            }
            if (row > 1 && isOpen(row-1, col)) {
                uf.union(getIndex(row, col), getIndex(row-1, col));
            }
            if (row < n && isOpen(row+1, col)) {
                uf.union(getIndex(row, col), getIndex(row+1, col));
            }
            if (col > 1 && isOpen(row, col-1)) {
                uf.union(getIndex(row, col), getIndex(row, col-1));
            }
            if (col < n && isOpen(row, col+1)) {
                uf.union(getIndex(row, col), getIndex(row, col+1));
            }
        }
    }
    // is validate?
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        for(int i = 0; i < n; i++) {
            if(uf.find(0) == uf.find(i))
            uf.union(i, 0);
        }
        return uf.find(0) == uf.find(getIndex(row, col)) && isOpen(row, col);
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (open[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n * n + 1);
    }
}
