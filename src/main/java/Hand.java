import java.util.LinkedList;

public class Hand {

	LinkedList<Card> cards;
	LinkedList<Card> aces = new LinkedList<Card>();
	LinkedList<Card> nonAces = new LinkedList<Card>();
	StrategyTable table = new StrategyTable();
	Card dealerCard;
	int total;
	boolean bust;

	public Hand(LinkedList<Card> cards, Card dealerCard) {
		this.cards = cards;
		this.dealerCard = dealerCard;
		bust = false;
		setAces();
		calculateValue();
	}

	public int getValue() {
		return this.total;
	}

	public void addCard(Card c) {
		cards.add(c);
		setAces();
		calculateValue();
		if (total >= 21) {
			bust = true;
		}
	}

	public void setAces() {
		aces = new LinkedList<Card>();
		nonAces = new LinkedList<Card>();
		for (Card card : cards) {
			if (card.getValue() == 11) {
				aces.add(card);
			} else {
				nonAces.add(card);
			}
		}
	}

	public int determineScenario() {
		if (cards.size() == 2 && cards.get(0).equals(cards.get(1)) && cards.get(0).getValue() == 11
				&& cards.get(1).getValue() == 11) {
			return 4;
		} else if (cards.size() == 2 && cards.get(0).equals(cards.get(1))) {
			return 2;
		} else if (aces.size() != 0) {
			return 3;
		} else {
			return 1;
		}
	}

	public String calculateMove() {
		switch (determineScenario()) {
		case 1: // Regular Play
			return table.plays[total - 5][dealerCard.getValue() - 2];
		case 2: // Cards are Equal
			return table.plays[total / 2 + 23][dealerCard.getValue() - 2];
		case 3: // Aces
			if (calculateCards() >= 9) {
				return table.plays[total - 5][dealerCard.getValue() - 2];
			} else {
				return table.plays[calculateCards() + 15][dealerCard.getValue() - 2];
			}
		case 4: // Two Aces
			return table.plays[34][dealerCard.getValue() - 2];
		}
		return "wait";
	}

	public void calculateValue() {
		total = 0;
		for (Card card : nonAces) {
			total += card.getValue();
		}
		for (Card ace : aces) {
			if (total + 11 > 21) {
				total += 1;
			} else {
				total += 11;
			}
		}

		if (total >= 21) {
			bust = true;
		}
	}

	public int calculateCards() {
		int value = 0;
		for (Card card : nonAces) {
			value += card.getValue();
		}
		return value;
	}

}
