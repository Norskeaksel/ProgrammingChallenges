package sorting.algorithms;

import java.util.Arrays;

public class Utils {
    public static void swap(int[] a, int i, int j) {
        if(i==j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        System.out.println("Swapping " + a[i] + " with " + a[j] + " to make: " + Arrays.toString(a));
    }
}
