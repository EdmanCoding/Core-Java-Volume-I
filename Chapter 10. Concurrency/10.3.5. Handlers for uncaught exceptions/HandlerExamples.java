import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class HandlerExamples {
    public static void main(String[] args) {
        // Replacement handler that logs to file
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            try {
                Files.write(
                        Path.of("thread_errors.log"),
                        List.of("[" + new Date() + "] Thread '" + thread.getName() + "' failed: " + exception),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Create a custom exception handler
        Thread.UncaughtExceptionHandler customHandler = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("🚨 Thread '" + t.getName() + "' crashed!");
                System.out.println("   Error: " + e.getMessage());
                e.printStackTrace();
            }
        };
        // Handler for database threads
        Thread.UncaughtExceptionHandler dbHandler = (thread, exception) -> {
            System.out.println("DATABASE THREAD FAILED: " + thread.getName() + " " + exception);
            // Maybe restart database connection
        };
        // Handler for web service threads
        Thread.UncaughtExceptionHandler webHandler = (thread, exception) -> {
            System.out.println("WEB THREAD FAILED: " + thread.getName()+ " " + exception.getMessage());
            // Maybe send alert to monitoring system
        };

        // Multiple threads that might crash
        Thread t1 = new Thread(() -> { throw new RuntimeException("Task 1 failed"); });
        Thread t2 = new Thread(() -> { throw new RuntimeException("Task 2 failed"); });
        t1.start();
        t2.start();

        // Use with custom exception handler
        Thread worker = new Thread(() -> {
            throw new RuntimeException("Database connection failed!");
        });
        worker.setUncaughtExceptionHandler(customHandler);
        worker.start();

        // Use custom DB and Web threads
        Thread dbThread = new Thread(() -> {
            throw new RuntimeException("SQL Syntax Error");
        });
        dbThread.setUncaughtExceptionHandler(dbHandler);
        Thread webThread = new Thread(() -> {
            throw new RuntimeException("HTTP Timeout");
        });
        webThread.setUncaughtExceptionHandler(webHandler);

        dbThread.start();
        webThread.start();
    }
}
