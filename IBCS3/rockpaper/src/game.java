import java.util.Scanner;
public class game {
	public String[] AI = {};
	private int p1score;
	private int p2score;
	private String difficulty;
	Scanner sc = new Scanner(System.in);
	
	public game() {

		this.p1score = 0;
		this.p2score = 0;
		System.out.println(
				"    ____  ____  ________ __    ____  ___    ____  __________     _____ ____________________ ____  ____ _____\r\n" + 
				"   / __ \\/ __ \\/ ____/ //_/   / __ \\/   |  / __ \\/ ____/ __ \\   / ___// ____/  _/ ___/ ___// __ \\/ __ / ___/\r\n" + 
				"  / /_/ / / / / /   / ,<     / /_/ / /| | / /_/ / __/ / /_/ /   \\__ \\/ /    / / \\__ \\\\__ \\/ / / / /_/ \\__ \\ \r\n" + 
				" / _, _/ /_/ / /___/ /| |   / ____/ ___ |/ ____/ /___/ _, _/   ___/ / /____/ / ___/ ___/ / /_/ / _, ____/ / \r\n" + 
				"/_/ |_|\\____/\\____/_/ |_|  /_/   /_/  |_/_/   /_____/_/ |_|   /____/\\____/___//____/____/\\____/_/ |_/____/   for your life\r\n" + 
				"                                                                                                            \r\n" + 
				" by Gabriel S.\n"+
				"_________________________________________________________________________________________________________________________\n");
		System.out.println("AI DIFFICULTY: Easy OR Hard");
		difficulty = sc.next();

		
	}
	
	public void runGame() {
		bot bot1 = new bot(difficulty);
		System.out.println("ENTER YOUR FIRST HAND");
		String p1hand1 = sc.next();
		p1hand1 = formatString(p1hand1);
		System.out.println("ENTER YOUR SECOND HAND");
		String p1hand2 = sc.next();
		p1hand2 = formatString(p1hand2);
		String p2hand1 = aiGo(bot1.current);
		String p2hand2 = aiGo(bot1.current);
		checkHand1(p1hand1, p2hand1);
		checkHand2(p1hand2, p2hand2);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Your first hand is "+p1hand1);
		System.out.println("Your second hand is "+p1hand2);
		System.out.println("The AI's first hand is "+p2hand1);
		System.out.println("The AI's second hand is "+p2hand2);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(checkWinner(checkHand1(p1hand1, p2hand1), checkHand2(p1hand2, p2hand2)));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Score: \n Player: "+p1score+ " AI: "+p2score);
		System.out.println("Play again?");
		String decision = sc.next();
		System.out.println("__________________________________________________________________________\n");
		playAgain(decision);
	}
	
	public String formatString(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	public void playAgain(String str) {
		if(str.length() > 0) {
			str = formatString(str);
			if(str.equals("Yes")) {
				this.runGame();
			} else {
				System.out.println("Game ended");
				System.out.println("Final Score: \n Player: "+p1score+ " AI: "+p2score);
			}
		}

	}
	

	
	public String aiGo(String[] aiArray) {		
		if(aiArray.length > 0) {
			int max = aiArray.length - 1;
			int random = (int)(Math.random() * max + 1);
			return aiArray[random];
		} else {
			return null;
		}
	}
	

	public int checkHand1(String p1hand1, String p2hand1) { //0 = tie; 1=p1; 2=p2
		if(p2hand1.equals("God")) {
			return 2;
		} else if (p1hand1.equals("Rock") && p2hand1.equals("Scissor")) {
			return 1;
		} else if (p1hand1.equals("Rock") && p2hand1.equals("Paper")) {
			return 2;
		} else if (p1hand1.equals("Rock") && p2hand1.equals("Rock")) {
			return 0;
		} else if (p1hand1.equals("Paper") && p2hand1.equals("Scissor")) {
			return 2;
		} else if (p1hand1.equals("Paper") &&p2hand1.equals("Paper")) {
			return 0;
		} else if (p1hand1.equals("Paper")&& p2hand1.equals("Rock")) {
			return 1;
		} else if (p1hand1.equals("Scissor") && p1hand1.equals("Scissor")) {
			return 0;
		} else if (p1hand1.equals("Scissor") && p1hand1.equals("Paper")) {
			return 1;
		} else if (p1hand1.equals("Scissor") && p1hand1.equals("Rock")) {
			return 2;
		}
		return -1;
	}
	
	
	public int checkHand2(String p1hand2 , String p2hand2) { //0 = tie; 1=p1; 2=p2
		if(p2hand2.equals("God")) {
			return 2;
		} else if (p1hand2.equals("Rock") && p2hand2.equals("Scissor")) {
			return 1;
		} else if (p1hand2.equals("Rock") && p2hand2.equals("Paper")) {
			return 2;
		} else if (p1hand2.equals("Rock") && p2hand2.equals("Rock")) {
			return 0;
		} else if (p1hand2.equals("Paper") && p2hand2.equals("Scissor")) {
			return 2;
		} else if (p1hand2.equals("Paper") &&p2hand2.equals("Paper")) {
			return 0;
		} else if (p1hand2.equals("Paper")&& p2hand2.equals("Rock")) {
			return 1;
		} else if (p1hand2.equals("Scissor") && p2hand2.equals("Scissor")) {
			return 0;
		} else if (p1hand2.equals("Scissor") && p2hand2.equals("Paper")) {
			return 1;
		} else if (p1hand2.equals("Scissor") && p2hand2.equals("Rock")) {
			return 2;
		}
		return -1;
	}
	
	public String checkWinner(int hand1, int hand2) {
		if((hand1 == 1) && (hand2 == 1)) {
			this.p1score++;
			return "You have won";
		} else if((hand1 == 2) && (hand2 == 2)) {
			this.p2score++;
			return "The AI has won!";
		} else if((hand1 == 1) && (hand2 == 1)) {
			this.p1score++;
			return "You have won";
		} else if((hand1 == 1) && (hand2 == 2)) {
			return "TIE";
		} else if((hand1 == 2) && (hand2 == 1)) {
			return "TIE";
		} else if((hand1 == 0) && (hand2 == 0)) {
			return "TIE";
		}  else if((hand1 == 1) && (hand2 == 0)) {
			this.p1score++;
			return "You have won";
		}  else if((hand1 == 0) && (hand2 == 1)) {
			this.p1score++;
			return "You have won";
		}  else if((hand1 == 2) && (hand2 == 0)) {
			this.p2score++;
			return "The AI has won!";
		}  else if((hand1 == 0) && (hand2 == 2)) {
			this.p2score++;
			return "The AI has won!";
		} 
		
		return "ERROR: VALID CHOICE NOT INSERTED";
	}
	
	
}

