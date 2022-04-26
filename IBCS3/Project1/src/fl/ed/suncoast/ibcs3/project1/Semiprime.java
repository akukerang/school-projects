package fl.ed.suncoast.ibcs3.project1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Semiprime {
	

	
	
	
	public static boolean isPrime(int num) {
		
		if (num <= 1) { 
			return false;
		}
		for(int i = 2; i < num; i++) { //if the number mod i returns zero it's not a prime.
			if(num%i == 0 ) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isSemiPrime(int n) {
		if(isPrime(n)) { //if the input is prime, it cant be semi prime
			return false;
		}
		
		
		int count = 0; 
		
		for(int i = 2; count < 2 && i * i <= n; i++) {
			
			while(n % i == 0 ) { //if n mod i = 0, it add to count
				n /= i; //divides the input by i
				count++;
			}
			
			
			
		}
		
		if (n > 1) { //if the remainig n is greater than one it adds to count.
			count++;
		}
		
		if (count == 2) { //if count is 2 than it is semiprime else false
			return true;
		} else {
			return false;
		}
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter n: ");
		int n = 0;
		
		while(true) { //checks if the scanner input is a integer
			try {
				n = sc.nextInt(); 
				break;
			} catch (InputMismatchException e) { //if it catches an inputmismatchexception, it tries again.
				System.out.println("that's  not a integer");
				sc.nextLine();
			}			
		
		
	}
		System.out.println(isSemiPrime(n));
	sc.close();
	
	
	
	
	}
}
