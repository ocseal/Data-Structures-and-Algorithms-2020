import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }
    @Test
    public static void addremoveTest() {
        System.out.println("Running add/remove test.");

        ArrayDeque<Integer> ad9 = new ArrayDeque<Integer>();
        ad9.addLast(0);
        ad9.addLast(1);
        ad9.addFirst(2);
        ad9.addLast(3);
        ad9.removeFirst();
        ad9.addFirst(5);
        ad9.addLast(6);
        ad9.addLast(7);
        ad9.addLast(9);
        ad9.addLast(10);
        ad9.addLast(12);
        ad9.removeFirst();

        /*
        ArrayDeque<Integer> ad8 = new ArrayDeque<Integer>();
        ad8.addFirst(0);
        ad8.removeLast();
        ad8.addLast(2);
        ad8.removeLast();
        ad8.addLast(4);
        ad8.addLast(5);
        ad8.addFirst(7);
        ad8.addLast(8);
        ad8.removeLast();
        ad8.addLast(11);
        ad8.addLast(13);
        ad8.removeFirst();
        ad8.removeFirst();
        ad8.addLast(16);
        ad8.removeFirst();
        ad8.removeFirst();
        ad8.removeFirst();
        ad8.removeFirst();
        ad8.addFirst(22);
        ad8.addLast(23);

        ArrayDeque<Integer> ad2 = new ArrayDeque<Integer>();
        ad2.addLast(0);
        ad2.addLast(1);
        ad2.addFirst(2);
        ad2.addFirst(3);
        ad2.addLast(4);
        ad2.removeFirst();
        ad2.addFirst(6);
        ad2.get(2);
        ad2.addLast(8);
        ad2.removeLast();
        ad2.addFirst(10);
        ad2.removeFirst();
        ad2.addLast(12);
        ad2.removeLast();
        assertEquals(6, (int) ad2.removeFirst()); */

        ArrayDeque<Integer> ad6 = new ArrayDeque<Integer>();
        ad6.addLast(1);
        ad6.addLast(2);
        ad6.addFirst(5);
        ad6.addFirst(7);
        ad6.addFirst(1);
        ad6.addFirst(0);
        ad6.addFirst(10);
        ad6.addFirst(3);
        ad6.addFirst(8);
        ad6.addFirst(9);
        ad6.removeFirst();
        ad6.removeFirst();
        ad6.removeFirst();
        ad6.removeFirst();
        ad6.removeLast();
        ad6.removeLast();

        ArrayDeque<Integer> ad5 = new ArrayDeque<Integer>();
        ad5.addLast(1);
        ad5.addLast(2);
        ad5.addLast(3);
        ad5.addLast(4);
        ad5.addLast(5);
        ad5.addLast(7);
        ad5.addLast(8);
        ad5.addLast(9);
        ad5.addLast(10);
        ad5.addLast(11);
        assertEquals(1, (int) ad5.removeFirst());

        /* ArrayDeque<Integer> ad4 = new ArrayDeque<Integer>();
        ad4.addFirst(1);
        ad4.addFirst(2);
        ad4.addFirst(3);
        ad4.addFirst(4);
        ad4.addFirst(5);
        ad4.addFirst(7);
        assertEquals(1, (int) ad4.removeLast()); */

        ArrayDeque<Integer> ad3 = new ArrayDeque<Integer>();
        ad3.addLast(1);
        ad3.addLast(2);
        ad3.addFirst(3);
        ad3.addFirst(4);
        ad3.removeLast();
        ad3.addLast(6);
        ad3.removeFirst();
        ad3.addLast(8);
        ad3.removeLast();
        assertEquals(3, (int) ad3.removeFirst());

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addFirst(0);
        ad1.addFirst(1);
        ad1.removeLast();
        ad1.addFirst(3);
        ad1.addFirst(4);
        ad1.addFirst(5);
        assertEquals(1, (int) ad1.removeLast());
    }

    public static void printTest() {
        System.out.println("Running arraydeque print test.");
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addFirst(1);
        ad1.addLast(2);
        ad1.addLast(0);
        ad1.addFirst(4);
        ad1.addLast(6);
        ad1.printDeque();

        System.out.println(" ");

        ArrayDeque<Integer> ad2 = new ArrayDeque<Integer>();
        ad2.addFirst(0);
        ad2.addFirst(1);
        ad2.addFirst(2);
        ad2.addFirst(3);
        ad2.addFirst(4);
        ad2.printDeque();

        System.out.println(" ");

        ArrayDeque<Integer> ad3 = new ArrayDeque<Integer>();
        ad3.addLast(0);
        ad3.addLast(1);
        ad3.addLast(2);
        ad3.addLast(3);
        ad3.addLast(4);
        ad3.printDeque();

        System.out.println("Tests passed!");

    }
    @Test
    public static void getTest() {
        System.out.println("Running get test.");

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addFirst(0);
        ad1.removeFirst();
        ad1.addFirst(2);
        ad1.addLast(3);
        ad1.get(0);
        ad1.removeLast();
        ad1.addFirst(6);
        ad1.removeFirst();
        assertEquals(2, (int) ad1.get(0));

        ArrayDeque<Integer> ad7 = new ArrayDeque<Integer>();
        ad7.addFirst(0);
        ad7.removeFirst();
        ad7.addFirst(2);
        ad7.addFirst(4);
        ad7.removeFirst();
        ad7.removeFirst();
        ad7.addLast(9);
        ad7.addLast(10);
        ad7.addFirst(11);
        ad7.removeFirst();
        ad7.addLast(13);
        ad7.addLast(14);
        ad7.addLast(15);
        ad7.addFirst(16);
        assertEquals(15, (int) ad7.get(5));

        ArrayDeque<Integer> ad2 = new ArrayDeque<Integer>();
        ad2.addFirst(0);
        ad2.removeFirst();
        ad2.addFirst(2);
        ad2.addLast(3);
        ad2.get(0);
        ad2.removeLast();
        ad2.addFirst(6);
        ad2.removeFirst();
        assertEquals(2, (int) ad2.get(0));

        System.out.println("Tests passed!");
    }

    public static void main(String[] args) {
        // printTest();
        addremoveTest();
        getTest();
    }
}
