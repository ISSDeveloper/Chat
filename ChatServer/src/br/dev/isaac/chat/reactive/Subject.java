package br.dev.isaac.chat.reactive;

import java.util.List;
import java.util.Vector;

public class Subject<T> {

	private List<Observable<T>> observers;
	private Observable<T> defaultObservable;

	public Subject() {
		observers = new Vector<>();
		defaultObservable = new Observable<>();
		observers.add(defaultObservable);
	}

	public void next(T value) {
		synchronized (observers) {
			for (Observable<T> observable : observers) {
				observable.next(value);
			}
		}
	}

	public Observable<T> asObservable() {
		Observable<T> observable = new Observable<>();
		observers.add(observable);
		return observable;
	}

	public void subscribe(Subscription<T> subscription) {
		defaultObservable.subscribe(subscription);
	}
}
