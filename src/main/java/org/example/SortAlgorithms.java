package org.example;

public class SortAlgorithms {
    private static final int INSERTION_SORT_THRESHOLD = 16;

    public static void introSort(int[] array) {
        int depthLimit = 2 * logBase2(array.length);
        introSort(array, 0, array.length - 1, depthLimit);
    }

    private static void introSort(int[] array, int low, int high, int depthLimit) {
        while (low < high) {
            if (high - low < INSERTION_SORT_THRESHOLD) {
                insertionSort(array, low, high);
                return;
            }

            if (depthLimit == 0) {
                heapSort(array, low, high);
                return;
            }

            int pivotIndex = partition(array, low, high);
            if (pivotIndex - low < high - pivotIndex) {
                introSort(array, low, pivotIndex - 1, depthLimit - 1);
                low = pivotIndex + 1;
            } else {
                introSort(array, pivotIndex + 1, high, depthLimit - 1);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    public static void bubbleSort(int[] array) {
        boolean swapped;
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static void mergeSort(int[] array) {
        if (array.length < 2) {
            return;
        }
        int[] helper = new int[array.length];
        mergeSort(array, helper, 0, array.length - 1);
    }

    private static void mergeSort(int[] array, int[] helper, int low, int high) {
        if (low < high) {
            int middle = (low + high) / 2;
            mergeSort(array, helper, low, middle);
            mergeSort(array, helper, middle + 1, high);
            merge(array, helper, low, middle, high);
        }
    }

    private static void merge(int[] array, int[] helper, int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }

        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;

        while (helperLeft <= middle && helperRight <= high) {
            if (helper[helperLeft] <= helper[helperRight]) {
                array[current] = helper[helperLeft];
                helperLeft++;
            } else {
                array[current] = helper[helperRight];
                helperRight++;
            }
            current++;
        }

        int remaining = middle - helperLeft;
        for (int i = 0; i <= remaining; i++) {
            array[current + i] = helper[helperLeft + i];
        }
    }

    public static void insertionSort(int[] array, int low, int high) {
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

    public static void heapSort(int[] array, int low, int high) {
        for (int i = high / 2 - 1; i >= low; i--) {
            heapify(array, high, i);
        }
        for (int i = high; i > low; i--) {
            swap(array, low, i);
            heapify(array, i, low);
        }
    }

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(array, low, high);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(array, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(array, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
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

    private static int logBase2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }
}