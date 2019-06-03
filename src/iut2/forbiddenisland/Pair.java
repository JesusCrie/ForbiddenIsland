package iut2.forbiddenisland;

public class Pair<L, R> {

    private final L left;
    private final R right;

    public static <T, V> Pair<T, V> of(final T left, final V right) {
        return new Pair<>(left, right);
    }

    private Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
