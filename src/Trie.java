

/**
 * Interface for Trie - defines the basic functions that a Trie should have
 * 
 * @author: Parth Parekh
 **/
public interface Trie {

	/*
	 * adds a string to trie
	 * 
	 * @param wordString - string you want to add to Trie
	 * @return returns true if the add was successful, false otherwise
	 */
	public boolean addWord(Word word);

	/*
	 * removes a string from trie
	 * 
	 * @param wordString - string you want to remove from Trie
	 * @return returns true if the remove was successful, false otherwise 
	 */
	public boolean removeWord(Word word);
	
	/*
	 * search a string from trie
	 * 
	 * @param wordString - string you want to search from Trie
	 * @return returns true if the search was successful, false otherwise 
	 */
	public boolean hasWord(Word word);
	
	/*
	 * returns array of characters from trie that follows given prefixString
	 * 
	 * @param prefixString - string that is prefix to all possible characters returned by this function
	 * @return returns array of characters from trie that begin with prefixString, null otherwise 
	 */
	public char[] getNextCharacters(String prefixString);
}
