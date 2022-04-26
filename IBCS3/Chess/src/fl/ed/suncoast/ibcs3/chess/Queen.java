package fl.ed.suncoast.ibcs3.chess;

/*
 * QUEEN
 * 
 * One of the six chess pieces.
 * This piece can move in any orthogonal or diagonal direction any number of spaces.
 * 
 */

public class Queen extends ChessPiece
{
	public Queen(int x, int y, boolean color)
	{
		super(x, y, color);
		super.setName(this.getColor()?"q":"Q");
	}

	//Checks if this piece can move to the square at (x,y).
	public boolean checkLegalMove(int x, int y)
	{
		if(this.getX() == x && this.getY() == y) return false;
		
		if(Math.abs(this.getX() - x) != Math.abs(this.getY() - y)) 
		{
			if(Math.abs(this.getX() - x) > 0 && Math.abs(this.getY() - y) > 0) return false;
		}
		
		return true;
	}
	
	//Assuming the move would be legal, checks if other pieces are blocking the path.
	public boolean checkLegalPath(int x, int y, ChessPiece[][] squares)
	{
		if(this.getX() != x)
		{
			for(int i = 1 + (x <= this.getX()?x:this.getX()); i < (x <= this.getX()?this.getX():x); i++)
			{
				if(this.getY() != y)
				{
					//diagonal move
					for(int j = 1 + (y <= this.getY()?y:this.getY()); j < (y <= this.getY()?this.getY():y); j++)
					{
						//Only check squares along this diagonal.
						if(this.checkLegalMove(i, j))
						{
							if(squares[i][j] != null) return false;
						}
					}
				}
				else
				{
					//horizontal move
					if(squares[i][y] != null) return false;
				}
				
			}
		}
		else
		{
			//vertical move
			for(int j = 1 + (y <= this.getY()?y:this.getY()); j < (y <= this.getY()?this.getY():y); j++)
			{
				if(squares[x][j] != null) return false;
			}
		}
		
		return true;
	}
}
