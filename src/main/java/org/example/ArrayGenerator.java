package org.example;

import java.util.Arrays;
import java.util.Random;

public class ArrayGenerator {
    public static int[] createPartiallySortedArray(int size) {
        int[] array = createSortedArray(size);
        Random random = new Random();
        for (int i = 0; i < size / 10; i++) {
            int index1 = random.nextInt(size);
            int index2 = random.nextInt(size);
            int temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
        return array;
    }

    public static int[] createArrayWithDuplicates(int size, int numberOfUniqueElements) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(numberOfUniqueElements);
        }
        return array;
    }

    public static int[] createArrayWithIdenticalElements(int size, int element) {
        int[] array = new int[size];
        Arrays.fill(array, element);
        return array;
    }

    public static int[] createSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int[] createUnsortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }

    public static int[] createRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }
}