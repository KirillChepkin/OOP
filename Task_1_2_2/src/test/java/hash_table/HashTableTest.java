
package hash_table;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    private static class CollisionKey {
        private final int id;

        CollisionKey(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof CollisionKey other)) {
                return false;
            }

            return id == other.id;
        }

        @Override
        public int hashCode() {
            return 42;
        }
    }

    private static class NegativeHashKey {
        private final int id;

        NegativeHashKey(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof NegativeHashKey other)) {
                return false;
            }

            return id == other.id;
        }

        @Override
        public int hashCode() {
            return -12345;
        }
    }

    // --------------------------------------------------
    // put()
    // --------------------------------------------------

    @Test
    void putInsertsNewEntry() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.put("apple", 10));

        assertTrue(table.containsKey("apple"));
        assertEquals(10, table.get("apple"));
    }

    @Test
    void putReturnsPreviousValueWhenUpdatingEntry() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        assertEquals(10, table.put("apple", 20));
        assertEquals(20, table.get("apple"));
    }

    @Test
    void putDoesNotInsertDuplicateKeys() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);
        table.put("apple", 20);

        int count = 0;

        for (Entry<String, Integer> entry : table) {
            if (entry.getKey().equals("apple")) {
                count++;
            }
        }

        assertEquals(1, count);
    }

    @Test
    void putSupportsNullValues() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", null);

        assertTrue(table.containsKey("apple"));
        assertNull(table.get("apple"));
    }

    @Test
    void putUpdatesEntryWithNullValue() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", null);

        assertNull(table.put("apple", 20));

        assertTrue(table.containsKey("apple"));
        assertEquals(20, table.get("apple"));

        int count = 0;

        for (Entry<String, Integer> entry : table) {
            if (entry.getKey().equals("apple")) {
                count++;
            }
        }

        assertEquals(1, count);
    }

    // --------------------------------------------------
    // get()
    // --------------------------------------------------

    @Test
    void getReturnsValueForExistingKey() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        assertEquals(10, table.get("apple"));
    }

    @Test
    void getReturnsNullForNonexistentKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.get("apple"));
    }

    @Test
    void getReturnsNullForKeyWithNullValue() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", null);

        assertNull(table.get("apple"));
        assertTrue(table.containsKey("apple"));
    }

    // --------------------------------------------------
    // containsKey()
    // --------------------------------------------------

    @Test
    void containsKeyReturnsTrueForExistingKey() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        assertTrue(table.containsKey("apple"));
    }

    @Test
    void containsKeyReturnsFalseForNonexistentKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertFalse(table.containsKey("apple"));
    }

    @Test
    void containsKeyWorksForNullValues() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", null);

        assertTrue(table.containsKey("apple"));
        assertFalse(table.containsKey("banana"));
    }

    // --------------------------------------------------
    // remove()
    // --------------------------------------------------

    @Test
    void removeDeletesExistingEntry() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        assertEquals(10, table.remove("apple"));

        assertFalse(table.containsKey("apple"));
        assertNull(table.get("apple"));
    }

    @Test
    void removeReturnsNullForNonexistentKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.remove("apple"));
    }

    @Test
    void removeHandlesEntryWithNullValue() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", null);

        assertNull(table.remove("apple"));
        assertFalse(table.containsKey("apple"));
    }

    @Test
    void removeDoesNotDeleteOtherEntries() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);
        table.put("banana", 20);

        table.remove("apple");

        assertFalse(table.containsKey("apple"));
        assertTrue(table.containsKey("banana"));
        assertEquals(20, table.get("banana"));
    }

    // --------------------------------------------------
    // Collision handling
    // --------------------------------------------------

    @Test
    void handlesDifferentKeysWithIdenticalHashCodes() {
        HashTable<CollisionKey, String> table = new HashTable<>();

        CollisionKey first = new CollisionKey(1);
        CollisionKey second = new CollisionKey(2);
        CollisionKey third = new CollisionKey(3);

        table.put(first, "first");
        table.put(second, "second");
        table.put(third, "third");

        assertEquals("first", table.get(first));
        assertEquals("second", table.get(second));
        assertEquals("third", table.get(third));
    }

    @Test
    void updatesCorrectEntryWhenKeysHaveIdenticalHashCodes() {
        HashTable<CollisionKey, String> table = new HashTable<>();

        CollisionKey first = new CollisionKey(1);
        CollisionKey second = new CollisionKey(2);

        table.put(first, "first");
        table.put(second, "second");

        assertEquals("first", table.put(first, "updated"));

        assertEquals("updated", table.get(first));
        assertEquals("second", table.get(second));
    }

    @Test
    void removesCorrectEntryWhenKeysHaveIdenticalHashCodes() {
        HashTable<CollisionKey, String> table = new HashTable<>();

        CollisionKey first = new CollisionKey(1);
        CollisionKey second = new CollisionKey(2);

        table.put(first, "first");
        table.put(second, "second");

        assertEquals("first", table.remove(first));

        assertFalse(table.containsKey(first));
        assertTrue(table.containsKey(second));
        assertEquals("second", table.get(second));
    }

    // --------------------------------------------------
    // Negative hash codes
    // --------------------------------------------------

    @Test
    void handlesNegativeHashCodes() {
        HashTable<NegativeHashKey, String> table = new HashTable<>();

        NegativeHashKey key = new NegativeHashKey(1);

        table.put(key, "value");

        assertTrue(table.containsKey(key));
        assertEquals("value", table.get(key));
    }

    @Test
    void removesEntryWithNegativeHashCode() {
        HashTable<NegativeHashKey, String> table = new HashTable<>();

        NegativeHashKey key = new NegativeHashKey(1);

        table.put(key, "value");

        assertEquals("value", table.remove(key));
        assertFalse(table.containsKey(key));
    }

    // --------------------------------------------------
    // Iterator
    // --------------------------------------------------

    @Test
    void iteratorTraversesAllEntries() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);
        table.put("banana", 20);
        table.put("orange", 30);

        Set<Entry<String, Integer>> entries = new HashSet<>();

        for (Entry<String, Integer> entry : table) {
            entries.add(entry);
        }

        assertEquals(3, entries.size());
        assertTrue(entries.contains(new Entry<>("apple", 10, "apple".hashCode())));
        assertTrue(entries.contains(new Entry<>("banana", 20, "banana".hashCode())));
        assertTrue(entries.contains(new Entry<>("orange", 30, "orange".hashCode())));
    }

    @Test
    void iteratorWorksOnEmptyTable() {
        HashTable<String, Integer> table = new HashTable<>();

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorThrowsNoSuchElementExceptionWhenExhausted() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        assertNotNull(iterator.next());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorSkipsEmptyBuckets() {
        HashTable<Integer, String> table = new HashTable<>();

        table.put(0, "zero");
        table.put(5, "five");
        table.put(15, "fifteen");

        int count = 0;

        for (Entry<Integer, String> entry : table) {
            assertTrue(entry.getKey() == 0
                    || entry.getKey() == 5
                    || entry.getKey() == 15);
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    void iteratorDetectsInsertionAfterCreation() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.put("banana", 20);

        assertThrows(
                ConcurrentModificationException.class,
                iterator::hasNext
        );
    }

    @Test
    void iteratorDetectsRemovalAfterCreation() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.remove("apple");

        assertThrows(
                ConcurrentModificationException.class,
                iterator::hasNext
        );
    }

    @Test
    void iteratorDoesNotDetectValueUpdate() {
        HashTable<Integer, String> table = new HashTable<>();

        table.put(0, "old");

        Iterator<Entry<Integer, String>> iterator = table.iterator();

        table.put(0, "new");

        assertDoesNotThrow(iterator::hasNext);

        Entry<Integer, String> entry = iterator.next();

        assertEquals(0, entry.getKey());
        assertEquals("new", entry.getValue());
    }

    @Test
    void iteratorDoesNotDetectRemovalOfNonexistentKey() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("apple", 10);

        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.remove("banana");

        assertDoesNotThrow(iterator::hasNext);
    }

    @Test
    void iteratorDoesNotDetectUpdatingWithNullValue() {
        HashTable<Integer, String> table = new HashTable<>();

        table.put(0, "old");

        Iterator<Entry<Integer, String>> iterator = table.iterator();

        table.put(0, null);

        assertDoesNotThrow(iterator::hasNext);
        assertNull(iterator.next().getValue());
    }
}
