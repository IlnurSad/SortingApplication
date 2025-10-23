package ru.aston.finalproject.classloader.interfaces;

import java.util.List;

public interface DataLoader<T> {
    List<T> loadData(int size);
}
