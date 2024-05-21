import java.util.Comparator;
public class PdqSort {
    // Разделы ниже этого размера сортируются с помощью сортировки вставками
    public static final int INSERTION_SORT_THRESHOLD = 24;

    // Разделы выше этого размера используют Tukey's ninther для выбора опорного элемента
    public static final int NINTHER_THRESHOLD = 128;

    // Когда мы обнаруживаем уже отсортированный раздел, пытаемся использовать сортировку вставками, которая позволяет
    // сделать такое количество перемещений элементов до отказа от этого вида сортировки
    public static final int PARTIAL_INSERTION_SORT_LIMIT = 8;

    // Должен быть кратен 8 из-за зацикливания, и < 256, чтобы поместиться в беззнаковый char
    public static final int BLOCK_SIZE = 64;

    // Размер кэша, предполагая степень двойки
    public static final int CACHELINE_SIZE = 64;

    public static int log2(int n) {  // Вычисление целочисленного логарифма по основанию 2
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");

        return 31 - Integer.numberOfLeadingZeros(n);
    }
    public static void insertionSort(int[] array, int low, int high) {
        if (low == high) return;

        for (int cur = low + 1; cur <= high; ++cur) {
            int sift = cur;
            int sift_1 = cur - 1;

            // Compare first so we can avoid 2 moves for an element already positioned correctly.
            if (array[sift] < array[sift_1]) {
                int tmp = array[sift];

                do {
                    array[sift--] = array[sift_1];
                } while (sift != low && tmp < array[--sift_1]);

                array[sift] = tmp;
            }
        }
    }

    // Сортирует диапазон [begin, end) с помощью сортировки вставками с заданной функцией сравнения.
    // Предполагается, что элемент перед начальным элементом диапазона меньше или равен
    // любому элементу в диапазоне [begin, end).
    public static void unguardedInsertionSort(int[] array, int begin, int end) {
        if (begin == end) return;

        for (int cur = begin + 1; cur != end; ++cur) {
            int sift = cur;
            int sift_1 = cur - 1;

            // Compare first so we can avoid 2 moves for an element already positioned correctly.
            if (array[sift] < array[sift_1]) {
                int tmp = array[sift];

                do {
                    array[sift--] = array[sift_1];
                } while (sift_1 >= begin && tmp < array[--sift_1]);

                array[sift] = tmp;
            }
        }
    }

    // Попытка использовать сортировку вставками на диапазоне [begin, end).
    // Возвращает false, если было перемещено более PARTIAL_INSERTION_SORT_LIMIT элементов и прерывает сортировку.
    // В противном случае успешно сортирует и возвращает true.
    public static boolean partialInsertionSort(int[] array, int begin, int end) {
        if (begin == end) return true;

        int limit = 0;
        for (int cur = begin + 1; cur < end; ++cur) {
            int sift = cur;
            int sift_1 = cur - 1;

            // Compare first so we can avoid 2 moves for an element already positioned correctly.
            if (array[sift] < array[sift_1]) {
                int tmp = array[sift];

                do {
                    array[sift--] = array[sift_1--];
                } while (sift != begin && tmp < array[sift_1]);

                array[sift] = tmp;
                limit += cur - sift;
            }

            if (limit > PARTIAL_INSERTION_SORT_LIMIT) return false;
        }

        return true;
    }

    // Сортирует элементы a и b с использованием функции сравнения.
    public static void sort2(int[] array, int a, int b) {
        if (array[b] < array[a]) {
            swap(array, a, b);
        }
    }

    // Сортирует элементы a, b и c с использованием функции сравнения.
    public static void sort3(int[] array, int a, int b, int c) {
        sort2(array, a, b);
        sort2(array, b, c);
        sort2(array, a, b);
    }

    // Разделяет [begin, end) вокруг опорного элемента, находящегося в начале. Элементы, равные опорному,
    // помещаются в правую часть. Возвращает позицию опорного элемента после разделения и информацию о том,
    // была ли исходная последовательность уже корректно разделена. Предполагается, что опорный элемент является медианой
    // по крайней мере из 3 элементов и что длина [begin, end) составляет как минимум INSERTION_SORT_THRESHOLD.
//    public static int partitionRightBranchless(int[] array, int begin, int end) {
//        int pivot = array[begin];
//        int first = begin;
//        int last = end - 1;
//
//        while (array[++first] < pivot) ;
//
//        if (first - 1 == begin) {
//            while (first <= last && array[--last] > pivot) ;
//        } else {
//            while (array[--last] > pivot) ;
//        }
//
//        boolean alreadyPartitioned = first >= last;
//        if (!alreadyPartitioned) {
//            swap(array, first, last);
//            first++;
//        }
//
//        int pivotPos = first - 1;
//        array[begin] = array[pivotPos];
//        array[pivotPos] = pivot;
//
//        return pivotPos;
//    }
//
//    // Эта функция нужна для распределения по убыванию, где нам нужно
//    // провести корректную замену для pdqsort, чтобы сохранить O(n).
//    public static void swapOffsets(int[] array, int[] offsetsLBase, int[] offsetsRBase, int startL, int startR, int num, boolean useSwaps) {
//        if (useSwaps) {
//            for (int i = 0; i < num; ++i) {
//                int l = offsetsLBase[startL + i];
//                int r = offsetsRBase[startR + i];
//                int temp = array[l];
//                array[l] = array[r];
//                array[r] = temp;
//            }
//        } else if (num > 0) {
//            int l = offsetsLBase[startL];
//            int r = offsetsRBase[startR];
//            int temp = array[l];
//            array[l] = array[r];
//            for (int i = 1; i < num; ++i) {
//                l = offsetsLBase[startL + i];
//                array[r] = array[l];
//                r = offsetsRBase[startR + i];
//                array[l] = array[r];
//            }
//            array[r] = temp;
//        }
//    }
    public static int partitionRightBranchless(int[] array, int begin, int end) {
        int pivot = array[begin];
        int first = begin;
        int last = end - 1;

        while (array[++first] < pivot) ;

        if (first - 1 == begin) {
            while (first <= last && array[--last] > pivot) ;
        } else {
            while (array[--last] > pivot) ;
        }

        boolean alreadyPartitioned = first >= last;
        if (!alreadyPartitioned) {
            swap(array, first, last);
            first++;
        }

        int pivotPos = first - 1;
        array[begin] = array[pivotPos];
        array[pivotPos] = pivot;

        return pivotPos;
    }


