import java.io.Serializable;

public class RhymeDictionaryTrie implements Trie, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
        	
            tempRoot.addChild(charValue);
            tempRoot = tempRoot.getChild(charValue);
            
        }
        
        tempRoot.setWord(word);
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
		
		String string = trieRoot.getChild('a').getChild('c').getChild('a').getChild('e').toString();
		
		return string;
		
	}

}
