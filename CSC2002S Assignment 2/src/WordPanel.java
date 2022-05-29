
/**
 * @author Cameron Rebelo RBLCAM001 
 * class to create panel that falling words are on; also controls threads
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class WordPanel extends JPanel implements ActionListener {
	private WordRecord[] words;
	private int noWords;
	private int maxY;
	Timer tm = new Timer(5, this);
	private Thread threads;

	/**
	 * @param g paints the panel and the words at their current positions
	 */
	public void paintComponent(Graphics g) {

		int width = getWidth();
		int height = getHeight();
		g.clearRect(0, 0, width, height);
		g.setColor(Color.red);
		g.fillRect(0, maxY - 10, width, height);

		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		// draw the words
		// animation must be added
		for (int i = 0; i < noWords; i++) {
			g.drawString(words[i].getWord(), words[i].getX(), words[i].getY());
		}
		tm.start();
	}

	WordPanel(WordRecord[] words, int maxY) {
		this.words = words; // will this work?
		noWords = words.length;
		this.maxY = maxY;
	}

	/**
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	/**
	 * method to start the game by starting the threads
	 */
	public synchronized void startGame() {
		for (int i = 0; i < words.length; i++) {
			threads = new Thread(words[i]);
			threads.start();
		}
	}

	/**
	 * method to stop the game by triggering the flags of the threads
	 */
	public synchronized void stopGame() {
		for (int i = 0; i < words.length; i++) {
			words[i].threadEnd();
		}
	}
}
