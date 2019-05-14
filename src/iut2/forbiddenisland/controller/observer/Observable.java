package iut2.forbiddenisland.controller.observer;

import java.util.*;

public class Observable<T> {

	private final List<Observer<T>> listeners = new ArrayList<>();
	private T value;

	public Observable(final T initialValue) {
		set(initialValue);
	}

	public void set(final T value) {
		listeners.forEach(l -> l.onChange(value));
		this.value = value;
	}

	public T get() {
		return value;
	}

	public void subscribe(final Observer obs) {
		listeners.add(obs);
	}

	/**
	 * 
	 * @param osb
	 */
	public void unsubscribe(final Observer obs) {
		listeners.remove(obs);
	}

}
