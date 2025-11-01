import java.util.*;
/**The newSetFromMap method is also useful with WeakHashMap (see Section 9.4.4)*/
public class WeakHashSetExample {
    public static void main(String[] args) {
        // Create a set backed by WeakHashMap
        Set<String> weakSet = Collections.newSetFromMap(new WeakHashMap<>());
        // Add some elements
        String element1 = new String("Hello");
        String element2 = new String("World");
        String element3 = new String("Java");

        weakSet.add(element1);
        weakSet.add(element2);
        weakSet.add(element3);

        System.out.println("Initial set: " + weakSet);
        System.out.println("Size: " + weakSet.size()); // 3
        // Remove strong references to some elements
        element1 = null;
        element2 = null;
        // Suggest garbage collection
        System.gc();
        // Wait a bit for GC to potentially run
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        System.out.println("After GC (may vary): " + weakSet);
        System.out.println("Size after GC: " + weakSet.size()); // May be 1
    }
}
