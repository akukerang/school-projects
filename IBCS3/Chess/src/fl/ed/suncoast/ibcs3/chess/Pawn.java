package fl.ed.suncoast.ibcs3.chess;

/*
 * PAWN
 * 
 * One of the six chess pieces.
 * This piece can move one space forward.
 * 
 * It may also move two spaces forward, or be captured en passant, under certain conditions.
 * 
 */

public class Pawn extends ChessPiece
{
	public Pawn(int x, int y, boolean color)
	{
		super(x, y, color);
		super.setName(this.getColor()?"p":"P");
	}
	
	//Checks if this piece can move to the square at (x,y).
	public boolean checkLegalMove(int x, int y)
	{
		if(this.getX() == x && this.getY() == y) return false;
		
		if((this.getColor()?y:this.getY()) > (this.getColor()?this.getY():y)) return false;
		
		if(Math.abs(x - this.getX()) > 2) return false;
		else if(Math.abs(x - this.getX()) == 1)
		{
			//Can this Pawn move diagonally forward?
			if(y != this.getY() + (this.getColor()?-1:1)) return false;		
		}
		else
		{
			if(this.getColor())
			{
				if(y < this.getY() - (this.getState() == State.STATE_UNMOVED?2:1)) return false;
			}
			else
			{
				if(y > this.getY() + (this.getState() == State.STATE_UNMOVED?2:1)) return false;
			}
		}
		
		return true;
	}
	
	//Assuming the move would be legal, checks if other pieces are blocking the path.
	public boolean checkLegalPath(int x, int y, ChessPiece[][] squares)
	{
		if(x != this.getX())
		{
			//Pawn is moving diagonally. Only allow if this is for a capture.
			if(squares[x][y] != null)
			{
				if(squares[x][y].getColor() == this.getColor()) return false;
			}
			else
			{
				//Pawn may still be able to capture an enemy pawn en passant.
				if(squares[x][this.getY()] != null)
				{
					if(squares[x][this.getY()].getColor() != this.getColor() && squares[x][this.getY()].getState() == State.STATE_EN_PASSANT)
					{
						//capture en passant
						squares[x][this.getY()].setState(State.STATE_CAPTURED);
						return true;
					}
				}
				else return false;
			}
		}
		
		return true;
	}
}
