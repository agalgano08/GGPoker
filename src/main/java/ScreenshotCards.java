import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ScreenshotCards {

	public final Rectangle moneyImage = new Rectangle(850, 57, 100, 25);
	public final Rectangle card1 = new Rectangle(780, 457, 23, 25);
	public final Rectangle card2 = new Rectangle(827, 457, 20, 25);
	public final Rectangle card3 = new Rectangle(854, 457, 20, 25);
	public final Rectangle card4 = new Rectangle(875, 457, 20, 25);
	public final Rectangle card5 = new Rectangle(889, 457, 20, 25);
	public final Rectangle dealerCard = new Rectangle(917, 255, 20, 24);
	public final Rectangle total = new Rectangle(813, 567, 30, 17);
	public final Rectangle betSpot = new Rectangle(700, 999, 43, 17);
	public final Rectangle[] cardArray = { card1, card2, card3, card4, card5};

	Robot robot;

	public ITesseract instance = new Tesseract();

	public ScreenshotCards() throws AWTException {
		robot = new Robot();
	}

	public float screenshotMoney() throws AWTException, IOException, TesseractException {
		float value = 0;

		// Screenshot Card
		BufferedImage screenShot = robot.createScreenCapture(moneyImage);
		ImageIO.write(screenShot, "png", new File("Money.png"));

		// Set value of Card
		String dir = System.getProperty("user.dir");
		String money = instance.doOCR(new File(dir + "\\Money.png")).replaceAll("[^\\d.]", "");
		value = Float.parseFloat(money);

		// System.out.println("Screenshot Money:" + "Money" + "Value: " + value);
		return value;
	}

	public float screenshotBetSpot() throws AWTException, IOException, TesseractException {
		float value = 0;

		// Screenshot Card
		BufferedImage screenShot = robot.createScreenCapture(betSpot);
		ImageIO.write(screenShot, "png", new File("BetSpot.png"));

		// Set value of Card
		String dir = System.getProperty("user.dir");
		String money = instance.doOCR(new File(dir + "\\BetSpot.png")).replaceAll("[^\\d.]", "");
		try {
			value = Float.parseFloat(money);
		} catch (Exception e) {
			value = (float) 0.00;
		}

		// System.out.println("Screenshot Money:" + "BetSpot" + "Value: " + value);
		return value;
	}

	public int screenshotCard(Rectangle location, String name) throws AWTException, IOException {
		int value = 0;

		// Screenshot Card
		BufferedImage screenShot = robot.createScreenCapture(location);
		ImageIO.write(screenShot, "png", new File("Image-" + name + ".png"));

		// Set value of Card
		String dir = System.getProperty("user.dir");
		String card = "10";
		try {
			card = instance.doOCR(new File(dir + "\\Image-" + name + ".png")).replaceAll("[^a-zA-Z0-9]", "");
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (card.equals("K") || card.equals("Q") || card.equals("J")) {
			value = 10;
		} else if (card.equals("A")) {
			value = 11;
		} else if (card.contentEquals("1s") || card.contentEquals("1S")) {
			value = 15;
		} else if (card.contentEquals("IO") || card.contentEquals("1O")) {
			value = 10;
		} else {
			try {
				value = Integer.parseInt(card);
			} catch (Exception e) {
				value = 10;
			}
		}

		System.out.println("Screenshot Card:" + name + "Value: " + value);
		return value;
	}

}
