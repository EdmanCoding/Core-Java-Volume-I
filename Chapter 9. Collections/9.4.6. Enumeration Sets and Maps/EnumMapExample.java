import java.util.*;
enum Weekday { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

class Employee {
    private String name;
    private String role;

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }
    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Employee other = (Employee) o;
        return name.equals(other.name) && role.equals(other.role);
    }
}
public class EnumMapExample {
    public static void main(String[] args) throws InterruptedException {
        // Create EnumMap
        EnumMap<Weekday, Employee> personInCharge = new EnumMap<>(Weekday.class);

        // Put entries into the map
        personInCharge.put(Weekday.MONDAY, new Employee("Alice", "Manager"));
        personInCharge.put(Weekday.TUESDAY, new Employee("Bob", "Developer"));
        personInCharge.put(Weekday.WEDNESDAY, new Employee("Charlie", "Designer"));
        personInCharge.put(Weekday.THURSDAY, new Employee("Diana", "Tester"));
        personInCharge.put(Weekday.FRIDAY, new Employee("Eve", "Developer"));

        // Access values
        System.out.println("Monday's person: " + personInCharge.get(Weekday.MONDAY));
        System.out.println("Tuesday's person: " + personInCharge.get(Weekday.TUESDAY));

        // Check if key exists
        System.out.println("Contains SATURDAY key: " + personInCharge.containsKey(Weekday.SATURDAY));

        // Iterate through the EnumMap
        System.out.println("\nWeekly schedule:");
        for (Weekday day : personInCharge.keySet()) {
            System.out.println(day + ": " + personInCharge.get(day));
        }

        // Using entrySet for iteration
        System.out.println("\nUsing entrySet:");
        for (var entry : personInCharge.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Update an entry
        personInCharge.put(Weekday.MONDAY, new Employee("Frank", "Senior Manager"));
        System.out.println("\nUpdated Monday: " + personInCharge.get(Weekday.MONDAY));

        // Remove an entry
        personInCharge.remove(Weekday.TUESDAY);
        System.out.println("After removing Tuesday, size: " + personInCharge.size());

        // Check if value exists
        Employee testEmployee = new Employee("Charlie", "Designer");
        System.out.println("Contains Charlie: " + personInCharge.containsValue(testEmployee));
        // Note: containsValue uses equals() method, so you'd need to implement equals() in Employee for this to work properly
    }
}
