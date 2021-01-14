package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

/* @source Hug's InputDemo */

public class InputSource {
    private static long seed;
    private static final boolean PRINT_TYPED_KEYS = true;
    public InputSource(String type) {
        switch (type) {
            case "menu":
                StdDraw.text(0.5, 0.6, "Main Menu");
                StdDraw.text(0.5, 0.4, "New Game (N)");
                StdDraw.text(0.5, 0.35, "Load Game (L)");
                StdDraw.text(0.5, 0.3, "Quit Game (Q)");
                break;
            case "seed":
                StdDraw.clear();
                StdDraw.text(0.5, 0.5, "Enter a seed for your map, then press S.");
                break;
            case "item":
                StdDraw.text(.2, .3, "Left hand (L)");
                StdDraw.text(.5, .3, "Right hand (R)");
                StdDraw.text(.8, .3, "Leave it. (X)");
                break;
            case "load":
                StdDraw.clear();
                StdDraw.text(0.5, 0.4, "Load directly (L)");
                StdDraw.text(0.5, 0.35, "Replay moves (R)");
                break;
            case "name":
                StdDraw.clear();
                StdDraw.text(0.5, 0.5, "Name your character?");
                StdDraw.text(0.5, 0.4, "Yes (Y)");
                StdDraw.text(0.5, 0.35, "No (N)");
                break;
            default:
                break;
        }
    }

    public char getNextKeyWithColon() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                if (c == ':') {
                    return getNextKeyWithColon();
                }
                if (c == 'Q') {
                    return '\0';
                }
                return c;
            }
        }
    }

    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                if (c == ':') {
                    return getNextKeyWithColon();
                }
                return c;
            }
        }
    }

    /* checks user input for a valid long-type seed. */ 
    public long getSeed() {
        StringBuilder charString = new StringBuilder();
        char next = this.getNextKey();
        while (next != 'S') {
            if (next == '\0') {
                System.exit(0);
            }
            charString.append(next);
            next = this.getNextKey();
        }
        try {
            seed = Long.parseLong(charString.toString());
        } catch (NumberFormatException e) {
            StdDraw.clear();
            StdDraw.text(0.5, 0.5, "Invalid seed, please try again!");
            getSeed();
        }
        return seed;
    }

    public char getMenuOption() {
        char next = this.getNextKey();
        return next;
    }

    public boolean possibleNextInput() {
        return true;
    }
}
