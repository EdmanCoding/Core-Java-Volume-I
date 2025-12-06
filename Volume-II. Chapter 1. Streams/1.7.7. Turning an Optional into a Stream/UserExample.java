import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

// The minimal implementation of the code snippets from the textbook
// ---- Minimal User class ----
record User(String id) {
    public String toString() {
        return "User{id='" + id + "'}";
    }
}
// ---- Minimal Users helper ----
class Users {
    // Simulate a small in-memory "database"
    private static final Set<String> validIds = Set.of("u1", "u2", "u3");
    // Returns Optional<User>: non-empty if ID is valid, empty if not
    public static Optional<User> lookup(String id) {
        if (validIds.contains(id)) {
            return Optional.of(new User(id));
        } else {
            return Optional.empty();
        }
    }
}
public class UserExample {
    public static void main(String[] args) {
        // A few sample user IDs
        Stream<String> ids = Stream.of("u1", "u2", "bad", "u3", "missing");

        // Using not recommended isPresent and get methods
        Stream<User> users = ids.map(Users::lookup)          // Stream<Optional<User>>
                .filter(Optional::isPresent) // keep only Optional<User> that have a value
                .map(Optional::get);         // unwrap Optional<User> → User

        ids = Stream.of("u1", "u2", "bad", "u3", "missing"); // recreate stream
        // More elegant option
        Stream<User> users2 = ids.map(Users::lookup)
                .flatMap(Optional::stream);

        // Print the resulting users
        users.forEach(System.out::println);
        users2.forEach(System.out::println);
    }
}
