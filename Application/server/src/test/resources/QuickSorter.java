

public class QuickSorter<T extends Comparable<T>> implements Sorter<T> {

    /***
     * T is the generic type which will be instantiated at the run time
     * Elements of the list should be comparable
     *
     * @param list
     */
	
	@Override
    public void sort(T[] list) {
        if (list.length == 0)
            return;
        quickSort(list, 0, list.length - 1);
    }

    /***
     * Main sorter function to sort the given list
     * @param list given list of type T
     * @param left start index
     * @param right end index
     */

    private void quickSort(T[] list, int left, int right) {

        int index = partition(list, left, right);

        if (left < index - 1)
            quickSort(list, left, index - 1); // Sort left half

        if (index < right)
            quickSort(list, index, right);
    }

    /**
     * Parition the list around pivot
     *
     * @param list  given list of type T
     * @param left  start index
     * @param right end index
     * @return
     */

    private int partition(T[] list, int left, int right) {

        T pivot = list[(left + right) / 2];

        while (left <= right) {

            while (myCompare(list[left], pivot) < 0) left++;
            while (myCompare(list[right], pivot) > 0) right--;

            if (left <= right) {
                swap(list, left, right);
                left++;
                right--;
            }

        }
        return left;
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
     * @return an integer representing whether t is greater than or less than or equal to T
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
