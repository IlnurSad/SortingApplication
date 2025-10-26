package ru.aston.finalproject.managers;


import ru.aston.finalproject.interfaces.SortStrategy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SortingManager<T> {
    private final ExecutorService executor;

    public SortingManager(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public Future<Void> sortAsync(List<T> list, SortStrategy<T> strategy) {
        return executor.submit(() -> {
            strategy.sort(list);
            return null;
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}