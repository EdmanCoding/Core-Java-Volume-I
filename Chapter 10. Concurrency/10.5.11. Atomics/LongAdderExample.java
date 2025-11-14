import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
//working example of the LongAdder class from the book
public class LongAdderExample {
    public static void main(String[] args) throws InterruptedException {

        int THREADS = 8;            // number of worker threads
        int INCREMENTS_PER_THREAD = 100_000; // work per thread

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        LongAdder adder = new LongAdder();

        for (int t = 0; t < THREADS; t++) {
            executor.submit(() -> {
                for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                    // some work...
                    if (i % 2 == 0) {  // just a condition to mimic the book example
                        adder.increment(); // fast and contention-friendly
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long total = adder.sum();

        System.out.println("Expected: " + (THREADS * (INCREMENTS_PER_THREAD / 2)));
        System.out.println("LongAdder sum(): " + total);
    }
}
