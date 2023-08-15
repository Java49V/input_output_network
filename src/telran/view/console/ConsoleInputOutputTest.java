package telran.view.console;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleInputOutputTest {
    private ConsoleInputOutput consoleIO = new ConsoleInputOutput();

    @Test
    void testReadLong() {
        long result = consoleIO.readLong("Enter a long: ", "Invalid input");
        assertEquals(1234567890L, result);
    }

    @Test
    void testReadLongInRange() {
        long result = consoleIO.readLong("Enter a long: ", "Invalid input", 1000L, 2000L);
        assertEquals(1500L, result);
    }
    
//    @Disabled
    @Test
    void testReadStringWithPredicate() {
        String result = consoleIO.readString("Enter 'yes' or 'no': ", "Invalid input", input -> input.equals("yes") || input.equals("no"));
        assertEquals("yes", result);
    }
    
//    @Disabled
    @Test
    void testReadStringWithOptions() {
        Set<String> options = new HashSet<>();
        options.add("mango");
        options.add("melon");
        options.add("grape");
        String result = consoleIO.readString("Enter a fruit: манго, виноград, дыня на английском", "Invalid input", options);
        assertEquals("grape", result);
    }
    
//    @Disabled
    @Test
    void testReadDate() {
        LocalDate result = consoleIO.readDate("Enter a date (yyyy-MM-dd): ", "Invalid date");
        assertEquals(LocalDate.of(2023, 8, 15), result);
    }

    @Test
    void testReadDateInRange() {
        LocalDate from = LocalDate.of(2023, 8, 1);
        LocalDate to = LocalDate.of(2023, 8, 31);
        LocalDate result = consoleIO.readDate("Enter a date (yyyy-MM-dd): ", "Invalid date", from, to);
        assertEquals(LocalDate.of(2023, 8, 10), result);
    }
    
//    @Disabled
    @Test
    void testReadDouble() {
        double result = consoleIO.readDouble("Enter a double: ", "Invalid input");
        assertEquals(3.14, result, 0.001);
    }
}
