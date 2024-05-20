package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SortBenchmark {

    private int[] sortedArray;  // Отсортированный
    private int[] unsortedArray;  // Обратно отсортированный
    private int[] randomArray;  // Случайный набор
    private int[] partiallySortedArray;  // Частично отсортированный
    private int[] arrayWithDuplicates;  // С повторениями
    private int[] arrayWithIdenticalElements;  // С одинаковыми значениями

    @Setup
    public void setup() {
        sortedArray = createSortedArray(10000);
        unsortedArray = createUnsortedArray(10000);
        randomArray = createRandomArray(10000);
        partiallySortedArray = createPartiallySortedArray(10000);
        arrayWithDuplicates = createArrayWithDuplicates(10000, 100); // 100 различных значений
        arrayWithIdenticalElements = createArrayWithIdenticalElements(10000, 42); // Все элементы равны 42
    }

    @Benchmark
    public void benchmarkIntroSortRandomArray() {
        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
        SortAlgorithms.introSort(copy);
    }
    @Benchmark
    public void benchmarkIntroSortSortedArray() {
        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
        SortAlgorithms.introSort(copy);
    }

    @Benchmark
    public void benchmarkIntroSortUnsortedArray() {
        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
        SortAlgorithms.introSort(copy);
    }

    @Benchmark
    public void benchmarkIntroSortPartiallySortedArray() {
        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
        SortAlgorithms.introSort(copy);
    }

    @Benchmark
    public void benchmarkIntroSortArrayWithDuplicates() {
        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
        SortAlgorithms.introSort(copy);
    }

    @Benchmark
    public void benchmarkIntroSortArrayWithIdenticalElements() {
        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
        SortAlgorithms.introSort(copy);
    }
    @Benchmark
    public void benchmarkQuickSortSortedArray() {
        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
        SortAlgorithms.quickSort(copy, 0, copy.length - 1);
    }
    @Benchmark
    public void benchmarkQuickSortUnsortedArray() {
        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
        SortAlgorithms.quickSort(copy, 0, copy.length - 1);
    }
    @Benchmark
    public void benchmarkQuickSortRandomArray() {
        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
        SortAlgorithms.quickSort(copy, 0, copy.length - 1);
    }
    @Benchmark
    public void benchmarkQuickSortPartiallySortedArray() {
        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
        SortAlgorithms.quickSort(copy, 0, copy.length - 1);
    }

    @Benchmark
    public void benchmarkQuickSortArrayWithDuplicates() {
        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
        SortAlgorithms.quickSort(copy, 0, copy.length - 1);
    }

    @Benchmark
    public void benchmarkQuickSortArrayWithIdenticalElements() {
        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
        SortAlgorithms.quickSort(copy, 0, copy.length - 1);
    }

    // Создание частично отсортированного массива
    private int[] createPartiallySortedArray(int size) {
        int[] array = createSortedArray(size);
        // Сделать небольшое количество "перемешиваний"
        Random random = new Random();
        for (int i = 0; i < size / 10; i++) {
            int index1 = random.nextInt(size);
            int index2 = random.nextInt(size);
            // Обмен элементами
            int temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
        return array;
    }

    // Создание массива с повторяющимися элементами
    private int[] createArrayWithDuplicates(int size, int numberOfUniqueElements) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(numberOfUniqueElements);
        }
        return array;
    }

    // Создание массива, в котором все элементы одинаковы
    private int[] createArrayWithIdenticalElements(int size, int element) {
        int[] array = new int[size];
        Arrays.fill(array, element);
        return array;
    }

    private int[] createSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private int[] createUnsortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }

    private int[] createRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }
}