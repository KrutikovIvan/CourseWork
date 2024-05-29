package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 20, timeUnit = TimeUnit.SECONDS) // Настройка прогрева
@Measurement(iterations = 5, time = 35, timeUnit = TimeUnit.SECONDS) // Настройка измерений
public class SortBenchmark {

    @Param({"1000", "10000", "100000"})
    private int arraySize;
    private int[] sortedArray;  // Отсортированный
    private int[] unsortedArray;  // Обратно отсортированный
    private int[] randomArray;  // Случайный набор
    private int[] partiallySortedArray;  // Частично отсортированный
    private int[] arrayWithDuplicates;  // С повторениями
    private int[] arrayWithIdenticalElements;  // С одинаковыми значениями

    @Setup  // Количество вызовов метода setup() зависит от количества потоков и количества значений параметра arraySize
    // Если потоков 2, то вызовов будет количество Param * 2, если 3, то Param * 3 и т.д.
    public void setup() {
        sortedArray = ArrayGenerator.createSortedArray(arraySize);
        unsortedArray = ArrayGenerator.createUnsortedArray(arraySize);
        randomArray = ArrayGenerator.createRandomArray(arraySize);
        partiallySortedArray = ArrayGenerator.createPartiallySortedArray(arraySize);
        arrayWithDuplicates = ArrayGenerator.createArrayWithDuplicates(arraySize, 100);
        arrayWithIdenticalElements = ArrayGenerator.createArrayWithIdenticalElements(arraySize, 42);
    }

    private void executeSort(int[] array, SortAlgorithm algorithm) {
        int[] copy = Arrays.copyOf(array, array.length);
        algorithm.sort(copy);
    }

    @Benchmark
    public void benchmarkIntroSortRandomArray() {
        executeSort(randomArray, SortAlgorithms::introSort);
    }

    @Benchmark
    public void benchmarkIntroSortSortedArray() {
        executeSort(sortedArray, SortAlgorithms::introSort);
    }

    @Benchmark
    public void benchmarkIntroSortUnsortedArray() {
        executeSort(unsortedArray, SortAlgorithms::introSort);
    }

    @Benchmark
    public void benchmarkIntroSortPartiallySortedArray() {
        executeSort(partiallySortedArray, SortAlgorithms::introSort);
    }

