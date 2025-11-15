import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueExample {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        /*
         * -----------------------------------------------------
         * CASE 1 — Consumer IS waiting → immediate handoff
         * -----------------------------------------------------
         */
        Thread waitingConsumer = new Thread(() -> {
            try {
                System.out.println("[Case 1] Consumer is waiting in take()...");
                String item = queue.take(); // will block immediately
                System.out.println("[Case 1] Consumer received (handoff): " + item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread case1Producer = new Thread(() -> {
            try {
                Thread.sleep(300); // give consumer time to block
                System.out.println("[Case 1] Producer transferring 'X'...");
                queue.transfer("X"); // immediate handoff
                System.out.println("[Case 1] Producer unblocked after X was taken.\n");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        waitingConsumer.start();
        case1Producer.start();
        waitingConsumer.join();
        case1Producer.join();
        /*
         * -----------------------------------------------------
         * Now queue is empty: preload for Case 2
         * -----------------------------------------------------
         */
        queue.put("A");
        queue.put("B");
        queue.put("C");
        System.out.println("Queue preloaded for Case 2: A, B, C\n");
        /*
         * -----------------------------------------------------
         * CASE 2 — No consumer waiting → goes to TAIL + blocks
         * -----------------------------------------------------
         */
        Thread case2Producer = new Thread(() -> {
            try {
                System.out.println("[Case 2] Producer transferring 'Y'...");
                // No consumer waiting → goes to tail and producer blocks
                queue.transfer("Y");
                System.out.println("[Case 2] Producer unblocked after Y was taken.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread delayedConsumer = new Thread(() -> {
            try {
                Thread.sleep(1200); // ensure producer blocks
                System.out.println("[Case 2] Consumer is now ready...");

                System.out.println("[Case 2] Consumer took: " + queue.take()); // A
                System.out.println("[Case 2] Consumer took: " + queue.take()); // B
                System.out.println("[Case 2] Consumer took: " + queue.take()); // C
                System.out.println("[Case 2] Consumer took: " + queue.take()); // Y (unblocks producer)

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        case2Producer.start();
        delayedConsumer.start();

        case2Producer.join();
        delayedConsumer.join();
    }
}
