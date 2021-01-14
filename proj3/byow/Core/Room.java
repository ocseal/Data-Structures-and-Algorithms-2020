package byow.Core;

import java.util.ArrayList;

public class Room {
    private int height;
    private int width;
    private final Position topLeft;
    private Position bottomLeft;
    private Position topRight;
    private Position bottomRight;
    private ArrayList<Position> positions;

    public Room(int height, int width, Position topLeft) {
        this.height = height;
        this.width = width;
        this.topLeft = topLeft;
        constructorHelper();
    }

        // stores corners as positions, primarily used for walls.
    private void constructorHelper() {
        this.bottomLeft = new Position(topLeft.getX(), topLeft.getY() - height + 1);
        this.topRight = new Position(topLeft.getX() + width - 1 + width, topLeft.getY());
        this.bottomRight = new Position(topLeft.getX() + width - 1, topLeft.getY() - height + 1);

        // ArrayList of all the positions occupied by the room.
        this.positions = new ArrayList<>(height * width);

        /* adds all possible positions within the height and width dimensions to the ArrayList */
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position p = new Position(topLeft.getX() + x, topLeft.getY() - y);
                positions.add(p);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomLeft() {
        return bottomLeft;
    }

    public Position getTopRight() {
        return topRight;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }
}
