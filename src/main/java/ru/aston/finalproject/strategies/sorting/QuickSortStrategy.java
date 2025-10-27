package ru.aston.finalproject.strategies.sorting;

import ru.aston.finalproject.interfaces.SortStrategy;

import java.util.List;

public class QuickSortStrategy implements SortStrategy<Object> {
    @Override
    public void sort(List<Object> list) {
        if (list == null || list.size() <= 1) return;
        quickSort(list, 0, list.size() - 1);
    }

    private void quickSort(List<Object> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    @SuppressWarnings("unchecked")
    private int partition(List<Object> list, int low, int high) {
        Object pivot = list.get(high);
        Comparable<Object> pivotComparable = (Comparable<Object>) pivot;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            Comparable<Object> currentComparable = (Comparable<Object>) list.get(j);
            if (currentComparable.compareTo(pivot) <= 0) {
                i++;
                Object temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Object temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }
}