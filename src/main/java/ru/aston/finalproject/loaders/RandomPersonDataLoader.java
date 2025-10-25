package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPersonDataLoader implements DataLoader<Person> {
    private final Validator<Person> validator;
    private final Random random;

    public RandomPersonDataLoader(Validator<Person> validator) {
        this.validator = validator;
        this.random = new Random();
    }

    @Override
    public List<Person> loadData() {
        int size = getRandomSize();
        List<Person> data = new ArrayList<>();
        String[] names = {"Алексей", "Мария", "Дмитрий", "Елена", "Сергей", "Ольга", "Иван", "Анна"};
        String[] professions = {"Инженер", "Врач", "Учитель", "Программист", "Бухгалтер", "Менеджер", "Дизайнер", "Аналитик"};

        System.out.println("Генерируем " + size + " случайных людей...");

        for (int i = 0; i < size; i++) {
            String name = names[random.nextInt(names.length)];
            int age = 20 + random.nextInt(40);
            String profession = professions[random.nextInt(professions.length)];

            Person person = Person.builder()
                    .setName(name)
                    .setAge(age)
                    .setProfession(profession)
                    .build();

            data.add(person);
        }

        return data;
    }

    private int getRandomSize() {
        return 5 + random.nextInt(10);
    }
}