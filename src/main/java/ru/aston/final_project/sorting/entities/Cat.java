package ru.aston.final_project.sorting.entities;

import java.util.Objects;

public class Cat implements Comparable<Cat> {
    private final String name;
    private final int age;
    private final String breed;
    
    private Cat(CatBuilder CatBuilder) {
        this.name = CatBuilder.name;
        this.age = CatBuilder.age;
        this.breed = CatBuilder.breed;
    }
    
    public String getName() {return name;}
    
    public int getAge() {return age;}
    
    public String getBreed() {return breed;}
    
    @Override
    public int compareTo(Cat other) {
        int result = this.name.compareTo(other.name);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(this.age, other.age);
        if (result != 0) {
            return result;
        }
        return this.breed.compareTo(other.breed);
    }
    
    @Override
    public String toString() {
        return String.format("Cat{name='%s', age=%d, breed='%s'}", name, age, breed);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cat cat = (Cat) obj;
        return age == cat.age && name.equals(cat.name) && breed.equals(cat.breed);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age, breed);
    }
    
    public static CatBuilder builder() {
        return new CatBuilder();
    }
    
    public static class CatBuilder {
        private String name;
        private int age;
        private String breed;
        
        public CatBuilder setName(String name) {
            this.name = name;
            return this;
        }
        
        public CatBuilder setAge(int age) {
            this.age = age;
            return this;
        }
        
        public CatBuilder setBreed(String breed) {
            this.breed = breed;
            return this;
        }
        
        public Cat build() {
            return new Cat(this);
        }
    }
}
