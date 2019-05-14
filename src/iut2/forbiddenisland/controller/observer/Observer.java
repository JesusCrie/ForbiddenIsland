package iut2.forbiddenisland.controller.observer;

public interface Observer<T> {

	void onChange(T value);
}
