import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double ONENINTYSIX = 1.96;
    private final double[] resultSet;
    private final int trials;
    private double mean = 0, stadarddev = 0;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        resultSet = new double[trials];
        for (int i = 0; i < trials; i++) {
            var percolate = new Percolation(n);
            while (true) {
                var index = StdRandom.uniform(0, (n * n));
                var row = index / n;
                var col = index % n;
                percolate.open(row + 1, col + 1);

                if (percolate.percolates()) {
                    resultSet[i] = ((double) percolate.numberOfOpenSites())/ (n * n);
                    break;
                }
            }

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (this.mean == 0)
        {
            this.mean = StdStats.mean(resultSet);
        }
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        if (this.stadarddev == 0)
        {
            this.stadarddev = StdStats.stddev(resultSet);
        }
        return this.stadarddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        var conflo = mean() - ONENINTYSIX* stddev()/Math.sqrt(trials);
        return conflo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        var conflo = mean() + ONENINTYSIX* stddev()/Math.sqrt(trials);
        return conflo;
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args[0] == null || args[1] == null) {
            throw new IllegalArgumentException();
        }
        var stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println(stats.mean());
        StdOut.println(stats.stddev());
        StdOut.println(stats.confidenceLo());
        StdOut.println(stats.confidenceHi());
    }

}