import java.util.concurrent.*;

// Safely published example of the BankAccount from the book
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    public synchronized double getBalance() {
        return balance;
    }
    public synchronized void deposit(double amount) {
        balance += amount;
    }
    public synchronized void withdraw(double amount) {
        balance -= amount;
    }
}
public class SafeBankAccountExample {
    private static BankAccount account;
    public static synchronized void initAccount() {
        account = new BankAccount(1000);   // SAFE PUBLICATION
    }
    public static synchronized BankAccount getAccount() {
        return account;
    }

    public static void main(String[] args) throws InterruptedException {
        initAccount();                     // publish safely

        BankAccount acc = getAccount();    // safely read
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(() -> {
            System.out.println(acc.getBalance());
        });
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Optional: do other work while waiting
            Thread.sleep(100);
        }
    }
}
