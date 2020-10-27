package bearmaps;

import org.junit.Test;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/* Multiple tests were sourced/inspired by Hug's "randomized tests" guide video. */
public class TestKDTree {
    private static Random random = new Random(100);
    private static KDTree buildLectureTree() { /* Sourced from Hug's video */
        Point a = new Point(2, 3);
        Point z = new Point(4, 2);
        Point b = new Point(4, 2);
        Point c = new Point(4, 5);
        Point d = new Point(3, 3);
        Point e = new Point(1, 5);
        Point f = new Point(4, 4);
        List<Point> points = List.of(a, z, b, c, d, e, f);
        return new KDTree(points);
    }

    @Test
    public void insertionTest() {
        buildLectureTree();
    }

    @Test
    public void specTestNearest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        KDTree tree = new KDTree(List.of(p1, p2, p3));
        Point ret = tree.nearest(3.0, 4.0); // returns p2
        assertEquals(ret.getX(), 3.3, 0.001); // evaluates to 3.3
        assertEquals(ret.getY(), 4.4, 0.001); // evaluates to 4.4
    }

    private List<Point> randomPoints(int size) { /* Sourced from Hug's video */
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    private Point randomPoint() { /* Sourced from Hug's video */
        double x = random.nextDouble();
        double y = random.nextDouble();
        return new Point(x, y);
    }

    @Test
    public void lectureTestNearest() {
        KDTree tree = buildLectureTree();
        Point actual = tree.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    @Test
    public void test100Points() {
        List<Point> points = randomPoints(100);
        NaivePointSet naivePoints = new NaivePointSet(points);
        KDTree tree = new KDTree(points);

        List<Point> tests = randomPoints(100);
        for (Point p: tests) {
            Point nVal = naivePoints.nearest(p.getX(), p.getY());
            Point kVal = tree.nearest(p.getX(), p.getY());
            assertEquals(nVal, kVal);
        }
    }

    @Test
    public void test1000Points() { /* @Source Hug's testing video */
        List<Point> points = randomPoints(1000);
        NaivePointSet naivePoints = new NaivePointSet(points);
        KDTree tree = new KDTree(points);

        List<Point> tests = randomPoints(1000);
        for (Point p: tests) {
            Point nVal = naivePoints.nearest(p.getX(), p.getY());
            Point kVal = tree.nearest(p.getX(), p.getY());
            assertEquals(nVal, kVal);
        }
    }

    @Test
    public void test10000Points2000Tests() {
        List<Point> points = randomPoints(10000);
        NaivePointSet naivePoints = new NaivePointSet(points);
        KDTree tree = new KDTree(points);

        List<Point> tests = randomPoints(2000);
        for (Point p: tests) {
            Point nVal = naivePoints.nearest(p.getX(), p.getY());
            Point kVal = tree.nearest(p.getX(), p.getY());
            assertEquals(nVal, kVal);
        }
    }

    /* @Test
    public void distTest() {
        assertEquals(3.11, tree.distance(p1, p2), 0.01);
        assertEquals(6.2, tree.distance(p2, p3), 0.01);
        assertEquals(4.47, tree.distance(p3, p1), 0.01);
    } */

}
