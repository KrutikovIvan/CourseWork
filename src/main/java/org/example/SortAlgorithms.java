package org.example;

public class SortAlgorithms {
    private static final int INSERTION_SORT_THRESHOLD = 16;

    // Начальный метод для Introsort
    public static void introSort(int[] array) {
        int depthLimit = 2 * logBase2(array.length);
        introSort(array, 0, array.length - 1, depthLimit);
    }

    // Реализация Introsort
    private static void introSort(int[] array, int low, int high, int depthLimit) {
        if (high - low < INSERTION_SORT_THRESHOLD) {
            insertionSort(array, low, high);
            return;
        }

        if (depthLimit == 0) {
            heapSort(array, low, high);
            return;
        }

        if (low < high) {
            int pivotIndex = partition(array, low, high);
            introSort(array, low, pivotIndex - 1, depthLimit - 1);
            introSort(array, pivotIndex + 1, high, depthLimit - 1);
        }
    }
    private static void insertionSort(int[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= low && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    // Реализация сортировки кучей
    public static void heapSort(int[] array, int low, int high) {
        for (int i = high / 2 - 1; i >= low; i--) {
            heapify(array, high, i);
        }
        for (int i = high; i > low; i--) {
            swap(array, low, i);
            heapify(array, i, low);
        }
    }
    // Пример реализации быстрой сортировки (quicksort)
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }
    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        if (right < n && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private static int logBase2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }


    // Добавьте другие алгоритмы сортировки здесь...
}