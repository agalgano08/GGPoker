import java.awt.AWTException;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.LinkedList;

import net.sourceforge.tess4j.TesseractException;

public class Game {

	static Card card1, card2, card3, card4, card5, dealerCard;
	static LinkedList<Card> cards;
	static ScreenshotCards screenshotCards;
	static Action action;
	static int cardScreenshotNumber = 2;

	static Hand hand;

	static float net = 0;
	static int bets = 1;
	static int loses = 0;
	static float preMoney;
	static float postMoney;

	static GUI gui = new GUI();

	public static void main(String[] args) throws AWTException, IOException, TesseractException, InterruptedException {
		screenshotCards = new ScreenshotCards();
		action = new Action();

		gui.start(265, 600, 370, 250);

		while (true) {
			System.out.println("=========== NEW GAME ===========");

			setupBets();

			play();

			while (screenshotCards.screenshotBetSpot() != 0.00) {
				action.click(action.standLocation);
			}

			postMoney = screenshotCards.screenshotMoney();
			net += postMoney - preMoney;
			calculateBets();

			System.out.print("Net:");
			System.out.printf("%.2f", net);
			System.out.println();
			System.out.println("Loses:" + loses);
			System.out.println("=========== Done ===========");
			Thread.sleep(3000);
		}

	}

	public static void play() throws AWTException, InterruptedException, IOException {
		setUpCardsandHand();
		while (!hand.bust) {

			System.out.println("Total:" + hand.total);
			try {
				setAction(hand.calculateMove());
			} catch (Exception e) {
				hand.bust = true;
				return;
			}

			switch (setAction(hand.calculateMove())) {

			case Hit:
				handleHit();
				break;

			case Stand:
				action.click(action.standLocation);
				Thread.sleep(100);
				action.click(action.standLocation);
				hand.bust = true;
				break;

			case Double:
				if (hand.cards.size() == 2) {
					action.click(action.doubleLocation);
					hand.bust = true;
				} else {
					handleHit();
				}
				break;

			case Split:
				if (hand.cards.size() == 2) {
					action.click(action.splitLocation);
					Thread.sleep(3000);
					play();
					System.out.println("Hand1Over");
					Thread.sleep(3000);
					setUpCardsandHand();
				}
				break;

			case Wait:
				break;
			}
			Thread.sleep(1000);

			if (cardScreenshotNumber == 5) {
				action.click(action.standLocation);
				hand.bust = true;
			}
		}
	}

	private static void setupBets() throws InterruptedException, AWTException, IOException, TesseractException {
		preMoney = screenshotCards.screenshotMoney();
		action.bet(bets);
		action.click(action.dealLocation);
		Thread.sleep(1000);
		action.click(action.dealLocation);
		Thread.sleep(3000);
		action.click(action.cancelInsuranceLocation);
		Thread.sleep(3000);
	}

	public static void setUpCardsandHand() throws AWTException, IOException {
		cardScreenshotNumber = 2;
		cards = new LinkedList<Card>();
		card1 = new Card(screenshotCards.screenshotCard(screenshotCards.card2, "Card1"));
		card2 = new Card(screenshotCards.screenshotCard(screenshotCards.card1, "Card2"));
		cards.add(card1);
		cards.add(card2);
		dealerCard = new Card(screenshotCards.screenshotCard(screenshotCards.dealerCard, "DealerCards"));
		hand = new Hand(cards, dealerCard);
	}

	private static void handleHit() throws InterruptedException, AWTException, IOException {
		action.click(action.hitLocation);
		Thread.sleep(100);
		action.click(action.hitLocation);
		Thread.sleep(6000);

		hand.addCard(new Card(screenshotCards.screenshotCard(screenshotCards.cardArray[cardScreenshotNumber],
				"Card" + (cardScreenshotNumber + 1) + "Value")));
		cardScreenshotNumber++;

	}

	public static void calculateBets() {
		if (preMoney > postMoney) {
			loses++;
		} else if (preMoney < postMoney) {
			loses = 0;
		}
		if (loses == 7) {
			loses = 0;
		}

		switch (loses) {
		case 0:
			bets = 1;
			break;
		case 1:
			bets = 1;
			break;
		case 2:
			bets = 2;
			break;
		case 3:
			bets = 4;
			break;
		case 4:
			bets = 8;
			break;
		case 5:
			bets = 16;
			break;
		case 6:
			bets = 32;
			break;

		}

	}

	public static Move setAction(String action) {

		if (action.equals("hit")) {
			return Move.Hit;
		} else if (action.equals("stand")) {
			return Move.Stand;
		} else if (action.equals("double")) {
			return Move.Double;
		} else if (action.equals("split")) {
			return Move.Split;
		} else {
			return Move.Wait;
		}
	}

}
