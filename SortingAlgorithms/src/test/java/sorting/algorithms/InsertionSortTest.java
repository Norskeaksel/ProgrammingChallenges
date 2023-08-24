package sorting.algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class InsertionSortTest {

    @Test
    void insertionSort() {
        int[] largeRandomArray = new int[200];
        Random random = new Random();
        for (int i = 0; i < largeRandomArray.length; i++) {
            largeRandomArray[i] = random.nextInt(100_000);
        }
        int[] largeRandomArrayCopy = largeRandomArray.clone();
        InsertionSort.insertionSort(largeRandomArray);
        Arrays.sort(largeRandomArrayCopy);
        assert Arrays.equals(largeRandomArray, largeRandomArrayCopy);
    }
}