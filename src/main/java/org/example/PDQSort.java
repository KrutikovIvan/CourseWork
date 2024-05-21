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
                int tmp = key;

                do {
                    array[j + 1] = array[j];
                    j--;
                } while (j >= low && array[j] > tmp);

                array[j + 1] = tmp;
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
        int log = 0;
        while (n > 1) {
            n >>= 1;
            log++;
        }
        return log;
    }

    private static int medianOfThree(int[] array, int a, int b, int c) {
        if (array[a] < array[b]) {
            if (array[b] < array[c]) {
                return b;
            } else if (array[a] < array[c]) {
                return c;
            } else {
                return a;
            }
        } else {
            if (array[a] < array[c]) {
                return a;
            } else if (array[b] < array[c]) {
                return c;
            } else {
                return b;
            }
        }
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
                swap(array, begin, begin + s2);            } else {
                sort3(array, begin + s2, begin, end - 1);
            }

            int pivot = array[begin];
            int i = begin;
            int j = end;

            while (true) {
                do {
                    i++;
                } while (array[i] < pivot);

                do {
                    j--;
                } while (array[j] > pivot);

                if (i >= j) {break;
                }

                swap(array, i, j);
            }

            swap(array, begin, j);

            if (j - begin < end - (j + 1)) {
                pdqSortLoop(array, begin, j, badAllowed, leftmost);
                begin = j + 1;
            } else {
                pdqSortLoop(array, j + 1, end, badAllowed, false);
                end = j;
            }

            if (badAllowed == 0) {
                return;
            }

            badAllowed--;
        }
    }

    public static void pdqSort(int[] array) {
        pdqSortLoop(array, 0, array.length, log2(array.length), true);
    }
}