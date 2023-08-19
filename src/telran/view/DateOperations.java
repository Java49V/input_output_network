package telran.view;

import java.time.Duration;
import java.time.LocalDate;

public class DateOperations {

    public static void daysAfter(InputOutput io) {
        LocalDate date = io.readDate("Enter a date", "Invalid date");
        int days = io.readInt("Enter the number of days to add", "Invalid number of days");
        io.writeLine("New date: " + date.plusDays(days));
    }

    public static void daysBefore(InputOutput io) {
        LocalDate date = io.readDate("Enter a date", "Invalid date");
        int days = io.readInt("Enter the number of days to subtract", "Invalid number of days");
        io.writeLine("New date: " + date.minusDays(days));
    }

    public static void daysBetween(InputOutput io) {
        LocalDate firstDate = io.readDate("Enter the first date", "Invalid date");
        LocalDate secondDate = io.readDate("Enter the second date", "Invalid date");
        long daysBetween = Duration.between(firstDate.atStartOfDay(), secondDate.atStartOfDay()).toDays();
        io.writeLine("Days between the two dates: " + daysBetween);
    }

    public static Item[] getItems() {
        Item[] items = {
            Item.of("Date after a given number of days", io -> daysAfter(io)),
            Item.of("Date before a given number of days", io -> daysBefore(io)),
            Item.of("Days between two dates", io -> daysBetween(io)),
            Item.ofExit()
        };
        return items;
    }
}
