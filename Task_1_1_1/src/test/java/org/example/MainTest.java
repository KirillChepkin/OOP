package org.example;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void try_array() {
        int[] arrayInit = {1, 2, 3, 4, 7, 9, 11, 23, 44, 0, -1};
        Main.heapsort(arrayInit);
        int[] arrayDesired = {-1, 0, 1, 2, 3, 4, 7, 9, 11, 23, 44};
        assertArrayEquals(arrayDesired, arrayInit,
                "failed with result: " + Arrays.toString(arrayInit) + "\n");
    }

    @Test
    void try_empty() {
        int[] arrayInit = {};
        Main.heapsort(arrayInit);
        int[] arrayDesired = {};
        assertArrayEquals(arrayDesired, arrayInit);
    }
}