package hash_table;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HashTable<K, V> implements Iterable<Entry<K, V>> {
    private static final int startSize = 16;
    private LinkedList<Entry<K, V>>[] buckets = new LinkedList[startSize];
    private int elementCount = 0;
    private int structuralModifications = 0;

    public HashTable() {
        for (int i = 0; i < startSize; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

//    private void resizeArray() {
//
//        int newSize = buckets.length * 2;
//        LinkedList<Entry<K, V>>[] newBuckets = Arrays.copyOf(buckets, newSize);
//
//    }

    @NotNull()
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<Entry<K, V>> {
        private final int modifications = structuralModifications;
        private int currentBucketIndex = 0;
        private Iterator<Entry<K, V>> currentBucketIterator = buckets[0].iterator();

        private void checkStructuralChanges() {
            if (modifications != structuralModifications) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            checkStructuralChanges();
            while (!currentBucketIterator.hasNext() && currentBucketIndex < buckets.length - 1) {
                currentBucketIndex++;
                currentBucketIterator = buckets[currentBucketIndex].iterator();
            }
            return currentBucketIterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return currentBucketIterator.next();
        }
    }

    public V put(K key, V value) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        V previous = null;
        boolean present = false;
        Entry<K, V> newEntry = new Entry<>(key, value, keyHash);
        ListIterator<Entry<K, V>> iterator = buckets[bucket].listIterator();
        while (iterator.hasNext()) {
            Entry<K, V> next = iterator.next();
            if (next.keyEquals((newEntry))) {
                iterator.set(newEntry);
                present = true;
                previous = next.getValue();
                break;
            }
        }
        if (!present) {
            structuralModifications++;
            buckets[bucket].add(newEntry);
            elementCount++;
        }
        return previous;
    }

    public V remove(K key) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        V value = null;
        Entry<K, V> newEntry = new Entry<>(key, null, keyHash);
        ListIterator<Entry<K, V>> iterator = buckets[bucket].listIterator();
        while (iterator.hasNext()) {
            Entry<K, V> next = iterator.next();
            if (next.keyEquals((newEntry))) {
                iterator.remove();
                value = next.getValue();
                elementCount--;
                structuralModifications++;
                break;
            }
        }
        return value;
    }

    public V get(K key) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        V value = null;
        Entry<K, V> newEntry = new Entry<>(key, null, keyHash);
        for (Entry<K, V> next : buckets[bucket]) {
            if (next.keyEquals((newEntry))) {
                value = next.getValue();
                break;
            }
        }
        return value;
    }

    public boolean containsKey(K key) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        boolean contains = false;
        Entry<K, V> newEntry = new Entry<>(key, null, keyHash);
        for (Entry<K, V> next : buckets[bucket]) {
            if (next.keyEquals((newEntry))) {
                contains = true;
                break;
            }
        }
        return contains;
    }

//    @Override
//    public boolean equals(Object map) {
//
//    }
//
//    @Override
//    public String toString() {
//
//    }
}