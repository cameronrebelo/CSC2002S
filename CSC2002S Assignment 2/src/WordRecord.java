/**
 * @author Cameron Rebelo RBLCAM001 WordRecord class desgned to make threads of
 *         each falling word
 */
public class WordRecord implements Runnable {
	private String text;
	private int x;
	private int y;
	private int maxY;
	private boolean dropped;
	private Score score;

	private int fallingSpeed;
	private static int maxWait = 1500;
	private static int minWait = 100;
	private boolean threadEnded;

	public static WordDictionary dict;

	WordRecord() {
		text = "";
		x = 0;
		y = 0;
		maxY = 300;
		dropped = false;
		fallingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
	}

	WordRecord(String text) {
		this();
		this.text = text;
	}

	WordRecord(String text, int x, int maxY, Score score) {
		this(text);
		this.x = x;
		this.maxY = maxY;
		this.score = score;
	}

	/**
	 * run method that updates the position of y while it is above the bottom and
	 * while the flag to end the thread has not been triggered
	 */
	public void run() {
		threadEnded = false;
		while (getY() < maxY && !threadEnded) {
			drop(10);
			try {
				Thread.sleep(fallingSpeed);
			} catch (InterruptedException e) {
				System.out.println("error " + e);
			}
			if (getY() == maxY) {
				score.missedWord();
				resetWord();
			}
		}
	}

	/**
	 * @param y mutator for y
	 */
	public synchronized void setY(int y) {
		if (y > maxY) {
			y = maxY;
			dropped = true;
		}
		this.y = y;
	}

	/**
	 * @param x mutator for x
	 */
	public synchronized void setX(int x) {
		this.x = x;
	}

	/**
	 * @param text mutator for word
	 */
	public synchronized void setWord(String text) {
		this.text = text;
	}

	/**
	 * @return String accessor for word
	 */
	public synchronized String getWord() {
		return text;
	}

	/**
	 * @return int accessor for x
	 */
	public synchronized int getX() {
		return x;
	}

	/**
	 * @return int accessor for y
	 */
	public synchronized int getY() {
		return y;
	}

	/**
	 * @return int accessor for speed
	 */
	public synchronized int getSpeed() {
		return fallingSpeed;
	}

	/**
	 * @param x
	 * @param y mutator method that changed x and y at the same time
	 */
	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	/**
	 * method to return word to top of screen
	 */
	public synchronized void resetPos() {
		setY(0);
	}

	/**
	 * method to chose a new word and falling speed and resetPos
	 */
	public synchronized void resetWord() {
		resetPos();
		text = dict.getNewWord();
		dropped = false;
		fallingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
	}

	/**
	 * @param typedText method to check if param is same as the word and if it is
	 *                  resets the word as well
	 * @return boolean
	 */
	public synchronized boolean matchWord(String typedText) {
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		} else
			return false;
	}

	/**
	 * @param inc method to move the y position down
	 */
	public synchronized void drop(int inc) {
		setY(y + inc);
	}

	/**
	 * @return boolean
	 */
	public synchronized boolean dropped() {
		return dropped;
	}

	/**
	 * method to trigger the flag to end the thread
	 * 
	 * @return boolean
	 */
	public synchronized void threadEnd() {
		threadEnded = true;
	}
}
