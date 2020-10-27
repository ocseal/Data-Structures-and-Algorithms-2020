package bearmaps;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class TimeKDTree {
    private static final Random random = new Random(500);
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

    private static List<Point> randomPoints(int size) { /* @Source Sourced from Hug's video */
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            points.add(randomPoint());
        }
        return points;
    }


    private static Point randomPoint() { /* @Source Sourced from Hug's video */
        double x = random.nextDouble();
        double y = random.nextDouble();
        return new Point(x, y);
    }

    @Test
    public void timeNaivePointSet() {

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

            List<Point> random = randomPoints(100000);
            int opCalls = 0;
            NaivePointSet n = new NaivePointSet(random);
            List<Point> tests = randomPoints(10000);
            Stopwatch sw = new Stopwatch();
            for (Point p: tests) {
                n.nearest(p.getX(), p.getY());
                opCalls++;
            }
            times.add(sw.elapsedTime());
            ops.add(opCalls);
            sizes.add(100000);
        printTable(sizes, times, ops);
    }

    @Test
    public void timeKDTreeNearest() {

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        for (int size = 31250; size <= 1000000; size *= 2) {
            List<Point> random = randomPoints(size);
            int opCalls = 0;
            KDTree t = new KDTree(random);
            List<Point> tests = randomPoints(1000000);
            Stopwatch sw = new Stopwatch();
            for (Point p: tests) {
                t.nearest(p.getX(), p.getY());
                opCalls++;
            }
            times.add(sw.elapsedTime());
            ops.add(opCalls);
            sizes.add(size);
        }
        printTable(sizes, times, ops);
    }

    @Test
    public void timeKDTreeConstructor() {

        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);

        for (int size = 31250; size <= 2000000; size *= 2) {
            Stopwatch sw = new Stopwatch();
            List<Point> random = randomPoints(size);
            int opCalls = size;
            KDTree t = new KDTree(random);
            times.add(sw.elapsedTime());
            ops.add(opCalls);
            sizes.add(size);
        }
        printTable(sizes, times, ops);
    }
}
