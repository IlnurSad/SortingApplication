package ru.aston.finalproject.strategies.sorting;

import ru.aston.finalproject.interfaces.SortStrategy;

import java.util.List;

public class BubbleSortStrategy implements SortStrategy<Object> {
    @Override
    public void sort(List<Object> list) {
        if (list == null || list.size() <= 1) return;

        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                @SuppressWarnings("unchecked")
                Comparable<Object> obj1 = (Comparable<Object>) list.get(j);
                @SuppressWarnings("unchecked")
                Comparable<Object> obj2 = (Comparable<Object>) list.get(j + 1);

                if (obj1.compareTo(obj2) > 0) {
                    Object temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}