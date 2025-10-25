package ru.aston.finalproject.entity.comparators;

import ru.aston.finalproject.entity.Person;

import java.util.Comparator;

public class PersonProfessionComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getProfession().compareTo(p2.getProfession());
    }
}