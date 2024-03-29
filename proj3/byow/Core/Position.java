package byow.Core;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns the euclidean distance (L2 norm) squared between two Positions
     * (x1, y1) and (x2, y2). Note: This is the square of the Euclidean distance,
     * i.e. there's no square root.
     */
    private static double distance(double x1, double x2, double y1, double y2) {
        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
    }

    /**
     * Returns the euclidean distance (L2 norm) squared between two Positions.
     * Note: This is the square of the Euclidean distance, i.e.
     * there's no square root. 
     */
    public static double distance(Position p1, Position p2) {
        return distance(p1.getX(), p2.getX(), p1.getY(), p2.getY());
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Position otherPosition = (Position) other;
        return getX() == otherPosition.getX() && getY() == otherPosition.getY();
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) ^ Double.hashCode(y);
    }
}
