package iut2.forbiddenisland.controller.observer;

/**
 * Derivation of the observable which will immediately triggers itself
 * upon creation to initialize its value.
 *
 * @param <T> - The type of wrapped object.
 */
public class NotifyOnCreateObservable<T> extends Observable<T> {

    public NotifyOnCreateObservable() {
        this(null);
    }

    public NotifyOnCreateObservable(final T initialValue) {
        super(initialValue);
        notifyChanges();
    }
}
