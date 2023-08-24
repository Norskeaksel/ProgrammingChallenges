package sorting.algorithms;

import java.util.Arrays;

public class InsertionSort {
    /*
    Maintains a sorted and unsorted region of the array.
    Minimizes data movement. Efficient on almost sorted data. O(n^2)
     */
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int j = i;
            while (j > 0 && array[j] < array[j - 1]) {
                Utils.swap(array, j, j - 1);
                j--;
            }
            System.out.println("After sweep " + i + ": " + Arrays.toString(array));
        }
    }
}