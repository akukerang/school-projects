package fl.ed.suncoast.ibcs3.chess;

/*
 * KNIGHT
 * 
 * One of the six chess pieces.
 * This piece can jump 2 spaces in one orthogonal direction, and 1 space in the other orthogonal direction.
 * 
 */

public class Knight extends ChessPiece
{

	public Knight(int x, int y, boolean color)
	{
		super(x, y, color);
		super.setName(this.getColor()?"n":"N");
	}

	//Checks if this piece can move to the square at (x,y).
	public boolean checkLegalMove(int x, int y)
	{
		if(this.getX() == x && this.getY() == y) return false;
		
		if(Math.abs(this.getX() - x) > 2 || Math.abs(this.getY() - y) > 2) return false;
		else
		{
			if(Math.abs(Math.abs(this.getX() - x) - Math.abs(this.getY() - y)) != 1) return false;
		}
		
		return true;
	}
}
