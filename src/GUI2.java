import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.shakenearth.rhyme_essentials.*;


public class GUI2 extends JFrame {
	JPanel panel = new JPanel();
	
	JLabel firstLabel = new JLabel("First Word or Phrase");
	
	JTextField firstTextField = new JTextField();
	
	JTable wordsThatRhyme = new JTable(new Object[][]{{"hello"}}, new String[]{"h"});
	
	JButton findRhymingWords = new JButton("Find Words That Rhyme");
	
	public static void main(String[] args){
		
		GUI2 frame = new GUI2();
		
	}
	
	public GUI2(){
		
		RhymeDictionaryAssembler.buildWords();
		
		setTitle("Find Rhyming Words");
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		findRhymingWords.addActionListener(new FindRhymingWordsListener());
		
		panel.add(firstLabel);
		panel.add(firstTextField);
		panel.add(wordsThatRhyme);
		panel.add(findRhymingWords);
		
		add(panel);
		setSize(400, 400);
		setVisible(true);
		
	}
	
	class FindRhymingWordsListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			String string1 = firstTextField.getText();
			ArrayList<String> firstStrings = new ArrayList<String>();
			String wordToAdd1 = "";
			for(int i = 0; i < string1.length(); i++){
				
				if(string1.charAt(i) != ' '){
					
					wordToAdd1 = wordToAdd1 + string1.charAt(i);
					
				}else{
					
					firstStrings.add(wordToAdd1);
					wordToAdd1 = "";
					
				}
				
			}
			firstStrings.add(wordToAdd1);
			
			ArrayList<Word> firstWords = new ArrayList<Word>(), secondWords = new ArrayList<Word>();
			
			for(int w = 0; w < firstStrings.size(); w++){
				
				firstWords.add(RhymeDictionaryAssembler.getTrie().getWord(firstStrings.get(w)));
				
			}
			
			//create super-list of phonemes for each sentence entered
			ArrayList<Phoneme> firstListOfPhonemes = new ArrayList<Phoneme>(), secondListOfPhonemes = new ArrayList<Phoneme>();
			
			for(int w = 0; w < firstWords.size(); w++){
				
				for(int p = 0; p < firstWords.get(w).getListOfPhonemes().size(); p++){
					
					firstListOfPhonemes.add(firstWords.get(w).getListOfPhonemes().get(p));
					
				}
				
			}
			Word word1 = new Word(string1, firstListOfPhonemes);
			
			for(int j = 0; j < RhymeDictionaryAssembler.words.size(); j++){
				
				double rhymePercentile = 0.0;
				
				rhymePercentile = RhymeDictionaryAssembler.findRhymeValueAndPercentileForWords(word1, RhymeDictionaryAssembler.words.get(j));
				
				if(rhymePercentile >= 0.4){
					
					word1.addWordThisRhymesWith(j, rhymePercentile);
					
				}
				
			}
			
			System.out.println("----------------------------------------------");
			
			if(word1.getWordsThisRhymesWith().size() >= 50){
				
				for(int i = 0; i < 50; i++){
					
					System.out.println(RhymeDictionaryAssembler.words.get((int) word1.getWordsThisRhymesWith().get(i).getX()).getWordName());
					
				}
				
			}else{
				
				for(int i = 0; i < word1.getWordsThisRhymesWith().size(); i++){
					
					System.out.println(RhymeDictionaryAssembler.words.get((int) word1.getWordsThisRhymesWith().get(i).getX()).getWordName());
					
				}
				
			}
			
		}
		
	}
	
}