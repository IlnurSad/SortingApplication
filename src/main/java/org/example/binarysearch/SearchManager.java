package org.example.binarysearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

class SearchManager<E extends Comparable<? super E>> {
    private SearchStrategy<E> strategy;

    public void setStrategy(SearchStrategy<E> strategy) {
        this.strategy = strategy;
    }

    public int performSearch(List<? extends E> list, E key) {
        if (strategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        return strategy.search(list, key);
    }

    public int countOccurrences(List<? extends E> list, E key, int numThreads) throws InterruptedException {
        if (list.isEmpty()) return 0;

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
