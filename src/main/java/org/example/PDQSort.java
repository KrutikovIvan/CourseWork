package org.example;

public class PDQSort {
    private static final int INSERTION_SORT_THRESHOLD = 24;
    private static final int NINTHER_THRESHOLD = 128;
    private static final int PARTIAL_INSERTION_SORT_LIMIT = 8;

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

    private static void unguardedInsertionSort(int[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;

            while (array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static boolean partialInsertionSort(int[] array, int low, int high) {
        int limit = 0;
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;

            if (array[j] > key) {

                do {
                    array[j + 1] = array[j];
                    j--;
                } while (j >= low && array[j] > key);

                array[j + 1] = key;
                limit += i - (j + 1);
            }

            if (limit > PARTIAL_INSERTION_SORT_LIMIT) {
                return false;
            }
        }

        return true;
    }

    private static void sort2(int[] array, int a, int b) {
        if (array[b] < array[a]) {
            int temp = array[a];
            array[a] = array[b];
            array[b] = temp;
        }
    }

    private static void sort3(int[] array, int a, int b, int c) {
        sort2(array, a, b);
        sort2(array, b, c);
        sort2(array, a, b);
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private static int log2(int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    private static int partition(int[] array, int begin, int end) {
        int pivot = array[begin];
        int i = begin + 1;
        int j = end - 1;

        while (i <= j) {
            while (i <= j && array[i] <= pivot) {
                i++;
            }
            while (i <= j && array[j] >= pivot) {
                j--;
            }
            if (i < j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        swap(array, begin, j);
        return j;
    }

    private static void pdqSortLoop(int[] array, int begin, int end, int badAllowed, boolean leftmost) {
        while (true) {
            int size = end - begin;

            if (size < INSERTION_SORT_THRESHOLD) {
                if (leftmost) {
                    insertionSort(array, begin, end - 1);
                } else {
                    unguardedInsertionSort(array, begin, end - 1);
                }
                return;
            }

            int s2 = size / 2;
            if (size > NINTHER_THRESHOLD) {
                sort3(array, begin, begin + s2, end - 1);
                sort3(array, begin + 1, begin + (s2 - 1), end - 2);
                sort3(array, begin + 2, begin + (s2 + 1), end - 3);
                sort3(array, begin + (s2 - 1), begin + s2, begin + (s2 + 1));
                swap(array, begin, begin + s2);
            } else {
                sort3(array, begin + s2, begin, end - 1);
            }

            int pivotPos = partition(array, begin, end);
            int lSize = pivotPos - begin;
            int rSize = end - (pivotPos + 1);
            boolean highlyUnbalanced = lSize < size / 8 || rSize < size / 8;

            if (highlyUnbalanced) {
                if (--badAllowed == 0) {
                    heapSort(array, begin, end);
                    return;
                }

                if (lSize >= INSERTION_SORT_THRESHOLD) {
                    swap(array, begin, begin + lSize / 4);
                    swap(array, pivotPos - 1, pivotPos - lSize / 4);

                    if (lSize > NINTHER_THRESHOLD) {
                        swap(array, begin + 1, begin + (lSize / 4 + 1));
                        swap(array, begin + 2, begin + (lSize / 4 + 2));
                        swap(array, pivotPos - 2, pivotPos - (lSize / 4 + 1));
                        swap(array, pivotPos - 3, pivotPos - (lSize / 4 + 2));
                    }
                }

                if (rSize >= INSERTION_SORT_THRESHOLD) {
                    swap(array, pivotPos + 1, pivotPos + (1 + rSize / 4));
                    swap(array, end - 1, end - rSize / 4);

                    if (rSize > NINTHER_THRESHOLD) {
                        swap(array, pivotPos + 2, pivotPos + (2 + rSize / 4));
                        swap(array, pivotPos + 3, pivotPos + (3 + rSize / 4));
                        swap(array, end - 2, end - (1 + rSize / 4));
                        swap(array, end - 3, end - (2 + rSize / 4));
                    }
                }
            } else {
                if (partialInsertionSort(array, begin, pivotPos) && partialInsertionSort(array, pivotPos + 1, end - 1)) {
                    return;
                }
            }

            if (pivotPos - begin < end - (pivotPos + 1)) {
                pdqSortLoop(array, begin, pivotPos, badAllowed, leftmost);
                begin = pivotPos + 1;
                leftmost = false;
            } else {
                pdqSortLoop(array, pivotPos + 1, end, badAllowed, false);
                end = pivotPos;
            }
        }
    }

    private static void heapSort(int[] array, int begin, int end) {
        for (int i = (end - begin) / 2 - 1 + begin; i >= begin; i--) {
            heapify(array, end, i, begin);
        }
        for (int i = end - 1; i > begin; i--) {
            swap(array, begin, i);
            heapify(array, i, begin, begin);
        }
    }

    private static void heapify(int[] array, int n, int i, int offset) {
        int largest = i;
        int left = 2 * (i - offset) + 1 + offset;
        int right = 2 * (i - offset) + 2 + offset;

        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        if (right < n && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest, offset);
        }
    }

    public static void pdqSort(int[] array) {
        pdqSortLoop(array, 0, array.length, log2(array.length), true);
    }
}