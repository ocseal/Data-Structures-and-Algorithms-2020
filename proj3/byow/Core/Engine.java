package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;


import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import java.util.HashMap;

public class Engine {
    private static MapGenerator mapGenerator;
    private static TETile[][] world;
    private static TERenderer ter = new TERenderer();
    private static Avatar avatar;
    private static InputSource keyboard;
    private static long seed;
    private static StringBuilder saveString = new StringBuilder();
    private static StringBuilder undoString = new StringBuilder();
    private static String currentTileName = "";
    private HashMap<Position, Item> itemMap;
    private HashMap<Item, Integer> allItems;
    private static String testerString;

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        mainMenu(); // displays the Main Menu in StdDraw.
        char menuOption = keyboard.getNextKey();
        if (menuOption == 'N') { // generates map for new game, appends saveString.
            saveString.append('N');
            generateMap();
            saveString.append(seed);
            saveString.append('S');
        } else if (menuOption == 'L') { // reads from save file
            load();
            ter.initialize(WIDTH, HEIGHT + 9);
            display(world);
        } else if (menuOption == 'Q') { // quits game.
            System.exit(0);
        } else {
            interactWithKeyboard();
        }
        updateMouse();
        play();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    // changed to static, may cause problems?
    public static TETile[][] interactWithInputString(String input) {
        saveString = new StringBuilder();
        String save = input.toUpperCase();
        String moves;
        if (save.charAt(0) == 'L') {
            moves = save.substring(1);
            assignLoadedWorld(false, moves);
            return world;
        } else {
            saveString.append('N');
            seed = Long.parseLong(save.substring(save.indexOf('N') + 1, save.indexOf('S')));
            saveString.append(seed);
            saveString.append('S');
            // takes the substring between 'S' and the end as the string of moves.
            moves = save.substring(save.indexOf('S') + 1);
        }
        char[] moveArray = moves.toCharArray();
        // generates new map, populates it with avatar.
        mapGenerator = new MapGenerator(WIDTH, HEIGHT, seed);
        TETile[][] finalWorldFrame = mapGenerator.getWorld();
        HashMap<Position, Item> items = mapGenerator.getItemMap();
        avatar = new Avatar(mapGenerator.getAvatarStart(), finalWorldFrame, items);
        // goes through charArray, making moves w/avatar until final state.

        for (int i = 0; i < moveArray.length; i++) {
            char c = moveArray[i];
            if (c == 'W') {
                if (avatar.moveUp()) {
                    saveString.append('W');
                    undoString.append('W');
                }
            }
            if (c == 'A') {
                if (avatar.moveLeft()) {
                    saveString.append('A');
                    undoString.append('A');
                }
            }
            if (c == 'S') {
                if (avatar.moveDown()) {
                    saveString.append('S');
                    undoString.append('S');
                }
            }
            if (c == 'D') {
                if (avatar.moveRight()) {
                    saveString.append('D');
                    undoString.append('D');
                }
            }
            if (c == 'X') {
                undo();
                saveString.append('X');
            }
            if (c == 'Q') {
                if (moveArray[i - 1] == ':') {
                    saveGame();
                    // System.exit(0);
                }
            }
        }
        world = finalWorldFrame;
        return world;
    }

    public static void replay(String input) throws InterruptedException {
        seed = Long.parseLong(input.substring(input.indexOf('N') + 1, input.indexOf('S')));
        String moves = input.substring(input.indexOf('S') + 1);
        mapGenerator = new MapGenerator(WIDTH, HEIGHT, seed);
        world = mapGenerator.getWorld();
        avatar = new Avatar(mapGenerator.getAvatarStart(), world, mapGenerator.getItemMap());
        ter.initialize(WIDTH, HEIGHT + 9);
        display(world);
        Thread.sleep(100);
        char[] moveArray = moves.toCharArray();
        for (char c : moveArray) {
            if (c == 'W') {
                avatar.moveUp();
                undoString.append('W');
            }
            if (c == 'A') {
                avatar.moveLeft();
                undoString.append('A');
            }
            if (c == 'S') {
                avatar.moveDown();
                undoString.append('S');
            }
            if (c == 'D') {
                avatar.moveRight();
                undoString.append('D');
            }
            if (c == 'X') {
                undo();
            }
            System.out.print(c);
            ter.renderFrame(world);
            StdDraw.show();
            Thread.sleep(100);
        }
    }

    private static void mainMenu() {
        keyboard = new InputSource("menu");
    }

