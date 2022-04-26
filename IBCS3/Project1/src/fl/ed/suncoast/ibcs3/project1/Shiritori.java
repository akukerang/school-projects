package fl.ed.suncoast.ibcs3.project1;
import java.util.ArrayList;
import java.util.Scanner;



public class Shiritori {

	public static ArrayList<String> words = new ArrayList<String>(); //global arraylist words keeps track of words written
	
	public static boolean gameOver = false; //global variable gameOver
	
	public static void main(String[] args) {

		play(); //plays game
		System.out.println(getWords()); //returns words arraylist
		
		
		
		
	}
	
	
	
	public static void play() {
		boolean isLetter = false;
		String firstWord = "";
		Scanner sc = new Scanner(System.in); //Scanner
		
		while(!isLetter) {
		
			
			System.out.println("Initial Word:");
			firstWord = sc.nextLine().toLowerCase(); //gets the input for the firstWord
			for(int i = 0; i < firstWord.length(); i++) {
				//check each letter to see if it's an actual letter
				if(!Character.isLowerCase(firstWord.charAt(i))) { 
					break; //breaks for loop if there is a characther that's not a letter
					
				}
				isLetter = true; //if all characthers are letter, isLetter is set to true and the while loop ends
				
			}
		}
		
		
		
		String user = "";
		int counter = 0;
		
		words.add(firstWord);
		while(gameOver == false) {
			
			
			isLetter = false;
			
			while(!isLetter) {
				
				
				System.out.println("Next Word:");
				user = sc.nextLine().toLowerCase(); //gets the input for the next word
				for(int i = 0; i < user.length(); i++) {
					//check each letter to see if it's an actual letter
					if(!Character.isLowerCase(user.charAt(i))) {
						break; //breaks for loop if there is a characther that's not a letter
						
					}
					isLetter = true; //if all characthers are letter, isLetter is set to true and the while loop ends
					
				}
			}
			
			
			
			
			
			
			
			
			
			
			if(user.charAt(0) == words.get(counter).charAt(words.get(counter).length() - 1)) { //checks if the user input first characther matches the last word ending characther.
				
				if(words.contains(user)) { //checks if the arraylist has the user input already. if it does the boolean gameover is set to false;
					System.out.println("You Lose");
					gameOver = true;
					
				} else {
					words.add(user); //if the word is not already in the arraylist it adds it to the arraylist
					counter++; //adds 1 to the counter so the arraylist can point to the most recent number in the previous if statement.
				}
				
				
				
			} else { //if first characther does not match last word last characther game is over.
				
				System.out.println("You Lose");
				gameOver = true;
		
			}
			
			
			
		
			
		}
		System.out.println("Play again? (Y/N)");
		String ans = sc.nextLine().toUpperCase(); //user input for play again
		char charans = ans.charAt(0); //sets the ans to a characther, only getting the first character
		if(charans == 'Y') { //if the input starts with a Y, the game plays again
			restart(); //clears words arraylist and sets gameOver to false
			play(); //runs play function
		} 
		sc.close(); //closes scanner
		
		
		
		
	}
	
	
	public static String getWords() {
		String s = "";
		
		for(int i = 0; i < words.size(); i++) { //for loop going through arraylist adding to String s
			s += words.get(i) + " ";
		}
		return s; 
		
	}
	
	public static void restart() {
		words.clear(); //clears arraylist
		gameOver = false; //sets gameOver to false
		
		
	}
	
	
	
}
