import java.util.*;

// removeIf works on any Collection
// replaceAll only works on List (since order matters for replacement)
public class RemoveIfReplaceAll {
    public static void main(String[] args) {
        //Example 1: Basic Example
        List<String> words = new ArrayList<>(Arrays.asList(
                "THE", "quick", "BROWN", "fox", "JUMPS", "over", "THE", "LAZY", "dog"
        ));
        System.out.println("Original list: " + words);
        // Remove all short words (length <= 3)
        words.removeIf(w -> w.length() <= 3);
        System.out.println("After removeIf (length <= 3): " + words);
        // Change all remaining words to lowercase
        words.replaceAll(String::toLowerCase);
        System.out.println("After replaceAll (toLowerCase): " + words + "\n");

        //Example 2: Working with Numbers
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Original numbers: " + numbers);
        // Remove even numbers
        numbers.removeIf(n -> n % 2 == 0);
        System.out.println("After removing even numbers: " + numbers);
        // Square all remaining numbers
        numbers.replaceAll(n -> n * n);
        System.out.println("After squaring: " + numbers + "\n");

        //Example 3: Custom Objects
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 17),
                new Person("Charlie", 30),
                new Person("Diana", 16),
                new Person("Eve", 22)
        ));
        System.out.println("Original people: " + people);
        // Remove minors (age < 18)
        people.removeIf(p -> p.getAge() < 18);
        System.out.println("After removing minors: " + people);
        // Increment everyone's age by 1
        people.replaceAll(p -> new Person(p.getName(), p.getAge() + 1));
        System.out.println("After birthday: " + people + "\n");

        //Example 4: Complex Conditions and Transformations
        List<String> sentences = new ArrayList<>(Arrays.asList(
                "Hello World",
                "Java Programming",
                "CollectionsFramework",
                "Lambda Expressions",
                "Stream API"
        ));
        System.out.println("Original: " + sentences);
        // Remove sentences that don't contain space (single words)
        sentences.removeIf(s -> !s.contains(" "));
        System.out.println("After removing single words: " + sentences);
        // Replace with word counts
        sentences.replaceAll(s -> s + " [" + s.split(" ").length + " words]");
        System.out.println("After adding word counts: " + sentences + "\n");

        //Example 5: Exception Handling and Side Effects
        List<String> mixedData = new ArrayList<>(Arrays.asList(
                "123", "456", "abc", "789", "def"
        ));
        System.out.println("Original: " + mixedData);
        // Remove elements that cannot be parsed as integers
        mixedData.removeIf(s -> {
            try {
                Integer.parseInt(s);
                return false; // Keep if parsable
            } catch (NumberFormatException e) {
                return true; // Remove if not parsable
            }
        });
        System.out.println("After removing non-numbers: " + mixedData);
        mixedData.replaceAll(s -> {
            int num = Integer.parseInt(s); // Convert to integers
            return String.valueOf(num * 2); // Double the numbers
        });
        System.out.println("After doubling: " + mixedData + "\n");

        //Example 6: Data Cleaning Pipeline
        List<String> userInput = new ArrayList<>(Arrays.asList(
                "  ALICE  ", "bob", "  ", "CHARLIE", "", "diana", "  EVE  "
        ));
        System.out.println("Raw input: " + userInput);
        // Step 1: Remove empty and whitespace-only strings
        userInput.removeIf(s -> s.trim().isEmpty());
        System.out.println("After removing empty: " + userInput);
        // Step 2: Trim whitespace
        userInput.replaceAll(String::trim);
        System.out.println("After trimming: " + userInput);
        // Step 3: Capitalize first letter, lowercase rest
        userInput.replaceAll(s ->
                s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()
        );
        System.out.println("After formatting: " + userInput +"\n");

        //Example 7: Performance Comparison
        List<Integer> numbers2 = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            numbers2.add(i);
        }
        // Traditional way (before Java 8)
        long start1 = System.currentTimeMillis();
        Iterator<Integer> iterator = numbers2.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }
        long time1 = System.currentTimeMillis() - start1;
        // Reset list
        numbers2.clear();
        for (int i = 0; i < 100000; i++) {
            numbers2.add(i);
        }
        // Modern way (with removeIf)
        long start2 = System.currentTimeMillis();
        numbers2.removeIf(n -> n % 2 == 0);
        long time2 = System.currentTimeMillis() - start2;

        System.out.println("Traditional iterator: " + time1 + " ms");
        System.out.println("removeIf: " + time2 + " ms");
        System.out.println("removeIf is " + (time1 / (double)time2) + "x faster");
    }
    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
}
