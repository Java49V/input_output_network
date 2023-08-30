package telran.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculatorTcpClient {
    static final String HOST = "localhost";
    static final int PORT = 5000;

    public static void main(String[] args) {
        try (TcpHandler tcpHandler = new TcpHandler(HOST, PORT);
				Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;

            List<String> operationTypes = new ArrayList<>();
            operationTypes.add("add");
            operationTypes.add("minus");
            operationTypes.add("multiply");
            operationTypes.add("divide");
            operationTypes.add("exit");

            while (!exit) {
                System.out.println("Select an operation type:");
                for (int i = 0; i < operationTypes.size(); i++) {
                    System.out.println((i + 1) + ". " + operationTypes.get(i));
                }

                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == operationTypes.size()) {
                    exit = true;
                    System.out.println("Exiting the client...");
                } else if (choice >= 1 && choice <= operationTypes.size() - 1) {
                    String operation = operationTypes.get(choice - 1);

                    System.out.println("Enter first number:");
                    double num1 = Double.parseDouble(scanner.nextLine());

                    System.out.println("Enter second number:");
                    double num2 = Double.parseDouble(scanner.nextLine());

                    double[] requestData = {num1, num2};
                    Serializable response = tcpHandler.send(operation, requestData);
                    System.out.println("Server response: " + response);
                } else {
                    System.out.println("Invalid choice. Please select again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
