package fl.ed.suncoast.ibcs3.project1;
import java.util.InputMismatchException;
import java.util.Scanner;
public class BinomialFormula {
	
	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter n: ");
		long n = 0L;
		
		while(true) { //detects if scanner input is a integer.
			try {
				n = sc.nextLong();
				break;
			} catch (InputMismatchException e) {//if it catches an inputmismatchexception, it tries again.
				System.out.println("that's  not a integer");
				sc.nextLine();
			}			
		
		}
		System.out.println(BinomialExpansion(n));
		
		sc.close(); //closes scanner because memory leaks blow.
		
		
	}
	
	

	public static String BinomialExpansion(long n) {
		if(n == 0) {
			return "1"; //if n is 0 then it equals 1, if n is 1 then it equals a+b, if it's -1 than it equals 1/a+b.
		}
		if(n == 1) {
			return "a+b";
		}
		if(n == -1) {
			return "1/a+b";
		}
		
		
		if(n < 0) { //if n is greater than zero then it does this aka negative
			String s = "a^" + Math.abs(n) + " + "; //creates a String where the equation started with a since the first binomialcoeff is 1 to the power of n, it has to be absolute value because of negative values
			
			for(long i = 1; i < Math.abs(n); i++) {
				
				s += getBinomialCoeff(Math.abs(n), i) + "a^" + (Math.abs(n)-i) + "b^" + (i) +" + "; //it gets the coeff of the nth size binomial equation at the i place, the power of a goes down while the power of b goes up
				
				
				}
			
			s += "b^" + Math.abs(n); //equation finishes with b to the power of n
			
			return "1/"+s; //then it puts the entire s string over 1 because it's negative and returns this string
			
			
			
		}
		
		
		else if (n > 1) { //same thing as the previous section except for positive numbers and doesnt do the 1/s.
		
			String s = "a^" + n + " + ";
			
			for(long i = 1; i < n; i++) {
				
				s += getBinomialCoeff(n, i) + "a^" + (n-i) + "b^" + (i) +" + ";
				
				}
			
			s += "b^" + n;
			
			return s; //returns the string
		}
		
		return ""; //returns an empty string if none of this wroks
		
	}
	
	
	public static long getBinomialCoeff(long n, long k) {
		
		
		if(k == 0 || k == n) { 
			return 1;
		}
		return getBinomialCoeff(n-1, k) + getBinomialCoeff(n-1, k-1); //gets the coeff using pascal triangle stuff. and recursion stuff i'm still a bit confused about.
		

		
	}
	
	

	
	
	
}
