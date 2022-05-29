
/**
@author Cameron Rebelo RBLCAM001
Score class to keep track of user score concurrently
*/

import java.util.concurrent.atomic.AtomicInteger;

public class Score {
	private AtomicInteger missedWords;
	private AtomicInteger caughtWords;
	private AtomicInteger gameScore;

	Score() {
		missedWords = new AtomicInteger(0);
		caughtWords = new AtomicInteger(0);
		gameScore = new AtomicInteger(0);
	}

	/**
	 * accessor for missedWords
	 * 
	 * @return int
	 */
	public synchronized int getMissed() {
		return missedWords.get();
	}

	/**
	 * accessor for caughtWords
	 * 
	 * @return int
	 */
	public synchronized int getCaught() {
		return caughtWords.get();
	}

	/**
	 * accessor for totalWords
	 * 
	 * @return int
	 */
	public synchronized int getTotal() {
		return (missedWords.get() + caughtWords.get());
	}

	/**
	 * accessor for score
	 * 
	 * @return int
	 */
	public synchronized int getScore() {
		return gameScore.get();
	}

	/**
	 * method to increment missed value
	 */
	public synchronized void missedWord() {
		missedWords.getAndIncrement();

	}

	/**
	 * @param length method to increment the caught value and increase score by the
	 *               words length
	 */
	public synchronized void caughtWord(int length) {
		caughtWords.getAndIncrement();
		gameScore.getAndAdd(length);
	}

	/**
	 * method to reset the user's score
	 */
	public synchronized void resetScore() {
		caughtWords.set(0);
		missedWords.set(0);
		gameScore.set(0);
	}
}
