package org.example;


import java.util.Arrays;

/**
* Main class contains all other functions.
 */
public class Main {
    static void main() {
//        int[] arr = {1, 2, 3, 4, 7, 9, 11, 23, 44, 0, -1};
//        heapsort(arr);
//        IO.println(Arrays.toString(arr));
    }

    /**
    * method sifts arr[idx] element down the heap if it's smaller than any of its descendents.
    * designed to be used in Main.heapsort(int[] arr) method.
     */
    static void sift(int[] arr, int arr_size, int idx) {
        // an element gets sifted down as long as it's the smallest
        // arr_size <= arr.length is not necessarily equal to arr.length
        int li;
        int ri;
        int buf;
        while (true) {
            li = 2 * idx + 1;
            ri = 2 * idx + 2;
            if (li < arr_size && arr[idx] < arr[li] && (ri >= arr_size || arr[li] >= arr[ri])) {
                // swapping the element with its left descendent
                buf = arr[idx];
                arr[idx] = arr[li];
                arr[li] = buf;
                idx = li;}
            else if (ri < arr_size && arr[idx] < arr[ri] && arr[ri] >= arr[li]) {
                // swapping the element with its right descendent
                buf = arr[idx];
                arr[idx] = arr[ri];
                arr[ri] = buf;
                idx = ri;}
            else {
                break;
            }
        }
    }

    /**
    * takes an array of integers and sorts it in ascending
     * order with the use of the heapsort algorithm.
    * Invokes Main.sift method.
     *
     * @param arr - an array that has to be sorted.
     */
    public static void heapsort(int[] arr) {
        int n = arr.length;
        int buf;

        // sifting each element from last to first
        for (int i = n - 1; i >= 0; i--) {
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
