package iut2.forbiddenisland.controller.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private final List<Observer<T>> listeners = new ArrayList<>();
    protected T value;

    public Observable(final T initialValue) {
        set(initialValue);
    }

    /**
     * Set the value of the observable and notify any observer.
     *
     * @param value - The value to assign.
     */
    public void set(final T value) {
        this.value = value;
        notifyChanges();
    }

    /**
     * Get the wrapped value and nothing else.
     *
     * @return The wrapped value.
     */
    public T get() {
        return value;
    }

    /**
     * Notify a change on the wrapped object.
     * Calling this will trigger every listeners attached to it.
     */
    public void notifyChanges() {
        listeners.forEach(l -> l.onChange(value));
    }

    /**
     * Subscribe to any changes on the wrapped object.
     *
     * @param obs - The observer to notify on changes.
     */
    public void subscribe(final Observer<T> obs) {
        listeners.add(obs);
    }

    /**
     * Unsubscribe a previously subscribe observer. If it wasn't subscribed,
     * nothing happens.
     *
     * @param obs - The observer to unsubscribe.
     */
    public void unsubscribe(final Observer<T> obs) {
        listeners.remove(obs);
    }

}
