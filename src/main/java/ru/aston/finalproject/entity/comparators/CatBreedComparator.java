package ru.aston.finalproject.entity.comparators;

import ru.aston.finalproject.entity.Cat;

import java.util.Comparator;

public class CatBreedComparator implements Comparator<Cat> {
    @Override
    public int compare(Cat c1, Cat c2) {
        return Comparator.comparing(Cat::getBreed).thenComparing(Cat::getName).thenComparing(Cat::getAge).compare(c1, c2);
    }
}