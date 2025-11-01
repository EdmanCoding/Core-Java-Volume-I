import java.util.*;
public class UnmodifiableExample {
    // This is the "lookAt" method - it's a custom method YOU write
    public static void lookAt(List<String> list) {
        System.out.println("=== Inside lookAt method ===");
        // Can READ from the list
        System.out.println("First element: " + list.get(0));
        System.out.println("List size: " + list.size());
        System.out.println("Contains 'Alice': " + list.contains("Alice"));
        // Can ITERATE over the list
        System.out.print("All elements: ");
        for (String item : list) {
            System.out.print(item + " ");
        }
        System.out.println();

        // But CANNOT MODIFY the list
        try {
            list.add("Eve"); // This will throw exception
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add: " + e.getClass().getSimpleName());
        }
        try {
            list.remove(0); // This will also throw exception
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot remove: " + e.getClass().getSimpleName());
        }
    }
    public static void main(String[] args) {
        // Create the original mutable list
        var staff = new LinkedList<String>();
        staff.add("Alice");
        staff.add("Bob");
        staff.add("Charlie");

        System.out.println("Original list: " + staff);
        // Pass an unmodifiable view to the lookAt method
        lookAt(Collections.unmodifiableList(staff));
        // Original list is unchanged
        System.out.println("\nOriginal list after lookAt: " + staff);
    }
}
