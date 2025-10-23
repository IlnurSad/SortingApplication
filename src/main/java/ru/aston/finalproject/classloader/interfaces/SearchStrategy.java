package ru.aston.finalproject.classloader.interfaces;

public interface SearchStrategy<T> {
    int search(T[] array, T key);
}