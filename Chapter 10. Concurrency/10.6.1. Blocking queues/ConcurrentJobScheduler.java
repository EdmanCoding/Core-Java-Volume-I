import java.util.concurrent.PriorityBlockingQueue;

// Example demonstrate the concurrent work of JobScheduler.java
// from "Chapter 9. Collections/9.3.6 Priority Queues"
// using PriorityBlockingQueue instead of PriorityQueue

// Job class with priority (1 = highest priority)
record Job(String name, int priority) implements Comparable<Job> {
    @Override
    public int compareTo(Job other) {
        // Lower number = higher priority
        return Integer.compare(this.priority, other.priority);
    }
}
public class ConcurrentJobScheduler {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Job> jobQueue = new PriorityBlockingQueue<>();

        // Producer thread: submits jobs in random order
        Thread producer = new Thread(() -> {
            try {
                jobQueue.put(new Job("System Backup", 3));
                System.out.println("Producer added: System Backup");

                Thread.sleep(300);

                jobQueue.put(new Job("User Login", 5));
                System.out.println("Producer added: User Login");

                Thread.sleep(300);

                jobQueue.put(new Job("Report Generation", 4));
                System.out.println("Producer added: Report Generation");

                Thread.sleep(300);

                jobQueue.put(new Job("Emergency Patch", 1));
                System.out.println("Producer added: Emergency Patch (HIGH)");

                Thread.sleep(300);

                jobQueue.put(new Job("Database Update", 2));
                System.out.println("Producer added: Database Update");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        // Consumer thread: processes jobs by priority
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Job job = jobQueue.take(); // blocks if empty
                    System.out.println("Consumer processing: " + job.name()
                            + " (Priority " + job.priority() + ")");
                    Thread.sleep(500); // simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();

        producer.join();
        Thread.sleep(2500);
        consumer.interrupt();
    }
}
