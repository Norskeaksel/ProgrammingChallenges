package sorting.algorithms;

public class QuickSort {
    /*
    Reduces the job by sorting two smaller arrays by performing a partition.
    The fastes in memory sorting algorithm, O(n log n) normally. O(n^2) worst case.
     */
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(array, low, high);
            quickSort(array, low, pivotIdx - 1);
            quickSort(array, pivotIdx + 1, high);
        }
    }

    static int partition(int[] array, int low, int high) {
        int pivot = high;
        int firstHigh = low;
        System.out.println("Chosen pivot: " + array[pivot]);
        for (int i = low; i < high; i++)
            if (array[i] < array[pivot]) {
                Utils.swap(array, firstHigh, i);
                firstHigh++;
            }
        Utils.swap(array, firstHigh, pivot);
        return firstHigh;
    }
}
