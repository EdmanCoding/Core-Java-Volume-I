import java.util.concurrent.*;

public class FutureTaskExamples {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> expensiveCalculation = () -> {
            System.out.println("Starting heavy calculation...");
            Thread.sleep(2000); // Simulate 2 seconds of work
            System.out.println("Calculation complete!");
            return 42; // The answer to everything!
        };

        // Wrap Callable in FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(expensiveCalculation);

        // Run it in a thread (FutureTask implements Runnable)
        Thread workerThread = new Thread(futureTask);
        workerThread.start();

        // Main thread can do other work here
        System.out.println("Main thread doing other work...");
        Thread.sleep(1000);
        System.out.println("Still working...");

        // Get the result (blocks until available)
        Integer result = futureTask.get();
        System.out.println("Final result: " + result+"\n");

        // Example 2
        Callable<String> fetchData = () -> {
            Thread.sleep(5000); // Simulate slow network call
            return "Data from server";
        };
        FutureTask<String> futureTask2 = new FutureTask<>(fetchData);
        Thread worker = new Thread(futureTask);
        worker.start();

        try {
            // Wait max 2 seconds for result
            String result2 = futureTask2.get(2, TimeUnit.SECONDS);
            System.out.println("Got: " + result2);
        } catch (TimeoutException e) {
            System.out.println("Task took too long! Cancelling...");
            futureTask2.cancel(true); // Interrupt the task
        }

        // Example 3 - Create a Runnable (no return value)
        Runnable fileProcessor = () -> {
            System.out.println("Processing files...");
            try {
                // Simulate file processing
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(500);
                    System.out.println("Processed file " + i);
                }
                System.out.println("All files processed!");
            } catch (InterruptedException e) {
                System.out.println("File processing interrupted!");
            }
        };

        FutureTask<Void> fileTask = new FutureTask<>(fileProcessor, null);
        Thread fileWorker = new Thread(fileTask);
        fileWorker.start();

        // Main thread can monitor completion
        while (!fileTask.isDone()) {
            System.out.println("Files still processing...");
            Thread.sleep(1000);
        }

        System.out.println("File processing task completed!");
        fileTask.get(); // Immediately returns null since we know it's done
    }
}
