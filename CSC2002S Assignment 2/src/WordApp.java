/**
 * @author Cameron Rebelo RBLCAM001 
 * main class to run the program. handles user input and output
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.util.Scanner;
import java.util.concurrent.*;

public class WordApp {
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;
	static volatile boolean gameStarted = false;

	static WordDictionary dict = new WordDictionary();

	static WordRecord[] words;
	static Score score;
	static Timer t;
	static WordPanel w;
	static JPanel g;

	static Thread[] threads;

	static JFrame frame = new JFrame("WordGame");
	
	
	
	
	/** 
	 * @param frameX
	 * @param frameY
	 * @param yLimit
	 * method to initialise the frame and add logic to all buttons and textfields
	 */
	public static void setupGUI(int frameX, int frameY, int yLimit) {
		// Frame init and dimensions
		score = new Score();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameX, frameY);
		g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
		g.setSize(frameX, frameY);

		w = new WordPanel(words, yLimit);
		w.setSize(frameX, yLimit + 100);
		g.add(w);

		JPanel txt = new JPanel();
		txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));
		JLabel caught = new JLabel("Caught: " + score.getCaught() + "    ");
		JLabel missed = new JLabel("Missed:" + score.getMissed() + "    ");
		JLabel scr = new JLabel("Score:" + score.getScore() + "    ");
		txt.add(caught);
		txt.add(missed);
		txt.add(scr);

		final JTextField textEntry = new JTextField("", 20);
		textEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String text = textEntry.getText();
				for (int i = 0; i < words.length; i++) {
					if (words[i].matchWord(text)) {
						score.caughtWord(text.length());
					}
				}
				textEntry.setText("");
				textEntry.requestFocus();
			}
		});

		txt.add(textEntry);
		txt.setMaximumSize(txt.getPreferredSize());
		g.add(txt);

		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));

		JButton startB = new JButton("Start");
		startB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!gameStarted) {
					gameStarted = true;
					w.startGame();
					t = new Timer(1, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							caught.setText("Caught: " + score.getCaught() + "    ");
							scr.setText("Score:" + score.getScore() + "    ");
							missed.setText("Missed:" + score.getMissed() + "    ");
							if (score.getTotal() >= totalWords) {
								resetup();
							}
						}
					});
					t.start();
					textEntry.requestFocus();

				} else {
					JOptionPane.showMessageDialog(null, "Game Already Started");
				}
			}
		});

		JButton endB = new JButton("End");
		endB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameStarted) {
					gameStarted = false;
					resetup();
				} else {
					JOptionPane.showMessageDialog(null, "No Game Started");
				}
			}
		});

		JButton quitB = new JButton("Quit");
		quitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JButton helpB = new JButton("Help");
		helpB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"How To Play: \nPress the start button to start playing.\nThe aim of the game is to type the words that are falling from the screen before they reach the red zone.\nOnce you've typed a word, press enter to score points. You will receive points based on how long the word is!\nThe game ends once the set amount of words has been reached.\nYou can press the end button at any time to end your current game or quit using the quit button",
						"Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		b.add(startB);
		b.add(endB);
		b.add(quitB);
		b.add(helpB);

		g.add(b);

		frame.setLocationRelativeTo(null);
		frame.add(g);
		frame.setContentPane(g);
		frame.setVisible(true);
	}

	/** 
	 * own method that serves the purpose of reseting up the game. 
	 * it stops the timer that checks win condition, resets the score and words,
	 * ends all threads and then displays user's score to screen
	 */
	public static void resetup() {
		t.stop();
		int tempScore = score.getScore();
		int tempMissed = score.getMissed();
		int tempCaught = score.getCaught();
		score.resetScore();
		w.stopGame();
		for (int i = 0; i < words.length; i++) {
			words[i].resetWord();
		}
		gameStarted=false;
		JOptionPane.showMessageDialog(null,
				"Score: " + tempScore + "\nCaught:" + tempCaught + "\nMissed:" + tempMissed,
				"Game Over", JOptionPane.DEFAULT_OPTION);
	}

   
   /** 
	* @param filename
	* method to create a dict from a provided filename
	* @return String[]
	*/
   public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new File(filename));
			int dictLength = dictReader.nextInt();
			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]= dictReader.next();
			}
			dictReader.close();
		} 
		catch (IOException e) {
	    	System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;
	}

	
	
	/** 
	 * @param args
	 * main method that reads command line arguments and setups the screen and initialises the wordRecord array
	 */
	public static void main(String[] args) {

		totalWords=Integer.parseInt(args[0]); 
		noWords=Integer.parseInt(args[1]);
		assert(totalWords>=noWords);

		String[] tmpDict=getDictFromFile(args[2]);
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict;

		words = new WordRecord[noWords];
		setupGUI(frameX, frameY, yLimit);  
		int x_inc=(int)frameX/noWords;

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(), i * x_inc, yLimit, score);
		}
	}
}