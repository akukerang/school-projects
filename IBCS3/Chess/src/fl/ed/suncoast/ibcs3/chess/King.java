package fl.ed.suncoast.ibcs3.chess;

public class King extends ChessPiece
{
	private boolean hasCastled;
	
	public King(int x, int y, boolean color)
	{
		super(x, y, color);
		super.setName(this.getColor()?"k":"K");
		this.setCastled(false);
	}

	//Gets	
	public boolean getCastled()
	{
		return hasCastled;
	}

	//Sets
	public void setCastled(boolean hasCastled)
	{
		this.hasCastled = hasCastled;
	}
	
	//Checks if this piece can move to the square at (x,y).
	public boolean checkLegalMove(int x, int y)
	{
		if(this.getX() == x && this.getY() == y) return false;
		
		//try to castle
		if(y == this.getY())
		{
			if(Math.abs(x - this.getX()) == 2) return true;
		}
		
		if(Math.abs(this.getX() - x) > 1 || Math.abs(this.getY() - y) > 1) return false;
		
		return true;
	}
	
	//Assuming the move would be legal, checks if other pieces are blocking the path.
	public boolean checkLegalPath(int x, int y, ChessPiece[][] squares)
	{
		//try to castle
		if(y == this.getY())
		{
			if(x == this.getX() - 2)
			{
				//try to queenside castle
				if(this.getState() != State.STATE_UNMOVED) return false;
				if(squares[0][y] != null)
				{
					if(squares[0][y].getState() != State.STATE_UNMOVED) return false;
				}
				else return false;
				if(squares[1][y] != null) return false;
				if(squares[2][y] != null) return false;
				if(squares[3][y] != null) return false;
				for(int i = 0; i <= 3; i++) if(isSquareChecked(i,y,squares)) return false;
			}
			else if(x == this.getX() + 2)
			{
				//try to kingside castle
				if(this.getState() != State.STATE_UNMOVED) return false;
				if(squares[7][y] != null)
				{
					if(squares[7][y].getState() != State.STATE_UNMOVED) return false;
				}
				else return false;
				if(squares[6][y] != null) return false;
				if(squares[5][y] != null) return false;
				for(int i = 7; i >= 4; i--) if(isSquareChecked(i,y,squares)) return false;
			}
		}
		
		//only move if the target square isn't checked
		return !this.isSquareChecked(x, y, squares);
	}
	
