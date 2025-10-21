package org.example.binarysearch;

import java.util.List;

class BinarySearchStrategy<E extends Comparable<? super E>> implements SearchStrategy<E> {
    @Override
    public int search(List<? extends E> list, E key) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            E midElement = list.get(mid);
            int cmp = midElement.compareTo(key);

            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
