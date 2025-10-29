package ru.aston.finalproject.interfaces;

import java.util.List;

public interface SearchResultListener {
    <T> void onSearchResults(List<T> list, List<Integer> positions, String entityType);
}