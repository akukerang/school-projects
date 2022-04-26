package fl.ed.suncoast.ibcs3.chess;

import java.util.Scanner;

/*
 * CHESS
 * 
 * The main loop of the program.
 * 
 * Contains several utility functions as well.
 */


public class Chess
{
	public static void main(String[] args)
	{
		System.out.println("Welcome to Chess.");
				
		ChessBoard cb = new ChessBoard("YELLOW", "GREEN");

		boolean whiteMove = true;
		Scanner userInput =  new Scanner(System.in);
		
		while(true)
		{
			//Show the current state of the board.
			printBoardColor(cb);
			
			//Let one player move.
			boolean validMove = false;
			do
			{
				validMove = cb.move(userInput, whiteMove);
			} while(validMove == false);
			
			//Change control to the other player.
			whiteMove = !whiteMove;
		}
	}
	
	public static void resetPrintColor()
	{
		setPrintColor("","");
	}
	
	public static void setPrintColor(String foreground, String background)
	{
		String s = "\033[";
		
		if(foreground.equalsIgnoreCase("BLACK")) 		s += "30";
		else if(foreground.equalsIgnoreCase("RED")) 	s += "31";
		else if(foreground.equalsIgnoreCase("GREEN")) 	s += "32";
		else if(foreground.equalsIgnoreCase("YELLOW")) 	s += "33";
		else if(foreground.equalsIgnoreCase("BLUE")) 	s += "34";
		else if(foreground.equalsIgnoreCase("MAGENTA"))	s += "35";
		else if(foreground.equalsIgnoreCase("CYAN")) 	s += "36";
		else if(foreground.equalsIgnoreCase("WHITE"))	s += "37";
		else if(foreground.equalsIgnoreCase("GRAY"))	s += "90";
		else											s += "39";

		s += "m\033[";
		
		if(background.equalsIgnoreCase("BLACK")) 		s += "40";
		else if(background.equalsIgnoreCase("RED")) 	s += "41";
		else if(background.equalsIgnoreCase("GREEN")) 	s += "42";
		else if(background.equalsIgnoreCase("YELLOW")) 	s += "43";
		else if(background.equalsIgnoreCase("BLUE")) 	s += "44";
		else if(background.equalsIgnoreCase("MAGENTA"))	s += "45";
		else if(background.equalsIgnoreCase("CYAN")) 	s += "46";
		else if(background.equalsIgnoreCase("WHITE")) 	s += "47";
		else if(foreground.equalsIgnoreCase("GRAY"))	s += "100";
		else  											s += "49";
		
		s += "m";
		
		System.out.printf("%s", s);
	}
	
	public static void printBoard(ChessBoard cb)
	{
		System.out.printf("\n abcdefgh");
		
		for(int j = 0; j < 8; j++)
		{
			System.out.printf("\n%d",8-j);
			for(int i = 0; i < 8; i++)
			{				
				if(cb.getPiece(i, j) != null)
				{
					System.out.printf("%s", cb.getPiece(i,j).getName());
				}
				else
				{
					System.out.printf("-");
				}
			}
		}
		System.out.printf("\n");
	}
	
	public static void printBoardColor(ChessBoard cb)
	{
		System.out.printf("\n abcdefgh");
		
		for(int j = 0; j < 8; j++)
		{
			resetPrintColor();
			System.out.printf("\n\033[22m%d",8-j);
			
			for(int i = 0; i < 8; i++)
			{
				setPrintColor(cb.getPiece(i, j)==null?"WHITE":cb.getPiece(i, j).getColor()?"WHITE":"BLACK",(i+j)%2==0?cb.getWhiteSquareColor():cb.getBlackSquareColor());
				System.out.printf("\033[1m");
				
				if(cb.getPiece(i, j) != null)
				{
					System.out.printf("%s", cb.getPiece(i,j).getName().toUpperCase());
				}
				else
				{
					System.out.printf(" ");
				}
			}
		}

		resetPrintColor();
		System.out.printf("\033[22m\n");
	}
	
}
