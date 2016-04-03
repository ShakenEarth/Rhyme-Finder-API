import java.awt.event.ActionEvent;
import com.shakenearth.rhyme_essentials.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Prototype extends JFrame {
	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	
	JLabel instructionsLabel = new JLabel("Enter the text of your piece below:");
	
	JTextArea textArea = new JTextArea();
	JTextField findRhymesFor = new JTextField();
	
	JTextArea wordsThatRhyme = new JTextArea(" ");
	
	JButton findRhymingWords = new JButton("Find Words That Rhyme"), saveButton = new JButton("Save Poem"), openButton = new JButton("Open Poem");
	
	public static void main(String[] args){
		
		Prototype frame = new Prototype();
		
	}
	
	public Prototype(){
		
		RhymeFinder.buildWords("/Users/thomas/Desktop/Dev/rap-writer/src/cmudict-0.7b_modified.txt");
		wordsThatRhyme.setEditable(false);
		JFrame frame = new JFrame();
		frame.setSize(500, 600);
		frame.setVisible(true);
		
		setTitle("Find Rhyming Words");
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		findRhymingWords.addActionListener(new FindRhymingWordsListener());
		saveButton.addActionListener(new SaveTextListener());
		openButton.addActionListener(new OpenTextListener());
		
		panel2.add(wordsThatRhyme);
		
		panel.add(instructionsLabel);
		panel.add(textArea);
		panel.add(findRhymesFor);
		panel.add(findRhymingWords);
		panel.add(saveButton);
		panel.add(openButton);
		
		frame.add(panel2);
		
		add(panel);
		setSize(600, 400);
		setVisible(true);
		
	}
	
	class FindRhymingWordsListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			String string1 = findRhymesFor.getText();
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
				
				firstWords.add(RhymeFinder.getTrie().getWord(firstStrings.get(w)));
				
			}
			
			//create super-list of phonemes for each sentence entered
			ArrayList<Phoneme> firstListOfPhonemes = new ArrayList<Phoneme>(), secondListOfPhonemes = new ArrayList<Phoneme>();
			
			for(int w = 0; w < firstWords.size(); w++){
				
				for(int p = 0; p < firstWords.get(w).getListOfPhonemes().size(); p++){
					
					firstListOfPhonemes.add(firstWords.get(w).getListOfPhonemes().get(p));
					
				}
				
			}
			Word word1 = new Word(string1, firstListOfPhonemes);
			
			for(int j = 0; j < RhymeFinder.words.size(); j++){
				
				double rhymePercentile = 0.0;
				
				rhymePercentile = RhymeFinder.findRhymeValueAndPercentileForWords(word1, RhymeFinder.words.get(j));
				
				if(rhymePercentile >= 0.4){
					
					word1.addWordThisRhymesWith(j, rhymePercentile);
					
				}
				
			}
			
			wordsThatRhyme.setText("");
			
			if(word1.getWordsThisRhymesWith().size() >= 50){
				
				for(int i = 0; i < 50; i++){
					
					wordsThatRhyme.setText(wordsThatRhyme.getText() + RhymeFinder.words.get((int) word1.getWordsThisRhymesWith().get(i).getX()).getWordName() + ", " + word1.getWordsThisRhymesWith().get(i).getY() + "\n");
					
				}
				
			}else{
				
				for(int i = 0; i < word1.getWordsThisRhymesWith().size(); i++){
					
					wordsThatRhyme.setText(wordsThatRhyme.getText() + RhymeFinder.words.get((int) word1.getWordsThisRhymesWith().get(i).getX()).getWordName() + ", " + word1.getWordsThisRhymesWith().get(i).getY() + "\n");
					
				}
				
			}
			
		}
		
	}
	
	class SaveTextListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			try {
				
				PrintWriter out = new PrintWriter("/Users/thomas/Desktop/poem.txt");
				out.println(textArea.getText());
				out.close();
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	
	class OpenTextListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			try {
	            // make a 'file' object 
	            File file = new File("/Users/thomas/Desktop/poem.txt");
	            //  Get data from this file using a file reader. 
	            FileReader fr = new FileReader(file);
	            // To store the contents read via File Reader
	            BufferedReader br = new BufferedReader(fr);                                                 
	            // Read br and store a line in 'data', print data
	            String data;
	            String text = "";
	            boolean firstLine = true;
	            
	            while((data = br.readLine()) != null) 
	            {
	                
	            	if(firstLine == true){
	            		
	            		text = text + data;
	            		firstLine = false;
	            		
	            	}else{
	            		
	            		text = text + "\n" + data;
	            	
	            	}
	                
	            }
	            
	            textArea.setText(text);
	            
	        } catch(IOException e1) {
	        	e1.printStackTrace();
	        }
		}
		
	}
	
}