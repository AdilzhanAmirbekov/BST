# Assignment 3: Data Structures Implementation

This project implements two major data structures from scratch for the Algorithms and Data Structures (ADS) course:

- **MyHashTable<K, V>**: Custom hash table with chaining for collision handling.
- **BST<K extends Comparable<K>, V>**: Custom Binary Search Tree with standard operations.

## Implementations

### MyHashTable<K, V>
- `put(K key, V value)`: Inserts a key-value pair.
- `get(K key)`: Retrieves value by key.
- `remove(K key)`: Removes a key-value pair.
- `contains(V value)`: Checks if value exists.
- `getKey(V value)`: Retrieves key by value.
- `printBucketSizes()`: Prints the number of elements in each bucket.

### MyTestingClass
- Custom class with manually overridden `hashCode()` method.
- Designed to ensure uniform distribution when inserted into the hash table.

### BST<K extends Comparable<K>, V>
- `put(K key, V value)`: Inserts a key-value pair.
- `get(K key)`: Retrieves value by key.
- `delete(K key)`: Deletes a key-value pair.
- `iterator()`: In-order traversal of the tree.
- Size tracking (`size` field).

### MyStack<E>
- Simple custom stack implementation to support BST in-order traversal.

## Testing

- 10,000 random elements are inserted into `MyHashTable`.
- Distribution across buckets is printed.
- `BST` is tested with several insertions, retrievals, deletions, and iteration.

## Technologies Used

- Java
- No usage of `java.util.*` collections except for `Iterator`.

## How to Run

1. Open `Assignment3.java` in IntelliJ IDEA or any Java IDE.
2. Compile and run the `main()` method.
3. Outputs will show bucket distribution and BST traversal.

## Author
Adil
