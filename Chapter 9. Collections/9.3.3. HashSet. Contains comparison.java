import java.util.*;

public class ContainsComparison {
    public static void main(String[] args) {
        // Comparison of ArrayList vs HashSet contains() performance
        List<String> arrayList = new ArrayList<>();
        Set<String> hashSet = new HashSet<>();
        // Add 1,000,000 elements
        for (int i = 0; i < 1_000_000; i++) {
            String value = "Element" + i;
            arrayList.add(value);
            hashSet.add(value);
        }
        // Search for element at the end
        long start = System.nanoTime();
        boolean arrayListResult = arrayList.contains("Element999999");
        long arrayListTime = System.nanoTime() - start;

        start = System.nanoTime();
        boolean hashSetResult = hashSet.contains("Element999999");
        long hashSetTime = System.nanoTime() - start;

        System.out.println("ArrayList contains: " + arrayListTime + " ns"); // ~O(n)
        System.out.println("HashSet contains: " + hashSetTime + " ns");     // ~O(1)
    }
}
