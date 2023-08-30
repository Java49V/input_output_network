package telran.net;

import java.io.IOException;

public class CalculatorTcpServer {
    static final int PORT = 5000;

    public static void main(String[] args) {
        try {
            ApplProtocol protocol = new CalculatorProtocol();
            TcpServer server = new TcpServer(PORT, protocol);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

