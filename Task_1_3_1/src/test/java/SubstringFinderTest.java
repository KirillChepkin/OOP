import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SubstringFinderTest {

    @Test
    void findsFirstOccurrence() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("XXABCYY");

        assertTrue(finder.findFirstOccurrence(stream, "ABC"));
        assertEquals(2L, finder.next());
        assertFalse(finder.hasNext());
    }

    @Test
    void returnsFalseWhenSubstringIsAbsent() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("ABCDEFG");

        assertFalse(finder.findFirstOccurrence(stream, "XYZ"));
        assertFalse(finder.hasNext());
    }

    @Test
    void returnsFalseForEmptyInputStream() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("");

        assertFalse(finder.findFirstOccurrence(stream, "ABC"));
        assertFalse(finder.hasNext());
    }

    @Test
    void findsMultipleOccurrences() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("ABCXXABCYYABC");

        assertTrue(finder.findFirstOccurrence(stream, "ABC"));

        assertEquals(
                List.of(0L, 5L, 10L),
                collectOccurrences(finder)
        );
    }

    @Test
    void findsOverlappingOccurrences() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("AAAAA");

        assertTrue(finder.findFirstOccurrence(stream, "AAA"));

        assertEquals(
                List.of(0L, 1L, 2L),
                collectOccurrences(finder)
        );
    }

    @Test
    void findsOverlappingOccurrencesWithPartialPatternFallback()
            throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("ABABAB");

        assertTrue(finder.findFirstOccurrence(stream, "ABAB"));

        assertEquals(
                List.of(0L, 2L),
                collectOccurrences(finder)
        );
    }

    @Test
    void findsOccurrenceAcrossChunkBoundary() throws IOException {
        SubstringFinder finder = new SubstringFinder();

        // The first two pattern bytes are in the first chunk.
        // The last byte is in the second chunk.
        byte[] data = new byte[8195];
        java.util.Arrays.fill(data, (byte) 'X');

        data[8190] = 'A';
        data[8191] = 'B';
        data[8192] = 'C';

        ByteArrayInputStream stream = new ByteArrayInputStream(data);

        assertTrue(finder.findFirstOccurrence(stream, "ABC"));

        assertEquals(8190L, finder.next());
        assertFalse(finder.hasNext());
    }

    @Test
    void findsMultipleOccurrencesAcrossChunkBoundary()
            throws IOException {
        SubstringFinder finder = new SubstringFinder();

        byte[] data = new byte[8197];
        java.util.Arrays.fill(data, (byte) 'X');

        data[8190] = 'A';
        data[8191] = 'B';
        data[8192] = 'C';

        data[8194] = 'A';
        data[8195] = 'B';
        data[8196] = 'C';

        ByteArrayInputStream stream = new ByteArrayInputStream(data);

        assertTrue(finder.findFirstOccurrence(stream, "ABC"));

        assertEquals(
                List.of(8190L, 8194L),
                collectOccurrences(finder)
        );
    }

    @Test
    void returnsUtf8BytePositions() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("абвёжгде");

        assertTrue(finder.findFirstOccurrence(stream, "ёж"));

        // The three preceding Cyrillic characters occupy 6 UTF-8 bytes.
        assertEquals(6L, finder.next());
        assertFalse(finder.hasNext());
    }

    @Test
    void hasNextDoesNotSkipCachedOccurrence() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("ABCABC");

        assertTrue(finder.findFirstOccurrence(stream, "ABC"));

        assertTrue(finder.hasNext());
        assertTrue(finder.hasNext());

        assertEquals(0L, finder.next());
        assertEquals(3L, finder.next());

        assertFalse(finder.hasNext());
    }

    @Test
    void nextThrowsWhenNoOccurrencesRemain() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("ABC");

        assertTrue(finder.findFirstOccurrence(stream, "ABC"));
        assertEquals(0L, finder.next());

        assertThrows(NoSuchElementException.class, finder::next);
    }

    @Test
    void iteratorCanBeUsedInForEachLoop() throws IOException {
        SubstringFinder finder = new SubstringFinder();
        ByteArrayInputStream stream = inputStream("ABCXXABC");

        assertTrue(finder.findFirstOccurrence(stream, "ABC"));

        List<Long> actual = new ArrayList<>();
        for (Long position : finder) {
            actual.add(position);
        }

        assertEquals(List.of(0L, 5L), actual);
    }

    @Test
    void canStartAnotherSearchOnSameFinder() throws IOException {
        SubstringFinder finder = new SubstringFinder();

        assertTrue(finder.findFirstOccurrence(
                inputStream("ABCABC"), "ABC"
        ));

        assertEquals(0L, finder.next());
        assertEquals(3L, finder.next());
        assertFalse(finder.hasNext());

        assertTrue(finder.findFirstOccurrence(
                inputStream("XXXYZ"), "XYZ"
        ));

        assertEquals(2L, finder.next());
        assertFalse(finder.hasNext());
    }

    @Test
    void rejectsNullStream() {
        SubstringFinder finder = new SubstringFinder();

        assertThrows(
                IllegalArgumentException.class,
                () -> finder.findFirstOccurrence(null, "ABC")
        );
    }

    @Test
    void rejectsNullSubstring() {
        SubstringFinder finder = new SubstringFinder();

        assertThrows(
                IllegalArgumentException.class,
                () -> finder.findFirstOccurrence(inputStream("ABC"), null)
        );
    }

    @Test
    void rejectsEmptySubstring() {
        SubstringFinder finder = new SubstringFinder();

        assertThrows(
                IllegalArgumentException.class,
                () -> finder.findFirstOccurrence(inputStream("ABC"), "")
        );
    }

    @Test
    void rejectsIteratorUsageBeforeInitialization() {
        SubstringFinder finder = new SubstringFinder();

        assertThrows(IllegalStateException.class, finder::hasNext);
        assertThrows(IllegalStateException.class, finder::iterator);
    }

    private static ByteArrayInputStream inputStream(String content) {
        return new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8)
        );
    }

    private static List<Long> collectOccurrences(Iterator<Long> iterator) {
        List<Long> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }
}