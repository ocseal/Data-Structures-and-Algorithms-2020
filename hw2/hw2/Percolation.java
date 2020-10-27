package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] checkOpen;
    private boolean[] checkOpen1D;
    private int numOpen;
    private int N;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufBackwash;
    public Percolation(int N) { // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N cannot be negative or 0");
        }
        this.N = N;
        this.checkOpen = new boolean[N][N];
        this.checkOpen1D = new boolean[N * N];
        this.uf = new WeightedQuickUnionUF(N * N + 2);
        this.ufBackwash = new WeightedQuickUnionUF(N * N + 1);
        this.numOpen = 0;
        this.top = N * N;
        this.bottom = N * N + 1;
    }

    private int xyTo1D(int row, int col) {
        return (N * row) + col;
    }

    private void checkIndex(int row, int col) {
        if (row > N || col > N || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Row or column out of range");
        }
    }

    private void connect(int row, int col) {
        int square = xyTo1D(row, col);
        int topSquare = xyTo1D(row - 1, col);
        int bottomSquare = xyTo1D(row + 1, col);
        int leftSquare = xyTo1D(row, col - 1);
        int rightSquare = xyTo1D(row, col + 1);

        if ((row - 1) >= 0 && isOpen(topSquare)) {
            uf.union(square, topSquare);
            ufBackwash.union(square, topSquare);
        }

        if ((row + 1) < N && isOpen(bottomSquare)) {
            uf.union(square, bottomSquare);
            ufBackwash.union(square, bottomSquare);
        }

        if ((col - 1) >= 0 && isOpen(leftSquare)) {
            uf.union(square, leftSquare);
            ufBackwash.union(square, leftSquare);
        }

        if ((col + 1) < N && isOpen(rightSquare)) {
            uf.union(square, rightSquare);
            ufBackwash.union(square, rightSquare);
        }
    }

    public void open(int row, int col) {
        checkIndex(row, col);
        if (!isOpen(row, col)) {
            numOpen++;
            checkOpen[row][col] = true;
            checkOpen1D[xyTo1D(row, col)] = true;
            connect(row, col);
            if (row == 0) {
                uf.union(this.top, xyTo1D(row, col));
                ufBackwash.union(this.top, xyTo1D(row, col));
            }

            if (row == (N - 1)) {
                uf.union(xyTo1D(row, col), this.bottom);
            }
        }
    }

    private boolean isOpen(int index) {
        if (index < 0 || index > (N * N)) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return checkOpen1D[index];
    }

    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return checkOpen[row][col];
    }

    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        if (!ufBackwash.connected(top, xyTo1D(row, col))) {
            return false;
        }
        return isOpen(row, col);
    }
    public int numberOfOpenSites() {
        return numOpen;
    }
    public boolean percolates() {
        return uf.connected(this.top, this.bottom);
    }

    public static void main(String[] args) {

    }
}
