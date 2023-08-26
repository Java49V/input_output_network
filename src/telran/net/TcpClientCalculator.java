package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class TcpClientCalculator {
    static final String HOST = "localhost";
    static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket(HOST, PORT);
             PrintStream writer = new PrintStream(socket.getOutputStream());
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            InputOutput io = new ConsoleInputOutput();
            Menu menu = new Menu("TCP Calculator Client", getItems(writer, reader));
            menu.perform(io);
        }
    }

    private static Item[] getItems(PrintStream writer, BufferedReader reader) {
        Item[] items = {
                Item.of("Add", io -> calculate(io, writer, reader, "add")),
                Item.of("Subtract", io -> calculate(io, writer, reader, "subtract")),
                Item.of("Multiply", io -> calculate(io, writer, reader, "multiply")),
                Item.of("Divide", io -> calculate(io, writer, reader, "divide")),
                Item.ofExit()
        };
        return items;
    }

    private static void calculate(InputOutput io, PrintStream writer, BufferedReader reader, String operator) {
        double operand1 = io.readDouble("Enter first operand", "Invalid input");
        double operand2 = io.readDouble("Enter second operand", "Invalid input");

        String request = String.format("%s %.2f %.2f", operator, operand1, operand2);
        System.out.println("Sending request: " + request);
        writer.println(request);

        try {
            String response = reader.readLine();
            System.out.println("Received response: " + response);
            io.writeLine("Result: " + response);
        } catch (Exception e) {
            io.writeLine("Error occurred while receiving response from server");
        }
    }
}