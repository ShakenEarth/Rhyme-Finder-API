package com.shakenearth;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.shakenearth.rhyme_essentials.*;

public class Tester {
	
	static RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt", 
			"/Users/thomas/Desktop/Dev/rap-writer/src/features.txt");
	
	public static void main(String[] args){

		//0 for comparing two words and/or phrases, 1 for finding rhyming words for a specific word or phrase, 2 for a GUI.
		final int TESTING = 2;
		
		if(TESTING == 0){ //comparing two words and/or phrases
			
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter first word: ");
			String firstWordSpelling = reader.nextLine();
			
			System.out.println("Enter second word: ");
			String secondWordSpelling = reader.nextLine();
			reader.close();
			
			String[] firstWordComponents = firstWordSpelling.split(" ");
			String[] secondWordComponents = secondWordSpelling.split(" ");
			
			String firstWordPhonemeString = "";
			String secondWordPhonemeString = "";
			
			for(int i = 0; i < firstWordComponents.length; i++){
				
				firstWordPhonemeString = firstWordPhonemeString + finder.getDictionary().get(firstWordComponents[i].toLowerCase()) + " ";
				
			}
			
			for(int i = 0; i < secondWordComponents.length; i++){
				
				secondWordPhonemeString = secondWordPhonemeString + finder.getDictionary().get(secondWordComponents[i].toLowerCase()) + " ";
				
			}
			
			Word firstWord = new Word(firstWordSpelling, firstWordPhonemeString);
			Word secondWord = new Word(secondWordSpelling, secondWordPhonemeString);
			
			System.out.println(finder.findRhymeValueAndPercentileForWords(firstWord, secondWord) * 100 + "%");
			
		}else if(TESTING == 1){ //finding rhyming words for a specific word or phrase
			
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Enter a word to find rhymes for: ");
			String wordSpelling = reader.nextLine();
			reader.close();
			
			String[] wordComponents = wordSpelling.split(" ");
			String wordPhonemeString = "";
			
			for(int i = 0; i < wordComponents.length; i++){
				
				wordPhonemeString = wordPhonemeString + finder.getDictionary().get(wordComponents[i].toLowerCase()) + " ";
				
			}
			
			Word firstWord = new Word(wordSpelling, wordPhonemeString);
			String vowelString = firstWord.getVowelPhonemesAsString();
			
			int beginningIndex = finder.getStructureReference().get(vowelString);
			boolean nextStructFound = false;
			
			
			int currentIndex = beginningIndex;
			
			while(nextStructFound == false){
				
				currentIndex = currentIndex + 1;
				
				String currentWord = finder.getWordList().get(currentIndex);
				Word newWord = new Word(currentWord, finder.getDictionary().get(currentWord));
				
				if(newWord.getVowelPhonemesAsString().equals(vowelString) == false){
					
					break;
					
				}else{
					
					Word secondWord = new Word(currentWord, finder.getDictionary().get(currentWord));
					
					System.out.println(currentWord + ", " + finder.findRhymeValueAndPercentileForWords(firstWord, secondWord) * 100 + "%");
					
				}
				
			}
			
		}else if(TESTING == 2){
			
			//writing frame setup
			
			WritingFrame writingFrame = new WritingFrame();
			
			//comparison frame setup
			
			ComparisonFrame comparisonFrame = new ComparisonFrame();
			
			}
		
		}
	
		
	}
	
	class ComparisonFrame extends JFrame{
		
		JPanel panel = new JPanel();
		JLabel firstLabel = new JLabel("First Word or Phrase"), secondLabel = new JLabel("Second Word or Phrase");
		JTextField firstWordTextField = new JTextField(), secondWordTextField = new JTextField();
		JButton compareButton = new JButton("Compare");
		JLabel result = new JLabel("Result: ");
		RhymeFinder finder = new RhymeFinder("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt", 
				"/Users/thomas/Desktop/Dev/rap-writer/src/features.txt");
		
		public ComparisonFrame(){
			
			setSize(300, 300);
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			
			compareButton.addActionListener(new CompareButtonListener());
			
			panel.add(firstLabel);
			panel.add(firstWordTextField);
			panel.add(secondLabel);
			panel.add(secondWordTextField);
			panel.add(compareButton);
			panel.add(result);
			
			add(panel);
			setVisible(true);
			
		}
		
		class CompareButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String[] firstWordComponents = firstWordTextField.getText().split(" ");
				String[] secondWordComponents = secondWordTextField.getText().split(" ");
				
				String firstWordPhonemeString = "";
				String secondWordPhonemeString = "";
				
				for(int i = 0; i < firstWordComponents.length; i++){
					
					firstWordPhonemeString = firstWordPhonemeString + finder.getDictionary().get(firstWordComponents[i].toLowerCase()) + " ";
					
				}
				
				for(int i = 0; i < secondWordComponents.length; i++){
					
					secondWordPhonemeString = secondWordPhonemeString + finder.getDictionary().get(secondWordComponents[i].toLowerCase()) + " ";
					
				}
				
				Word firstWord = new Word(firstWordTextField.getText(), firstWordPhonemeString);
				Word secondWord = new Word(secondWordTextField.getText(), secondWordPhonemeString);
				
				System.out.println(firstWord);
				System.out.println(secondWord);
				
				result.setText("Result: " + (finder.findRhymeValueAndPercentileForWords(firstWord, secondWord) * 100) + "%");
				
			}
		
	}

}
	
	class WritingFrame extends JFrame{
		
		JPanel contentPanel = new JPanel(), textPanel = new JPanel(), tablePanel = new JPanel(), buttonPanel = new JPanel();
		JTable table = new JTable();
		JScrollPane tableScrollPane = null;
		JTextPane textAreaScrollPane = null;
		JButton findWordsButton = new JButton("Find Words");
		String[][] data = {{"-", "0%"}, {"-", "0%"}, {"-", "0%"}, {"-", "0%"}, {"-", "0%"}, {"-", "0%"}, {"-", "0%"}, {"-", "0%"}
		, {"-", "0%"}, {"-", "0%"}, {"-", "0%"}, {"-", "0%"}};
		String[] columnNames = {"Word", "Rhyme Percentile"};
		
		public WritingFrame(){
			
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
			textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
			
			//table setup
			table = new JTable(data, columnNames);
			table.addMouseListener(new java.awt.event.MouseAdapter() {
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			        int row = table.rowAtPoint(evt.getPoint());
			        int col = table.columnAtPoint(evt.getPoint());
			        if (row >= 0 && col >= 0) {
			            
			        	String wordToAdd = (String) table.getValueAt(row, 0);
			        	
			        	if(textAreaScrollPane.getText().endsWith(" ")){
			        		
			        		textAreaScrollPane.setText(textAreaScrollPane.getText() + wordToAdd);
			        		
			        	}else{
			        		
			        		textAreaScrollPane.setText(textAreaScrollPane.getText() + " " + wordToAdd);
			        		
			        	}
			        	
			        }
			    }
			});
			
			tableScrollPane = new JScrollPane(table);
			
			//textArea.setSize(800, 250);
			textAreaScrollPane = new JTextPane();
			
			findWordsButton.addActionListener(new FindWordsButtonListener());
			
			textPanel.add(textAreaScrollPane);
			tablePanel.add(tableScrollPane);
			buttonPanel.add(findWordsButton);
			contentPanel.add(textPanel);
			contentPanel.add(tablePanel);
			contentPanel.add(findWordsButton);
			
			add(contentPanel);
			setSize(800, 500);
			setVisible(true);
			
		}
		
		class FindWordsButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<String[]> tableContent = new ArrayList<String[]>();
				
				String wordSpelling = textAreaScrollPane.getSelectedText().trim();
				
				String[] wordComponents = wordSpelling.split(" ");
				String wordPhonemeString = "";
				
				for(int i = 0; i < wordComponents.length; i++){
					
					wordPhonemeString = wordPhonemeString + Tester.finder.getDictionary().get(wordComponents[i].toLowerCase()) + " ";
					
				}
				
				Word firstWord = new Word(wordSpelling, wordPhonemeString);
				String vowelString = firstWord.getVowelPhonemesAsString();
				
				int beginningIndex = Tester.finder.getStructureReference().get(vowelString);
				boolean nextStructFound = false;
				
				
				int currentIndex = beginningIndex;
				
				while(nextStructFound == false){
					
					currentIndex = currentIndex + 1;
					
					String currentWord = Tester.finder.getWordList().get(currentIndex);
					Word newWord = new Word(currentWord, Tester.finder.getDictionary().get(currentWord));
					
					if(newWord.getVowelPhonemesAsString().equals(vowelString) == false){
						
						break;
						
					}else{
						
						Word secondWord = new Word(currentWord, Tester.finder.getDictionary().get(currentWord));
						
						double rhymePercentile = Tester.finder.findRhymeValueAndPercentileForWords(firstWord, secondWord);
						
						if(rhymePercentile > 0.7){
							
							String[] wordPair = new String[2];
							
							if(currentWord.endsWith(")")){
								
								currentWord = currentWord.substring(0, currentWord.length() - 3);
								wordPair[0] = currentWord;
								
							}else{
								
								wordPair[0] = currentWord;
								
							}
							
							wordPair[1] = Double.toString(rhymePercentile*100) + "%";
							
							tableContent.add(wordPair);
							
						}
						
					}
					
				}
				
				data = new String[2][tableContent.size()];
				data = tableContent.toArray(data);
				DefaultTableModel newModel = new DefaultTableModel(data, columnNames );
				table.setModel(newModel);
				
			}
			
			
			
		}
		
}
