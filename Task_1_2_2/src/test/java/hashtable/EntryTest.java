package hashtable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    @Test
    void constructorStoresKeyAndValue() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, "apple".hashCode());

        assertEquals("apple", entry.getKey());
        assertEquals(10, entry.getValue());
        assertEquals("apple".hashCode(), entry.getKeyHash());
    }

    @Test
    void constructorRejectsNullKey() {
        assertThrows(NullPointerException.class,
                () -> new Entry<>(null, 10, 0));
    }

    @Test
    void constructorRejectsNullValue() {
        assertThrows(NullPointerException.class,
                () -> new Entry<>("apple", null, 0));
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
        assertEquals(second, first);
    }

    @Test
    void equalsReturnsFalseForDifferentKeys() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("orange", 10, 456);

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

        assertNotEquals("apple=10", entry);
    }

    @Test
    void hashCodeIsEqualForEqualEntries() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("apple", 10, 123);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void hashCodeDoesNotDependOnCachedKeyHash() {
        Entry<String, Integer> first = new Entry<>("apple", 10, 123);
        Entry<String, Integer> second = new Entry<>("apple", 10, 456);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void keyEqualsReturnsTrueForEqualKeysAndHashes() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertTrue(entry.keyEquals("apple", 123));
    }

    @Test
    void keyEqualsReturnsFalseForDifferentKeys() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertFalse(entry.keyEquals("orange", 123));
    }

    @Test
    void keyEqualsReturnsFalseForDifferentHashes() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertFalse(entry.keyEquals("apple", 456));
    }

    @Test
    void keyEqualsRejectsNullKey() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertThrows(NullPointerException.class,
                () -> entry.keyEquals(null, 123));
    }

    @Test
    void toStringReturnsKeyAndValue() {
        Entry<String, Integer> entry = new Entry<>("apple", 10, 123);

        assertEquals("apple=10", entry.toString());
    }
}
