package byow.Core;

import byow.Core.Items.Weapon;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
// import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapGenerator {
    private int WIDTH;
    private int HEIGHT;
    private int numRooms;
    private final Random RANDOM;
    private static final TERenderer TER = new TERenderer();
    private final TETile[][] world;
    private final ArrayList<Position> floorPositions;
    private Position avatarStart;
    private ArrayList<Room> rooms;
    private HashMap<Position, Item> itemMap;

    /* public MapGenerator (long seed) {
        SEED = seed;
        RANDOM = new Random(SEED);
        world = new TETile[WIDTH][HEIGHT];
        numRooms = randomIntInRange(20, 21); // takes in min and max.
        outputs a random number of rooms in range.
        floorPositions = new ArrayList<>(WIDTH * HEIGHT);
        rooms = new ArrayList<>(numRooms); // makes a new ArrayList to store all rooms.
        May not be necessary later, but you could use the ArrayList to compare rooms.
        union = new WeightedQuickUnionUF(numRooms);
        //make a new union to help determine what rooms are connected by hallways
        ter.initialize(WIDTH, HEIGHT);
        clear();
        addRooms();
        addHallways();
        addWalls();
    } */

    public MapGenerator(int width, int height, long seed) {
        WIDTH = width;
        HEIGHT = height;
        RANDOM = new Random(seed);
        world = new TETile[WIDTH][HEIGHT];
        numRooms = randomIntInRange(20, 25); // outputs a random number of rooms in range.
        floorPositions = new ArrayList<>(WIDTH * HEIGHT);
        clear();
        rooms = new ArrayList<>(numRooms);
        addRooms();
        addHallways();
        addWalls();
        addItems();
        assignAvatarStart();
    }

    private void addRooms() { // adds all rooms to our TETile[][] world.
        // for loop puts in numRooms number of rooms into the world.
        for (int i = 0; i < numRooms; i++) {
            // assigns random height and width. fiddle with these bounds to see changes in map.
            int width = randomIntInRange(2, WIDTH / 10);
            int height = randomIntInRange(2, WIDTH / 10);

            // assigns random starting position (x, y).
            // the bound prevents the room from going off frame. (1 offset for the walls).
            int randomX = randomIntInRange(1, WIDTH - width - 1);
            int randomY = randomIntInRange(height, HEIGHT - 1);

            // stores random topLeft position for a new room.
            Position topLeft = new Position(randomX, randomY);

            // creates a new Room with given parameters.
            Room newRoom = new Room(height, width, topLeft);
            rooms.add(newRoom); // adds the new room to Rooms arraylist.

            // for loop iterates through all of the new room's occupied positions
            for (Position p : newRoom.getPositions()) {
                world[p.getX()][p.getY()] = Tileset.FLOOR;
                floorPositions.add(p);
            }
        }
    }

    /* adds walls to all of each floor tile's empty neighbors */
    private void addWalls() {
        for (Position f : floorPositions) {
            for (Position p : emptyNeighbors(f.getX(), f.getY())) {
                world[p.getX()][p.getY()] = Tileset.WALL;
            }
        }
    }

    private void addHallways() {
        HashMap<Position, Room> roomMap = new HashMap<>();
        ArrayList<Position> roomPositions = new ArrayList<>();
        for (Room r : rooms) {
            roomMap.put(r.getTopLeft(), r);
            roomPositions.add(r.getTopLeft());
        }
        KDTree tree = new KDTree(roomPositions);
        Room r = rooms.get(0);
        for (int i = 0; i < rooms.size(); i++) {
            Room nextRoom = roomMap.get(tree.nearest(r.getTopLeft().getX(), r.getTopLeft().getY()));
            Hallway h = new Hallway(r, nextRoom);
            for (Position p : h.getFloorTiles()) {
                /*
                if (p.getX() > 0 && p.getX() < world.length) {
                    if (p.getY() > 0 && p.getY() < world[0].length) {
                        world[p.getX()][p.getY()] = Tileset.FLOOR;
                        floorPositions.add(p);
                    }
                }*/
                world[p.getX()][p.getY()] = Tileset.FLOOR;
                floorPositions.add(p);
            }
            r = nextRoom;
        }
            /*
            for (Position p : h.getWallTiles()) {
                if (world[p.getX()][p.getY()] != Tileset.FLOOR) {
                    world[p.getX()][p.getY()] = Tileset.WALL;
                }
            }*/
    }

    private void assignAvatarStart() {
        Room startRoom = rooms.get(0);
        int randomIndex = randomIntInRange(0, startRoom.getPositions().size() - 1);
        Position randomPosition = startRoom.getPositions().get(randomIndex);
        this.avatarStart = randomPosition;
        world[avatarStart.getX()][avatarStart.getY()] = Tileset.AVATAR;
    }

    private void addItems() {
        itemMap = new HashMap<>();
        ArrayList<Item> items = new ArrayList<>();
        Item a = new Weapon("Weapon A", "A", 10, 0);
        Item b = new Weapon("Weapon B", "B", 5, 5);
        Item c = new Weapon("Weapon C", "C", 100, 100);
        Item d = new Weapon("Weapon D", "D", 69, 69);
        items.add(a);
        items.add(b);
        items.add(c);
        items.add(d);
        for (int i = 0; i < randomIntInRange(1, 3); i++) {
            Position tile = floorPositions.get(randomIntInRange(0, floorPositions.size()));
            world[tile.getX()][tile.getY()] = Tileset.LOCKED_DOOR;
            Item item = items.get(randomIntInRange(0, items.size()));
            itemMap.put(tile, item);
            /* while (itemMap.containsValue(item)) { // ensures no duplicate items appear.
                item = items.get(randomIntInRange(0, items.size()));
                if (!itemMap.containsValue(item)) {
                    itemMap.put(tile, item);
                    break;
                }
            } */
        }
    }

    /* sets all tiles to Tileset.NOTHING */
    private void clear() {
        for (int x = 0; x <= WIDTH - 1; x++) {
            for (int y = 0; y <= HEIGHT - 1; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /*
    Checks to see if any of a floor tile's neighbors are empty.
    If they are, add their positions to an ArrayList of empty spots.
    Consider T as our target tile. Positions p1...p8 are as follows:

    p1 p2 p3
    p4 T  p5
    p6 p7 p8

     */

    private ArrayList<Position> emptyNeighbors(int x, int y) {
        ArrayList<Position> neighbors = new ArrayList<Position>(8);
        ArrayList<Position> empty = new ArrayList<Position>(8);
        neighbors.add(new Position(x - 1, y + 1));
        neighbors.add(new Position(x, y + 1));
        neighbors.add(new Position(x + 1, y + 1));
        neighbors.add(new Position(x - 1, y));
        neighbors.add(new Position(x + 1, y));
        neighbors.add(new Position(x - 1, y - 1));
        neighbors.add(new Position(x, y - 1));
        neighbors.add(new Position(x + 1, y - 1));

        for (Position p : neighbors) {
            if (world[p.getX()][p.getY()] == Tileset.NOTHING) {
                empty.add(p);
            }
        }
        return empty;
    }

    public int randomIntInRange(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public Position getAvatarStart() {
        return avatarStart;
    }

    public HashMap<Position, Item> getItemMap() {
        return itemMap;
    }

    //add more cool items to our item list.
    private static void initializeItems() {
        ArrayList<Item> items = new ArrayList<>();
    }
}
