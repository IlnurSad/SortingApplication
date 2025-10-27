package ru.aston.finalproject.managers;

import ru.aston.finalproject.strategies.search.BinarySearchStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class SearchManager<T> {
    private BinarySearchStrategy<T> binaryStrategy;

    public void setBinarySearchStrategy(Comparator<? super T> comparator) {
        this.binaryStrategy = new BinarySearchStrategy<>(comparator);
    }

    public int performBinarySearch(List<? extends T> list, T key) {
        if (binaryStrategy == null) {
            throw new IllegalStateException("Binary search strategy not set");
        }
        return binaryStrategy.search(list, key);
    }

    public List<Integer> findAllOccurrences(List<? extends T> list, Predicate<T> predicate) throws InterruptedException {
        if (list.isEmpty()) return new ArrayList<>();

        int numThreads = 4;
        int chunkSize = Math.max(1, (list.size() + numThreads - 1) / numThreads);
        List<Callable<List<Integer>>> tasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, list.size());
            tasks.add(() -> {
                List<Integer> indices = new ArrayList<>();
                for (int j = start; j < end; j++) {
                    if (predicate.test(list.get(j))) {
                        indices.add(j);
                    }
                }
                return indices;
            });
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<List<Integer>>> results = executor.invokeAll(tasks);

        List<Integer> allIndices = new ArrayList<>();
        try {
            for (Future<List<Integer>> future : results) {
                allIndices.addAll(future.get());
            }
        } catch (ExecutionException e) {
            throw new RuntimeException("Error during search", e);
        } finally {
            executor.shutdown();
        }

        return allIndices;
    }

    public int countExactOccurrences(List<? extends T> list, T key) throws InterruptedException {
        if (list.isEmpty()) return 0;

        int numThreads = 4;
        int chunkSize = Math.max(1, (list.size() + numThreads - 1) / numThreads);
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, list.size());
            tasks.add(() -> {
                int count = 0;
                for (int j = start; j < end; j++) {
                    if (list.get(j).equals(key)) {
                        count++;
                    }
                }
                return count;
            });
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> results = executor.invokeAll(tasks);

        int totalCount = 0;
        try {
            for (Future<Integer> future : results) {
                totalCount += future.get();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException("Error during counting", e);
        } finally {
            executor.shutdown();
        }

        return totalCount;
    }
}