package org.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MainTest {
    @Test
    void try_array() {
        int[] array_init = {1, 2, 3, 4, 7, 9, 11, 23, 44, 0, -1};
        Main.heapsort(array_init);
        int[] array_desired = {-1, 0, 1, 2, 3, 4, 7, 9, 11, 23, 44};
        assertArrayEquals(array_desired, array_init,
                "failed with result: " + Arrays.toString(array_init) + "\n");
    }

    @Test
    void try_empty() {
        int[] array_init = {};
        Main.heapsort(array_init);
        int[] array_desired = {};
        assertArrayEquals(array_desired, array_init);
    }
}