    @Benchmark
    public void benchmarkIntroSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, SortAlgorithms::introSort);
    }

    @Benchmark
    public void benchmarkIntroSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, SortAlgorithms::introSort);
    }

    @Benchmark
    public void benchmarkPdqSortRandomArray() {
        executeSort(randomArray, PDQSort::pdqSort);
    }

    @Benchmark
    public void benchmarkPdqSortSortedArray() {
        executeSort(sortedArray, PDQSort::pdqSort);
    }

    @Benchmark
    public void benchmarkPdqSortUnsortedArray() {
        executeSort(unsortedArray, PDQSort::pdqSort);
    }

    @Benchmark
    public void benchmarkPdqSortPartiallySortedArray() {
        executeSort(partiallySortedArray, PDQSort::pdqSort);
    }

    @Benchmark
    public void benchmarkPdqSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, PDQSort::pdqSort);
    }

    @Benchmark
    public void benchmarkPdqSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, PDQSort::pdqSort);
    }

    @Benchmark
    public void benchmarkQuickSortSortedArray() {
        executeSort(sortedArray, SortAlgorithms::quickSort);
    }

    @Benchmark
    public void benchmarkQuickSortUnsortedArray() {
        executeSort(unsortedArray, SortAlgorithms::quickSort);
    }

    @Benchmark
    public void benchmarkQuickSortRandomArray() {
        executeSort(randomArray, SortAlgorithms::quickSort);
    }

    @Benchmark
    public void benchmarkQuickSortPartiallySortedArray() {
        executeSort(partiallySortedArray, SortAlgorithms::quickSort);
    }

    @Benchmark
    public void benchmarkQuickSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, SortAlgorithms::quickSort);
    }

    @Benchmark
    public void benchmarkQuickSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, SortAlgorithms::quickSort);
    }

    @Benchmark
    public void benchmarkHeapSortSortedArray() {
        executeSort(sortedArray, (array) -> SortAlgorithms.heapSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkHeapSortUnsortedArray() {
        executeSort(unsortedArray, (array) -> SortAlgorithms.heapSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkHeapSortRandomArray() {
        executeSort(randomArray, (array) -> SortAlgorithms.heapSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkHeapSortPartiallySortedArray() {
        executeSort(partiallySortedArray, (array) -> SortAlgorithms.heapSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkHeapSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, (array) -> SortAlgorithms.heapSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkHeapSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, (array) -> SortAlgorithms.heapSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkInsertionSortSortedArray() {
        executeSort(sortedArray, (array) -> SortAlgorithms.insertionSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkInsertionSortUnsortedArray() {
        executeSort(unsortedArray, (array) -> SortAlgorithms.insertionSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkInsertionSortRandomArray() {
        executeSort(randomArray, (array) -> SortAlgorithms.insertionSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkInsertionSortPartiallySortedArray() {
        executeSort(partiallySortedArray, (array) -> SortAlgorithms.insertionSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkInsertionSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, (array) -> SortAlgorithms.insertionSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkInsertionSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, (array) -> SortAlgorithms.insertionSort(array, 0, array.length - 1));
    }

    @Benchmark
    public void benchmarkBubbleSortSortedArray() {
        executeSort(sortedArray, SortAlgorithms::bubbleSort);
    }

    @Benchmark
    public void benchmarkBubbleSortUnsortedArray() {
        executeSort(unsortedArray, SortAlgorithms::bubbleSort);
    }

    @Benchmark
    public void benchmarkBubbleSortRandomArray() {
        executeSort(randomArray, SortAlgorithms::bubbleSort);
    }

    @Benchmark
    public void benchmarkBubbleSortPartiallySortedArray() {
        executeSort(partiallySortedArray, SortAlgorithms::bubbleSort);
    }

    @Benchmark
    public void benchmarkBubbleSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, SortAlgorithms::bubbleSort);
    }

    @Benchmark
    public void benchmarkBubbleSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, SortAlgorithms::bubbleSort);
    }

    @Benchmark
    public void benchmarkMergeSortSortedArray() {
        executeSort(sortedArray, SortAlgorithms::mergeSort);
    }

    @Benchmark
    public void benchmarkMergeSortUnsortedArray() {
        executeSort(unsortedArray, SortAlgorithms::mergeSort);
    }

    @Benchmark
    public void benchmarkMergeSortRandomArray() {
        executeSort(randomArray, SortAlgorithms::mergeSort);
    }

    @Benchmark
    public void benchmarkMergeSortPartiallySortedArray() {
        executeSort(partiallySortedArray, SortAlgorithms::mergeSort);
    }

    @Benchmark
    public void benchmarkMergeSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, SortAlgorithms::mergeSort);
    }

    @Benchmark
    public void benchmarkMergeSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, SortAlgorithms::mergeSort);
    }

    @Benchmark
    public void benchmarkArraysSortRandomArray() {
        executeSort(randomArray, Arrays::sort);
    }

    @Benchmark
    public void benchmarkArraysSortSortedArray() {
        executeSort(sortedArray, Arrays::sort);
    }

    @Benchmark
    public void benchmarkArraysSortUnsortedArray() {
        executeSort(unsortedArray, Arrays::sort);
    }

    @Benchmark
    public void benchmarkArraysSortPartiallySortedArray() {
        executeSort(partiallySortedArray, Arrays::sort);
    }

    @Benchmark
    public void benchmarkArraysSortArrayWithDuplicates() {
        executeSort(arrayWithDuplicates, Arrays::sort);
    }

    @Benchmark
    public void benchmarkArraysSortArrayWithIdenticalElements() {
        executeSort(arrayWithIdenticalElements, Arrays::sort);
    }

    @FunctionalInterface
    private interface SortAlgorithm {
        void sort(int[] array);
    }
}