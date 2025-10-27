package ru.aston.finalproject.strategies.search;

import java.util.Comparator;
import java.util.List;
import ru.aston.finalproject.interfaces.SearchStrategy;

public class BinarySearchStrategy<T> implements SearchStrategy<T> {
    private final Comparator<? super T> comparator;

    public BinarySearchStrategy(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int search(List<? extends T> list, T key) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = list.get(mid);
            int cmp = comparator.compare(midElement, key);

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