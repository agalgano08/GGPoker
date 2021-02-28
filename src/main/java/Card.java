
public class Card {
	int value;

	public Card(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) {
			return false;
		}
		Card c = (Card) o;
		if (this.getValue() == c.getValue()) {
			return true;
		} else
			return false;

	}

}
