public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private int startSize = 8;
    private T[] list;
    public ArrayDeque() {
        list = (T[]) new Object[startSize];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private int add(int i,  int length) {
        return Math.floorMod(i + 1, length);
    }

    private int sub(int i, int length) {
        return Math.floorMod(i - 1, length);
    }

    private void upsize(boolean addFront) {
        T[] oldArrDeque = list;
        int oldNextFirst = nextFirst;
        int oldLength = oldArrDeque.length;
        list = (T[]) new Object[size() * 2];
        int back = sub(nextLast, oldLength);
        int front = add(nextFirst + oldLength, oldLength);
        int newIndex = sub(nextLast, oldLength);
        while (back != front) {
            list[newIndex] = oldArrDeque[back];
            newIndex = sub(newIndex, list.length);
            back = sub(back, oldLength);
        }
        list[newIndex] = oldArrDeque[back];
        if (nextLast == 0 && nextFirst == oldLength - 1) {
            nextFirst = list.length - 1;
            nextLast = oldLength;
        } else if (nextFirst == 0 && nextLast == oldLength - 1) {
            nextFirst = oldLength;
            nextLast = list.length - 1;
        } else {
            nextLast = add(nextLast - 1, oldLength);
            nextFirst = sub(nextFirst - oldLength + 1, list.length);
        }
    }

    private void downsize() {
        T[] oldArrDeque = list;
        int oldLength = oldArrDeque.length;
        list = (T[]) new Object[oldLength / 2];
        int back = sub(nextLast, oldLength);
        int newIndex = Math.floorDiv(back, 2);
        int front = add(nextFirst, oldLength);
        while (back != front) {
            if (oldArrDeque[back] != null) {
                list[newIndex] = oldArrDeque[back];
            }
            newIndex = sub(newIndex, list.length);
            back = sub(back, oldLength);
        }
        if (oldArrDeque[back] != null) {
            list[newIndex] = oldArrDeque[front];
        }
        nextFirst = sub(newIndex, list.length);
        nextLast = (int) Math.ceil((double) nextLast / 2);
    }

    @Override
    public void addFirst(T item) {
        list[nextFirst] = item;
        nextFirst = sub(nextFirst, list.length);
        size++;
        if (size == list.length) {
            upsize(true);
        }
    }

    @Override
    public void addLast(T item) {
        list[nextLast] = item;
        nextLast = add(nextLast, list.length);
        size++;
        if (size == list.length) {
            upsize(false);
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            nextFirst = add(nextFirst, list.length);
            T removed = list[nextFirst];
            list[nextFirst] = null;
            size--;
            if (size >= 8 && size <= (list.length / 4)) {
                downsize();
            }
            return removed;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            nextLast = sub(nextLast, list.length);
            T removed = list[nextLast];
            list[nextLast] = null;
            size--;
            if (size >= 8 && size <= (list.length / 4)) {
                downsize();
            }
            return removed;
        }
    }

    @Override
    public int size() {
        return Math.max(size, 0);
    }

    @Override
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("null");
        } else {
            int i = nextFirst + 1;
            while (i < list.length) {
                System.out.println(list[i]);
                i++;
            }
            i = 0;
            while (i <= nextLast - 1) {
                System.out.println(list[i]);
                i++;
            }
        }
    }

    @Override
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        if (index >= list.length) {
            return null;
        }
        if (index < 0) {
            return null;
        }
        int pos = nextFirst + index + 1;
        if (pos >= list.length) {
            pos -= list.length;
        }
        return list[pos];
    }
}

