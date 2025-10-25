package ru.aston.finalproject.strategies.search;

import ru.aston.finalproject.interfaces.SearchStrategy;

import java.util.List;

public class BinarySearchStrategy<T extends Comparable<T>> implements SearchStrategy<T> {

    @Override
    public int search(List<T> list, T key) {
        if (list == null || key == null) return -1;

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = list.get(mid).compareTo(key);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}