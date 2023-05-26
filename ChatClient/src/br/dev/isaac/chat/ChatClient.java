package br.dev.isaac.chat;

import java.awt.EventQueue;

import br.dev.isaac.chat.ui.UiPrincipal;

public class ChatClient {

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(() -> {
			try {
				UiPrincipal window = new UiPrincipal();
				window.setVisible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
