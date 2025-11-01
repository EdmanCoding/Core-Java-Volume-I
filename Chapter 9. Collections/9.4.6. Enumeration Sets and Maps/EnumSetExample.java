import java.util.*;
enum Weekday { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

public class EnumSetExample {
    public static void main(String[] args) {
        // Create EnumSets
        EnumSet<Weekday> workday = EnumSet.range(Weekday.MONDAY, Weekday.FRIDAY);
        EnumSet<Weekday> weekend = EnumSet.of(Weekday.SATURDAY, Weekday.SUNDAY);
        EnumSet<Weekday> mwf = EnumSet.of(Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY);

        // Using Set interface methods to modify EnumSet
        // 1. add() - Add single element
        System.out.println("Before add: " + workday);
        workday.add(Weekday.SATURDAY);
        System.out.println("After adding SATURDAY: " + workday);

        // 2. addAll() - Add all elements from another set
        EnumSet<Weekday> allDays = EnumSet.noneOf(Weekday.class);
        allDays.addAll(workday);
        allDays.addAll(weekend);
        System.out.println("All days: " + allDays);

        // 3. remove() - Remove single element
        allDays.remove(Weekday.MONDAY);
        System.out.println("After removing MONDAY: " + allDays);

        // 4. removeAll() - Remove all elements from another set
        allDays.removeAll(weekend);
        System.out.println("After removing weekend: " + allDays);

        // 5. retainAll() - Keep only elements present in another set
        allDays.retainAll(mwf);
        System.out.println("After retaining only MWF: " + allDays);

        // 6. clear() - Remove all elements
        allDays.clear();
        System.out.println("After clear: " + allDays);

        // 7. contains() - Check if element exists
        System.out.println("workday contains MONDAY: " + workday.contains(Weekday.MONDAY));

        // 8. size() and isEmpty()
        System.out.println("workday size: " + workday.size());
        System.out.println("allDays is empty: " + allDays.isEmpty());
    }
}
