package iut2.forbiddenisland.controller.observer;

/**
 * Derivation of the observable which will immediately pass the current value
 * of the wrapped object to every listener at registration.
 * This way, you can be sure that it will have a value.
 *
 * @param <T> - The type of wrapped object.
 */
public class NotifyOnSubscribeObservable<T> extends Observable<T> {

    public NotifyOnSubscribeObservable(final T initialValue) {
        super(initialValue);
    }

    /**
     * Extend the behaviour of subscribing by notifying the listener immediatly
     * after subscription.
     *
     * @param obs - The observer to notify on changes.
     */
    @Override
    public void subscribe(final Observer<T> obs) {
        super.subscribe(obs);
        obs.onChange(get());
    }
}