    private void generateMap() {
        keyboard = new InputSource("seed");
        seed = keyboard.getSeed();
        mapGenerator = new MapGenerator(WIDTH, HEIGHT, seed);
        world = mapGenerator.getWorld();
        this.itemMap = mapGenerator.getItemMap();
        avatar = new Avatar(mapGenerator.getAvatarStart(), world, itemMap);
        ter.initialize(WIDTH, HEIGHT + 9);
        display(world);
    }

    private void nameAvatar() {
        keyboard = new InputSource("name");
    }

    private static void assignLoadedWorld(Boolean replay, String loadedMoves) {
        try {
            FileReader fr = new FileReader("cs61b game save.txt");
            StringBuilder sb = new StringBuilder();
            char[] addedMoves =  loadedMoves.toCharArray();
            int i = fr.read();
            while ((i != -1)) {
                sb.append((char) i);
                i = fr.read();
            }
            saveString.append(sb.toString());
            if (replay) {
                replay(saveString.toString());
            } else {
                for (char c: addedMoves) {
                    saveString.append(c);
                }
                world = interactWithInputString(saveString.toString());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            /*
            StdDraw.clear();
            StdDraw.text(0.5, 0.5, "No save game found!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException i) {
                System.out.println("InterruptedException");
            }
            StdDraw.clear(); */
        }
    }

    private static void load() {
        keyboard = new InputSource("load");
        boolean replay = false;
        while (true) {
            char c = keyboard.getNextKey();
            if (c == 'R') {
                replay = true;
                break;
            }
            if (c == 'L') {
                break;
            }
            if (c == '\0') {
                System.exit(0);
            }
        }
        // reads through file, changes world to previously played world.
        assignLoadedWorld(replay, "");
    }

    private static void updateMouse() {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        if (mouseX >= WIDTH || mouseY >= HEIGHT) {
            currentTileName = "nothing";
        } else {
            TETile currentTile = world[mouseX][mouseY];
            currentTileName = currentTile.description();
        }
    }

    private static void play() {
        while (true) {
            // currentTile = world[mouseX][mouseY];
            // currentTileName = currentTile.description();
            while (!StdDraw.hasNextKeyTyped()) {
                updateMouse();
                display(world);
            }
            char nextMove = keyboard.getNextKey();
            if (nextMove == 'W') {
                if (avatar.moveUp()) {
                    saveString.append('W');
                    undoString.append('W');
                }
            }
            if (nextMove == 'A') {
                if (avatar.moveLeft()) {
                    saveString.append('A');
                    undoString.append('A');
                }
            }
            if (nextMove == 'S') {
                if (avatar.moveDown()) {
                    saveString.append('S');
                    undoString.append('S');
                }
            }
            if (nextMove == 'D') {
                if (avatar.moveRight()) {
                    saveString.append('D');
                    undoString.append('D');
                }
            }
            if (nextMove == 'X') {
                undo();
                saveString.append('X');
            }

            if (nextMove == '\0') {
                saveGame();
                System.exit(0);
            }
            display(world);
        }
    }

    private static void undo() {
        if (undoString.length() > 0) {
            char result = undoString.charAt(undoString.length() - 1);
            undoString.deleteCharAt(undoString.length() - 1);
            switch (result) {
                case 'W':
                    avatar.moveDown();
                    break;
                case 'A':
                    avatar.moveRight();
                    break;
                case 'S':
                    avatar.moveUp();
                    break;
                case 'D':
                    avatar.moveLeft();
                    break;
                default:
                    return;
            }
        } else {
            return;
        }
    }

    private static void saveGame() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("cs61b game save.txt"))) {
            out.write(saveString.toString());
        } catch (IOException e) {
            StdDraw.text(0.5, 0.5, "Save game error");
        }
    }

    public static void display(TETile[][] tiles) {
        ter.renderFrame(tiles);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(5, HEIGHT + 8, "Health: " + avatar.getHealth() + "/100");
        StdDraw.text(5.5, HEIGHT + 7, "Enemies defeated: " + avatar.getKillCount());
        StdDraw.text(5.5, HEIGHT + 6, "Loot box keys: " + avatar.getKeys());
        StdDraw.text(WIDTH * 0.75, HEIGHT + 8, "Inventory: ");
        StdDraw.text(5, HEIGHT + 5, currentTileName);
        StdDraw.text(WIDTH * .45, HEIGHT + 8, "WASD to move ");
        StdDraw.text(WIDTH * .55, HEIGHT + 8, "X to undo ");

        if (avatar.getInventory() != null) {
            int lineDecrease = 1;
            for (Item i : avatar.getInventory().keySet()) {
                StdDraw.text(WIDTH * 0.75, HEIGHT + 8 - lineDecrease, i.getName());
                lineDecrease++;
            }
        }
        StdDraw.show();
    }
}
