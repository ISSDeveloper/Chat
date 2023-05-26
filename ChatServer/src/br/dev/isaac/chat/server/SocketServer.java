package br.dev.isaac.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.dev.isaac.chat.client.SocketClient;
import br.dev.isaac.chat.reactive.Subject;

public class SocketServer {

	private ServerSocket serverSocket;
	private ExecutorService pool;
	private Subject<String> dispatchMensager;

	public SocketServer() throws IOException {

		System.out.println("starting server...");

		serverSocket = new ServerSocket(4288);
		pool = Executors.newCachedThreadPool();
		dispatchMensager = new Subject<>();

		System.out.println("server ok!");
	}

	public void exe() throws IOException {
		for (;;) {
			Socket client = serverSocket.accept();
			SocketClient socketClient = new SocketClient(client, this.pool);

			System.out.println("connected client: " + client);

			socketClient.reader().subscribe(msg -> {
				dispatchMensager.next(msg);
			});

			dispatchMensager.subscribe(msg -> {
				socketClient.write(msg);
			});
		}
	}

}
