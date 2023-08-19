package telran.view;

import java.util.function.BinaryOperator;

public class NumberOperations {
    
    public static void calculate(InputOutput io, BinaryOperator<Double> operator) {
        double first = io.readDouble("Enter first number", "Must be a number");
        double second = io.readDouble("Enter second number", "Must be a number");
        io.writeLine("Result: " + operator.apply(first, second));
    }
    
    public static Item[] getItems() {
        Item[] items = {
            Item.of("Add numbers", io -> calculate(io, (a, b) -> a + b)),
            Item.of("Subtract numbers", io -> calculate(io, (a, b) -> a - b)),
            Item.of("Multiply numbers", io -> calculate(io, (a, b) -> a * b)),
            Item.of("Divide numbers", io -> calculate(io, (a, b) -> a / b)),
            Item.ofExit()
        };
        return items;
    }
}
