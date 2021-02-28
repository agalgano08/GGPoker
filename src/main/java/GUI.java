import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class GUI {

	public static JFrame frame = new JFrame("GGPokers");
	public static JPanel panel = new JPanel();
	public static JButton starButton = new JButton("StarGame");
	public static JButton stopButton = new JButton("StopGame");
	public static JLabel status = new JLabel("Running", SwingConstants.HORIZONTAL);
	public static JTextArea ta = new JTextArea();
	public static TextAreaOutputStream taos = new TextAreaOutputStream(ta, 2000);
	public static PrintStream ps = new PrintStream(taos);

	public GUI() {
	}

	public void start(int width, int height, int x, int y) {
		frame.add(new JLabel(" Outout"), BorderLayout.NORTH);
		System.setOut(ps);
		System.setErr(ps);

		frame.add(new JScrollPane(ta));

		frame.pack();
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == 35 || key.getKeyChar() == 'x') {
					status.setText("Exiting");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					System.exit(0);
				}
			}

			public void keyReleased(KeyEvent key) {
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}
