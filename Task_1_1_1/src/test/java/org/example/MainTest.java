package org.example;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void tryArray() {
        int[] actual = {1, 2, 3, 4, 7, 9, 11, 23, 44, 0, -1};
        Main.heapsort(actual);
        int[] expected = {-1, 0, 1, 2, 3, 4, 7, 9, 11, 23, 44};
        assertArrayEquals(expected, actual);
    }

    @Test
    void tryEmpty() {
        int[] actual = {};
        Main.heapsort(actual);
        int[] expected = {};
        assertArrayEquals(expected, actual);
    }

    @Test
    void tryEqual() {
        int[] actual = {1, 1, 1, 1};
        Main.heapsort(actual);
        int[] expected = {1, 1, 1, 1};
        assertArrayEquals(expected, actual);
    }

    @Test
    void tryNegative() {
        int[] actual = {-1, -4, 0, 1, -6, 44, -10};
        Main.heapsort(actual);
        int[] expected = {-10, -6, -4, -1, 0, 1, 44};
        assertArrayEquals(expected, actual);
    }

    @Test
    void tryDouble() {
        int[] actual = {1, 1, 1, 3, 1, 3};
        Main.heapsort(actual);
        int[] expected = {1, 1, 1, 1, 3, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void tryAscending() {
        int[] actual = {44, 10, 6, 5, -1, -10, -20};
        Main.heapsort(actual);
        int[] expected = {-20, -10, -1, 5, 6, 10, 44};
        assertArrayEquals(expected, actual);
    }
}