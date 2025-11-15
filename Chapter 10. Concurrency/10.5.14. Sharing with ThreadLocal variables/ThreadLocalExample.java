// Example from 10.4.4. but using ThreadLocal.withInitial
public class ThreadLocalExample {
    // Each thread will have its own StringBuilder object
    private static final ThreadLocal<StringBuilder> THREAD_LOG =
            ThreadLocal.withInitial(StringBuilder::new);
    public static void main(String[] args) {
        // Define a task that will run in multiple threads
        Runnable task = () -> {
            // Don't need to set new StringBuilder now
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
        System.out.println(Thread.currentThread().getName() + ": " +"First step");
    }
    private static void secondStep() {
        THREAD_LOG.get().append("Processing -> ");
        System.out.println(Thread.currentThread().getName() + ": " +"Second step");
    }
    private static void thirdStep() {
        THREAD_LOG.get().append("Done!");
        System.out.println(Thread.currentThread().getName() + ": " +"Third step");
    }
}
