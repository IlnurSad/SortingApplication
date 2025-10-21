package org.example.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class BinarySearchDemo {
    private static List<Person> people = new ArrayList<>();
    private static List<Book> books = new ArrayList<>();
    private static SearchManager<Person> personManager = new SearchManager<>();
    private static SearchManager<Book> bookManager = new SearchManager<>();

    public static void main(String[] args) throws InterruptedException {
        personManager.setStrategy(new BinarySearchStrategy<>());
        bookManager.setStrategy(new BinarySearchStrategy<>());

        initializeCollections();

        SearchUI searchUI = new SearchUI(personManager, bookManager, people, books);
        searchUI.performUserSearch();
    }

    private static void initializeCollections() {
        people.addAll(Arrays.asList(
                new Person("Анна", 25, "инженер"),
                new Person("Борис", 30, "врач"),
                new Person("Виктор", 35, "учитель"),
                new Person("Анна", 25, "инженер"),
                new Person("Мария", 28, "дизайнер"),
                new Person("Борис", 30, "врач")
        ));
        books.addAll(Arrays.asList(
                new Book("Война и мир", "Лев Толстой", 1869),
                new Book("Преступление и наказание", "Фёдор Достоевский", 1866),
                new Book("1984", "Джордж Оруэлл", 1949),
                new Book("Война и мир", "Лев Толстой", 1869),
                new Book("1984", "Джордж Оруэлл", 1949)
        ));

        Collections.sort(people);
        Collections.sort(books);

        System.out.println("Коллекции инициализированы и отсортированы.");
    }
}