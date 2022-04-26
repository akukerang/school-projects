package fl.ed.suncoast.ibcs3.chess;

/*
 * CHESS PIECE
 * 
 * The main component of the game.
 * Each piece has a position and color on the board,
 * as well as a state (captured or not).
 * 
 */

public class ChessPiece
{
	enum State
	{
		STATE_UNMOVED,
		STATE_EN_PASSANT,
		STATE_MOVED,
		STATE_CHECKED,
		STATE_CHECKMATED,
		STATE_CAPTURED
	};
	private State state;
	
	private int x, y;
	private String name;
	private boolean b_isWhite;

	//Creates a new chess piece.
	public ChessPiece(int x, int y, boolean color)
	{
		this.setX(x);
		this.setY(y);
		this.setColor(color);
		this.setState(State.STATE_UNMOVED);
	}
	
	//Gets
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public String getName()
	{
		return name;
	}

	public boolean getColor()
	{
		return b_isWhite;
	}

	public State getState()
	{
		return state;
	}
		
	//Sets
	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setColor(boolean color)
	{
		this.b_isWhite = color;
	}
	
	public void setState(State state)
	{
		this.state = state;
	}
	
	//Checks if this piece can move to the square at (x,y).
	public boolean checkLegalMove(int x, int y)
	{
		return true;
	}
	
	//Assuming the move would be legal, checks if other pieces are blocking the path.
	public boolean checkLegalPath(int x, int y, ChessPiece[][] squares)
	{
		return true;
	}
}