	//Checks if square is in check.
	public boolean isSquareChecked(int x, int y, ChessPiece[][] squares)
	{	
		/*
		 * Add functionality to this Chess program by checking if a square is under attack.
		 * 
		 * Given a square (x,y), you must check for attacks from the opposite team from every direction.
		 * 
		 * The boolean function ChessPiece.getColor(), accessed using this.getColor(), will tell you which color team is currently in play.
		 * The enemy team is therefore the opposite value, !this.getColor().
		 * 
		 * Each piece on the chessboard can be accessed using the squares 2D array.
		 * For example, squares[0][0] would return whatever ChessPiece is in the top left corner.
		 * Be careful as this may be null; you must check to see if it's not null before you access any methods on a value of squares.
		 * 
		 * You must do the following:
		 * 
		 * 1) Given the square (x,y), you must check squares to the left until you hit a non-null square. 
		 * In other words, check (x-1,y), (x-2, y), etc. up to (0,y), until you find a ChessPiece that isn't null.
		 * If that piece is an enemy rook or queen, this square is under attack by that rook or queen, and you may return true immediately.
		 * Do not check squares after the first piece is found.
		 */
		int iterator = 1;
		while(x>=0)
		{
			if(squares[x-iterator][y] != null)
			{
				if(squares[x-iterator][y].getName().equalsIgnoreCase("q")&&squares[x-iterator][y].getColor() != this.getColor())
				{
					return true;
				}
				else if(squares[x-iterator][y].getName().equalsIgnoreCase("r")&&squares[x-iterator][y].getColor() != this.getColor())
				{
					return true;
				}
				else
				{
					break;
				}
			}
			else
			{
				iterator++;
			}
		}
		 /*
		 * 2) Repeat the above process but in the right direction.
		 * In other words check (x+1,y), (x+2,y), etc. up to (7,y) until you find a ChessPiece that isn't null.
		 * As in step 1, if this piece is an enemy rook or queen, you can return true for the same reason as in step 1.
		 * If it isn't an enemy rook or queen, stop checking to the right.
		 */
		iterator = 1;
		while(x<=7)
		{
			if(squares[x+iterator][y] != null)
			{
				if(squares[x+iterator][y].getName().equalsIgnoreCase("q")&&squares[x+iterator][y].getColor() != this.getColor())
				{
					return true;
				}
				else if(squares[x+iterator][y].getName().equalsIgnoreCase("r")&&squares[x+iterator][y].getColor() != this.getColor())
				{
					return true;
				}
				else
				{
					break;
				}
			}
			else
			{
				iterator++;
			}
		}
		 /* 
		 * 3) Repeat steps 1 and 2, but vertically.
		 * Check (x,y-1), (x,y-2), ...(x,0) and see if the first piece is an enemy rook or queen.
		 * Then check (x,y+1),(x,y+2), ...(x,7) in the same manner.
		 */
		
		iterator = 1;
		while(y>=0)
		{
			if(squares[x][y-iterator] != null)
			{
				if(squares[x][y-iterator].getName().equalsIgnoreCase("q")&&squares[x][y-iterator].getColor() != this.getColor())
				{
					return true;
				}
				else if(squares[x][y-iterator].getName().equalsIgnoreCase("r")&&squares[x][y-iterator].getColor() != this.getColor())
				{
					return true;
				}
				else
				{
					break;
				}
			}
			else
			{
				iterator++;
			}
		}
		iterator = 1;
		while(y<=7)
		{
			if(squares[x][y+iterator] != null)
			{
				if(squares[x][y+iterator].getName().equalsIgnoreCase("q")&&squares[x][y+iterator].getColor() != this.getColor())
				{
					return true;
				}
				else if(squares[x][y+iterator].getName().equalsIgnoreCase("r")&&squares[x][y+iterator].getColor() != this.getColor())
				{
					return true;
				}
				else
				{
					break;
				}
			}
			else
			{
				iterator++;
			}
		}
		/*
		 * 4) Check the square (x-1,y-1), (x-2,y-2), etc. until a ChessPiece is found.
		 * Be careful not to let x or y go past 0.
		 * As this is diagonal you are now looking for an enemy bishop or queen.
		 * If there is a black pawn at (x-1,y-1) and this king is white (check with this.getColor()), then that pawn is checking as well and you may return true.
		 */
		 iterator = 1;
		 while(x>=0 && y>=0)
		 {
			 if(squares[x-iterator][y-iterator]!= null)
			 {
				 if(iterator == 1 && squares[x-iterator][y-iterator].getName().equalsIgnoreCase("p") && squares[x-iterator][y-iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else if((squares[x-iterator][y-iterator].getName().equalsIgnoreCase("q") || squares[x-iterator][y-iterator].getName().equalsIgnoreCase("b")) && squares[x-iterator][y-iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else
				 {
					 break;
				 }
			 }
			 else
			 {
				 iterator++;
			 }
		 }
		 /* 
		 * 5) Check in the top-right direction by checking (x+1,y-1), (x+2,y-2), etc.
		 * Look for an enemy bishop or queen.
		 * If (x+1,y-1) has a black pawn and this king is white, return true.
		 */
		 iterator = 1;
		 while(x<=7 && y>=0)
		 {
			 if(squares[x+iterator][y-iterator]!= null)
			 {
				 if(iterator == 1 && squares[x+iterator][y-iterator].getName().equalsIgnoreCase("p") && squares[x+iterator][y-iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else if((squares[x+iterator][y-iterator].getName().equalsIgnoreCase("q") || squares[x+iterator][y-iterator].getName().equalsIgnoreCase("b")) && squares[x+iterator][y-iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else
				 {
					 break;
				 }
			 }
			 else
			 {
				 iterator++;
			 }
		 }
		 /* 6) Check in the downward diagonal directions for enemy bishops and queens.
		 * If the first square has a white pawn and this king is black, return true.
		 */
		 iterator = 1;
		 while(x<=7 && y<=7)
		 {
			 if(squares[x+iterator][y+iterator]!= null)
			 {
				 if(iterator == 1 && squares[x+iterator][y+iterator].getName().equalsIgnoreCase("p") && squares[x+iterator][+iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else if((squares[x+iterator][y+iterator].getName().equalsIgnoreCase("q") || squares[x+iterator][y+iterator].getName().equalsIgnoreCase("b")) && squares[x+iterator][y+iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else
				 {
					 break;
				 }
			 }
			 else
			 {
				 iterator++;
			 }
		 }
		 iterator = 1;
		 while(x>=0 && y<=7)
		 {
			 if(squares[x-iterator][y+iterator]!= null)
			 {
				 if(iterator == 1 && squares[x-iterator][y+iterator].getName().equalsIgnoreCase("p") && squares[x-iterator][y+iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else if((squares[x-iterator][y+iterator].getName().equalsIgnoreCase("q") || squares[x-iterator][y+iterator].getName().equalsIgnoreCase("b")) && squares[x-iterator][y+iterator].getColor()!=this.getColor())
				 {
					 return true;
				 }
				 else
				 {
					 break;
				 }
			 }
			 else
			 {
				 iterator++;
			 }
		 }
		 /* 7) Check for knight attacks by looking at the 8 squares a knight could be attacking from.
		 * In other words, check the squares at (x-2,y-1), (x-2,y+1), (x-1,y-2), (x-1,y+2), (x+1,y-2), (x+1,y+2), (x+2,y-1), and (x+2,y+1).
		 * If any of these 8 squares have an enemy knight, return true.
		 */
		 if(squares[x-2][y-1].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }
	        if(squares[x-2][y+1].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }

	        if(squares[x-1][y-2].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }
	        if(squares[x-1][y+2].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }


	        if(squares[x+1][y-2].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }
	        if(squares[x+1][y+2].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }

	        if(squares[x+2][y-1].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }
	        if(squares[x+2][y+1].getName().equalsIgnoreCase("n")&&squares[x][y].getColor() != this.getColor()) {
	            return true;
	        }
	        
	        //CHECK FOR ENEMY KINGS
	        if(squares[x+1][y].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        if(squares[x-1][y].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        if(squares[x+1][y+1].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        if(squares[x-1][y+1].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        if(squares[x+1][y-1].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        if(squares[x-1][y-1].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        if(squares[x][y-1].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        if(squares[x][y+1].getName().equalsIgnoreCase("k")&&squares[x][y].getColor() != this.getColor()) {
	        	return true;
	        }
	        
	        
	        
	        
	        
	        
	        
		 /* 8) If you haven't returned true yet, this square should be safe and you can return false.
		 */ 
	     /* 
		 * For the Chess enthusiasts in class, note that completing this function won't complete the program.
		 * There's still no way to detect checkmate, for example.
		 */
		return false;
	}
}