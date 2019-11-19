import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private final WeightedQuickUnionUF uf, ufbak;
    private final int size;
    private boolean[][] mat;
    private int count;

    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException();
        }
        count = 0;
        size = n;
        uf = new WeightedQuickUnionUF(n*n+2);
        ufbak = new WeightedQuickUnionUF(n*n+1);
        mat = new boolean[n][n];

    }

    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }

        row = row - 1;
        col = col - 1;
        var currentblock = row * size + col;
        mat[row][col] = true;

        if (row == 0) {
            uf.union(size * size, col);
            ufbak.union(size * size, col);
        }

        if (row != 0 && mat[row - 1][col]) {
            uf.union(currentblock, size * (row - 1) + col);
            ufbak.union(currentblock, size * (row - 1) + col);
        }
        if (col != 0 && mat[row][col - 1]) {
            uf.union(currentblock, size * (row) + col - 1);
            ufbak.union(currentblock, size * (row) + col - 1);
        }
        if (col + 1 != size && mat[row][col + 1]) {
            uf.union(currentblock, size * (row) + col + 1);
            ufbak.union(currentblock, size * (row) + col + 1);
        }
        if (row + 1 != size && mat[row + 1][col]) {
            uf.union(currentblock, size * (row + 1) + col);
            ufbak.union(currentblock, size * (row + 1) + col);
        }

        if (row == size - 1) {
            uf.union(currentblock, size * size+1);
        }
        count++;
    }

    public boolean isOpen(int row, int col)
    {
        if (row <= 0 || col <= 0 || row > size || col > size)
        {
            throw new IllegalArgumentException();
        }
        return mat[row-1][col-1];
    }

    public boolean isFull(int row, int col)
    {
        if (row <= 0 || col <= 0 || row > size || col > size)
        {
            throw new IllegalArgumentException();
        }

        return ufbak.connected(size*(row-1)+col-1, size*size) && mat[row-1][col-1];
    }

    public int numberOfOpenSites()
    {
        return count;
    }

    public boolean percolates() {
        return uf.connected(size * size, size * size + 1);

    }

    public static void main(String[] args)
    {
        if (args[0] == null)
        {
            throw new IllegalArgumentException();
        }
        var n = Integer.parseInt(args[0]);
        var percolate = new Percolation(n);

        while (true) {
            var index = StdRandom.uniform(1, (n * n));
            var row = index / n;
            var col = index % n;
            percolate.open(row + 1, col + 1);
            if (percolate.percolates()) {
                StdOut.println(percolate.count);
                break;
            }
        }
    }
}
