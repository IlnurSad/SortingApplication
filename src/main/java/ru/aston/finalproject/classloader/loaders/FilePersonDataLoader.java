package ru.aston.finalproject.classloader.loaders;

import ru.aston.finalproject.classloader.entities.Person;
import ru.aston.finalproject.classloader.interfaces.DataLoader;
import ru.aston.finalproject.classloader.interfaces.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilePersonDataLoader implements DataLoader<Person> {
    private final Validator<Person> validator;

    public FilePersonDataLoader(Validator<Person> validator) {
        this.validator = validator;
    }

    @Override
    public Person[] loadData(int size) {
        createSampleFile();

        List<Person> persons = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("persons_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && persons.size() < size) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Person person = Person.builder()
                                .setName(parts[0].trim())
                                .setAge(Integer.parseInt(parts[1].trim()))
                                .setProfession(parts[2].trim())
                                .build();

                        if (validator.isValid(person)) {
                            persons.add(person);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка чтения строки: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return persons.toArray(new Person[0]);
    }

    private void createSampleFile() {
        try (PrintWriter writer = new PrintWriter("persons_data.txt")) {
            writer.println("Иван Петров,25,Инженер");
            writer.println("Мария Сидорова,30,Врач");
            writer.println("Алексей Козлов,35,Программист");
            writer.println("Елена Новикова,28,Учитель");
            writer.println("Дмитрий Волков,40,Менеджер");
        } catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());
        }
    }
}
