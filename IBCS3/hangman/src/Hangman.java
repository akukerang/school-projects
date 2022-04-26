import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Hangman 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		File f_wordbank = new File("C:/Users/Darren-PC/eclipse-workspace/hangman/src/hangman.txt"); //argument is the filename including path name
		Scanner sc_word = new Scanner(f_wordbank);
		
		//Scan the file for a random word.
		String st_word = "";
		
		Random r = new Random();
		int i_word = r.nextInt(213); //one less than the amount of words in the file
		for(int i = 0; i < i_word; i++)
		{			
			st_word = sc_word.nextLine(); 
		}
		

		//Play some Hangman.
		//String with several underscores (as many spaces as there are characters in st_word.
		//Scan for user input for a letter.
		//Check if st_word has that letter, and if it does replace the empty spaces with the letter.
		//Continue until the full word is guessed.
		String guess = "";
		int counter = 0;
		for(int i = 0; i < st_word.length(); i++) {
			
			guess = guess + "*";
		}

		
		Scanner sc = new Scanner(System.in);
		
		
		while(counter < 7 && guess.contains("*") ) {
			System.out.println("guess a letter");
			System.out.println(guess);
			String user = sc.nextLine();
			
			if(st_word.contains(user)) {
				String temp = "";
				for(int i = 0; i < st_word.length(); i++) {
				
					if (st_word.charAt(i) == user.charAt(0)){
						
						temp += user;
						
					} else if (guess.charAt(i) != '*') {
						temp += st_word.charAt(i);
						
					}

					else {
						temp += "*";
					}
				

				}
				
				if(guess.equals(st_word)) {
					System.out.println("------------------------------------------");
					System.out.println("win");
					System.out.println("word was "+st_word);
					System.out.println("------------------------------------------");
				} else {
					guess = temp;
				}
				

	
				
			} else {
				counter++;
				System.out.println(7-counter + " chances left");
			}
			
			
		}
		
		if(counter == 7) {
			System.out.println("------------------------------------------");
			System.out.println("YOU LOST LOL");
			System.out.println("word was "+ st_word);
			System.out.println("------------------------------------------");
		}
		
		
		
		sc_word.close();
		sc.close();
	}
	
	

	
	
}
