/*
Bonus Lab 1
Date: 2/23/2022
*/
#define _CRT_SECURE_NO_WARNINGS 
#pragma warning(disable:6031)
#include <stdio.h>
#define SINGLE 17850
#define HEAD 23900
#define JOINT 29750
#define SEPERATE 14875
#define PERCENT1 .15
#define PERCENT2 .28

int main() {
	int incomebracket;
	double income, tax;
	printf("Please enter the income bracket you are in: \n1: Single \n2: Head of Household \n3: Married, Joint\n4: Married, Seperate \n");
	scanf("%d", &incomebracket);
	printf("Please enter your income: ");
	scanf("%lf", &income);

	if (income > 0) {
		switch (incomebracket) {
		case 1:
			if (income >= SINGLE) {
				tax = (PERCENT1 * SINGLE) + (PERCENT2 * (income - SINGLE));
			}
			else {
				tax = PERCENT1 * income;
			}
			printf("You owe $ %.2lf in taxes", tax);
			break;
		case 2:
			if (income >= HEAD) {
				tax = (PERCENT1 * HEAD) + (PERCENT2 * (income - HEAD));
			}
			else {
				tax = PERCENT1 * income;
			}
			printf("You owe $%.2lf in taxes", tax);
			break;
		case 3:
			if (income >= JOINT) {
				tax = (PERCENT1 * JOINT) + (PERCENT2 * (income - JOINT));
			}
			else {
				tax = PERCENT1 * income;
			}
			printf("You owe $%.2lf in taxes", tax);
			break;
		case 4:
			if (income >= SEPERATE) {
				tax = (PERCENT1 * SEPERATE) + (PERCENT2 * (income - SEPERATE));
			}
			else {
				tax = PERCENT1 * income;
			}
			printf("You owe $%.2lf in taxes", tax);
			break;
		default:
			printf("Invalid option. Try Again.");
		}
	}
	else {
		printf("Please insert a valid number");
	}
	
	


	return 0;
};