package ru.aston.finalproject.entities.comparators;

import ru.aston.finalproject.entities.Cat;

import java.util.Comparator;

public class CatBreedComparator implements Comparator<Cat> {
    @Override
    public int compare(Cat c1, Cat c2) {
        return c1.getBreed().compareTo(c2.getBreed());
    }
}