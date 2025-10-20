package org.example.sorting;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public interface SortingStrategy<T> {
    List<T> sort(List<T> list, Comparator<T> comparator, ExecutorService executor);
}
