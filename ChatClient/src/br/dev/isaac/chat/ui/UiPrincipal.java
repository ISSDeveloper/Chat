package br.dev.isaac.chat.ui;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import br.dev.isaac.chat.client.SocketClient;

public class UiPrincipal {

	private SocketClient socket;

	private JFrame frame;
	private JTextField txNome;
	private JList<String> listMessager;
	private DefaultListModel<String> messager;
	private JScrollPane scrollPane;
	private JTextField txMessage;
	private JButton btnEnviar;

	public UiPrincipal() throws Exception {
		socket = new SocketClient();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 420, 483);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txNome = new JTextField();
		txNome.setBounds(10, 28, 384, 20);
		frame.getContentPane().add(txNome);
		txNome.setColumns(10);
		txNome.setText("Desconhecido");

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(lblNome);

		messager = new DefaultListModel<>();
		listMessager = new JList<>(messager);

		scrollPane = new JScrollPane(listMessager);
		scrollPane.setBounds(10, 54, 384, 348);
		frame.getContentPane().add(scrollPane);

		txMessage = new JTextField();
		txMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnEnviar.doClick();
				}
			}
		});
		txMessage.setToolTipText("");
		txMessage.setBounds(10, 413, 285, 20);
		frame.getContentPane().add(txMessage);
		txMessage.setColumns(10);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(e -> {
			if (!txMessage.getText().isEmpty() && !txMessage.getText().isBlank()) {
				String msg = txNome.getText() + ":  " + txMessage.getText();
				socket.write(msg);
				txMessage.setText("");
			}
		});
		btnEnviar.setBounds(305, 412, 89, 23);
		frame.getContentPane().add(btnEnviar);

		socket.reader().subscribe(msg -> {
			appendMessage(msg);
		});
	}

	private void appendMessage(String msg) {
		messager.addElement(msg);
		scrollToBottom();
	}

	public void setVisible(boolean flag) {
		frame.setVisible(flag);
	}

	private void scrollToBottom() {
		SwingUtilities.invokeLater(() -> {
			int lastIndex = messager.getSize() - 1;
			if (lastIndex >= 0) {
				listMessager.ensureIndexIsVisible(lastIndex);
			}
		});
	}
}
