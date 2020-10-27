package bearmaps;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import java.util.List;

public class NaivePointSetTest {
    private static Random random = new Random(100);
    @Test
    public void specTest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        assertEquals(ret.getX(), 3.3, 0.01); // evaluates to 3.3
        assertEquals(ret.getY(), 4.4, 0.01); // evaluates to 4.4
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
}
