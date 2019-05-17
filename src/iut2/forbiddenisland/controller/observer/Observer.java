package iut2.forbiddenisland.controller.observer;

public interface Observer<T> {

	/**
	 * Callback triggered when a change as been notified in any Observable
	 * which this Observer is subscribed to.
	 * @param value
	 */
	void onChange(T value);
}
