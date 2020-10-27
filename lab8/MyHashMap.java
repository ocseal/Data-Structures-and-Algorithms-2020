import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V>{
    private ArrayList<Node>[] AList;
    private int size;
    private HashSet<K> keys;
    private double lf;

    private static final int DEFAULT_SIZE = 16;
    private static final double DEFAULT_LF = 0.75;

    private class Node {
        K key;
        V value;

        Node(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

    public MyHashMap(int initSize, double maxLoad) {
        AList = (ArrayList<Node>[]) new ArrayList[initSize];
        size = 0;
        lf = maxLoad;
        keys = new HashSet<K>();
    }

    public MyHashMap(int initSize) {
        this(initSize, DEFAULT_LF);
    }

    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_LF);
    }

    @Override
    public void clear() {
        AList = (ArrayList<Node>[]) new ArrayList[DEFAULT_SIZE];
        size = 0;
        keys = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        return keySet().contains(key);
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }

        if (((double)size / AList.length) > lf) {
            rebucket(AList.length * 2);
        }

        size += 1;
        keys.add(key);

        int index = findBucket(key, AList.length);
        ArrayList<Node> BList = AList[index];
        if (BList == null) {
            BList = new ArrayList<>();
            AList[index] = BList;
            Node newNode = new Node(key, value);
            BList.add(newNode);
        } else {
            Node newNode = new Node(key, value);
            BList.add(newNode);
        }
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    private int findBucket (K key, int numBuckets) {
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private int findBucket (K key) {
        return findBucket(key, AList.length);
    }

    private Node getNode(K key) {
        int i = findBucket(key);
        ArrayList<Node> BList = AList[i];
        if (BList != null) {
            for (Node node : BList) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    private void rebucket (int size) {
        ArrayList<Node>[] NList = (ArrayList<Node>[]) new ArrayList[size];
        for (K key : keys) {
            int i = findBucket(key, NList.length);
            if (NList[i] == null) {
                NList[i] = new ArrayList<>();
            }
            NList[i].add(getNode(key));
        }
        AList = NList;
    }
}
