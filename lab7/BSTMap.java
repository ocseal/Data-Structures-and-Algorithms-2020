import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private Node root;
    private int size;

    public BSTMap() {
        clear();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private class Node {
        K key;
        V val;
        Node left;
        Node right;
        Node (K k, V v) {
            key = k;
            val = v;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public void put(K key, V val) {
        root = putHelper(root, key, val);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private Node putHelper(Node n, K key, V val) {
        if (n == null) {
            size ++;
            Node node = new Node (key, val);
            return node;
        }
        int i = key.compareTo(n.key);
        if (i > 0) {
            n.right = putHelper(n.right, key, val);
        }
        if (i < 0) {
            n.left = putHelper(n.left, key, val);
        }
        n.val = val;
        return n;
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private V getHelper(Node n, K key) {
        if (n == null) {
            return null;
        } else {
            int i = key.compareTo(n.key);
            if (i > 0) {
                return getHelper(n.right, key);
            }
            if (i < 0) {
                return getHelper(n.left, key);
            }
            return n.val;
        }
     }
}
