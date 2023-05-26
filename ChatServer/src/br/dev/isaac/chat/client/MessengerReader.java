package br.dev.isaac.chat.client;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

import br.dev.isaac.chat.reactive.Observable;
import br.dev.isaac.chat.reactive.Subject;

public class MessengerReader {

	private Scanner scanner;
	private Subject<String> subject;
	private ExecutorService pool;

	public MessengerReader(InputStream inputStream, ExecutorService pool) {
		this.pool = pool;
		this.scanner = new Scanner(inputStream);
		this.subject = new Subject<>();
	}

	public Observable<String> reader() {

		pool.execute(() -> {
			while (scanner.hasNextLine()) {
				subject.next(scanner.nextLine());
			}
		});

		return subject.asObservable();
	}
}
