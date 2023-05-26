package br.dev.isaac.chat.client;

import java.io.OutputStream;
import java.io.PrintStream;

public class MessengerWriter {

	private PrintStream printStream;

	public MessengerWriter(OutputStream outputStream) {
		this.printStream = new PrintStream(outputStream, true);
	}

	public void write(String msg) {
		this.printStream.println(msg);
	}

}
