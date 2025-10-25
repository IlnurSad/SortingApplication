package ru.aston.finalproject.entities.comparators;

import ru.aston.finalproject.entities.Person;

import java.util.Comparator;

public class PersonProfessionComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getProfession().compareTo(p2.getProfession());
    }
}