package ru.aston.finalproject.managers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.aston.finalproject.interfaces.DataLoader;

import java.util.Scanner;

class DataLoaderManagerTest {
    private final DataLoaderManager dataLoaderManager= new DataLoaderManager(new Scanner(System.in));

    @Test
    void whenGetValidCatLoader_thenReturnNonNullDataLoader() {
        for (int i = 1; i <= 3; i++) {
            DataLoader<?> loader = dataLoaderManager.getDataLoader(1, i);
            assertNotNull(loader);
        }
    }

    @Test
    void whenGetValidPersonLoader_thenReturnNonNullDataLoader() {
        for (int i = 1; i <= 3; i++) {
            DataLoader<?> loader = dataLoaderManager.getDataLoader(2, i);
            assertNotNull(loader);
        }
    }

    @Test
    void whenGetDataLoaderWithInvalidLoaderType_thenReturnNull() {
        DataLoader<?> loader = dataLoaderManager.getDataLoader(999, 1);
        assertNull(loader);

        loader = dataLoaderManager.getDataLoader(1, 999);
        assertNull(loader);
    }

    @Test
    void whenInitializeDataLoaderManager_thenAllLoadersAreRegistered() {
        assertTrue(dataLoaderManager.isValidLoaderType(1, 1));
        assertTrue(dataLoaderManager.isValidLoaderType(1, 2));
        assertTrue(dataLoaderManager.isValidLoaderType(1, 3));
        assertTrue(dataLoaderManager.isValidLoaderType(2, 1));
        assertTrue(dataLoaderManager.isValidLoaderType(2, 2));
        assertTrue(dataLoaderManager.isValidLoaderType(2, 3));

        assertFalse(dataLoaderManager.isValidLoaderType(1, 0));
        assertFalse(dataLoaderManager.isValidLoaderType(1, 4));
        assertFalse(dataLoaderManager.isValidLoaderType(3, 1));
    }
}