package telran.net;

import java.io.*;
import java.net.*;

public class TcpHandler implements Closeable {
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public TcpHandler(String host, int port) throws Exception {
		socket = new Socket(host, port);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void close() throws IOException {
		socket.close();

	}

	private static final int MAX_RECONNECT_ATTEMPTS = 3;
	private static final long RECONNECT_DELAY_MS = 5000;

	public <T> T send(String requestType, Serializable requestData) {
		Request request = new Request(requestType, requestData);
		for (int attempt = 0; attempt < MAX_RECONNECT_ATTEMPTS; attempt++) {
			try {
				output.writeObject(request);
				Response response = (Response) input.readObject();
				if (response.code() != ResponseCode.OK) {
					throw new RuntimeException(response.responseData().toString());
				}
				@SuppressWarnings("unchecked")
				T res = (T) response.responseData();
				return res;
			} catch (IOException e) {
				try {
					close(); // Close the current broken connection
					Thread.sleep(RECONNECT_DELAY_MS);
					socket = new Socket(socket.getInetAddress(), socket.getPort());
					output = new ObjectOutputStream(socket.getOutputStream());
					input = new ObjectInputStream(socket.getInputStream());
				} catch (InterruptedException | IOException reconnectException) {
					// If reconnection fails, just continue to the next loop iteration
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		throw new RuntimeException("Failed to send request after multiple attempts");
	}

//	public <T> T send(String requestType, Serializable requestData)  {
//		Request request = new Request(requestType, requestData);
//		try {
//			output.writeObject(request);
//			Response response = (Response) input.readObject();
//			if (response.code() != ResponseCode.OK) {
//				throw new RuntimeException(response.responseData().toString());
//			}
//			@SuppressWarnings("unchecked")
//			T res = (T) response.responseData();
//			return res;
//		} catch (Exception e) {
//			
//			throw new RuntimeException(e.getMessage());
//		}
//	}

}
