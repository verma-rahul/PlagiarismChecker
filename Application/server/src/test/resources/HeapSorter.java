
public class HeapSorter<T extends Comparable<T>> implements Sorter<T> {

    /***
     * T is the generic type which will be instantiated at the run time
     * Elements of the list should be comparable
     *
     * @param list
     */
	@Override
    public void sort(T[] list) {

        buildMaxHeap(list);

        for (int i = list.length - 1; i >= 0; i--) {
            swap(list, 0, i);
            maxHeapify(list, i, 0);
        }
    }

    /**
     * Building heap ( rearranging array )
     *
     * @param list given list
     */


    private void buildMaxHeap(T[] list) {
        int heapSize = list.length;
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            maxHeapify(list, heapSize, i);
        }
    }

    /**
     * To heapify a subtree rooted with Node i
     *
     * @param list     given array/list of T
     * @param heapSize - current size of Heap
     * @param i
     */

    private void maxHeapify(T[] list, int heapSize, int i) {

        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heapSize && myCompare(list[left], list[largest]) > 0)
            largest = left;

        if (right < heapSize && myCompare(list[right], list[largest]) > 0)
            largest = right;

        if (largest != i) {
            swap(list, i, largest);
            maxHeapify(list, heapSize, largest);
        }
    }

    /**
     * Swap two elements of list T
     *
     * @param list given list T
     * @param i
     * @param j
     */

    private void swap(T[] list, int i, int j) {
        T temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    /**
     * Custom comparator for handling null values
     *
     * @param t     Element of type T
     * @param pivot Element  T
     * @return an int representing whether t is greater than or less than or equal to T
     */

    private int myCompare(T t, T pivot) {
        if (t == null && pivot == null)
            return 0;
        if (t == null)
            return -1;
        if (pivot == null)
            return 1;
        return t.compareTo(pivot);
    }
}
