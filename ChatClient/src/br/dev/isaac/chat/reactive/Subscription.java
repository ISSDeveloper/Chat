package br.dev.isaac.chat.reactive;

public interface Subscription<T> {

	void apply(T value);
}
