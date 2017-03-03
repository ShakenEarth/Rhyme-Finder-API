package com.shakenearth.rhyme_essentials;

import java.util.ArrayList;

public class CartesianProduct {
	
	private ArrayList<ArrayList<OrderedPair>> cartesianProductMatrix = new ArrayList<ArrayList<OrderedPair>>();
	
	public CartesianProduct(Word word1, Word word2){ //builds a new Cartesian product from the phonemes of the input words
		
		Word shorterWord = null;
		Word longerWord = null;
		
		System.out.println("Cartesian Product of Newest Method: \n");
		
		//these conditionals find which word is longer and which is shorter
		if(word1.getListOfPhonemes().size() < word2.getListOfPhonemes().size()){
			
			shorterWord = word1;
			longerWord = word2;
			
		}else{
			
			shorterWord = word2;
			longerWord = word1;
			
		}
		
		//creates Cartesian product (shorterWord X longerWord)
		for(int s = 0; s < shorterWord.getListOfPhonemes().size(); s++){
			
			ArrayList<OrderedPair> currentRow = new ArrayList<OrderedPair>();
			
			for(int l = 0; l < longerWord.getListOfPhonemes().size(); l++){
				
				OrderedPair newOrderedPair = new OrderedPair(shorterWord.getListOfPhonemes().get(s), longerWord.getListOfPhonemes().get(l), l);
				currentRow.add(newOrderedPair);
				
				//print the Ordered Pairs and make it look pretty
						
				if(newOrderedPair.getShorterWordPhoneme().length()  == 2 && newOrderedPair.getLongerWordPhoneme().length()  == 2){
					
					System.out.print(newOrderedPair + "  ");
							
				}else if(newOrderedPair.getShorterWordPhoneme().length()  != newOrderedPair.getLongerWordPhoneme().length()){
					
					System.out.print(newOrderedPair + "   ");
					
				}else if(newOrderedPair.getShorterWordPhoneme().length()  == 1 && newOrderedPair.getLongerWordPhoneme().length()  == 1){
					
					System.out.print(newOrderedPair + "    ");
					
				}
				
				//End print of ordered pairs
				
			}
			
			System.out.println();
			cartesianProductMatrix.add(currentRow);
			
		}
		
		System.out.println();
		
	}
	
	public void resetOrderedPairRVs(){
		
		ArrayList<OrderedPair> currentRow = null;
		
		for(int i = 0; i < cartesianProductMatrix.size(); i++){
			
			currentRow = cartesianProductMatrix.get(i);
			
			for(int j = 0; j < currentRow.size(); j++){
				
				currentRow.get(j).resetRV();
				
			}
			
		}
		
	}
	
	public void removeTopRow(){
		
		cartesianProductMatrix.remove(0);
		
	}

	public ArrayList<ArrayList<OrderedPair>> getCartesianProductMatrix() {
		
		return cartesianProductMatrix;
		
	}

	public void setCartesianProductMatrix(ArrayList<ArrayList<OrderedPair>> cartesianProductMatrix) {
		
		this.cartesianProductMatrix = cartesianProductMatrix;
		
	}

}
