/*
Assignment 6
Date: 3/29/2022
Factorials
*/
#define _CRT_SECURE_NO_WARNINGS 
#pragma warning(disable:6031)
#define PI 3.14159265
#include <stdio.h>
#include <math.h>
double factorialApprox(int n);
long int factorialAccurate(int n);
double percentError(double accurate, double approximate);
int main() {
	char again = 'Y';
	int n;
	while (again == 'Y') {
		printf("Please enter an integer: "); //Scans for user input
		scanf("%d", &n);
		if (n < 0) { //Checks if n is negative
			printf("Please enter a positive integer\n"); 
		}
		else if (n > 12) { //Checks if n is greater than 12
			printf("Please enter a value less than 12\n");
		}
		else { 
			printf("%d! is approximately %lf\n", n, factorialApprox(n)); //approximate factorial
			printf("%d! is %d accurately\n", n, factorialAccurate(n)); //accurate factorial
			printf("The percent error is %lf%%\n", percentError(factorialAccurate(n), factorialApprox(n))); //percent erro
			printf("Would you like to calculate another value(Y/N)?: ");
			scanf(" %c", &again); //If enter Y goes through loop again, else ends.
		}
	}

	return 0;
};
double factorialApprox(int n) {
	return pow(n, n) * exp(-n) * sqrt((2.0 * n + (1.0 / 3.0))*PI);	
}
long int factorialAccurate(int n) {
	int factorial = 1;
	for (int i = 1; i <= n; i++) {
		factorial *= i;
	}
	return factorial;
}

double percentError(double accurate, double approximate) {
	return fabs(accurate - approximate) / accurate * 100.0;
}