package iut2.forbiddenisland.controller.observer;

/**
 * An {@link Observable} that is made to be thrown away almost immediately.
 * It will deliver the initial value to the subscriber and that's all.
 *
 * @param <T> - The type of the observable.
 */
public class OneTimeObservable<T> extends Observable<T> {

    public OneTimeObservable() {
        super();
    }

    public OneTimeObservable(final T initialValue) {
        super(initialValue);
    }

    @Override
    public void subscribe(final Observer<T> obs) {
        obs.onChange(value);
    }
}
