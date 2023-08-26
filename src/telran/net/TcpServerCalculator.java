package telran.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerCalculator {
    static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is listening to port " + PORT);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted client connection from: " + socket.getInetAddress());
                clientRun(socket);
            }
        } finally {
            serverSocket.close();
            System.out.println("Server socket closed");
        }
    }

    private static void clientRun(Socket socket) {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    System.out.println("Client closed normally connection");
                    break;
                }
                System.out.println("Received request: " + line);
                String response = getResponse(line);
                System.out.println("Sending response: " + response);
                writer.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client closed abnormally connection");
        }
    }

    private static String getResponse(String line) {
        String[] tokens = line.split("\\s+");
        if (tokens.length == 3) {
            return calculate(tokens);
        }
        return "Invalid request format, usage: <operator> <operand1> <operand2>";
    }

    private static String calculate(String[] tokens) {
        String operator = tokens[0];
        double operand1 = Double.parseDouble(tokens[1].replace(',', '.'));
        double operand2 = Double.parseDouble(tokens[2].replace(',', '.'));

        double result = switch (operator) {
            case "add" -> operand1 + operand2;
            case "subtract" -> operand1 - operand2;
            case "multiply" -> operand1 * operand2;
            case "divide" -> operand1 / operand2;
            default -> Double.NaN;
        };

        return Double.toString(result);
    }
}