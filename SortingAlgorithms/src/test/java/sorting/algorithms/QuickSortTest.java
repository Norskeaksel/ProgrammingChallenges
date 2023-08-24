package sorting.algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class QuickSortTest {

    @Test
    void quickSort() {
        int[] largeRandomArray = new int[1000];
        Random random = new Random();
        for (int i = 0; i < largeRandomArray.length; i++) {
            largeRandomArray[i] = random.nextInt(100_000);
        }
        int[] largeRandomArrayCopy = largeRandomArray.clone();
        QuickSort.quickSort(largeRandomArray, 0, largeRandomArray.length - 1);
        Arrays.sort(largeRandomArrayCopy);
        assert Arrays.equals(largeRandomArray, largeRandomArrayCopy);
    }
}