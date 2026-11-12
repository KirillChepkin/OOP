package hashtable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HashTableTest {

    @Test
    void newHashTableIsEmpty() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals(0, table.getElementCount());
        assertFalse(table.iterator().hasNext());
    }

    @Test
    void putAddsNewEntry() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.put("apple", 10));

        assertEquals(1, table.getElementCount());
        assertTrue(table.containsKey("apple"));
        assertEquals(10, table.get("apple"));
    }

    @Test
    void putReplacesExistingValue() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        Integer previous = table.put("apple", 20);

        assertEquals(10, previous);
        assertEquals(20, table.get("apple"));
        assertEquals(1, table.getElementCount());
    }

    @Test
    void putWithEqualButDifferentKeyObjectReplacesEntry() {
        HashTable<String, Integer> table = new HashTable<>();

        String firstKey = new String("apple");
        String secondKey = new String("apple");

        table.put(firstKey, 10);
        Integer previous = table.put(secondKey, 20);

        assertEquals(10, previous);
        assertEquals(20, table.get(firstKey));
        assertEquals(1, table.getElementCount());
    }

    @Test
    void putRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class,
                () -> table.put(null, 10));
    }

    @Test
    void putRejectsNullValue() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class,
                () -> table.put("apple", null));
    }

    @Test
    void getReturnsNullForMissingKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.get("missing"));
    }

    @Test
    void getRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class,
                () -> table.get(null));
    }

    @Test
    void containsKeyReturnsTrueForExistingKey() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        assertTrue(table.containsKey("apple"));
    }

    @Test
    void containsKeyReturnsFalseForMissingKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertFalse(table.containsKey("apple"));
    }

    @Test
    void containsKeyRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class,
                () -> table.containsKey(null));
    }

    @Test
    void removeDeletesExistingEntry() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        Integer removed = table.remove("apple");

        assertEquals(10, removed);
        assertEquals(0, table.getElementCount());
        assertFalse(table.containsKey("apple"));
    }

    @Test
    void removeReturnsNullForMissingKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.remove("missing"));
        assertEquals(0, table.getElementCount());
    }

    @Test
    void removeRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class,
                () -> table.remove(null));
    }

    @Test
    void handlesKeysWithIdenticalHashes() {
        HashTable<CollisionKey, Integer> table = new HashTable<>();

        CollisionKey first = new CollisionKey(1);
        CollisionKey second = new CollisionKey(2);
        CollisionKey third = new CollisionKey(3);

        table.put(first, 10);
        table.put(second, 20);
        table.put(third, 30);

        assertEquals(3, table.getElementCount());
        assertEquals(10, table.get(first));
        assertEquals(20, table.get(second));
        assertEquals(30, table.get(third));
    }

    @Test
    void removesCorrectEntryAmongCollisions() {
        HashTable<CollisionKey, Integer> table = new HashTable<>();

        CollisionKey first = new CollisionKey(1);
        CollisionKey second = new CollisionKey(2);
        CollisionKey third = new CollisionKey(3);

        table.put(first, 10);
        table.put(second, 20);
        table.put(third, 30);

        assertEquals(20, table.remove(second));

        assertEquals(2, table.getElementCount());
        assertTrue(table.containsKey(first));
        assertFalse(table.containsKey(second));
        assertTrue(table.containsKey(third));
    }

    @Test
    void handlesNegativeHashCodes() {
        HashTable<Integer, String> table = new HashTable<>();

        table.put(-1, "minus one");
        table.put(Integer.MIN_VALUE, "minimum");
        table.put(-100, "minus one hundred");

        assertEquals("minus one", table.get(-1));
        assertEquals("minimum", table.get(Integer.MIN_VALUE));
        assertEquals("minus one hundred", table.get(-100));
    }

    @Test
    void resizesWhenManyEntriesAreInserted() {
        HashTable<Integer, Integer> table = new HashTable<>();

        for (int i = 0; i < 1000; i++) {
            table.put(i, i * 10);
        }

        assertEquals(1000, table.getElementCount());

        for (int i = 0; i < 1000; i++) {
            assertEquals(i * 10, table.get(i));
        }
    }

    @Test
    void resizesAndPreservesEntriesAfterManyRemovals() {
        HashTable<Integer, Integer> table = new HashTable<>();

        for (int i = 0; i < 1000; i++) {
            table.put(i, i * 10);
        }

        for (int i = 0; i < 990; i++) {
            assertEquals(i * 10, table.remove(i));
        }

        assertEquals(10, table.getElementCount());

        for (int i = 990; i < 1000; i++) {
            assertEquals(i * 10, table.get(i));
        }
    }

    @Test
    void iteratorVisitsEveryEntryExactlyOnce() {
        HashTable<Integer, Integer> table = new HashTable<>();

        for (int i = 0; i < 100; i++) {
            table.put(i, i * 10);
        }

        Set<Integer> visitedKeys = new HashSet<>();

        for (Entry<Integer, Integer> entry : table) {
            assertTrue(visitedKeys.add(entry.getKey()));
        }

        assertEquals(100, visitedKeys.size());
    }

    @Test
    void iteratorReturnsCorrectEntries() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);
        table.put("banana", 20);

        Set<Entry<String, Integer>> entries = new HashSet<>();

        for (Entry<String, Integer> entry : table) {
            entries.add(entry);
        }

        assertEquals(Set.of(
                new Entry<>("apple", 10, "apple".hashCode()),
                new Entry<>("banana", 20, "banana".hashCode())
        ), entries);
    }

    @Test
    void iteratorNextThrowsWhenTableIsEmpty() {
        HashTable<String, Integer> table = new HashTable<>();

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorNextThrowsAfterAllEntriesHaveBeenVisited() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorDetectsInsertion() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.put("banana", 20);

        assertThrows(ConcurrentModificationException.class,
                iterator::hasNext);
    }

    @Test
    void iteratorDetectsRemoval() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);
        table.put("banana", 20);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.remove("apple");

        assertThrows(ConcurrentModificationException.class,
                iterator::next);
    }

    @Test
    void iteratorDoesNotDetectValueReplacement() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.put("apple", 20);

        assertDoesNotThrow(iterator::hasNext);
        assertEquals(20, iterator.next().getValue());
    }

    @Test
    void iteratorDoesNotDetectRemovalOfMissingKey() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.remove("missing");

        assertDoesNotThrow(iterator::hasNext);
    }

    @Test
    void equalsReturnsTrueForSameObject() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        assertEquals(table, table);
    }

    @Test
    void equalsReturnsFalseForNull() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNotEquals(null, table);
    }

    @Test
    void equalsReturnsFalseForDifferentClass() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNotEquals("{}", table);
    }

    @Test
    void emptyHashTablesAreEqual() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();

        assertEquals(first, second);
        assertEquals(second, first);
    }

    @Test
    void hashTablesWithSameMappingsAreEqual() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();

        first.put("apple", 10);
        first.put("banana", 20);

        second.put("banana", 20);
        second.put("apple", 10);

        assertEquals(first, second);
        assertEquals(second, first);
    }

    @Test
    void hashTablesWithDifferentSizesAreNotEqual() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();

        first.put("apple", 10);
        second.put("apple", 10);
        second.put("banana", 20);

        assertNotEquals(first, second);
    }

    @Test
    void hashTablesWithDifferentKeysAreNotEqual() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();

        first.put("apple", 10);
        second.put("orange", 10);

        assertNotEquals(first, second);
    }

    @Test
    void hashTablesWithDifferentValuesAreNotEqual() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();

        first.put("apple", 10);
        second.put("apple", 20);

        assertNotEquals(first, second);
    }

    @Test
    void equalHashTablesHaveEqualHashCodes() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();

        first.put("apple", 10);
        first.put("banana", 20);

        second.put("banana", 20);
        second.put("apple", 10);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void hashCodeIsZeroForEmptyTable() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals(0, table.hashCode());
    }

    @Test
    void toStringReturnsEmptyBracesForEmptyTable() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals("{}", table.toString());
    }

    @Test
    void toStringReturnsEntryRepresentation() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 10);

        assertEquals("{apple=10}", table.toString());
    }

    @Test
    void equalsHandlesDifferentValueTypesInCollisionBucket() {
        HashTable<CollisionKey, Object> first = new HashTable<>();
        HashTable<CollisionKey, Object> second = new HashTable<>();

        CollisionKey firstKey = new CollisionKey(1);
        CollisionKey secondKey = new CollisionKey(2);

        first.put(firstKey, 10);
        first.put(secondKey, "hello");

        second.put(secondKey, "hello");
        second.put(firstKey, 10);

        assertEquals(first, second);
        assertEquals(second, first);
    }

    private static class CollisionKey {
        private final int id;

        CollisionKey(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }

            CollisionKey other = (CollisionKey) obj;
            return id == other.id;
        }
    }
}
