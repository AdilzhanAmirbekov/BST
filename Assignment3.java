// Assignment3.java
// Полная реализация MyHashTable, MyTestingClass, BST и Main в одном файле

import java.util.Iterator;

// MyHashTable Implementation
class MyHashTable<K, V> {
    private class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
        chainArray = new HashNode[M];
        size = 0;
    }

    public MyHashTable(int M) {
        this.M = M;
        chainArray = new HashNode[M];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % M;
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> head = chainArray[index];
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }
        size++;
        head = chainArray[index];
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = head;
        chainArray[index] = newNode;
    }

    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> head = chainArray[index];
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> head = chainArray[index];
        HashNode<K, V> prev = null;
        while (head != null) {
            if (head.key.equals(key)) {
                if (prev != null) {
                    prev.next = head.next;
                } else {
                    chainArray[index] = head.next;
                }
                size--;
                return head.value;
            }
            prev = head;
            head = head.next;
        }
        return null;
    }

    public boolean contains(V value) {
        for (HashNode<K, V> head : chainArray) {
            while (head != null) {
                if (head.value.equals(value)) {
                    return true;
                }
                head = head.next;
            }
        }
        return false;
    }

    public K getKey(V value) {
        for (HashNode<K, V> head : chainArray) {
            while (head != null) {
                if (head.value.equals(value)) {
                    return head.key;
                }
                head = head.next;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void printBucketSizes() {
        for (int i = 0; i < M; i++) {
            int count = 0;
            HashNode<K, V> head = chainArray[i];
            while (head != null) {
                count++;
                head = head.next;
            }
            System.out.println("Bucket " + i + ": " + count);
        }
    }
}

// MyTestingClass
class MyTestingClass {
    private int id;

    public MyTestingClass(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id * 31 + 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MyTestingClass that = (MyTestingClass) obj;
        return id == that.id;
    }
}

// BST Implementation
class BST<K extends Comparable<K>, V> implements Iterable<MyMapEntry<K, V>> {
    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;
    private int size;

    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node node, K key, V val) {
        if (node == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(node.left, key, val);
        else if (cmp > 0) node.right = put(node.right, key, val);
        else node.val = val;
        return node;
    }

    public V get(K key) {
        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) return node.val;
            else if (cmp < 0) node = node.left;
            else node = node.right;
        }
        return null;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node temp = min(node.right);
            node.key = temp.key;
            node.val = temp.val;
            node.right = delete(node.right, temp.key);
        }
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public int size() {
        return size;
    }

    public Iterator<MyMapEntry<K, V>> iterator() {
        return new Iterator<MyMapEntry<K, V>>() {
            private MyStack<Node> stack = new MyStack<>();

            {
                pushLeft(root);
            }

            private void pushLeft(Node node) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            public boolean hasNext() {
                return !stack.isEmpty();
            }

            public MyMapEntry<K, V> next() {
                Node node = stack.pop();
                pushLeft(node.right);
                return new MyMapEntry<>(node.key, node.val);
            }
        };
    }
}

// Helper Classes
class MyMapEntry<K, V> {
    private K key;
    private V value;

    public MyMapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

class MyStack<E> {
    private Object[] data = new Object[1000];
    private int size = 0;

    public void push(E item) {
        data[size++] = item;
    }

    public E pop() {
        return (E) data[--size];
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

// Main class
public class Assignment3 {
    public static void main(String[] args) {
        // Test MyHashTable
        MyHashTable<MyTestingClass, Integer> table = new MyHashTable<>();
        for (int i = 0; i < 10000; i++) {
            table.put(new MyTestingClass(i), i);
        }
        table.printBucketSizes();

        // Test BST
        BST<Integer, String> tree = new BST<>();
        tree.put(5, "Five");
        tree.put(3, "Three");
        tree.put(7, "Seven");
        tree.put(2, "Two");
        tree.put(4, "Four");

        for (MyMapEntry<Integer, String> elem : tree) {
            System.out.println("key is " + elem.getKey() + " and value is " + elem.getValue());
        }
    }
}

