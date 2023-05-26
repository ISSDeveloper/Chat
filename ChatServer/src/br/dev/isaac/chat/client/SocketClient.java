package br.dev.isaac.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import br.dev.isaac.chat.reactive.Observable;

public class SocketClient {

	private Socket client;
	private ExecutorService pool;
	private MessengerReader mensagerReader;
	private MessengerWriter messengerWriter;

	public SocketClient(Socket client, ExecutorService pool) throws IOException {
		this.client = client;
		this.pool = pool;
		this.mensagerReader = new MessengerReader(client.getInputStream(), pool);
		this.messengerWriter = new MessengerWriter(client.getOutputStream());
	}

	public Observable<String> reader() {
		return mensagerReader.reader();
	}

	public void write(String msg) {
		this.messengerWriter.write(msg);
	}

	@Override
	public int hashCode() {
		return Objects.hash(client, mensagerReader, messengerWriter, pool);
	}
}