    // Разделяет [begin, end) вокруг pivot, используя функцию сравнения comp. Элементы, равные опорному,
    // помещаются в правую часть. Возвращает позицию опорного элемента после разделения и информацию о том,
    // была ли последовательность уже правильно разделена. Предполагается, что опорный элемент - это медиана
    // хотя бы из 3 элементов и что длина [begin, end) составляет как минимум INSERTION_SORT_THRESHOLD.
    public static int partitionRight(int[] array, int begin, int end) {
        int pivot = array[begin];
        int first = begin + 1;
        int last = end - 1;

        while (first <= last) {
            while (first <= last && array[first] < pivot) {
                first++;
            }
            while (first <= last && array[last] > pivot) {
                last--;
            }
            if (first < last) {
                swap(array, first, last);
                first++;
                last--;
            }
        }

        int pivotPos = first - 1;
        swap(array, begin, pivotPos);

        return pivotPos;
    }

    // Вспомогательный метод для обмена элементами.
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Функция, аналогичная partition_right, но элементы, равные опорному,
    // помещаются в левую часть. Не проверяет и не возвращает, была ли
    // последовательность уже разделена. Поскольку это редко используется
    // (случай множества равных элементов), и в этом случае pdqsort уже имеет
    // производительность O(n), блочная быстрая сортировка здесь не применяется для простоты.
    public static int partitionLeft(int[] array, int begin, int end) {
        int pivot = array[begin];
        int first = begin + 1;
        int last = end - 1;

        while (last > begin && array[last] >= pivot) {
            last--;
        }

        if (last + 1 == end) {
            while (first < last && array[first] <= pivot) {
                first++;
            }
        } else {
            while (array[first] <= pivot) {
                first++;
            }
        }

        while (first < last) {
            swap(array, first, last);
            while (array[first] <= pivot) {
                first++;
            }
            while (array[last] >= pivot) {
                last--;
            }
        }

        int pivotPos = last;
        swap(array, begin, pivotPos);

        return pivotPos;
    }
    // Используйте цикл while для устранения хвостовой рекурсии.
    public static void pdqsortLoop(int[] array, int begin, int end, int badAllowed, boolean leftmost, boolean Branchless){
        while (true) {
            int size = end - begin;

            // Сортировка вставками быстрее для малых массивов.
            if (size < INSERTION_SORT_THRESHOLD) {
                if (leftmost) {
                    insertionSort(array, begin, end - 1);
                } else {
                    unguardedInsertionSort(array, begin, end);
                }
                return;
            }

            // Выбор опорного элемента как медианы из трех или псевдомедианы из девяти.
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

            if (begin > 0 && !leftmost && array[begin] == array[begin - 1]) {
                begin = partitionLeft(array, begin, end) + 1;
                continue;
            }

            // Разделение и получение результатов.
            int pivotPos;
            boolean alreadyPartitioned;
            if (Branchless) {
                pivotPos = partitionRightBranchless(array, begin, end);
                alreadyPartitioned = pivotPos == begin;
            } else {
                pivotPos = partitionRight(array, begin, end);
                alreadyPartitioned = pivotPos - begin == size - 1;
            }


            // Проверка на сильно несбалансированное разделение.
            int lSize = pivotPos - begin;
            int rSize = end - (pivotPos + 1);
            boolean highlyUnbalanced = lSize < size / 8 || rSize < size / 8;

            // Если получено сильно несбалансированное разделение, перемешиваем элементы, чтобы нарушить множество паттернов.
            // Если получено сильно несбалансированное разделение, перемешиваем элементы, чтобы нарушить множество паттернов.
            if (highlyUnbalanced) {
                // Если у нас слишком много плохих разделений, переходим к сортировке кучей для гарантированного O(n log n).
                if (--badAllowed == 0) {
                    heapSort(array, begin, end - 1);
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
                // Если было достаточно сбалансированное разделение и мы попытались отсортировать уже разделенную последовательность,
                // пробуем использовать сортировку вставками.
                if (alreadyPartitioned && partialInsertionSort(array, begin, pivotPos)
                        && partialInsertionSort(array, pivotPos + 1, end))
                    return;
            }


            // Сортируем левую часть сначала рекурсивно и выполняем элиминацию хвостовой рекурсии для правой части.
            pdqsortLoop(array, begin, pivotPos, badAllowed, leftmost, Branchless);
            begin = pivotPos + 1;
            leftmost = false;
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

    public static class Sorter {
        private final Comparator<Integer> comparator;
        private final boolean isBranchless;

        public Sorter(Comparator<Integer> comparator, boolean isBranchless) {
            this.comparator = comparator;
            this.isBranchless = isBranchless;
        }

        public void sort(int[] array) {
            if (array.length == 0) {
                return;
            }

            pdqsortLoop(array, 0, array.length, log2(array.length), false , isBranchless);
        }
    }
}
