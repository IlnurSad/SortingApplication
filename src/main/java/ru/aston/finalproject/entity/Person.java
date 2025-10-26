package ru.aston.finalproject.entity;

import java.util.Objects;

public class Person implements Comparable<Person> {
    private final String name;
    private final int age;
    private final String profession;
    
    private Person(PersonBuilder PersonBuilder) {
        this.name = PersonBuilder.name;
        this.age = PersonBuilder.age;
        this.profession = PersonBuilder.profession;
    }
    
    public String getName() {return name;}
    
    public int getAge() {return age;}
    
    public String getProfession() {return profession;}
    
    @Override
    public int compareTo(Person other) {
        int result = this.name.compareTo(other.name);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(this.age, other.age);
        if (result != 0) {
            return result;
        }
        return this.profession.compareTo(other.profession);
    }
    
    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, profession='%s'}", name, age, profession);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name) && profession.equals(person.profession);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age, profession);
    }
    
    public static PersonBuilder builder() {
        return new PersonBuilder();
    }
    
    public static class PersonBuilder {
        private String name;
        private int age;
        private String profession;
        
        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }
        
        public PersonBuilder setAge(int age) {
            this.age = age;
            return this;
        }
        
        public PersonBuilder setProfession(String profession) {
            this.profession = profession;
            return this;
        }
        
        public Person build() {
            return new Person(this);
        }
    }
}
