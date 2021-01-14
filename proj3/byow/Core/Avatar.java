package byow.Core;
// import byow.Core.Items.Weapon;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
// import edu.princeton.cs.introcs.StdDraw;

import java.util.HashMap;
import java.util.Random;

/* basic Avatar class with move methods that change the avatar's position
while modifying world tiles (changing original avatar tile to floor, changing
avatar destination tile to Tileset.AVATAR).
 */

public class Avatar {
    private TERenderer ter = new TERenderer();
    private Random random;
    private Position position;
    private TETile[][] world;
    private HashMap<Position, Item> itemMap;
    private HashMap<Item, Integer> inventory;
    private TETile lastTile;
    private Item rightHand;
    private Item leftHand;
    private int health;
    private int killCount;
    private int keys;
    private int jabDamage;
    private int sliceDamage;
    private int cleaveDamage;
    private int jabAccuracy;
    private int sliceAccuracy;
    private int cleaveAccuracy;

    public Avatar(Position position, TETile[][] world, HashMap<Position, Item> itemMap) {
        this.position = position;
        this.world = world;
        this.itemMap = itemMap;
        this.random = new Random();
        this.health = 100;
        this.killCount = 0;
        this.jabDamage = 15;
        this.sliceDamage = 30;
        this.cleaveDamage = 50;
        this.jabAccuracy = 85;
        this.sliceAccuracy = 70;
        this.cleaveAccuracy = 50;
        this.keys = 100;
        this.inventory = new HashMap<>();
        lastTile = Tileset.FLOOR;
    }

    /* public void attack(Enemy enemy) {
        int damage = 1;
        if (rightHand instanceof Weapon && ((Weapon) rightHand).attack(this, enemy)) {
            StdDraw.text(.5, .2, "Your " + rightHand.getName() + " broke!");
            rightHand = null;
        }
        if (leftHand instanceof Weapon && ((Weapon) leftHand).attack(this, enemy)) {
            StdDraw.text(.5, .2, "Your " + leftHand.getName() + " broke!");
            rightHand = null;
        }
    } */
    /*
    MOVE UPDATES:
    - made helper function "move" to eliminate redundant code
    - every time avatar moves, remember what the tile under the avatar was
    - replace the tile you're leaving with what used to be there.
    - if the avatar moves onto an item tile, ask them if they want to pick it up
    - if they do change that tile to floor

     */
    public boolean move(int toX, int toY) {
        //don't move through walls
        if (world[toX][toY].equals(Tileset.WALL)) {
            return false;
        }
        //replace the tile you're leaving
        world[position.getX()][position.getY()] = lastTile;
        this.position = new Position(toX, toY);
        //remember the tile you're on
        lastTile = world[toX][toY];
        //behave differently depending on the tile you enter
        switch (world[toX][toY].description()) {
            case "treasure chest":
                world[toX][toY] = Tileset.AVATAR;
                if (keys > 0) {
                    searchChest();
                    keys--;
                    return true;
                } else if (keys == 0) {
                    ter.renderFrame(world);
                    return true;
                }
                return true;
            default:
                world[toX][toY] = Tileset.AVATAR;
                return true;
        }
    }

    public boolean moveUp() {
        int toX = this.position.getX();
        int toY = this.position.getY() + 1;
        return move(toX, toY);
    }

    public boolean moveDown() {
        int toX = this.position.getX();
        int toY = this.position.getY() - 1;
        return move(toX, toY);
    }

    public boolean moveLeft() {
        int toX = this.position.getX() - 1;
        int toY = this.position.getY();
        return move(toX, toY);
    }

    public boolean moveRight() {
        int toX = this.position.getX() + 1;
        int toY = this.position.getY();
        return move(toX, toY);
    }

    public void searchChest() {
        Item i = itemMap.get(position);
        inventory.put(i, inventory.size());
        modifyStats(i);
        lastTile = Tileset.FLOOR;
        // ter.renderFrame(world);
    }

    /* public void pickUpWeapon(int toX, int toY) {
        world[toX][toY] = Tileset.AVATAR;
        String name = itemMap.get(position).getName();
        String rh = rightHand.getName();
        String lh = leftHand.getName();
        StdDraw.text(.5, .5, "You found a " + name + ". Would you like to pick it up?");
        InputSource keyboard = new InputSource("item");
        //TODO make it stable regarding savestring and inputs that aren't r l or x
        char c = keyboard.getNextKey();
        switch (c) {
            case 'R':
                if (rightHand != null) {
                    StdDraw.text(.5, .5, "You drop your " + rh + " to pick up the " + lh + ".");
                } else {
                    StdDraw.text(.5, .5, "You pick up the " + name + ".");
                }
                rightHand = itemMap.get(lastTile);
                lastTile = Tileset.FLOOR;
                return;
            case 'L':
                if (leftHand != null) {
                    StdDraw.text(.5, .5, "You drop your " + lh + " to pick up the " + name + ".");
                } else {
                    StdDraw.text(.5, .5, "You pick up the " + name + ".");
                }
                leftHand = itemMap.get(lastTile);
                lastTile = Tileset.FLOOR;
                return;
            case 'X':
                return;
        }
    } */

    public int getHealth() {
        return health;
    }

    public int getKillCount() {
        return killCount;
    }

    public int getKeys() {
        return keys;
    }

    public HashMap<Item, Integer> getInventory() {
        return inventory;
    }

    public int randomIntInRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    private void modifyStats(Item i) {
        jabDamage += i.damageBuff();
        sliceDamage += i.damageBuff();
        cleaveDamage += i.damageBuff();
        jabAccuracy += i.accuracyBuff();
        sliceAccuracy += i.accuracyBuff();
        cleaveAccuracy += i.accuracyBuff();
    }

}
