// Example showing the difference of synchronized method and synchronized on object code block
class Bank {
    private final Object lock = new Object();

    // Method using its own private lock
    public void transfer() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " entered transfer()");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println(Thread.currentThread().getName() + " leaving transfer()");
        }
    }
    // Synchronized method (locks on 'this')
    public synchronized void syncTransfer() {
        System.out.println(Thread.currentThread().getName() + " entered syncTransfer()");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println(Thread.currentThread().getName() + " leaving syncTransfer()");
    }
}
public class LockDemo {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Thread t1 = new Thread(() -> {
            synchronized (bank) {
                System.out.println("T1 locked bank externally");
                try { Thread.sleep(4000); } catch (InterruptedException e) {}
                System.out.println("T1 released bank");
            }
        }, "T1");

        Thread t2 = new Thread(() -> bank.transfer(), "T2");
        Thread t3 = new Thread(() -> bank.syncTransfer(), "T3");

        t1.start();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        t2.start();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        t3.start();
    }
}
