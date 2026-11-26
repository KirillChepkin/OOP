import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.NoSuchElementException;

import lombok.Getter;

/**
 * Searches for occurrences of a substring in an input stream using the KMP algorithm.
 * The search is performed in chunks, so the entire input does not need to be loaded into memory.
 */
public class SubstringFinder implements Iterable<Long>, Iterator<Long> {

    @Getter
    private final int chunkSize = 8192;
    private final byte[] fileChunk = new byte[chunkSize];

    private InputStream stream;
    private byte[] pattern;
    private int[] failureFunction;

    private int occurrencePrefixLength;
    private int bufferPosition;
    private int bytesObtained;

    private long bytePosition;
    private long nextOccurrence;

    private boolean nextReady;
    private boolean exhausted;
    private boolean initialized;

    /**
     * Initializes the search and finds the first occurrence of the specified substring.
     *
     * @param stream input stream to search
     * @param substring substring to find
     * @return true if at least one occurrence was found, false otherwise
     * @throws IOException if an I/O error occurs while reading the stream
     */
    public boolean findFirstOccurrence(InputStream stream, String substring)
            throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("InputStream must not be null");
        }
        if (substring == null || substring.isEmpty()) {
            throw new IllegalArgumentException("Substring must not be empty");
        }

        this.stream = stream;
        pattern = substring.getBytes(StandardCharsets.UTF_8);
        failureFunction = calculateFailureFunction();

        occurrencePrefixLength = 0;
        bufferPosition = 0;
        bytesObtained = 0;
        bytePosition = 0;

        nextReady = false;
        exhausted = false;
        initialized = true;

        return advanceToNextOccurrence();
    }

    private int[] calculateFailureFunction() {
        int[] failure = new int[pattern.length];

        for (int i = 1; i < pattern.length; i++) {
            int k = failure[i - 1];

            while (k > 0 && pattern[k] != pattern[i]) {
                k = failure[k - 1];
            }

            if (pattern[k] == pattern[i]) {
                k++;
            }

            failure[i] = k;
        }

        return failure;
    }

    private boolean advanceToNextOccurrence() throws IOException {
        nextReady = false;

        while (true) {
            if (bufferPosition >= bytesObtained) {
                bytesObtained = stream.read(fileChunk);
                bufferPosition = 0;

                if (bytesObtained == -1) {
                    exhausted = true;
                    return false;
                }

                if (bytesObtained == 0) {
                    continue;
                }
            }

            byte currentByte = fileChunk[bufferPosition++];

            while (occurrencePrefixLength > 0
                    && currentByte != pattern[occurrencePrefixLength]) {
                occurrencePrefixLength =
                        failureFunction[occurrencePrefixLength - 1];
            }

            if (currentByte == pattern[occurrencePrefixLength]) {
                occurrencePrefixLength++;
            }

            long currentPosition = bytePosition++;

            if (occurrencePrefixLength == pattern.length) {
                nextOccurrence = currentPosition - pattern.length + 1;
                occurrencePrefixLength =
                        failureFunction[occurrencePrefixLength - 1];
                nextReady = true;
                return true;
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (!initialized) {
            throw new IllegalStateException(
                    "Call findFirstOccurrence() before iterating"
            );
        }

        if (nextReady) {
            return true;
        }

        if (exhausted) {
            return false;
        }

        try {
            return advanceToNextOccurrence();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Long next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        nextReady = false;
        return nextOccurrence;
    }

    @Override
    public Iterator<Long> iterator() {
        if (!initialized) {
            throw new IllegalStateException(
                    "Call findFirstOccurrence() before iterating"
            );
        }

        return this;
    }
}