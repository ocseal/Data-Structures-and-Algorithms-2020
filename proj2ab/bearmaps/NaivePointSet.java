package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point n = points.get(0);
        for (Point point : points) {
            double distance = Point.distance(point, goal);
            if (distance < Point.distance(n, goal)) {
                n = point;
            }
        }
        return n;
    }
}
