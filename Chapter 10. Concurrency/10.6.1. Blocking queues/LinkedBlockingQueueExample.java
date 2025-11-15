import java.util.concurrent.*;
// LinkedBlockingQueue and LinkedBlockingDeque example
public class LinkedBlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
        // LinkedBlockingQueue with a capacity limit of 3
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        // Producer adding items
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String item = "Item-" + i;
                    System.out.println("Producer trying to put: " + item);
                    queue.put(item); // blocks if queue is full
                    System.out.println("Producer put: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        // Consumer removing items
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(1000); // wait so queue becomes full
                while (true) {
                    System.out.println("Consumer trying to take...");
                    String item = queue.take(); // blocks if empty
                    System.out.println("Consumer took: " + item);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        // LinkedBlockingDeque usage (double-ended)
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("A");
        deque.putLast("B");
        deque.putFirst("C"); // push to front
        deque.putLast("D");
        System.out.println("Deque content after inserts: " + deque);
        System.out.println("Removed from front: " + deque.takeFirst());
        System.out.println("Removed from back: " + deque.takeLast());
        System.out.println("Deque content: " + deque);

        producer.start();
        consumer.start();

        producer.join();
        Thread.sleep(3000);
        consumer.interrupt();
    }
}
