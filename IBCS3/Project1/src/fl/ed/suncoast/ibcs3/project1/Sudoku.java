package fl.ed.suncoast.ibcs3.project1;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;


public class Sudoku {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int[][] sudoku = new int[9][9];
		int n;
		for(int i = 0; i < 9; i++) { //for loop in a for loop to fill out the 2d array
			
			
			for(int j = 0; j < 9; j++) {
				System.out.println("enter the value for row "+ i+ " and column " + j);
				
				while(true) {
					try { //try catch checks if the user input is between 1-9 and an int
						n = sc.nextInt();
						
						if(n > 9 || n < 1) {
							throw new Exception ("enter a number 1-9"); //Throws an exception if the number is not between 1-9
						}
						
						
						break;
					} catch (InputMismatchException e) { //catches the exception if the input was a not an int
						System.out.println("that's  not a integer");
						sc.nextLine();
					} catch (Exception e) {
						System.out.println("enter a number 1 - 9"); //catches the exception if the input was a between 1-9
						sc.nextLine();	
						
					}
				
				}				
				sudoku[i][j] = n;
			}
			
			
		}
		
		print2dArray(sudoku);
		System.out.println(validate(sudoku));
		
		
		


		
		

		
		
	}
	
	public static boolean validate(int[][] user) {
		
		if(checkRows(user) && checkColumns(user) && checkSquare(user)) { //checks if the row, column, and square are all true, if they are return true
			return true;
		} 
		//if not all are true return false
		return false;
		
		
	}
	
	
	
	
	
	public static void print2dArray(int[][] a) {
		for(int i = 0; i < 9; i++) {
			
			for(int j = 0; j < 9; j++) {
				
				System.out.print(a[i][j] + " ");
				
			}
			System.out.println();
			
			
			
		}
	}
	
	public static boolean checkRows(int[][] a) {
		ArrayList <Integer> compareTo = new ArrayList<Integer>(); //arraylist from 1 to 9, to check the rows of the sudoku if it is 1 all numbers 1 to 9.
		for(int i = 1; i <= 9; i++) {
			compareTo.add(i);
			
		}
		ArrayList<Integer> row = new ArrayList<Integer>();
		for(int i = 0; i < 9; i++) {
			
			for(int j = 0; j < 9; j++) {
				
				row.add(a[i][j]);
				
				
			}
			if(!(row.containsAll(compareTo))) { //checks if the current row has all the values in the compareTo arraylist, checks if has all numbers 1 to 9
				return false;
			} 
			row.clear(); //clears the arraylist for the current row
			
		}
		return true;
		
	}
	
	
	public static boolean checkColumns(int[][] a) {
		ArrayList <Integer> compareTo = new ArrayList<Integer>(); //arraylist from 1 to 9, to check the rows of the sudoku if it is 1 all numbers 1 to 9.
		for(int i = 1; i <= 9; i++) {
			compareTo.add(i);
			
		}
		
		ArrayList<Integer> column = new ArrayList<Integer>();
		for(int j = 0; j < 9; j++) {

			for(int i = 0; i < 9; i++) { //goes through the column adding it to the arraylist
				column.add(a[i][j]);
			}
			if(!(column.containsAll(compareTo))) { //checks if the column arraylist is 1 to 9, by comparing it to the compareTo arraylist, if it's false it returns false
				return false;
			}
			column.clear(); //clears the column arraylist for the current column
		}

		return true;
		
		
		
	}
	
	
	public static boolean checkSquare(int[][] a) {
		ArrayList <Integer> compareTo = new ArrayList<Integer>(); //arraylist from 1 to 9, to check the rows of the sudoku if it is 1 all numbers 1 to 9.
		for(int i = 1; i <= 9; i++) {
			compareTo.add(i);
			
		}
		
		ArrayList <Integer> square = new ArrayList<Integer>();
		
		
		for(int squarerow = 0; squarerow < 3; squarerow++) { //there are 3 squares in each row 
			for(int squarecol = 0; squarecol < 3; squarecol++) { //there are 3 squares in each column
				
				
				for(int i = 0; i < 3; i++)  //goes through the 3x3 of each square adding to an arryalist
				{
					for(int j = 0; j < 3; j++) {
						
						square.add(a[i + (3 * squarerow)][j + (3 * squarecol)]); //goes to the appropirate square by adding it (3 * squre row) and (3 * square col)
						
						
					}
					
					if(!(square.containsAll(compareTo))) { //checks if each square has 1-9.
						return false;
					}
					
					square.clear(); //clears the square arraylist for the current suqre

				}

				
			}
			
			
		}

		

		
		return true;
	}
	
	
	
	
	
	
}
