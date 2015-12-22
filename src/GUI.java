
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

/**@author Thomas Lisankie*/

public class GUI extends JFrame {
	JPanel panel = new JPanel();
	
	JLabel firstLabel = new JLabel("First Word or Phrase"), secondLabel = new JLabel("Second Word or Phrase"), result = new JLabel("Result: ");
	
	JTextField firstTextField = new JTextField(), secondTextField = new JTextField();
	
	JButton findRhymePercentile = new JButton("Find How Well They Rhyme");
	
	public static void main(String[] args){
		
		GUI frame = new GUI();
		
	}
	
	public GUI(){
		
		RhymeDictionaryAssembler.buildWords();
		
		setTitle("Find How Well Two Words Rhyme");
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		findRhymePercentile.addActionListener(new FindRhymePercentileListener());
		
		panel.add(firstLabel);
		panel.add(firstTextField);
		panel.add(secondLabel);
		panel.add(secondTextField);
		panel.add(result);
		panel.add(findRhymePercentile);
		
		add(panel);
		setSize(400, 400);
		setVisible(true);
		
	}
	
	class FindRhymePercentileListener implements ActionListener{
		
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
			
			String string2 = secondTextField.getText();
			ArrayList<String> secondStrings = new ArrayList<String>();
			String wordToAdd2 = "";
			for(int i = 0; i < string2.length(); i++){
				
				if(string2.charAt(i) != ' '){
					
					wordToAdd2 = wordToAdd2 + string2.charAt(i);
					
				}else{
					
					secondStrings.add(wordToAdd2);
					wordToAdd2 = "";
					
				}
				
			}
			secondStrings.add(wordToAdd2);
			
			ArrayList<Word> firstWords = new ArrayList<Word>(), secondWords = new ArrayList<Word>();
			
			for(int w = 0; w < firstStrings.size(); w++){
				
				for(int i = 0; i < RhymeDictionaryAssembler.anchors.size(); i++){
					
					if(RhymeDictionaryAssembler.anchors.get(i).getWordName().equalsIgnoreCase(firstStrings.get(w))){
						
						firstWords.add(RhymeDictionaryAssembler.anchors.get(i));
						break;
						
					}
					
				}
				
			}
			
			for(int w = 0; w < secondStrings.size(); w++){
				
				for(int i = 0; i < RhymeDictionaryAssembler.anchors.size(); i++){
					
					if(RhymeDictionaryAssembler.anchors.get(i).getWordName().equalsIgnoreCase(secondStrings.get(w))){
						
						secondWords.add(RhymeDictionaryAssembler.anchors.get(i));
						break;
						
					}
					
				}
				
			}
			
			//create super-list of phonemes for each sentence entered
			ArrayList<Phoneme> firstListOfPhonemes = new ArrayList<Phoneme>(), secondListOfPhonemes = new ArrayList<Phoneme>();
			
			for(int w = 0; w < firstWords.size(); w++){
				
				for(int p = 0; p < firstWords.get(w).getListOfPhonemes().size(); p++){
					
					firstListOfPhonemes.add(firstWords.get(w).getListOfPhonemes().get(p));
					
				}
				
			}
			Word word1 = new Word(string1, firstListOfPhonemes);
			
			for(int w = 0; w < secondWords.size(); w++){
				
				for(int p = 0; p < secondWords.get(w).getListOfPhonemes().size(); p++){
					
					secondListOfPhonemes.add(secondWords.get(w).getListOfPhonemes().get(p));
					
				}
				
			}
			Word word2 = new Word(string2, secondListOfPhonemes);
			
			double rhymePercentile = RhymeDictionaryAssembler.findRhymeValueAndPercentileForWords(word1, word2);
			
			if(rhymePercentile > 0.85){
				
				result.setText("Result: " + rhymePercentile + " - Extremely well");
				
			}else if(rhymePercentile > 0.6){
				
				result.setText("Result: " + rhymePercentile + " - Pretty well");
				
			}else if(rhymePercentile > 0.4){
				
				result.setText("Result: " + rhymePercentile + " - Decently");
				
			}else if(rhymePercentile > 0.2){
				
				result.setText("Result: " + rhymePercentile + " - Mediocre");
				
			}else if(rhymePercentile > 0){
				
				result.setText("Result: " + rhymePercentile + " - Not at all");
				
			}
			
		}
		
	}

}
