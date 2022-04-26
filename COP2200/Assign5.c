/*
Assignment 5
Date: 3/13/2022
Check if number divisible by 9
*/
#define _CRT_SECURE_NO_WARNINGS 
#pragma warning(disable:6031)
#include <stdio.h>

int main() {
	char loop = 'Y';
	int n, sum, temp;
	while (loop == 'Y') {
		do {
			printf("Please enter a positive whole number: ");
			scanf("%d", &n);
		} while (n < 0); //Checks if n is a positive number, if negative asks for input again.
		temp = n;
		sum = 0;
		while (temp > 0) { //Displays the digits, starting with the rightmost
			printf("%d ", temp % 10);
			sum += (temp % 10); //adds sum of digits
			temp = temp / 10;
		}
		printf("\nSum of n is %d ", sum);
		if ((n % 9) != 0) { //Checks if divisible by 9
			printf("\n%d is not divisible by 9", n);
		}
		else {
			printf("\n%d is divisible by 9", n);
		}
		printf("\nWould you like to calculate another number? (Y/N): "); //Asks if user wants to go again, if 'Y' then loops again.
		scanf(" %c", &loop);
	}

	//154368 is divisible by 9 
	//621594 is divisible by 9 
	//123456 is not divisible by 9 
	return 0;
};