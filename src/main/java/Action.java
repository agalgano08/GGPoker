import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Action {

	public final Coordinate betLocation = new Coordinate("Bet", 750, 770);
	public final Coordinate standLocation = new Coordinate("Stand", 1040, 930);
	public final Coordinate dealLocation = new Coordinate("Deal", 950, 966);
	public final Coordinate hitLocation = new Coordinate("Hit", 920, 930);
	public final Coordinate doubleLocation = new Coordinate("Double", 1178, 930);
	public final Coordinate splitLocation = new Coordinate("Split", 785, 930);
	public final Coordinate cancelInsuranceLocation = new Coordinate("Cancel", 818, 961);

	Robot robot;

	public Action() throws AWTException {
		robot = new Robot();
	}

	public void click(Coordinate c) throws AWTException, InterruptedException {

		System.out.println("Clicking:" + c.toString());

		robot.mouseMove(c.x, c.y);
		Thread.sleep(100);

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(300);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);


	}

	public void bet(int bets) throws AWTException, InterruptedException {
		for (int i = 0; i < bets; i++) {
			click(betLocation);
		}

	}
}
