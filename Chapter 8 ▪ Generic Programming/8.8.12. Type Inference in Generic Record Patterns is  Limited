```java
sealed interface JSONPrimitive<T> permits JSONNumber, JSONBoolean, JSONString {}

record JSONNumber(double value) implements JSONPrimitive<Double> {}
record JSONBoolean(boolean value) implements JSONPrimitive<Boolean> {}
record JSONString(String value) implements JSONPrimitive<String> {}

record Pair<T>(T first, T second) {
    public static <U> Pair<U> of(U first, U second) {
        return new Pair<U>(first, second);
    }
}

public class Draft {
    // Original method that allows mixed pairs through constructor
    public static Object sumOld(Pair<? extends JSONPrimitive<?>> pair) {
        return switch (pair) {
            case Pair(JSONNumber(var left), JSONNumber(var right)) -> left + right;
            case Pair(JSONBoolean(var left), JSONBoolean(var right)) -> left | right;
            case Pair(JSONString(var left), JSONString(var right)) -> left.concat(right);
            default -> "mixed types"; // This will be reached!
        };
    }
    // New method that prevents mixed pairs
    public static <T extends JSONPrimitive<U>, U> Object sumNew(Pair<T> pair) {
        return switch (pair) {
            case Pair(JSONNumber(var left), JSONNumber(var right)) -> left + right;
            case Pair(JSONBoolean(var left), JSONBoolean(var right)) -> left | right;
            case Pair(JSONString(var left), JSONString(var right)) -> left.concat(right);
            default -> throw new AssertionError(); // Should never happen with valid inputs
        };
    }
    public static void main(String[] args) {
        // Test 1: This should COMPILE with sumOld but NOT with sumNew
        System.out.println("=== Testing Pair.of with mixed types ===");

        // This line WILL NOT COMPILE with either method - and that's the point!
        // sumOld(Pair.of(new JSONNumber(42), new JSONString("Fred"))); // COMPILE ERROR
        // sumNew(Pair.of(new JSONNumber(42), new JSONString("Fred"))); // COMPILE ERROR
        System.out.println("Pair.of with mixed types: COMPILE ERROR (as expected)");
        // Test 2: This compiles with sumOld but gives runtime issue
        System.out.println("\n=== Testing raw constructor with mixed types ===");

        Pair<JSONPrimitive<?>> mixedPair = new Pair<>(new JSONNumber(42), new JSONString("Fred"));

        System.out.println("sumOld(mixedPair): " + sumOld(mixedPair)); // Works but returns "mixed types"
        // Test 3: This should NOT compile with sumNew
        System.out.println("\n=== Testing if sumNew prevents mixed pairs ===");

        //sumNew(mixedPair); // COMPILE ERROR - uncomment to see the error

        System.out.println("sumNew(mixedPair): COMPILE ERROR (this is what we want!)");
        // Test 4: Valid same-type pairs
        System.out.println("\n=== Testing valid same-type pairs ===");
        Pair<JSONNumber> numberPair = Pair.of(new JSONNumber(3), new JSONNumber(4));
        Pair<JSONString> stringPair = Pair.of(new JSONString("Hello"), new JSONString("World"));

        System.out.println("sumOld(numberPair): " + sumOld(numberPair)); // 7.0
        System.out.println("sumNew(numberPair): " + sumNew(numberPair)); // 7.0
        System.out.println("sumOld(stringPair): " + sumOld(stringPair)); // "HelloWorld"
        System.out.println("sumNew(stringPair): " + sumNew(stringPair)); // "HelloWorld"
    }
}
```
