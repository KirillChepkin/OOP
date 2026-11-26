
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {
    @Getter
    private final int chunkSize = 8192;

    private final byte[] fileChunk = new byte[chunkSize];
    private final List<Long> byteIndices = new ArrayList<>();

    private byte[] subarray = {};
    private int[] failureFunction;
    private int occurrencePrefixLength = 0;
    private long chunkStart = 0;
    private int bytesObtained;

    public List<Long> findOccurrences(InputStream stream, String substring)
            throws IOException {

        if (substring == null || substring.isEmpty()) {
            throw new IllegalArgumentException(
                    "Substring must not be empty"
            );
        }

        subarray = substring.getBytes(StandardCharsets.UTF_8);

        // Reset search state.
        occurrencePrefixLength = 0;
        chunkStart = 0;
        byteIndices.clear();

        bytesObtained = stream.read(fileChunk);

        if (bytesObtained == -1) {
            return new ArrayList<>();
        }

        searchSubarray(stream);

        return new ArrayList<>(byteIndices);
    }

    private void searchSubarray(InputStream stream) throws IOException {
        precomputeFailureFunction();

        do {
            searchInChunk();
            chunkStart += bytesObtained;
            bytesObtained = stream.read(fileChunk);
        } while (bytesObtained != -1);
    }

    private void precomputeFailureFunction() {
        failureFunction = new int[subarray.length];

        for (int i = 1; i < subarray.length; i++) {
            int k = failureFunction[i - 1];

            while (k > 0 && subarray[k] != subarray[i]) {
                k = failureFunction[k - 1];
            }

            if (subarray[k] == subarray[i]) {
                k++;
            }

            failureFunction[i] = k;
        }
    }

    private void searchInChunk() {
        for (int i = 0; i < bytesObtained; i++) {
            while (occurrencePrefixLength > 0
                    && fileChunk[i] != subarray[occurrencePrefixLength]) {

                occurrencePrefixLength =
                        failureFunction[occurrencePrefixLength - 1];
            }

            if (fileChunk[i] == subarray[occurrencePrefixLength]) {
                occurrencePrefixLength++;
            }

            if (occurrencePrefixLength == subarray.length) {
                byteIndices.add(
                        chunkStart + i - occurrencePrefixLength + 1
                );

                occurrencePrefixLength =
                        failureFunction[occurrencePrefixLength - 1];
            }
        }
    }
}