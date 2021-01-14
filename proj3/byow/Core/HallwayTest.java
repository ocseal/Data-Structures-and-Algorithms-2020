package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class HallwayTest {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;


    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Room r1 = new Room(3, 3, new Position(1, 1));
        Room r2 = new Room(5, 5, new Position(10, 10));
        Hallway hallway = new Hallway(r2, r1);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        for (Position p : r1.getPositions()) {
            world[p.getY()][p.getX()] = Tileset.FLOOR;
        }
        for (Position p : r2.getPositions()) {
            world[p.getY()][p.getX()] = Tileset.FLOOR;
        }
        for (Position p : hallway.getFloorTiles()) {
            world[p.getY()][p.getX()] = Tileset.FLOOR;
        }

        for (Position p : hallway.getWallTiles()) {
            if (world[p.getY()][p.getX()] != Tileset.FLOOR) {
                world[p.getY()][p.getX()] = Tileset.WALL;
            }
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }
    /*
    TETile[][] world = new TETile[60][30];
    TERenderer ter = new TERenderer();
    ter.initialize(60, 30);
    for (int x = 0; x < 60; x += 1) {
        for (int y = 0; y < 30; y += 1) {
            world[x][y] = Tileset.NOTHING;
        }
    }
    ter.renderFrame(world);

*/
}
