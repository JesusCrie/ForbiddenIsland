public interface Deck {

	abstract Card drawCard();

	abstract void reset();

	abstract void shuffle();

}