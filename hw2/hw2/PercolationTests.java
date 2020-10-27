package hw2;
import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTests {
    public static void main(String[] args) {
        isFullTest();
        percolatesTest();
    }

    @Test
    public static void isFullTest() {
        Percolation N = new Percolation(4);
        N.open(1, 2);
        N.open(1, 3);
        assertFalse(N.isFull(1, 3));
        assertFalse(N.isFull(1, 2));
        N.open(0, 2);
        assertTrue(N.isFull(1, 3));
        assertTrue(N.isFull(1, 2));
        N.open(2, 3);
        N.open(2, 2);
        N.open(3, 3);
        N.open(3, 0);
        assertFalse(N.isFull(3, 0));
        assertTrue(N.isFull(3, 3));
        assertFalse(N.isFull(1, 1));
        assertTrue(N.isFull(0, 2));
        System.out.println("isFullTest passed!");
    }

    @Test
    public static void percolatesTest() {
        Percolation A = new Percolation(3);
        A.open(0, 0);
        A.open(0, 1);
        assertFalse(A.percolates());
        A.open(0, 2);
        A.open(1, 2);
        A.open(2, 2);
        assertTrue(A.percolates());
        System.out.println("percolatesTest passed!");
    }

}
