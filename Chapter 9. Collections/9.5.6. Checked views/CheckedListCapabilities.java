import java.util.*;

public class CheckedListCapabilities {
    public static void main(String[] args) {
        List<String> original = new ArrayList<>();
        List<String> checked = Collections.checkedList(original, String.class);

        System.out.println("=== What You CAN Do ===");

        // ✅ All normal List operations with correct types
        checked.add("A");
        checked.addAll(List.of("B", "C"));
        checked.remove("A");
        checked.get(0);
        checked.size();
        System.out.println("checked: " + checked);
        System.out.println("original: " + original);

        // ✅ Assign to any Collection interface
        Collection<String> coll = checked;
        List<String> list = checked;
        Iterable<String> iter = checked;

        System.out.println("coll: " + coll);
        System.out.println("list: " + list);
        System.out.println("iter: " + iter);

        System.out.println("All interface assignments successful");
        System.out.println("\n=== What You CANNOT Do ===");

        // ❌ Cannot assign to concrete implementations
        // ArrayList<String> arrayList = checked;  // Compile error
        // LinkedList<String> linkedList = checked; // Compile error

        // ❌ Cannot add wrong types (caught at runtime)
        try {
            List raw = checked;
            raw.add(123);  // Throws ClassCastException
        } catch (ClassCastException e) {
            System.out.println("Wrong type caught: " + e.getMessage());
        }
    }
}
