package ru.aston.finalproject.binarysearch;

import ru.aston.finalproject.appender.FileAppender;
import ru.aston.finalproject.entity.Cat;
import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.managers.MenuManager;
import ru.aston.finalproject.managers.SearchManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SearchUI {
    private Scanner scanner;

    public SearchUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void performCatSearch(SearchManager<Cat> catManager, List<Cat> cats) {
        MenuManager menuManager = new MenuManager(scanner);

        if (menuManager.askYesNo("Произвести поиск?")) {
            if (cats == null || cats.isEmpty()) {
                System.out.println("Сначала загрузите данные котов.");
                return;
            }

            System.out.println("\n=== Поиск кота ===");
            System.out.println("1. Точный поиск по всем полям");
            System.out.println("2. Поиск по имени");
            System.out.println("3. Поиск по возрасту");
            System.out.println("4. Поиск по породе");

            int choice = menuManager.readIntInput("Выберите тип поиска: ");

            switch (choice) {
                case 1:
                    searchCatByExactMatch(catManager, cats);
                    break;
                case 2:
                    searchCatByName(catManager, cats);
                    break;
                case 3:
                    searchCatByAge(catManager, cats);
                    break;
                case 4:
                    searchCatByBreed(catManager, cats);
                    break;
                default:
                    System.out.println("Неверный выбор, выполняется точный поиск.");
                    searchCatByExactMatch(catManager, cats);
            }
        }
    }

    private void searchCatByExactMatch(SearchManager<Cat> catManager, List<Cat> cats) {
        System.out.print("Введите имя кота: ");
        String name = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите породу: ");
        String breed = scanner.nextLine();

        Cat searchCat = Cat.builder()
                .setName(name)
                .setAge(age)
                .setBreed(breed)
                .build();

        performExactSearchWithThreads(catManager, cats, searchCat);
    }

    private void searchCatByName(SearchManager<Cat> catManager, List<Cat> cats) {
        System.out.print("Введите имя кота: ");
        String name = scanner.nextLine();

        try {
            List<Integer> positions = catManager.findAllOccurrences(cats,
                    cat -> cat.getName().equals(name));
            displaySearchResults(cats, positions);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    private void searchCatByAge(SearchManager<Cat> catManager, List<Cat> cats) {
        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        try {
            List<Integer> positions = catManager.findAllOccurrences(cats,
                    cat -> cat.getAge() == age);
            displaySearchResults(cats, positions);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    private void searchCatByBreed(SearchManager<Cat> catManager, List<Cat> cats) {
        System.out.print("Введите породу: ");
        String breed = scanner.nextLine();

        try {
            List<Integer> positions = catManager.findAllOccurrences(cats,
                    cat -> cat.getBreed().equals(breed));
            displaySearchResults(cats, positions);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    private void performExactSearchWithThreads(SearchManager<Cat> catManager, List<Cat> cats, Cat searchCat) {
        try {
            int index = catManager.performBinarySearch(cats, searchCat);

            if (index != -1) {
                List<Integer> positions = new ArrayList<>();
                for (int i = 0; i < cats.size(); i++) {
                    if (cats.get(i).equals(searchCat)) {
                        positions.add(i);
                    }
                }

                displaySearchResults(cats, positions);

                int occurrences = catManager.countExactOccurrences(cats, searchCat);
                System.out.println("Найдено точных совпадений: " + occurrences);
            } else {
                System.out.println("Точных совпадений не найдено.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    public void performPersonSearch(SearchManager<Person> personManager, List<Person> persons) {
        MenuManager menuManager = new MenuManager(scanner);

        if (menuManager.askYesNo("Произвести поиск?")) {
            if (persons == null || persons.isEmpty()) {
                System.out.println("Сначала загрузите данные людей.");
                return;
            }

            System.out.println("\n=== Поиск человека ===");
            System.out.println("1. Точный поиск по всем полям");
            System.out.println("2. Поиск по имени");
            System.out.println("3. Поиск по возрасту");
            System.out.println("4. Поиск по профессии");

            int choice = menuManager.readIntInput("Выберите тип поиска: ");

            switch (choice) {
                case 1:
                    searchPersonByExactMatch(personManager, persons);
                    break;
                case 2:
                    searchPersonByName(personManager, persons);
                    break;
                case 3:
                    searchPersonByAge(personManager, persons);
                    break;
                case 4:
                    searchPersonByProfession(personManager, persons);
                    break;
                default:
                    System.out.println("Неверный выбор, выполняется точный поиск.");
                    searchPersonByExactMatch(personManager, persons);
            }
        }
    }

    private void searchPersonByExactMatch(SearchManager<Person> personManager, List<Person> persons) {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите профессию: ");
        String profession = scanner.nextLine();

        Person searchPerson = Person.builder()
                .setName(name)
                .setAge(age)
                .setProfession(profession)
                .build();

        performPersonExactSearchWithThreads(personManager, persons, searchPerson);
    }

    private void searchPersonByName(SearchManager<Person> personManager, List<Person> persons) {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        try {
            List<Integer> positions = personManager.findAllOccurrences(persons,
                    person -> person.getName().equals(name));
            displaySearchResults(persons, positions);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    private void searchPersonByAge(SearchManager<Person> personManager, List<Person> persons) {
        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        try {
            List<Integer> positions = personManager.findAllOccurrences(persons,
                    person -> person.getAge() == age);
            displaySearchResults(persons, positions);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    private void searchPersonByProfession(SearchManager<Person> personManager, List<Person> persons) {
        System.out.print("Введите профессию: ");
        String profession = scanner.nextLine();

        try {
            List<Integer> positions = personManager.findAllOccurrences(persons,
                    person -> person.getProfession().equals(profession));
            displaySearchResults(persons, positions);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    private void performPersonExactSearchWithThreads(SearchManager<Person> personManager, List<Person> persons, Person searchPerson) {
        try {
            int index = personManager.performBinarySearch(persons, searchPerson);

            if (index != -1) {
                List<Integer> positions = new ArrayList<>();
                for (int i = 0; i < persons.size(); i++) {
                    if (persons.get(i).equals(searchPerson)) {
                        positions.add(i);
                    }
                }

                displaySearchResults(persons, positions);

                int occurrences = personManager.countExactOccurrences(persons, searchPerson);
                System.out.println("Найдено точных совпадений: " + occurrences);
            } else {
                System.out.println("Точных совпадений не найдено.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении поиска: " + e.getMessage());
        }
    }

    private <T> void writeSearchResultsToFile(List<T> list, List<Integer> positions, String filePath) {
        FileAppender<String> fileAppender = new FileAppender<>(filePath);

        fileAppender.appendValue("\nРезультаты поиска:", s -> s);

        if (positions.isEmpty()) {
            fileAppender.appendValue("Совпадений не найдено", s -> s);
        } else {
            List<Integer> userPositions = new ArrayList<>();
            for (int index : positions) {
                userPositions.add(index + 1);
            }

            fileAppender.appendValue("Позиции в списке: " + userPositions, s -> s);

            for (int index : positions) {
                String result = "На позиции " + (index + 1) + ": " + list.get(index);
                fileAppender.appendValue(result, s -> s);
            }
        }
    }

    private <T> void displaySearchResults(List<T> list, List<Integer> positions) {
        if (positions.isEmpty()) {
            System.out.println("Совпадений не найдено");
        } else {
            List<Integer> userPositions = new ArrayList<>();
            for (int index : positions) {
                userPositions.add(index + 1);
            }

            System.out.println("Позиции в списке: " + userPositions);
            for (int index : positions) {
                System.out.println("На позиции " + (index + 1) + ": " + list.get(index));
            }

            writeSearchResultsToFile(list, positions, "output.txt");
        }
    }
}
