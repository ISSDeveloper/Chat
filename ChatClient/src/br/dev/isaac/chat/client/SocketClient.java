package br.dev.isaac.chat.client;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.dev.isaac.chat.reactive.Observable;

public class SocketClient {

	private Socket socket;
	private ExecutorService pool;
	private MessengerReader messengerReader;
	private MessengerWriter messengerWriter;

	public SocketClient() throws Exception {
		System.out.println("creating connection server...");

		socket = new Socket("10.100.0.106", 4288);
		pool = Executors.newCachedThreadPool();

		messengerReader = new MessengerReader(socket.getInputStream(), pool);
		messengerWriter = new MessengerWriter(socket.getOutputStream());

		System.out.println("conected!");
	}

	public Observable<String> reader() {
		return messengerReader.reader();
	}

	public void write(String msg) {
		messengerWriter.write(msg);
	}
}
