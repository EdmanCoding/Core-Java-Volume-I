import java.util.*;

public class SimpleLRUCache {
    public static void main(String[] args) {
        // Simple cache that keeps only 3 most recent elements
        Set<String> cache = Collections.newSetFromMap(
                new LinkedHashMap<String, Boolean>() {
                    @Override
                    protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
                        return size() > 3;  // Keep only 3 elements
                    }
                }
        );
        System.out.println("=== Simple LRU Cache (Size: 3) ===");
        // Add elements
        cache.add("Apple");
        cache.add("Banana");
        cache.add("Cherry");
        System.out.println("After adding 3 elements: " + cache);
        // Add one more - removes the oldest
        cache.add("Date");
        System.out.println("After adding 'Date': " + cache);
        // Add another
        cache.add("Elderberry");
        System.out.println("After adding 'Elderberry': " + cache);
        // Add one more
        cache.add("Fig");
        System.out.println("After adding 'Fig': " + cache);
    }
}
