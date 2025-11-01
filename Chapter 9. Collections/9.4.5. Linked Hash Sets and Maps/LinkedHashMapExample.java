import java.util.*;
/**You can inspect the eldest entry to decide whether to remove it. For example, you
may want to check a time stamp stored with eldest and only ask for removal if it is
sufficiently old. This will not automatically remove all old elements since the
removeEldestEntry method is only called once for each new entry. You are allowed to
modify the map in the removeEldestEntry method, for example by removing the initial
elements that are sufficiently old. In that case, you must return false.*/

class TimedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long MAX_AGE_MS = 5000; // 5 seconds
    private Map<K, Long> timestamps = new HashMap<>();

    public TimedLinkedHashMap() {
        super(16, 0.75f, true); // access-order mode
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        long currentTime = System.currentTimeMillis();
        // Option 2: Check if eldest entry is old enough
        Long eldestTime = timestamps.get(eldest.getKey());
        boolean shouldRemoveEldest = false;
        if (eldestTime != null) {
            long eldestAge = currentTime - eldestTime;
            if (eldestAge > MAX_AGE_MS) {
                timestamps.remove(eldest.getKey());
                shouldRemoveEldest = true; // Will be removed automatically
            }
        }
        // Option 3: Also remove ALL other old entries
        List<K> keysToRemove = new ArrayList<>();
        for (Map.Entry<K, Long> entry : timestamps.entrySet()) {
            // Skip eldest if we're already removing it via return true
            if (shouldRemoveEldest && entry.getKey().equals(eldest.getKey())) {
                continue;
            }
            long age = currentTime - entry.getValue();
            if (age > MAX_AGE_MS) {
                keysToRemove.add(entry.getKey());
            }
        }
        // Remove the collected old entries
        for (K key : keysToRemove) {
            super.remove(key);
            timestamps.remove(key);
        }
        // Return true to remove eldest, or false if we're not removing it
        return shouldRemoveEldest;
    }
    @Override
    public V put(K key, V value) {
        timestamps.put(key, System.currentTimeMillis());
        return super.put(key, value);
    }
    @Override
    public V remove(Object key) {
        timestamps.remove(key);
        return super.remove(key);
    }
}
// Usage example:
public class LinkedHashMapExample {
    public static void main(String[] args) throws InterruptedException {
        TimedLinkedHashMap<String, String> cache = new TimedLinkedHashMap<>();

        cache.put("key1", "value1");
        Thread.sleep(2000);
        cache.put("key2", "value2");
        Thread.sleep(2000);
        cache.put("key3", "value3");
        System.out.println("Before: " + cache);
        Thread.sleep(2000); // Now key1 is ~6s old (will be removed)
        cache.put("key4", "value4");

        System.out.println("After put key4: " + cache);
        // key1 removed (eldest + old)
        Thread.sleep(3000); // Now key2 is ~7s old, key3 is ~5s old

        cache.get("key2"); // Moves key2 to end
        cache.get("key3"); // Moves key3 to end
        // Now order: key4 (eldest), key2, key3
        System.out.println("After get: " + cache);

        cache.put("key5", "value5");
        // key4 is ~3s old (NOT removed by Option 2)
        // But key2 (~7s) and key3 (~5s) ARE removed by Option 3
        System.out.println("After put key5: " + cache);
    }
}
