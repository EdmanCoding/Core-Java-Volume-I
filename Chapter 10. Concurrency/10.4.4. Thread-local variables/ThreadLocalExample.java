//Simple Thread local example
public class ThreadLocalExample {
    // Each thread will have its own StringBuilder object
    private static final ThreadLocal<StringBuilder> THREAD_LOG = new ThreadLocal<>();
    public static void main(String[] args) {
        // Define a task that will run in multiple threads
        Runnable task = () -> {
            // Set up a thread-specific object
            THREAD_LOG.set(new StringBuilder());
            // Add data step by step, simulating method calls
            firstStep();
            secondStep();
            thirdStep();
            // Print the final result for this thread
            System.out.println(Thread.currentThread().getName() + ": " + THREAD_LOG.get().toString());
        };
        // Run the same task in two separate threads
        Thread thread1 = new Thread(task, "Thread-A");
        Thread thread2 = new Thread(task, "Thread-B");

        thread1.start();
        thread2.start();
    }
    private static void firstStep() {
        THREAD_LOG.get().append("Start -> ");
    }
    private static void secondStep() {
        THREAD_LOG.get().append("Processing -> ");
    }
    private static void thirdStep() {
        THREAD_LOG.get().append("Done!");
    }
}
