package sorting.algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class SelectionSortTest {

    @Test
    void selectionSort() {
        int[] largeRandomArray = new int[1000];
        Random random = new Random();
        for (int i = 0; i < largeRandomArray.length; i++) {
            largeRandomArray[i] = random.nextInt(100_000);
        }
        int[] largeRandomArrayCopy = largeRandomArray.clone();
        SelectionSort.selectionSort(largeRandomArray);
        Arrays.sort(largeRandomArrayCopy);
        assert Arrays.equals(largeRandomArray, largeRandomArrayCopy);
    }
}