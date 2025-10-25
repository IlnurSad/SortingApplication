package ru.aston.finalproject.managers;

import ru.aston.finalproject.entity.validators.CatValidator;
import ru.aston.finalproject.entity.validators.PersonValidator;
import ru.aston.finalproject.interfaces.DataLoader;
import ru.aston.finalproject.loaders.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataLoaderManager {
    private final Map<Integer, Map<Integer, DataLoader<?>>> dataLoadersMap;
    private final Scanner scanner;

    public DataLoaderManager(Scanner scanner) {
        this.scanner = scanner;
        this.dataLoadersMap = initializeDataLoadersMap();
    }

    private Map<Integer, Map<Integer, DataLoader<?>>> initializeDataLoadersMap() {
        Map<Integer, Map<Integer, DataLoader<?>>> loadersMap = new HashMap<>();

        Map<Integer, DataLoader<?>> catLoaders = new HashMap<>();
        catLoaders.put(1, new FileCatDataLoader(new CatValidator(), scanner));
        catLoaders.put(2, new RandomCatDataLoader(new CatValidator()));
        catLoaders.put(3, new ManualCatDataLoader(scanner, new CatValidator()));
        loadersMap.put(1, catLoaders);

        Map<Integer, DataLoader<?>> personLoaders = new HashMap<>();
        personLoaders.put(1, new FilePersonDataLoader(new PersonValidator(), scanner));
        personLoaders.put(2, new RandomPersonDataLoader(new PersonValidator()));
        personLoaders.put(3, new ManualPersonDataLoader(scanner, new PersonValidator()));
        loadersMap.put(2, personLoaders);

        return loadersMap;
    }

    public DataLoader<?> getDataLoader(int entityType, int loaderType) {
        Map<Integer, DataLoader<?>> loaders = dataLoadersMap.get(entityType);
        if (loaders == null) {
            return null;
        }
        return loaders.get(loaderType);
    }

    public boolean isValidLoaderType(int entityType, int loaderType) {
        Map<Integer, DataLoader<?>> loaders = dataLoadersMap.get(entityType);
        return loaders != null && loaders.containsKey(loaderType);
    }
}