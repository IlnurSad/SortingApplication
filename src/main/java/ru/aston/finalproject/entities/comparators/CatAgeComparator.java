package ru.aston.finalproject.entities.comparators;

import ru.aston.finalproject.entities.Cat;

import java.util.Comparator;

public class CatAgeComparator implements Comparator<Cat> {
    @Override
    public int compare(Cat c1, Cat c2) {
        return Integer.compare(c1.getAge(), c2.getAge());
    }
}