package org.example;


import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 7, 9, 11, 23, 44, 0, -1};
        heapsort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * swaps values of two array indices
     * @param arr an array
     * @param idxA first index
     * @param idxB second index
     */
    static void swapInArray(int[] arr, int idxA, int idxB) {
        int buf;
        buf = arr[idxA];
        arr[idxA] = arr[idxB];
        arr[idxB] = buf;
    }

    /**
    * method sifts arr[idx] element down the heap if it's smaller than any of its descendents.
    * designed to be used in Main.heapsort(int[] arr) method.
     */
    static void sift(int[] arr, int arrSize, int index) {
        // an element gets sifted down as long as it's the smallest
        // arrSize <= arr.length is not necessarily equal to arr.length
        int leftIndex;
        int rightIndex;
        int toSwap; // index of the element that should be swaped with index element
        while (true) {
            leftIndex = 2 * index + 1;
            rightIndex = 2 * index + 2;

            toSwap = index;
            if (rightIndex < arrSize && arr[rightIndex] > arr[toSwap]) {
                toSwap = rightIndex;
            }

            if (leftIndex < arrSize && arr[leftIndex] > arr[toSwap]) {
                toSwap = leftIndex;
            }

            if (toSwap != index) {
                swapInArray(arr, index, toSwap);
                index = toSwap;
            }
            else {
                // an element did not move down
                break;
            }

            if (rightIndex >= arrSize) {
                // right child is out of range
                break;
            }
        }
    }

    /**
    * takes an array of integers and sorts it in ascending
     * order with the use of the heapsort algorithm.
     *
     * @param arr - an array that has to be sorted.
     */
    public static void heapsort(int[] arr) {
        int n = arr.length;
        int buf;

        // sifting each element from last to first
        for (int i = n / 2 - 1; i >= 0; i--) {
            sift(arr, arr.length, i);
        }

        // taking elements out of the heap one by one and resifting it
        for (int i = 0; i < arr.length - 1; i++) {
            buf = arr[0];
            arr[0] = arr[n - 1];
            arr[n - 1] = buf;
            n--;
            sift(arr, n, 0);
        }
    }
}
