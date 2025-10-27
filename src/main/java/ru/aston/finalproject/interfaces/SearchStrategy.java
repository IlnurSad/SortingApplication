package ru.aston.finalproject.interfaces;

import java.util.List;

public interface SearchStrategy<T> {
    int search(List<? extends T> list, T key);
}