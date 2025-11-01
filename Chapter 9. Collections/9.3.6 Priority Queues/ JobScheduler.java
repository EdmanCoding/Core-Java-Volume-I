import java.util.PriorityQueue;
/** A typical use for a priority queue is job scheduling. Each job has a priority. Jobs are
added in random order. Whenever a new job can be started, the highest priority job is
removed from the queue. (Since it is traditional for priority 1 to be the “highest”
priority, the remove operation yields the minimum element.)*/
// Job class with priority (1 = highest priority, 5 = lowest)
record Job(String name, int priority) implements Comparable<Job> {
    public int compareTo(Job other) {
        // Lower number = higher priority (priority 1 > priority 5)
        return Integer.compare(this.priority, other.priority);
    }
}
public class JobScheduler {
    public static void main(String[] args) {
        // Create priority queue - lower priority numbers come first
        PriorityQueue<Job> jobQueue = new PriorityQueue<>();
        // Add jobs in random order
        jobQueue.add(new Job("System Backup", 3));
        jobQueue.add(new Job("User Login", 5));
        jobQueue.add(new Job("Emergency Patch", 1)); // Highest priority!
        jobQueue.add(new Job("Database Update", 2));
        jobQueue.add(new Job("Report Generation", 4));

        System.out.println("Initial job queue (not in priority order):");
        for(Job job : jobQueue)
            System.out.println(job);

        System.out.println("\nProcessing jobs in priority order:");
        // Process jobs - highest priority first (lowest number)
        while (!jobQueue.isEmpty()) {
            Job nextJob = jobQueue.poll(); // Removes and returns highest priority job
            System.out.println("Processing: " + nextJob.name() +
                    " (Priority: " + nextJob.priority() + ")");
        }
    }
}
