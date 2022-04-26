package fl.ed.suncoast.ibcs3.chess;

/*
 * BISHOP
 * 
 * One of the six chess pieces.
 * This piece can move in any diagonal direction any number of spaces.
 * 
 */

public class Bishop extends ChessPiece
{

	public Bishop(int x, int y, boolean color)
	{
		super(x, y, color);
		super.setName(this.getColor()?"b":"B");
	}

	//Checks if this piece can move to the square at (x,y).
	public boolean checkLegalMove(int x, int y)
	{
		if(this.getX() == x && this.getY() == y) return false;
		
		if(Math.abs(this.getX() - x) != Math.abs(this.getY() - y)) return false;
		
		return true;
	}
	
	//Assuming the move would be legal, checks if other pieces are blocking the path.
		public boolean checkLegalPath(int x, int y, ChessPiece[][] squares)
		{
			for(int i = 1 + (x <= this.getX()?x:this.getX()); i < (x <= this.getX()?this.getX():x); i++)
			{
				for(int j = 1 + (y <= this.getY()?y:this.getY()); j < (y <= this.getY()?this.getY():y); j++)
				{
					//Only check squares along this diagonal.
					if(this.checkLegalMove(i, j))
					{
						if(squares[i][j] != null) return false;
					}
				}			
			}
			
			return true;
		}
}
