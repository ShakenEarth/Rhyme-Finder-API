import java.util.Scanner;

public class FindRPBetweenWords {
	
	public static void main(String[] args){
		
		System.out.println("Enter first word:");
		Scanner scanner = new Scanner(System.in);
		String string1 = scanner.nextLine();
		
		System.out.println("Enter second word:");
		String string2 = scanner.nextLine();
		
		RhymeDictionaryAssembler.buildWords();
		
		Word word1 = null, word2 = null;
		
		for(int i = 0; i < RhymeDictionaryAssembler.anchors.size(); i++){
			
			if(RhymeDictionaryAssembler.anchors.get(i).getWordName().equalsIgnoreCase(string1)){
				
				word1 = RhymeDictionaryAssembler.anchors.get(i);
				break;
				
			}
			
		}
		
		for(int i = 0; i < RhymeDictionaryAssembler.anchors.size(); i++){
			
			if(RhymeDictionaryAssembler.anchors.get(i).getWordName().equalsIgnoreCase(string2)){
				
				word2 = RhymeDictionaryAssembler.anchors.get(i);
				break;
				
			}
			
		}
		
		System.out.println(RhymeDictionaryAssembler.findRhymeValueAndPercentileForWords(word1, word2));
		
	}

}
