package ru.aston.finalproject.loaders;

import ru.aston.finalproject.entity.Person;
import ru.aston.finalproject.interfaces.DataLoader;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPersonDataLoader implements DataLoader<Person> {
    private final Random random;

    public RandomPersonDataLoader() {
        this.random = new Random();
    }

    @Override
    public List<Person> loadData() {
        int size = getRandomSize();
        String[] names = {"Алексей", "Мария", "Дмитрий", "Елена", "Сергей", "Ольга", "Иван", "Анна"};
        String[] professions = {"Инженер", "Врач", "Учитель", "Программист", "Бухгалтер", "Менеджер", "Дизайнер", "Аналитик"};

        System.out.println("Генерируем " + size + " случайных людей...");

        return IntStream.range(0, size)
                .mapToObj(i -> {
                    String name = names[random.nextInt(names.length)];
                    int age = random.nextInt(62) + 18;
                    String profession = professions[random.nextInt(professions.length)];
                    return Person.builder()
                            .setName(name)
                            .setAge(age)
                            .setProfession(profession)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private int getRandomSize() {
        return 30 + random.nextInt(50);
    }
}
