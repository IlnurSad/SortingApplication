package org.example.binarysearch;

import java.util.Objects;

class Person implements Comparable<Person> {
    String name;
    int age;
    String occupation;

    public Person(String name, int age, String occupation) {
        this.name = name;
        this.age = age;
        this.occupation = occupation;
    }

    @Override
    public int compareTo(Person other) {
        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) return nameCompare;

        int ageCompare = Integer.compare(this.age, other.age);
        if (ageCompare != 0) return ageCompare;

        return this.occupation.compareTo(other.occupation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(occupation, person.occupation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, occupation);
    }

    @Override
    public String toString() {
        return name + " (" + age + " лет, " + occupation + ")";
    }
}