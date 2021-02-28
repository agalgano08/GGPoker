import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ScreenshotTest {

	public static void main(String[] args) throws IOException, AWTException, TesseractException {

		final Rectangle moneyImage = new Rectangle(850, 57, 100, 25);
		final Rectangle card1 = new Rectangle(780, 457, 20, 25);
		final Rectangle card2 = new Rectangle(827, 457, 20, 25);
		final Rectangle card3 = new Rectangle(854, 461, 20, 25);
		final Rectangle dealerCard = new Rectangle(917, 254, 20, 20);
		final Rectangle total = new Rectangle(817, 567, 15, 17);
		// final Rectangle betSpot = new Rectangle(723, 756, 50, 35);
		final Rectangle betSpot = new Rectangle(700, 999, 43, 17);
		String dir = System.getProperty("user.dir");
		Robot robot = new Robot();
		ITesseract instance = new Tesseract();
		float value = 0;

		// Screenshot Card
		BufferedImage screenShot = robot.createScreenCapture(card3);
		ImageIO.write(screenShot, "png", new File("ImageTest.png"));
		System.out.println(instance.doOCR(new File(dir + "\\ImageTest.png")).replaceAll("[^\\d.]", ""));
		System.out.println(instance.doOCR(new File(dir + "\\Image-Card3.png")).replaceAll("[^a-zA-Z0-9]", ""));
		// replaceAll("[^a-zA-Z0-9]",

	}

}
