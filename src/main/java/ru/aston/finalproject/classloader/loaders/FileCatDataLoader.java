package ru.aston.finalproject.classloader.loaders;

import ru.aston.finalproject.classloader.entities.Cat;
import ru.aston.finalproject.classloader.interfaces.DataLoader;
import ru.aston.finalproject.classloader.interfaces.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCatDataLoader implements DataLoader<Cat> {
    private final Validator<Cat> validator;

    public FileCatDataLoader(Validator<Cat> validator) {
        this.validator = validator;
    }

    @Override
    public List<Cat> loadData(int size) {
        createSampleFile();

        List<Cat> cats = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("cats_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && cats.size() < size) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Cat cat = Cat.builder()
                                .setName(parts[0].trim())
                                .setAge(Integer.parseInt(parts[1].trim()))
                                .setBreed(parts[2].trim())
                                .build();

                        if (validator.isValid(cat)) {
                            cats.add(cat);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка чтения строки: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return cats;
    }

    private void createSampleFile() {
        try (PrintWriter writer = new PrintWriter("cats_data.txt")) {
            writer.println("Мурзик,3,Сиамская");
            writer.println("Барсик,5,Персидская");
            writer.println("Васька,2,Дворняжка");
            writer.println("Рыжик,4,Мейн-кун");
            writer.println("Снежок,1,Британская");
        } catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());
        }
    }
}