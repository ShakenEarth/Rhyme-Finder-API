import java.io.*;
import java.util.ArrayList;

public class SerializationTest {
	
	public static void main(String[] args){
		
		ArrayList<Phoneme> phonemes = new ArrayList<Phoneme>();
		
		Phoneme p1 = new Phoneme();
		Phoneme p2 = new Phoneme();
		Phoneme p3 = new Phoneme();
		p1.setPhoneme("AA");
		phonemes.add(p1);
		p2.setPhoneme("K");
		phonemes.add(p2);
		p3.setPhoneme("S");
		phonemes.add(p3);
		
		
		ArrayList<Word> wordsToSerialize = new ArrayList<Word>();
		wordsToSerialize.add(new Word("daka", phonemes));
		wordsToSerialize.add(new Word("huck", phonemes));
		wordsToSerialize.add(new Word("crick", phonemes));
		
		System.out.println("Pre-Serialization:");
		
		for(int w = 0; w < wordsToSerialize.size(); w++){
			
			System.out.println(wordsToSerialize.get(w));
			
		}
		
		try{
			
	         FileOutputStream fileOut = new FileOutputStream("/Users/thomas/Desktop/words.prhyme");
	         
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         
	         out.writeObject(wordsToSerialize);
	         
	         out.close();
	         
	         fileOut.close();
	         
	         System.out.printf("Serialized data is saved in /Users/thomas/Desktop/words.prhyme");
	         
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
		System.out.println();
		System.out.println("------------------------------");
		System.out.println();
		
		ArrayList<Word> deserializedWords = null;
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("/Users/thomas/Desktop/words.prhyme");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         deserializedWords = (ArrayList<Word>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Word class not found");
	         c.printStackTrace();
	         return;
	      }
		
		System.out.println("Post-Deserialization:");
		
		for(int w = 0; w < deserializedWords.size(); w++){
			
			System.out.println(deserializedWords.get(w));
			
		}
		
	}

}
