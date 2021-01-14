package byow.Core;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Harrison Dill 
 */

public class KDTree {
    private class Node {
        /**
         * This Node's corresponding Position
         */
        private Position p;
        /**
         * This Node's parent. Mostly useful for visualizer clarity.
         */
        private Node parent;
        /**
         * The upper child
         */
        private Node greater;
        /**
         * The lower child
         */
        private Node lesser;

        private boolean isElligible;

        /**
         * Make a new Node.
         * @param p the Node's Position.
         */
        Node(Position p) {
            this.p = p;
            isElligible = true;
        }

        /**
         * Recursively pass the Node off to the right
         *              child until it is added to the bottom of the tree.
         * @param n the Node
         * @param index whether we're comparing x or y values
         *              during this recursive call (even or odd)
         */
        private void add(Node n, int index) {
            if (index % 2 == 0) {
                if (this.p.getX() > n.p.getX()) {
                    if (this.lesser == null) {
                        this.lesser = n;
                        n.parent = this;
                    } else {
                        this.lesser.add(n, index + 1);
                    }
                } else if (this.greater == null) {
                    this.greater = n;
                    n.parent = this;
                } else {
                    this.greater.add(n, index + 1);
                }
            } else if (this.p.getY() > n.p.getY()) {
                if (this.lesser == null) {
                    this.lesser = n;
                    n.parent = this;
                } else {
                    this.lesser.add(n, index + 1);
                }
            } else if (this.greater == null) {
                this.greater = n;
                n.parent = this;
            } else {
                this.greater.add(n, index + 1);
            }
        }
    }

    /**
     * Root Node of the tree.
     */
    private Node root;

    /**
     * Make a new KDTree.
     * @param positions the Positions in the tree
     */
    public KDTree(List<Position> positions) {
        List<Position> lis = new ArrayList<>(positions);
        this.root = new Node(lis.remove(0));
        for (Position p : lis) {
            root.add(new Node(p), 0);
        }
    }

    /**
     * Helper variable for nearest. It's out here so
     * that both nearest and nearestHelper can access it.
     */
    private Node curBest;

    /**
     * Find the nearest Position in the tree to the
     *              given x, y coordinates efficiently.
     * @param x the x coord
     * @param y the y coord
     * @return the closest Position in the tree.
     */
    public Position nearest(int x, int y) {
        Position p = new Position(x, y);
        curBest = new Node(new Position(999999, 99999));
        return nearestHelper(p, root, 0);
    }

    /**
     * Recursively callable helper function for nearest.
     * Effectively checks all the Nodes below cur for a better Node than curBest.
     * @param p1 the Position (x, y) from nearest() parameters
     * @param cur the Node currently being compared
     * @param index whether this recursive call compares x or y coords
     * @return the closest Position in the tree.
     */
    private Position nearestHelper(Position p1, Node cur, int index) {
        if (cur == null) {
            return null;
        }
        if (Position.distance(cur.p, p1) < Position.distance(curBest.p, p1)) {
            if (cur.isElligible) {
                curBest.isElligible = true;
                curBest = cur;
                curBest.isElligible = false;
            }
        }
        if (index % 2 == 0) {
            if (p1.getX() > cur.p.getX()) {
                nearestHelper(p1, cur.greater, index + 1);
                if (Position.distance(new Position(cur.p.getX(), p1.getY()),
                        p1) > Position.distance(curBest.p, p1)) {
                    return curBest.p;
                } else {
                    nearestHelper(p1, cur.lesser, index + 1);
                    return curBest.p;
                }
            } else {
                nearestHelper(p1, cur.lesser, index + 1);
                if (Position.distance(new Position(cur.p.getX(), p1.getY()), p1)
                        > Position.distance(curBest.p, p1)) {
                    return curBest.p;
                } else {
                    nearestHelper(p1, cur.greater, index + 1);
                    return curBest.p;
                }
            }
        } else {
            if (p1.getY() > cur.p.getY()) {
                nearestHelper(p1, cur.greater, index + 1);
                if (Position.distance(new Position(p1.getX(), cur.p.getY()), p1)
                        > Position.distance(curBest.p, p1)) {
                    return curBest.p;
                } else {
                    nearestHelper(p1, cur.lesser, index + 1);
                    return curBest.p;
                }
            } else {
                nearestHelper(p1, cur.lesser, index + 1);
                if (Position.distance(new Position(p1.getX(), cur.p.getY()), p1)
                        > Position.distance(curBest.p, p1)) {
                    return curBest.p;
                } else {
                    nearestHelper(p1, cur.greater, index + 1);
                    return curBest.p;
                }
            }
        }
    }
}
