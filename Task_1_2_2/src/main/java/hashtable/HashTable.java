package hashtable;

import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class HashTable<K, V> implements Iterable<Entry<K, V>> {
    private static final int START_SIZE = 16;
    private static final float MAX_LOAD_FACTOR = 0.75f;
    private static final float MIN_LOAD_FACTOR = 0.25f;
    private LinkedList<Entry<K, V>>[] buckets = new LinkedList[START_SIZE];
    @Getter
    private int elementCount = 0;
    private int structuralModifications = 0;

    public HashTable() {
        for (int i = 0; i < START_SIZE; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private void resizeArray() {
        int newSize = buckets.length;

        while ((float) elementCount / newSize > MAX_LOAD_FACTOR) {
            newSize *= 2;
        }

        while (newSize > START_SIZE
                && (float) elementCount / newSize < MIN_LOAD_FACTOR) {
            newSize /= 2;
        }

        if (newSize == buckets.length) {
            return;
        }

        LinkedList<Entry<K, V>>[] newBuckets =
                 new LinkedList[newSize];

        for (int i = 0; i < newSize; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                int newBucketIndex = Math.floorMod(entry.getKeyHash(), newSize);
                newBuckets[newBucketIndex].add(entry);
            }
        }

        buckets = newBuckets;
    }

    @NotNull
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

    public V put(@NonNull K key, @NonNull V value) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        V previous = null;
        boolean present = false;
        ListIterator<Entry<K, V>> iterator = buckets[bucket].listIterator();
        while (iterator.hasNext()) {
            Entry<K, V> next = iterator.next();
            if (next.keyEquals(key, keyHash)) {
                iterator.set(new Entry<>(key, value, keyHash));
                present = true;
                previous = next.getValue();
                break;
            }
        }
        if (!present) {
            structuralModifications++;
            buckets[bucket].add(new Entry<>(key, value, keyHash));
            elementCount++;
            resizeArray();
        }
        return previous;
    }

    public V remove(@NonNull K key) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        V value = null;
        ListIterator<Entry<K, V>> iterator = buckets[bucket].listIterator();
        while (iterator.hasNext()) {
            Entry<K, V> next = iterator.next();
            if (next.keyEquals(key, keyHash)) {
                iterator.remove();
                value = next.getValue();
                elementCount--;
                structuralModifications++;
                resizeArray();
                break;
            }
        }
        return value;
    }

    public V get(@NonNull K key) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        V value = null;
        for (Entry<K, V> next : buckets[bucket]) {
            if (next.keyEquals(key, keyHash)) {
                value = next.getValue();
                break;
            }
        }
        return value;
    }

    public boolean containsKey(@NonNull K key) {
        int keyHash = key.hashCode();
        int bucket = Math.floorMod(keyHash, buckets.length);
        boolean contains = false;
        for (Entry<K, V> next : buckets[bucket]) {
            if (next.keyEquals(key, keyHash)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        HashTable<?, ?> unparameterizedTable = (HashTable<?, ?>) obj;
        Iterator<Entry<K, V>> a = iterator();
        Iterator<? extends Entry<?, ?>> b = unparameterizedTable.iterator();
        if (!a.hasNext() && !b.hasNext()) {
            return true;
        }
//        if ((a.hasNext()) != (b.hasNext())) {
//            return false;
//        }
//        Entry<K, V> aEntry = a.next();
//        Entry<?, ?> bEntry = b.next();
//
//        if (aEntry.getKey().getClass() != bEntry.getKey().getClass()
//            || aEntry.getValue().getClass() != bEntry.getValue().getClass()) {
//            return false;
//        }
        HashTable<K, V> parametrizedTable = (HashTable<K, V>) obj;
        if (elementCount != parametrizedTable.getElementCount()) {
            return false;
        }
        for (Entry<K, V> ourEntry : this) {
            if (!parametrizedTable.containsKey(ourEntry.getKey())
                || !Objects.equals(ourEntry.getValue(),
                    parametrizedTable.get(ourEntry.getKey()))) {
                return false;
            }
        }
        for (Entry<K, V> theirEntry : parametrizedTable) {
            if (!containsKey(theirEntry.getKey())
                || !Objects.equals(theirEntry.getValue(), get(theirEntry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (Entry<K, V> entry : this) {
            hash += entry.hashCode();
        }

        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        boolean first = true;
        for (Entry<K, V> entry : this) {
            if (!first) {
                result.append(", ");
            }
            result.append(entry.toString());
            first = false;
        }

        result.append("}");
        return result.toString();
    }
}