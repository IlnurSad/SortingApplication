package org.example.binarysearch;

import java.util.List;

interface SearchStrategy<E> {
    int search(List<? extends E> list, E key);
}
