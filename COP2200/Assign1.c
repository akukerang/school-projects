/*
Assignment 1
Date: 02/03/2022
Print and Scan practice
*/
#define _CRT_SECURE_NO_WARNINGS //for scanf warnings on visual studio
#pragma warning(disable:6031)

#include <stdio.h> //library for printf and scanf


int main() {
	int dob, mob;
	char lastInitial;
	printf("Please enter the month of your day of birth: \n"); //prompts the user to enter their birth month, then it scans for an input
	scanf("%d", &mob);
	printf("Please enter the date of your day of birth: \n"); //prompts the user to enter their birth date, then it scans for an input
	scanf("%d", &dob);
	printf("Please enter your last initial:  \n"); //prompts the user to enter their last initial, then scans for an input. %c has a space in front of it because without it, there is issues.
	scanf(" %c", &lastInitial);

	double result1, result2;
	//I think result1 would print the correct result
	mob = mob + 12; //adds 12 to the month and 30 to the date.
	dob = dob + 30;
	result1 = (dob * 30.0) / (mob * 12.0); //the calculation for result 1 and result2
	result2 = (dob * 30.0) / (mob * 15.0);
	printf("The average of result 1 is: %.2lf\n", result1); //prints out the result of result1 and result2 with 2 decimal points.
	printf("The average of result 2 is: %.2lf\n", result2);
	int dumpster = dob % mob;
	printf("The remainder of your birth date by birth month is %d\n", dumpster);
	printf("The ASCII code of your last initial %c is %d", lastInitial, (int)lastInitial);


	return 0;
};