public class LinkedListDeque<T> {
    private class Node<T> {
        T item;
        Node prev, next;
        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private Node s1, s2;

    public LinkedListDeque() {
        s1 = new Node(null, null, null);
        s2 = new Node(null, null, null);
        s1.next = s2;
        s2.prev = s1;
        size = 0;
    }

    private void addWhenZeroSize(T item) {
        s1.next = new Node(item, s1, s2);
        s2.prev = s1.next;
        size += 1;
    }

    public void addFirst(T item) {
        if (size == 0) {
            addWhenZeroSize(item);
        } else {
            Node temp = s1.next;
            s1.next = new Node(item, s1, temp);
            s1.next.next.prev = s1.next;
            size += 1;
        }
    }

    public void addLast(T item) {
        if (size == 0) {
            addWhenZeroSize(item);
        } else {
            Node temp = s2.prev;
            s2.prev = new Node(item, temp, s2);
            s2.prev.prev.next = s2.prev;
            size += 1;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return Math.max(size, 0);
    }

    public void printDeque() {
        Node printer = s1;
        while (printer.next != s2) {
            printer = printer.next;
            System.out.println(printer.item);
        }
    }

    private void removeWithOneItem() {
        s1.next = s2;
        s2.prev = s1;
        size -= 1;
    }

    public T removeFirst() {
        T removed = (T) s1.next.item;
        if (s1.next.equals(s2)) {
            return null;
        }
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            removeWithOneItem();
            return removed;
        } else {
            s1.next = s1.next.next;
            s1.next.prev = s1;
            size -= 1;
            return removed;
        }
    }

    public T removeLast() {
        T removed = (T) s2.prev.item;
        if (s2.prev.equals(s1)) {
            return null;
        }
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            removeWithOneItem();
            return removed;
        } else {
            s2.prev = s2.prev.prev;
            s2.prev.next = s2;
            size -= 1;
            return removed;
        }
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        if (index < 0) {
            return null;
        }
        Node n = s1.next;
        if (n == s2) {
            return null;
        }
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        return (T) n.item;
    }

    public T getRecursive(int index) {
        Node n = s1.next;
        int i = 0;
        return helper(index, n, i);
    }

    private T helper(int index, Node next, int i) {
        if (index >= size) {
            return null;
        }
        if (index < 0) {
            return null;
        }
        if (index == i) {
            return (T) next.item;
        }
        return (T) helper(index, next.next, i + 1);
    }

}
