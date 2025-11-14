import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// Usage example of LongAccumulator: 
public class TemperatureMonitor {
    private final LongAccumulator maxTemperature = new LongAccumulator(Math::max, -1000);
    private final LongAccumulator minTemperature = new LongAccumulator(Math::min, 1000);
    private final LongAccumulator totalReadings = new LongAccumulator(Long::sum, 0);
    private final LongAccumulator readingCount = new LongAccumulator(Long::sum, 0);

    public void reportTemperature(long temperature) {
        maxTemperature.accumulate(temperature);
        minTemperature.accumulate(temperature);
        totalReadings.accumulate(temperature);
        readingCount.accumulate(1);
    }
    public void printStats() {
        long count = readingCount.get();
        double average = count > 0 ? (double) totalReadings.get() / count : 0;

        System.out.println("Temperature Statistics:");
        System.out.println("  Maximum: " + maxTemperature.get() + "°C");
        System.out.println("  Minimum: " + minTemperature.get() + "°C");
        System.out.println("  Average: " + average + "°C");
        System.out.println("  Readings: " + count);
    }
    public static void main(String[] args) throws InterruptedException {
        TemperatureMonitor monitor = new TemperatureMonitor();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Simulate multiple sensors reporting temperatures
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                long temp = ThreadLocalRandom.current().nextLong(-10, 40);
                monitor.reportTemperature(temp);
            });
        }
        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        monitor.printStats();
    }
}
