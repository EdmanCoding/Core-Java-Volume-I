import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
/**
 * My version of ExecutorDemo with changed "occurrences" method 
 * that gave me x6 performance improvement over original with usage of Virtual threads
 */
public class MyVersion {
    /**
     * Counts occurrences of a given word in a file.
     * @return the number of times the word occurs in the given word
     */
    public static long occurrences(String word, Path path) {
        try {
            String content = Files.readString(path);
            // Simple but memory-intensive for large files
            return countOccurrences(content, word);
        } catch (IOException ex) {
            return 0;
        }
    }
    public static long countOccurrences(String content, String word) {
        int count = 0;
        int index = 0;

        while ((index = content.indexOf(word, index)) != -1) {
            count++;
            index += word.length(); // Move past the found word
        }
        return count;
    }
    /**
     * Returns all descendants of a given directory--see Chapters 1 and 2 of Volume II.
     * @param rootDir the root directory
     * @return a set of all descendants of the root directory
     */
    public static Set<Path> descendants(Path rootDir) throws IOException {
        try (Stream<Path> entries = Files.walk(rootDir)) {
            return entries.filter(Files::isRegularFile)
                    .collect(Collectors.toSet());
        }
    }
    /**
     * Yields a task that searches for a word in a file.
     * @param word the word to search
     * @param path the file in which to search
     * @return the search task that yields the path upon success
     */
    public static Callable<Path> searchForTask(String word, Path path) {
        return () -> {
            try (var in = new Scanner(path)) {
                while (in.hasNext()) {
                    if (in.next().equals(word)) return path;
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Search in " + path + " canceled.");
                        return null;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }
    public static void main(String[] args)
            throws InterruptedException, ExecutionException, IOException {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk-21-src): ");
            String start = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String word = in.nextLine();

            Set<Path> files = descendants(Path.of(start));
            var tasks = new ArrayList<Callable<Long>>();
            for (Path file : files) {
                Callable<Long> task = () -> occurrences(word, file);
                tasks.add(task);
            }
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
            Instant startTime = Instant.now();
            List<Future<Long>> results = executor.invokeAll(tasks);
            Instant endTime = Instant.now();
            long total = 0;
            for (Future<Long> result : results)
                total += result.get();
            System.out.println("Occurrences of " + word + ": " + total);
            System.out.println("Time elapsed: "
                    + Duration.between(startTime, endTime).toMillis() + " ms");

            /*var searchTasks = new ArrayList<Callable<Path>>();
            for (Path file : files)
                searchTasks.add(searchForTask(word, file));
            startTime = Instant.now();
            Path found = executor.invokeAny(searchTasks);
            endTime = Instant.now();
            System.out.println(word + " occurs in: " + found);
            System.out.println("Time elapsed: "
                    + Duration.between(startTime, endTime).toMillis() + " ms");*/

            executor.close();
        }
    }
}
