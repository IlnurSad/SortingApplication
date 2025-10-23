package ru.aston.finalproject.classloader.loaders;

import ru.aston.finalproject.classloader.entities.Person;
import ru.aston.finalproject.classloader.interfaces.DataLoader;
import ru.aston.finalproject.classloader.interfaces.Validator;

import java.util.Random;

public class RandomPersonDataLoader implements DataLoader<Person> {
    private final Validator<Person> validator;
    private final Random random;

    public RandomPersonDataLoader(Validator<Person> validator) {
        this.validator = validator;
        this.random = new Random();
    }

    @Override
    public Person[] loadData(int size) {
        Person[] data = new Person[size];
        String[] names = {"Алексей", "Мария", "Дмитрий", "Елена", "Сергей", "Ольга", "Иван", "Анна"};
        String[] professions = {"Инженер", "Врач", "Учитель", "Программист", "Бухгалтер", "Менеджер", "Дизайнер", "Аналитик"};

        for (int i = 0; i < size; i++) {
            String name = names[random.nextInt(names.length)] + " " + (i + 1);
            int age = 20 + random.nextInt(40);
            String profession = professions[random.nextInt(professions.length)];

            data[i] = Person.builder()
                    .setName(name)
                    .setAge(age)
                    .setProfession(profession)
                    .build();
        }

        return data;
    }
}