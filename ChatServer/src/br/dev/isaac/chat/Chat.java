package br.dev.isaac.chat;

import java.io.IOException;

import br.dev.isaac.chat.server.SocketServer;

public class Chat {

	public static void main(String[] args) throws IOException {
		SocketServer server = new SocketServer();
		server.exe();
	}
}
