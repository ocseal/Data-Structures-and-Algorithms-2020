package bearmaps;


import java.util.List;

public class KDTree implements PointSet {
    private static final boolean VERTICAL = false;
    private static final boolean HORIZONTAL = true;

    private Node root;

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node n = nearestHelper(root, goal, root);
        return n.p;
    }

    private class Node {
        private Point p;
        boolean orientation;
        Node left;
        Node right;
        Node(Point p, Boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }

    }

    private int compare(Point a, Point b, Boolean orientation) {
        if (orientation) {
            return (Double.compare(a.getX(), b.getX()));
        } else {
            return (Double.compare(a.getY(), b.getY()));
        }
    }

    private Node insert(Point p, Node n, Boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (!n.p.equals(p)) {
            if (compare(p, n.p, orientation) < 0) {
                n.left = insert(p, n.left, !orientation);
            } else if (compare(p, n.p, orientation) >= 0) {
                n.right = insert(p, n.right, !orientation);
            }
        }
        return n;
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = insert(p, root, HORIZONTAL);
        }
    }

    public Node nearestHelper(Node n, Point goal, Node best) {
        Node goodSide;
        Node badSide;
        Point badSideBest;
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        if (compare(n.p, goal, n.orientation) < 0) {
            goodSide = n.right;
            badSide = n.left;
        } else {
            goodSide = n.left;
            badSide = n.right;
        }
        if (n.orientation) {
            badSideBest = new Point(n.p.getX(), goal.getY());
        } else {
            badSideBest = new Point(goal.getX(), n.p.getY());
        }
        best = nearestHelper(goodSide, goal, best);
        if (Point.distance(badSideBest, goal) < Point.distance(best.p, goal)) {
            best = nearestHelper(badSide, goal, best);
        }
        return best;
    }


}
