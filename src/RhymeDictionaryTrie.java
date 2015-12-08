
public class RhymeDictionaryTrie implements Trie {

	protected RhymeDictionaryTrieNode trieRoot;
    public RhymeDictionaryTrie() {
    	
        trieRoot = new RhymeDictionaryTrieNode();
        
    }
	
	public boolean addWord(Word word) {
		
		/*if (wordString==null || wordString.isEmpty()) {
            return false;
        }
        wordString = wordString.toLowerCase();
        char[] wordCharArr = wordString.toCharArray();
        TrieNode tempRoot = trieRoot;
        for (char charValue : wordCharArr) {
            tempRoot.addChild(charValue);
            tempRoot = tempRoot.getChild(charValue);
        }
        tempRoot.setFinalChar(true);
        return true;*/
		
		return false;
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

}
