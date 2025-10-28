package ru.aston.finalproject.interfaces;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public interface SortingStrategy<T> {
    List<T> sort(List<T> list, Comparator<T> comparator, ForkJoinPool executor);
}
