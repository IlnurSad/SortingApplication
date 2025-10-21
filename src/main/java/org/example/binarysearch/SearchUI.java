package org.example.binarysearch;

import java.util.List;
import java.util.Scanner;

public class SearchUI {
    private Scanner scanner;
    private SearchManager<Person> personManager;
    private SearchManager<Book> bookManager;
    private List<Person> people;
    private List<Book> books;

    public SearchUI(SearchManager<Person> personManager, SearchManager<Book> bookManager,
                    List<Person> people, List<Book> books) {
        this.scanner = new Scanner(System.in);
        this.personManager = personManager;
        this.bookManager = bookManager;
        this.people = people;
        this.books = books;
    }

    public void performUserSearch() throws InterruptedException {
        System.out.println("Выберите тип элемента для поиска:");
        System.out.println("1 - Человек");
        System.out.println("2 - Книга");
        System.out.print("Ваш выбор: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                searchUserPerson();
                break;
            case 2:
                searchUserBook();
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }

    public void searchUserPerson() throws InterruptedException {
        System.out.println("\n=== ПОИСК ЧЕЛОВЕКА ===");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите профессию: ");
        String occupation = scanner.nextLine();

        Person userPerson = new Person(name, age, occupation);

        // Бинарный поиск
        int index = personManager.performSearch(people, userPerson);
        System.out.println("Бинарный поиск: найден на позиции " + index);

        // Многопоточный подсчет вхождений
        System.out.print("Введите количество потоков для подсчета: ");
        int threads = scanner.nextInt();
        int occurrences = personManager.countOccurrences(people, userPerson, threads);
        System.out.println("Найдено вхождений: " + occurrences);
    }

    public void searchUserBook() throws InterruptedException {
        System.out.println("\n=== ПОИСК КНИГИ ===");
        System.out.print("Введите название: ");
        String title = scanner.nextLine();
        System.out.print("Введите автора: ");
        String author = scanner.nextLine();
        System.out.print("Введите год: ");
        int year = scanner.nextInt();

        Book userBook = new Book(title, author, year);

        int index = bookManager.performSearch(books, userBook);
        System.out.println("Бинарный поиск: найден на позиции " + index);

        System.out.print("Введите количество потоков для подсчета: ");
        int threads = scanner.nextInt();
        int occurrences = bookManager.countOccurrences(books, userBook, threads);
        System.out.println("Найдено вхождений: " + occurrences);
    }
}