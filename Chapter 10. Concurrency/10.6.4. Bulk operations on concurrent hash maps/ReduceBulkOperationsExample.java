import java.util.*;
import java.util.concurrent.*;

// Examples of reduce bulk operations on ConcurrentHashMap
public class ReduceBulkOperationsExample {
    public static void main(String[] args) throws InterruptedException {
        // -------------------------------------------------------------------
        ConcurrentHashMap<String, Long> wordCounts = new ConcurrentHashMap<>();
        // Populate with some data
        wordCounts.put("the", 150L);
        wordCounts.put("java", 45L);
        wordCounts.put("programming", 30L);
        wordCounts.put("concurrent", 25L);

        // Find most frequent word
        Map.Entry<String, Long> mostFrequent = wordCounts.reduceEntries(4,
                (e1, e2) -> e1.getValue() >= e2.getValue() ? e1 : e2);
        System.out.println("Most frequent: " + mostFrequent.getKey() + " (" + mostFrequent.getValue() + " times)");

        // Calculate average frequency
        double average = wordCounts.reduceValuesToDouble(4,
                Long::doubleValue,    // Transformer: Long to double
                0.0,                  // Base value
                Double::sum           // Combiner: sum all values
        ) / wordCounts.size();
        System.out.println("Average frequency: " + average);

        //-------------------------------------------------------------------
        ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);

        // Find maximum score
        Integer maxScore = scores.reduceValues(2, Integer::max);
        System.out.println("Highest score: " + maxScore); // Highest score: 95
        // Find who has the maximum score
        Map.Entry<String, Integer> topScorer = scores.reduceEntries(2,
                (e1, e2) -> e1.getValue() >= e2.getValue() ? e1 : e2
        );
        System.out.println("Top scorer: " + topScorer.getKey() + " - " + topScorer.getValue());

        //-------------------------------------------------------------
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("apple", 10L);
        map.put("banana", 5L);
        map.put("orange", 8L);
        // Sum all values
        Long total = map.reduceValues(2, Long::sum);
        System.out.println("Total: " + total); // Total: 23

        // Time execution comparison ------------------------------------------
        ConcurrentHashMap<String, Integer> largeMap = new ConcurrentHashMap<>();
        for(int i =0; i<10000000; i++){
            largeMap.put(String.valueOf(i), i);
        }
        // warm up (multiple threads)
        largeMap.reduceValues(1, Integer::sum);

        // Sequential (single thread)
        long start2 = System.currentTimeMillis();
        Integer seqResult = largeMap.reduceValues(100000000, Integer::sum);
        long seqTime = System.currentTimeMillis() - start2;
        // Parallel (multiple threads)
        long start = System.currentTimeMillis();
        Integer parResult = largeMap.reduceValues(1, Integer::sum);
        long parTime = System.currentTimeMillis() - start;

        System.out.println("Sequential: " + seqTime + "ms, Parallel: " + parTime + "ms");

        // This example shows how much threads were used and their names----
        ConcurrentHashMap<String, Integer> map2 = new ConcurrentHashMap<>();
        for (int i = 0; i < 5_000_000; i++) {
            map2.put("k" + i, i);
        }
        // A thread-safe set to remember which threads were involved
        Set<String> threadNames = new ConcurrentSkipListSet<>();

        System.out.println("Warming up...");
        map2.reduceValues(1,v -> {
            return v; // does nothing with the value
        }, Integer::max); // warm-up

        System.out.println("\n=== PARALLEL (threshold = 1) ===");
        map2.reduceValues(1, v -> {
            threadNames.add(Thread.currentThread().getName());
            return v;
        }, Integer::max);
        System.out.println("Threads used: " + threadNames.size());
        threadNames.forEach(System.out::println);

        System.out.println("\n=== SEQUENTIAL (threshold = 100_000_000) ===");
        threadNames.clear();
        map2.reduceValues(100_000_000, v -> {
            threadNames.add(Thread.currentThread().getName());
            return v;
        }, Integer::max);
        System.out.println("Threads used: " + threadNames.size());
        threadNames.forEach(System.out::println);
    }
}
