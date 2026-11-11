
package hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    @Test
    void constructorInitializesFields() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertEquals("apple", entry.getKey());
        assertEquals(10, entry.getValue());
        assertEquals(123, entry.getKeyHash());
    }

    @Test
    void equalsReturnsTrueForSameObject() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertEquals(entry, entry);
    }

    @Test
    void equalsReturnsTrueForEqualEntries() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("apple", 10, 123);

        assertEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForDifferentKeys() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("banana", 10, 123);

        assertNotEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForDifferentValues() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("apple", 20, 123);

        assertNotEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForNull() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertNotEquals(null, entry);
    }

    @Test
    void equalsReturnsFalseForDifferentClass() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertNotEquals("apple", entry);
    }

    @Test
    void equalsHandlesNullValues() {
        Entry<String, Integer> first = new Entry<>("apple", null, 123);
        Entry<String, Integer> second = new Entry<>("apple", null, 123);

        assertEquals(first, second);
    }

//    @Test
//    void equalsHandlesNullKeys() {
//        Entry<String, Integer> first = new Entry<>(null, 10, 123);
//        Entry<String, Integer> second = new Entry<>(null, 10, 123);
//
//        assertEquals(first, second);
//    }

    @Test
    void equalsDoesNotDependOnCachedHashCode() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 456);
        Entry<String, Integer> second = new Entry<>("apple", 10, 456);

        assertEquals(first, second);
    }

    @Test
    void hashCodeIsEqualForEqualEntries() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("apple", 10, 123);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void hashCodeHandlesNullValues() {
        Entry<String, Integer> first = new Entry<>("apple", null, 123);
        Entry<String, Integer> second = new Entry<>("apple", null, 123);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void keyEqualsReturnsTrueForEqualKeysAndHashes() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("apple", 20, 123);

        assertTrue(first.keyEquals(second));
    }

    @Test
    void keyEqualsReturnsFalseForDifferentKeys() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("banana", 10, 123);

        assertFalse(first.keyEquals(second));
    }

    @Test
    void keyEqualsReturnsFalseForDifferentHashes() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("apple", 10, 456);

        assertFalse(first.keyEquals(second));
    }
}
