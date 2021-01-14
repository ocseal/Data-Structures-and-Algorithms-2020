package byow.Core;

import java.util.ArrayList;

public class Hallway {
    private ArrayList<Position> floorTiles;
    private ArrayList<Position> wallTiles;

    public ArrayList<Position> getFloorTiles() {
        return floorTiles;
    }

    public ArrayList<Position> getWallTiles() {
        return wallTiles;
    }

    //construct a Hallway that connects a random point on r1 to a random point on r2.
    // Eligible points must be facing goal room.
    public Hallway(Room r1, Room r2) {
        floorTiles = new ArrayList<>();
        wallTiles = new ArrayList<>();

        /*
        ArrayList<Position> possibleR1 = new ArrayList<>();
        ArrayList<Position> possibleR2 = new ArrayList<>();

        switch(roomConfig(r1.getTopLeft(), r2.getTopLeft())) {
            case 1:
                possibleR1.addAll(rightEdgeOfRoom(r1));
                possibleR1.addAll(bottomEdgeOfRoom(r1));
                possibleR2.addAll(topEdgeOfRoom(r2));
                possibleR2.addAll(leftEdgeOfRoom(r2));
                break;
            case 2:
                possibleR1.addAll(bottomEdgeOfRoom(r1));
                possibleR1.addAll(leftEdgeOfRoom(r1));
                possibleR2.addAll(topEdgeOfRoom(r2));
                possibleR2.addAll(rightEdgeOfRoom(r2));
                break;
            case 3:
                possibleR1.addAll(topEdgeOfRoom(r1));
                possibleR1.addAll(rightEdgeOfRoom(r1));
                possibleR2.addAll(bottomEdgeOfRoom(r2));
                possibleR2.addAll(leftEdgeOfRoom(r2));
                break;
            case 4:
                possibleR1.addAll(topEdgeOfRoom(r1));
                possibleR1.addAll(leftEdgeOfRoom(r1));
                possibleR2.addAll(bottomEdgeOfRoom(r2));
                possibleR2.addAll(rightEdgeOfRoom(r2));
                break;
        }

        Position start = possibleR1.get((int) Math.floor(Math.random() * possibleR1.size()));
        Position end = possibleR2.get((int) Math.floor(Math.random() * possibleR2.size()));

         */
        int startX = validateX((r1.getBottomRight().getX() + r1.getTopLeft().getX()) / 2);
        int startY = validateY((r1.getBottomRight().getY() + r1.getTopLeft().getY()) / 2);
        int endX = validateX((r2.getBottomRight().getX() + r2.getTopLeft().getX()) / 2);
        int endY = validateY((r2.getBottomRight().getY() + r2.getTopLeft().getY()) / 2);
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);
        Position c = start;

        // wallPos(cur);
        while (!(Math.abs(c.getY() - end.getY()) <= 0 && Math.abs(c.getX() - end.getX()) <= 0)) {
            switch (roomConfig(c, end)) {
                case 1:
                    c = new Position(c.getX(), c.getY() - 1);
                    break;
                case 2:
                    c = new Position(c.getX() + 1, c.getY());
                    break;
                case 3:
                    c = new Position(c.getX(), c.getY() + 1);
                    break;
                case 4:
                    c = new Position(c.getX() - 1, c.getY());
                    break;
                default:
                    return;
            }
            floorTiles.add(c);
            // wallPos(cur);
        }
    }

    /* private void wallPos(Position p) {
        wallTiles.add(new Position(p.getX() - 1, p.getY()));
        wallTiles.add(new Position(p.getX() + 1, p.getY()));
        wallTiles.add(new Position(p.getX(), p.getY() + 1));
        wallTiles.add(new Position(p.getX(), p.getY() - 1));
        wallTiles.add(new Position(p.getX() + 1, p.getY() + 1));
        wallTiles.add(new Position(p.getX() + 1, p.getY() - 1));
        wallTiles.add(new Position(p.getX() - 1, p.getY() + 1));
        wallTiles.add(new Position(p.getX() - 1, p.getY() - 1));
    } */

    /*return the location of r1 relative to r2
    1 - r2 is bottom of r1
    2 - r2 is right of r1
    3 - r2 is top of r1
    4 - r2 is left of r1
    */
    private int roomConfig(Position p1, Position p2) {
        double xdiff = Math.abs(p1.getX() - p2.getX());
        double ydiff = Math.abs(p1.getY() - p2.getY());
        if (p1.getY() - p2.getY() > 0 && ydiff >= xdiff) {
            return 1;
        } else if (p1.getX() - p2.getX() < 0 && ydiff <= xdiff) {
            return 2;
        } else if (p1.getY() - p2.getY() < 0 && ydiff >= xdiff) {
            return 3;
        } else if (p1.getX() - p2.getX() > 0 && ydiff <= xdiff) {
            return 4;
        }
        if (xdiff == 0 && ydiff == 0) {
            return 5;
        } else {
            throw new IllegalArgumentException("roomConfig error");
        }
    }
    /*
    private List<Position> rightEdgeOfRoom(Room r) {
        ArrayList<Position> rooms = new ArrayList<>();
        for (int i = 0; i < r.getHeight(); i++) {
            rooms.add(new Position(r.getTopRight().getX(), r.getTopRight().getY() - i));
        }
        return rooms;
    }

    private List<Position> leftEdgeOfRoom(Room r) {
        ArrayList<Position> rooms = new ArrayList<>();
        for (int i = 0; i < r.getHeight(); i++) {
            rooms.add(new Position(r.getTopLeft().getX(), r.getTopLeft().getY() - i));
        }
        return rooms;
    }

    private List<Position> bottomEdgeOfRoom(Room r) {
        ArrayList<Position> rooms = new ArrayList<>();
        for (int i = 0; i < r.getWidth(); i++) {
            rooms.add(new Position(r.getBottomLeft().getX() + i, r.getBottomLeft().getY()));
        }
        return rooms;
    }

    private List<Position> topEdgeOfRoom(Room r) {
        ArrayList<Position> rooms = new ArrayList<>();
        for (int i = 0; i < r.getHeight(); i++) {
            rooms.add(new Position(r.getTopLeft().getX() + i, r.getTopLeft().getY()));
        }
        return rooms;
    }

     */

    private int validateX(int x) {
        if (x <= 0) {
            System.out.println("invalid x position" + x);
            return 1;
        }
        if (x >= 79) {
            System.out.println("invalid x position" + x);
            return 78;
        }
        return x;
    }

    private int validateY(int y) {
        if (y <= 0) {
            System.out.println("invalid y position" + y);
            return 1;
        }
        if (y >= 29) {
            System.out.println("invalid y position" + y);
            return 28;
        }
        return y;
    }
}
