
public class RhymeDictionaryTrie implements Trie {

	protected RhymeDictionaryTrieNode trieRoot;
    public RhymeDictionaryTrie() {
    	
        trieRoot = new RhymeDictionaryTrieNode();
        
    }
	
	public boolean addWord(Word word) {
		
		if (word==null || word.getWordName().isEmpty()) {
            return false;
        }
        
        char[] wordCharArr = word.getWordName().toCharArray();
        RhymeDictionaryTrieNode tempRoot = trieRoot;
        for (char charValue : wordCharArr) {
            tempRoot.addChild(word, charValue);
            tempRoot = tempRoot.getChild(charValue);
        }
        tempRoot.setFinalChar(true);
        return true;
		
	}

	public boolean removeWord(Word word) {
		
		return false;
	}

	public boolean hasWord(Word word) {
		
		return false;
	}

	public char[] getNextCharacters(String prefixString) {
		
		return null;
	}
	
	@Override
	public String toString(){
		
		String string = trieRoot.getChild('a').getChild('c').toString();
		
		return string;
		
	}

}
