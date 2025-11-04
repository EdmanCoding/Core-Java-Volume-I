import java.util.*;

public class SubListBasicExample {
    public static void main(String[] args) {
        List<String> staff = new ArrayList<>(Arrays.asList(
                "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank",
                "Grace", "Henry", "Ivy", "Jack", "Karen", "Leo"
        ));
        System.out.println("Original staff: " + staff);

        // Example 1: Copy first 10 elements to another list
        List<String> relocated = new ArrayList<>();
        relocated.addAll(staff.subList(0, 10));
        System.out.println("Relocated (first 10): " + relocated);

        // The subrange can also be a target of a mutating operation.
        // Example 2: Clear first 10 elements from original
        staff.subList(0, 10).clear();
        System.out.println("After clearing first 10: " + staff);
    }
}
/*
  Output:
  Original staff: [Alice, Bob, Charlie, Diana, Eve, Frank, Grace, Henry, Ivy, Jack, Karen, Leo]
  Relocated (first 10): [Alice, Bob, Charlie, Diana, Eve, Frank, Grace, Henry, Ivy, Jack]
  After clearing first 10: [Karen, Leo]
 */
