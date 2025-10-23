package ru.aston.finalproject.classloader.interfaces;

import java.util.List;

public interface SearchStrategy<T> {
    int search(List<T> list, T key);
}