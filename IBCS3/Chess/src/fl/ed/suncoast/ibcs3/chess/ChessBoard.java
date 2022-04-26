package fl.ed.suncoast.ibcs3.chess;

import java.util.Scanner;

import fl.ed.suncoast.ibcs3.chess.ChessPiece.State;

/*
 * CHESS BOARD
 * 
 * The board on which the game is played.
 * It consists of 64 SQUARES, each of which can have a PIECE on it.
 * If a PIECE is moved to a SQUARE containing an enemy PIECE,
 * that PIECE is captured and removed from play.
 * 
 */

public class ChessBoard
{
	private ChessPiece[][] squares = new ChessPiece[8][8];
	private String whiteSquareColor, blackSquareColor;
	
	//Constructs a new ChessBoard with the two specified background colors.
	public ChessBoard(String white, String black)
	{
		this.setWhiteSquareColor(white);
		this.setBlackSquareColor(black);
		
		for(int i = 0; i < squares.length; i++)
		{
			for(int j = 0; j < squares[i].length; j++)
			{
				squares[i][j] = null;
			}
		}

		squares[0][0] = new Rook(0,0,false);
		squares[1][0] = new Knight(1,0,false);
		squares[2][0] = new Bishop(2,0,false);
		squares[3][0] = new Queen(3,0,false);
		squares[4][0] = new King(4,0,false);
		squares[5][0] = new Bishop(5,0,false);
		squares[6][0] = new Knight(6,0,false);
		squares[7][0] = new Rook(7,0,false);
		for(int i = 0; i < squares[1].length; i++) squares[i][1] = new Pawn(i,1,false);

		for(int i = 0; i < squares[6].length; i++) squares[i][6] = new Pawn(i,6,true);
		squares[0][7] = new Rook(0,7,true);
		squares[1][7] = new Knight(1,7,true);
		squares[2][7] = new Bishop(2,7,true);
		squares[3][7] = new Queen(3,7,true);
		squares[4][7] = new King(4,7,true);
		squares[5][7] = new Bishop(5,7,true);
		squares[6][7] = new Knight(6,7,true);
		squares[7][7] = new Rook(7,7,true);
	}

	//Gets
	public String getWhiteSquareColor()
	{
		return whiteSquareColor;
	}

	public String getBlackSquareColor()
	{
		return blackSquareColor;
	}
	
	public ChessPiece getPiece(int x, int y)
	{
		return squares[x][y];
	}

	//Sets
	public void setWhiteSquareColor(String whiteSquareColor)
	{
		this.whiteSquareColor = whiteSquareColor;
	}

	public void setBlackSquareColor(String blackSquareColor)
	{
		this.blackSquareColor = blackSquareColor;
	}
	
	public void setPiece(int x, int y, ChessPiece cp)
	{
		this.squares[x][y] = cp;
	}
	
	//Converts user input.
	public int[] convertUserInput(char x, char y)
	{
		int px, py;
		
		switch(x)
		{
			case 'a': px = 0; break;
			case 'b': px = 1; break;
			case 'c': px = 2; break;
			case 'd': px = 3; break;
			case 'e': px = 4; break;
			case 'f': px = 5; break;
			case 'g': px = 6; break;
			case 'h': px = 7; break;
			default:  px = -1;
		}
		
		if(y == '9') py = -1;
		else if(Character.isDigit(y)) py = 7 - (y - 49);
		else py = -1;
		
		int[] pos = {px,py};
		return pos;
	}
	
	//Moves a piece on the board.
	public boolean move(Scanner userInput, boolean whiteMove)
	{
		String pos = "";
		char x = ' ', y = ' ';
		
		//Get starting position.
		System.out.println("\nWhich piece will " + (whiteMove?"white":"black") + " move? Enter a position such as a1: ");
		pos = userInput.next();
		x = pos.charAt(0);
		y = pos.charAt(1);
		
		//Convert user input into usable format.
		int[] convertedPos = convertUserInput(x,y);
		int px = convertedPos[0], py = convertedPos[1];	
		
		//If the position isn't a position on the board, abort.
		if(px < 0 || py < 0)
		{
			System.out.println("Invalid position Must use a-h and 1-8.");
			return false;
		}
		
		//Check if a valid piece is in this position.
		ChessPiece cp = this.getPiece(px, py);
		if(cp == null)
		{
			System.out.println("\nNo piece to move.");
			return false;
		}
		if(cp.getColor() != whiteMove)
		{
			System.out.println("\nMust select a " + (whiteMove?"white":"black") + " piece.");
			return false;
		}
		
		//Get and convert user input for destination position.
		System.out.println("\nWhere will this piece move to? Enter a position such as a1: ");
		pos = userInput.next();
		x = pos.charAt(0);
		y = pos.charAt(1);
		
		convertedPos = convertUserInput(x,y);
		px = convertedPos[0];
		py = convertedPos[1];
		
		//If the position isn't a position on the board, abort.
		if(px < 0 || py < 0)
		{
			System.out.println("Invalid position Must use a-h and 1-8.");
			return false;
		}
		
		//check if space is unoccupied by ally piece
		if(this.getPiece(px, py) != null)
		{
			if(this.getPiece(px, py).getColor() == cp.getColor())
			{
				System.out.println("Cannot capture own piece.");
				return false;
			}
		}
		
		//check valid move
		if(!cp.checkLegalMove(px, py))
		{
			System.out.println("Illegal move.");
			return false;
		}
		
		//check valid path
		if(!cp.checkLegalPath(px, py, this.squares))
		{
			System.out.println("Illegal move.");
			return false;
		}
		
		//Move is valid. Update states.
		if(this.getPiece(px, py) != null)
		{
			this.getPiece(px, py).setState(State.STATE_CAPTURED);
		}
		
		switch(cp.getState())
		{
			case STATE_UNMOVED:
			{
				if(cp instanceof Pawn) cp.setState(State.STATE_EN_PASSANT);
				else cp.setState(State.STATE_MOVED);
				break;
			}
			case STATE_EN_PASSANT:
			{
				cp.setState(State.STATE_MOVED);
				break;
			}
			default:
		}
		
		//Clean board.
		if(this.squares[px][cp.getY()] != null)
		{
			if(this.squares[px][cp.getY()].getState() == State.STATE_CAPTURED)
			{
				this.setPiece(px, cp.getY() , null);
			}
		}
		
		//Do it!
		this.setPiece(cp.getX(), cp.getY(), null);
		this.setPiece(px, py, cp);
		cp.setX(px);
		cp.setY(py);
		
		
		
		return true;
	};
}
