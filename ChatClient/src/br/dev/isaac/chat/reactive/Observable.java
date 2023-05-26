package br.dev.isaac.chat.reactive;

import java.util.List;
import java.util.Vector;

public class Observable<T> {

	private List<Subscription<T>> registrations = new Vector<>();

	public void subscribe(Subscription<T> subscription) {
		registrations.add(subscription);
	}

	public void next(T value) {
		synchronized (registrations) {
			for (Subscription<T> subscription : registrations) {
				subscription.apply(value);
			}
		}
	}
}
