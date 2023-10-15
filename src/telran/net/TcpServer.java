package telran.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer implements Runnable {
    private int port;
    private ApplProtocol protocol;
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public TcpServer(int port, ApplProtocol protocol, int numThreads) throws IOException {
        this.port = port;
        this.protocol = protocol;
        serverSocket = new ServerSocket(port);
        executor = Executors.newFixedThreadPool(numThreads);
    }

    @Override
    public void run() {
        System.out.println("Server is listening on port " + port);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                TcpClientServer clientServer = new TcpClientServer(socket, protocol);
                executor.execute(clientServer); // Submit the task to the thread pool
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//import java.io.IOException;
//import java.net.*;
//public class TcpServer implements Runnable {
//	private int port;
//	private ApplProtocol protocol;
//	private ServerSocket serverSocket;
//	public TcpServer(int port, ApplProtocol protocol) throws IOException {
//		this.port = port;
//		this.protocol = protocol;
//		serverSocket = new ServerSocket(port);
//	}
//
//	@Override
//	public void run() {
//		System.out.println("Server is listening on port " + port);
//		try {
//			while(true) {
//				Socket socket = serverSocket.accept();
//				TcpClientServer clientServer = new TcpClientServer(socket, protocol);
//				clientServer.run();
//			}
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
