package sorting.algorithms;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = { 3, 1, 5, 2 };
        System.err.println("Before sorting: "+Arrays.toString(array));
        //SelectionSort.selectionSort(array);
        //InsertionSort.insertionSort(array);
        QuickSort.quickSort(array,0, array.length-1);
    }
}