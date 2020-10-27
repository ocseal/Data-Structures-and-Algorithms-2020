package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {

    private static final Random RANDOM = new Random(12);
    private static final Random RANDOM2 = new Random(4);


    private static void printTable(List<Integer> ints, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < ints.size(); i += 1) {
            int N = ints.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    @Test
    public void timeHeapAdd() {
        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        Integer[] array = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }

        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();

        Stopwatch sw = new Stopwatch();
        for (int i : array) {
            double y = RANDOM2.nextDouble();
            a.add(i, y);
        }
        times.add(sw.elapsedTime());
        ops.add(1000000);
        sizes.add(1000000);
        printTable(sizes, times, ops);
    }

    @Test
    public void timeNaiveGetSmallest() {
        Integer[] array = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        NaiveMinPQ<Integer> a = new NaiveMinPQ<>();
        for (int i : array) {
            a.add(i, RANDOM2.nextDouble());
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            a.getSmallest();
        }
        times.add(sw.elapsedTime());
        ops.add(1000);
        sizes.add(1000000);

        printTable(sizes, times, ops);
    }

    @Test
    public void timeNaiveRemoveSmallest() {
        Integer[] array = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        NaiveMinPQ<Integer> a = new NaiveMinPQ<>();
        for (int i : array) {
            a.add(i, RANDOM2.nextDouble());
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            a.removeSmallest();
        }
        times.add(sw.elapsedTime());
        ops.add(1000);
        sizes.add(1000000);

        printTable(sizes, times, ops);
    }

    @Test
    public void timeHeapGetSmallest() {
        Integer[] array = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        for (int i : array) {
            a.add(i, RANDOM2.nextDouble());
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            a.getSmallest();
        }
        times.add(sw.elapsedTime());
        ops.add(100000);
        sizes.add(1000000);

        printTable(sizes, times, ops);
    }

    @Test
    public void timeHeapRemoveSmallest() {
        Integer[] array = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        for (int i : array) {
            a.add(i, RANDOM2.nextDouble());
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            a.removeSmallest();
        }
        times.add(sw.elapsedTime());
        ops.add(1000);
        sizes.add(1000000);

        printTable(sizes, times, ops);
    }

    @Test
    public void timeChangePriority() {
        Integer[] array = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        for (int i : array) {
            a.add(i, RANDOM.nextDouble());
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            a.changePriority(i, RANDOM.nextDouble());
        }
        times.add(sw.elapsedTime());
        ops.add(1000000);
        sizes.add(1000000);

        printTable(sizes, times, ops);

    }

    @Test
    public void testSinkAndChangePriority() {
        ArrayHeapMinPQ<Integer> aPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> nPQ = new NaiveMinPQ<>();
        aPQ.add(2, 18);
        aPQ.add(15, 2);
        aPQ.add(9, 9);
        aPQ.add(3, 3);
        aPQ.add(5, 3);
        aPQ.add(8, 6);
        aPQ.changePriority(9, 1);
        aPQ.changePriority(3, 2);
        nPQ.add(2, 18);
        nPQ.add(15, 2);
        nPQ.add(9, 9);
        nPQ.add(3, 3);
        nPQ.add(5, 3);
        nPQ.add(8, 6);
        nPQ.changePriority(9, 1);
        nPQ.changePriority(3, 2);
        // printSimpleHeapDrawing(aPQ);
        assertEquals(nPQ.getSmallest(), aPQ.getSmallest());
    }

    @Test
    public void testGetRemoveSmallest() {
        ArrayHeapMinPQ<Integer> aPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> nPQ = new NaiveMinPQ<>();
        aPQ.add(2, 18);
        aPQ.add(15, 2);
        aPQ.add(9, 9);
        aPQ.add(3, 3);
        aPQ.add(5, 3);
        aPQ.add(8, 6);
        aPQ.add(14, 2);
        aPQ.add(7, 7);
        aPQ.add(21, 4);
        aPQ.changePriority(9, 1);
        aPQ.changePriority(3, 2);
        aPQ.changePriority(15, 14);
        aPQ.removeSmallest();
        aPQ.removeSmallest();
        nPQ.add(2, 18);
        nPQ.add(15, 2);
        nPQ.add(9, 9);
        nPQ.add(3, 3);
        nPQ.add(5, 3);
        nPQ.add(8, 6);
        nPQ.add(14, 2);
        nPQ.add(7, 7);
        nPQ.add(21, 4);
        nPQ.changePriority(9, 1);
        nPQ.changePriority(3, 2);
        nPQ.changePriority(15, 14);
        nPQ.removeSmallest();
        nPQ.removeSmallest();
        // printSimpleHeapDrawing(aPQ);
        assertEquals(nPQ.getSmallest(), aPQ.getSmallest());

    }

    /* public static void printSimpleHeapDrawing(ArrayHeapMinPQ<Integer> heap) {
        int depth = ((int) (Math.log(heap.size()) / Math.log(2)));
        int level = 0;
        int itemsUntilNext = (int) Math.pow(2, level);
        for (int j = 0; j < depth; j++) {
            System.out.print(" ");
        }

        for (int i = 0; i < heap.size(); i++) {
            System.out.printf("%d ", heap.get(i));
            if (i == itemsUntilNext) {
                System.out.println();
                level++;
                itemsUntilNext += Math.pow(2, level);
                depth--;
                for (int j = 0; j < depth; j++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }
    */
}
