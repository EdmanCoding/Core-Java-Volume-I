import java.lang.ProcessHandle;
import java.util.Optional;

// This is the example of Optional, since textbook doesn't provide
// explanation of Optional class. Might be useful (Definitely in my case)
public class OptionalUsageExample {
    public static void main(String[] args) {
        String input = "  Java  ";

        Optional<String> result = Optional.ofNullable(input)
                .map(String::trim)          // "Java"
                .filter(s -> s.length() > 3) // true → keep
                .map(String::toUpperCase);   // "JAVA"
        System.out.println("Result is " + result.orElse("Empty") + "\n");

        // Optional with ProcessHandle.Info as described in the textbook
        ProcessHandle current = ProcessHandle.current();
        ProcessHandle.Info info = current.info();
        // Every method below returns an Optional<?> — never null!
        // Command used to start the process (e.g., "java")
        System.out.println("Command: " + info.command().orElse("N/A"));

        // Full command line (e.g., "java ProcessInfoDemo")
        System.out.println("Command line: " + info.commandLine().orElse("N/A"));

        // Arguments passed to the process (e.g., ["ProcessInfoDemo"])
        String argsStr = info.arguments()
                .map(arr -> String.join(", ", arr))
                .orElse("none");
        System.out.println("Arguments: [" + argsStr + "]");

        // User who owns the process
        System.out.println("User: " + info.user().orElse("Unknown"));

        // Process start time
        info.startInstant()
                .ifPresent(instant -> System.out.println("Started: " + instant));

        // Total CPU time used by the process
        info.totalCpuDuration()
                .ifPresent(duration -> System.out.println("CPU time: " + duration.toMillis() + " ms"));
    }
}
