import java.util.ArrayList;

public class TrieTest {

	public static void main(String[] args){
		
		ArrayList<Phoneme> nemes = new ArrayList<Phoneme>();
		
		Word w1 = new Word("ace", nemes);
		Word w2 = new Word("ate", nemes);
		Word w3 = new Word("aca", nemes);
		
		RhymeDictionaryTrie trie = new RhymeDictionaryTrie();
		trie.addWord(w1);
		trie.addWord(w2);
		trie.addWord(w3);
		
		System.out.println(trie);
		
	}
	
}
