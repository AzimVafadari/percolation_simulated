import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] x;
    private double n;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        this.n = n;
        this.T = trials;
        int row;
        int col;
        x = new double[trials];
        for (int i = 0; i < T; i++) {
            Percolation pe = new Percolation(n);
            do {
                do {
                        row = StdRandom.uniformInt(n) + 1;
                        col = StdRandom.uniformInt(n) + 1;
                }
                while (pe.isOpen(row, col));
                pe.open(row, col);
            }
            while (!pe.percolates());
            x[i] = pe.numberOfOpenSites()/(this.n*this.n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
//        double mean = 0;
//        for (int i = 0; i < T; i++) {
//            mean += x[i];
//        }
//        return mean/T;
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(x);
//        double s = 0;
//        for (int i = 0; i < T; i++) {
//            s += (x[i] - mean())*(x[i] - mean());
//        }
//        return Math.sqrt(s/(T-1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - 1.96*stddev()/Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + 1.96*stddev()/Math.sqrt(T);
    }

    // test client (see below)
    public static void main(String[] args){
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

}
