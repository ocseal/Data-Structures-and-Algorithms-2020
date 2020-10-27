package bearmaps;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private final ArrayList<Node> arrayHeap;
    private final HashMap<T, Integer> items;

    public ArrayHeapMinPQ() {
        arrayHeap = new ArrayList<Node>();
        items = new HashMap<>();
    }

    private class Node {
        private final T value;
        private double priority;

        Node(T item, Double priority) {
            this.value = item;
            this.setPriority(priority);
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }
    }

    /* public T get(int index) {
        return arrayHeap.get(index).value;
    } */

    @Override
    public void add(T item, double priority) {
        if (items.containsKey(item)) {
            throw new IllegalArgumentException("Item already exists!");
        }
        items.put(item, arrayHeap.size());
        Node newNode = new Node(item, priority);
        arrayHeap.add(newNode);
        swim(arrayHeap.size() - 1);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (arrayHeap.size() == 0) {
            throw new NoSuchElementException("array heap has no items");
        }
        return arrayHeap.get(0).value;
    }

    @Override
    public T removeSmallest() {
        if (arrayHeap.size() == 0) {
            throw new NoSuchElementException("array heap has no items");
        }
        T removed = getSmallest();
        swap(0, items.size() - 1);
        arrayHeap.remove(items.size() - 1);
        items.remove(removed);
        sink(0);
        return removed;
    }

    @Override
    public int size() {
        return arrayHeap.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!items.containsKey(item)) {
            throw new NoSuchElementException("Item not found");
        }
        double oldPriority = arrayHeap.get(items.get(item)).priority;
        arrayHeap.get(items.get(item)).setPriority(priority);
        if (arrayHeap.get(items.get(item)).priority < oldPriority) {
            swim(items.get(item));
        } else if (arrayHeap.get(items.get(item)).priority > oldPriority) {
            sink(items.get(item));
        }
    }

    private int sinkPath(int position) {
        int leftChild = (2 * position) + 1;
        int rightChild = (2 * position) + 2;

        if (rightChild >= arrayHeap.size()) {
            if (leftChild >= arrayHeap.size()) {
                return 0;
            }
            return leftChild;
        }

        if (arrayHeap.get(leftChild).priority <= arrayHeap.get(rightChild).priority) {
            return leftChild;
        } else {
            return rightChild;
        }
    }

    private void sink(int position) {
        int next = sinkPath(position);
        if (next == 0) {
            return;
        }
        while (arrayHeap.get(next).priority < arrayHeap.get(position).priority) {
            swap(position, next);
            position = next;
            next = sinkPath(next);
            if (next == 0) {
                return;
            }
        }
    }

    private void swim(int position) {
        Node child = arrayHeap.get(position);
        Node parent = arrayHeap.get(parent(position));
        if (child.priority < parent.priority) {
            swap(position, parent(position));
            position = parent(position);
            swim(position);
        }
    }

    private void swap(int p1, int p2) {
        Node t1 = arrayHeap.get(p1);
        Node t2 = arrayHeap.get(p2);
        items.put(t1.value, p2);
        items.put(t2.value, p1);
        arrayHeap.set(p1, t2);
        arrayHeap.set(p2, t1);
    }

    private int parent(int position) {
        return (position - 1) / 2;
    }
}
