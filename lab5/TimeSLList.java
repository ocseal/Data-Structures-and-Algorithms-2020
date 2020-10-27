import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);
        int size = 1000;

        while (size <= 128000) {
            int opCalls = 10000;
            int opsRemaining = 10000;
            SLList<Integer> n = new SLList<Integer>();
            while (n.size() < size) {
                n.addLast(1);
            }
            Stopwatch sw = new Stopwatch();
            while (opsRemaining != 0) {
                n.getLast();
                opsRemaining--;
            }
            times.add(sw.elapsedTime());
            ops.add(opCalls);
            sizes.add(size);
            size *= 2;
        }
        printTimingTable(sizes, times, ops);
    }

}
