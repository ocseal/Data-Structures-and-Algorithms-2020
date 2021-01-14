package byow.Core;
import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RoomTest {

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
    public void positionArrayListTest() {
        Position topLeft = new Position(2, 2);
        Room newRoom = new Room(2, 3, topLeft);
        Position p2 = new Position(2, 3);
        Position p3 = new Position(3, 2);
        Position p4 = new Position(3, 3);
        Position p5 = new Position(4, 2);
        Position p6 = new Position(4, 3);
        ArrayList<Position> testerPositions = new ArrayList<>(4);
        testerPositions.add(topLeft);
        testerPositions.add(p2);
        testerPositions.add(p3);
        testerPositions.add(p4);
        testerPositions.add(p5);
        testerPositions.add(p6);

        /* Note that we can't compare positions using .equals. May be a minor inconvenience */
        // assertTrue(testerPositions.get(0).equals(newRoom.getPositions().get(0))) fails.

        assertEquals(testerPositions.get(0).getX(), newRoom.getPositions().get(0).getX());
        assertEquals(testerPositions.get(0).getY(), newRoom.getPositions().get(0).getY());
        assertEquals(testerPositions.get(1).getX(), newRoom.getPositions().get(1).getX());
        assertEquals(testerPositions.get(1).getY(), newRoom.getPositions().get(1).getY());
        assertEquals(testerPositions.get(2).getX(), newRoom.getPositions().get(2).getX());
        assertEquals(testerPositions.get(2).getY(), newRoom.getPositions().get(2).getY());
        assertEquals(testerPositions.get(3).getX(), newRoom.getPositions().get(3).getX());
        assertEquals(testerPositions.get(3).getY(), newRoom.getPositions().get(3).getY());
        assertEquals(testerPositions.get(4).getX(), newRoom.getPositions().get(4).getX());
        assertEquals(testerPositions.get(4).getY(), newRoom.getPositions().get(4).getY());
        assertEquals(testerPositions.get(5).getX(), newRoom.getPositions().get(5).getX());
        assertEquals(testerPositions.get(5).getY(), newRoom.getPositions().get(5).getY());
    }

    @Test
    public void mapGeneratorRoomsTest() {
        MapGenerator map = new MapGenerator(80, 30, 99);
    }

    @Test
    public void timeRoomConstruction() {
        List<Double> times = new ArrayList<>(8);
        List<Integer> sizes = new ArrayList<>(8);
        List<Integer> ops = new ArrayList<>(8);
        Stopwatch sw = new Stopwatch();
        MapGenerator map;
        for (int i = 8; i < 128; i *= 2) {
            map = new MapGenerator(i, i, 100);
            times.add(sw.elapsedTime());
            ops.add(i * i);
            sizes.add(i * i);
        }
        printTable(sizes, times, ops);
    }

    public static void main(String[] args) {
    }
}
