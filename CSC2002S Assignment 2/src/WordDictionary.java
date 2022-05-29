/**
 * @author Cameron Rebelo RBLCAM001 
 * WordDictionary class to store a dictionary of words to choose form
 */

public class WordDictionary {
	int size;
	static String[] theDict = { "litchi", "banana", "apple", "mango", "pear", "orange", "strawberry", "cherry", "lemon",
			"apricot", "peach", "guava", "grape", "kiwi", "quince", "plum", "prune", "cranberry", "blueberry",
			"rhubarb", "fruit", "grapefruit", "kumquat", "tomato", "berry", "boysenberry", "loquat", "avocado" }; 

	WordDictionary(String[] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i = 0; i < size; i++) {
			theDict[i] = tmp[i];
		}

	}

	WordDictionary() {
		size = theDict.length;

	}

	/**
	 * method to choose a new word from the dictionary
	 * 
	 * @return String
	 */
	public synchronized String getNewWord() {
		int wdPos = (int) (Math.random() * size);
		return theDict[wdPos];
	}

}
