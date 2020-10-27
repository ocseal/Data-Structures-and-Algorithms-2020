import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        Stopwatch sw = new Stopwatch();
        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);
        int size = 1000;

        while (size <= 128000) {
            int opCalls = 0;
            AList<Integer> n = new AList<>();
            while (n.size() < size) {
                n.addLast(1);
                opCalls += 1;
            }
            times.add(sw.elapsedTime());
            ops.add(opCalls);
            sizes.add(size);
            size *= 2;
        }
        printTimingTable(sizes, times, ops);
    }
}


