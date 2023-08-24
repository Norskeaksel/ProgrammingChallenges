package sorting.algorithms;


import static java.lang.Math.min;

import java.util.Arrays;

public class SelectionSort {
    /*
    Maintains a sorted and unsorted region of the array.
    Swap efficient. n-1 in worst case. Many comparisons. O(n^2)
     */
    public static void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n-1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++)
                if (array[j] < array[minIndex])
                    minIndex = j;
            Utils.swap(array, i, minIndex);
            System.out.println("After sweep " + (i + 1) + ": " + Arrays.toString(array));
        }
    }
}
