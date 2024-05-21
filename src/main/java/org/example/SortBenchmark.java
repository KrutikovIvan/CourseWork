package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SortBenchmark {

    @Param({"1000", "10000", "50000"})
    private int arraySize;
    private int[] sortedArray;  // Отсортированный
    private int[] unsortedArray;  // Обратно отсортированный
    private int[] randomArray;  // Случайный набор
    private int[] partiallySortedArray;  // Частично отсортированный
    private int[] arrayWithDuplicates;  // С повторениями
    private int[] arrayWithIdenticalElements;  // С одинаковыми значениями

    @Setup
    public void setup() {
        sortedArray = ArrayGenerator.createSortedArray(arraySize);
        unsortedArray = ArrayGenerator.createUnsortedArray(arraySize);
        randomArray = ArrayGenerator.createRandomArray(arraySize);
        partiallySortedArray = ArrayGenerator.createPartiallySortedArray(arraySize);
        arrayWithDuplicates = ArrayGenerator.createArrayWithDuplicates(arraySize, 100);
        arrayWithIdenticalElements = ArrayGenerator.createArrayWithIdenticalElements(arraySize, 42);
    }

//    @Benchmark
//    public void benchmarkIntroSortRandomArray() {
//        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
//        SortAlgorithms.introSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkIntroSortSortedArray() {
//        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
//        SortAlgorithms.introSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkIntroSortUnsortedArray() {
//        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
//        SortAlgorithms.introSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkIntroSortPartiallySortedArray() {
//        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
//        SortAlgorithms.introSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkIntroSortArrayWithDuplicates() {
//        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
//        SortAlgorithms.introSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkIntroSortArrayWithIdenticalElements() {
//        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
//        SortAlgorithms.introSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkPdqSortRandomArray() {
//        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
//        PDQSort.pdqSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkPdqSortSortedArray() {
//        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
//        PDQSort.pdqSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkPdqSortUnsortedArray() {
//        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
//        PDQSort.pdqSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkPdqSortPartiallySortedArray() {
//        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
//        PDQSort.pdqSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkPdqSortArrayWithDuplicates() {
//        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
//        PDQSort.pdqSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkPdqSortArrayWithIdenticalElements() {
//        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
//        PDQSort.pdqSort(copy);
//    }

    @Benchmark
    public void benchmarkQuickSortSortedArray() {
        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
        SortAlgorithms.quickSort(copy);
    }

    @Benchmark
    public void benchmarkQuickSortUnsortedArray() {
        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
        SortAlgorithms.quickSort(copy);
    }

    @Benchmark
    public void benchmarkQuickSortRandomArray() {
        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
        SortAlgorithms.quickSort(copy);
    }

    @Benchmark
    public void benchmarkQuickSortPartiallySortedArray() {
        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
        SortAlgorithms.quickSort(copy);
    }

    @Benchmark
    public void benchmarkQuickSortArrayWithDuplicates() {
        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
        SortAlgorithms.quickSort(copy);
    }

    @Benchmark
    public void benchmarkQuickSortArrayWithIdenticalElements() {
        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
        SortAlgorithms.quickSort(copy);
    }

//    @Benchmark
//    public void benchmarkHeapSortSortedArray() {
//        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
//        SortAlgorithms.heapSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkHeapSortUnsortedArray() {
//        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
//        SortAlgorithms.heapSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkHeapSortRandomArray() {
//        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
//        SortAlgorithms.heapSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkHeapSortPartiallySortedArray() {
//        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
//        SortAlgorithms.heapSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkHeapSortArrayWithDuplicates() {
//        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
//        SortAlgorithms.heapSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkHeapSortArrayWithIdenticalElements() {
//        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
//        SortAlgorithms.heapSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkInsertionSortSortedArray() {
//        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
//        SortAlgorithms.insertionSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkInsertionSortUnsortedArray() {
//        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
//        SortAlgorithms.insertionSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkInsertionSortRandomArray() {
//        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
//        SortAlgorithms.insertionSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkInsertionSortPartiallySortedArray() {
//        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
//        SortAlgorithms.insertionSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkInsertionSortArrayWithDuplicates() {
//        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
//        SortAlgorithms.insertionSort(copy, 0, copy.length - 1);
//    }
//
//    @Benchmark
//    public void benchmarkInsertionSortArrayWithIdenticalElements() {
//        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
//        SortAlgorithms.insertionSort(copy, 0, copy.length - 1);
//    }
//
//
//    @Benchmark
//    public void benchmarkBubbleSortSortedArray() {
//        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
//        SortAlgorithms.bubbleSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkBubbleSortUnsortedArray() {
//        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
//        SortAlgorithms.bubbleSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkBubbleSortRandomArray() {
//        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
//        SortAlgorithms.bubbleSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkBubbleSortPartiallySortedArray() {
//        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
//        SortAlgorithms.bubbleSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkBubbleSortArrayWithDuplicates() {
//        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
//        SortAlgorithms.bubbleSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkBubbleSortArrayWithIdenticalElements() {
//        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
//        SortAlgorithms.bubbleSort(copy);
//    }
//    @Benchmark
//    public void benchmarkMergeSortSortedArray() {
//        int[] copy = Arrays.copyOf(sortedArray, sortedArray.length);
//        SortAlgorithms.mergeSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkMergeSortUnsortedArray() {
//        int[] copy = Arrays.copyOf(unsortedArray, unsortedArray.length);
//        SortAlgorithms.mergeSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkMergeSortRandomArray() {
//        int[] copy = Arrays.copyOf(randomArray, randomArray.length);
//        SortAlgorithms.mergeSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkMergeSortPartiallySortedArray() {
//        int[] copy = Arrays.copyOf(partiallySortedArray, partiallySortedArray.length);
//        SortAlgorithms.mergeSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkMergeSortArrayWithDuplicates() {
//        int[] copy = Arrays.copyOf(arrayWithDuplicates, arrayWithDuplicates.length);
//        SortAlgorithms.mergeSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkMergeSortArrayWithIdenticalElements() {
//        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arrayWithIdenticalElements.length);
//        SortAlgorithms.mergeSort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkArraysSortRandomArray() {
//        int[] copy = Arrays.copyOf(randomArray, arraySize);
//        Arrays.sort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkArraysSortSortedArray() {
//        int[] copy = Arrays.copyOf(sortedArray, arraySize);
//        Arrays.sort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkArraysSortUnsortedArray() {
//        int[] copy = Arrays.copyOf(unsortedArray, arraySize);
//        Arrays.sort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkArraysSortPartiallySortedArray() {
//        int[] copy = Arrays.copyOf(partiallySortedArray, arraySize);
//        Arrays.sort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkArraysSortArrayWithDuplicates() {
//        int[] copy = Arrays.copyOf(arrayWithDuplicates, arraySize);
//        Arrays.sort(copy);
//    }
//
//    @Benchmark
//    public void benchmarkArraysSortArrayWithIdenticalElements() {
//        int[] copy = Arrays.copyOf(arrayWithIdenticalElements, arraySize);
//        Arrays.sort(copy);
//    }


}