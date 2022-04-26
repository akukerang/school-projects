/*
Assignment 2
Math Functions
*/
#define _CRT_SECURE_NO_WARNINGS 
#pragma warning(disable:6031) //above is required for scanf to work on VS.
#include <stdio.h> //required libraries
#include <math.h>

int main() {
	printf("Smile Smile!\n"); //Part 1
	printf("Joy\nGratitude\n");
	printf("Work ");
	printf("Hard\n");
	printf("Play Harder\n");

	//Part 2
	int val1, val2, val3;
	printf("Please enter the value for value 1: "); //Inputs for the values
	scanf("%d", &val1);
	printf("Please enter the value for value 2: ");
	scanf("%d", &val2);
	printf("Please enter the value for value 3: ");
	scanf("%d", &val3);
	double average = (val1 + val2 + val3) / 3.0; //Calculates the average
	printf("The average of three values %d,%d and %d is %.2lf\n", val1, val2, val3, average);
	double std_dev = sqrt((((val1 - average) * (val1 - average)) + ((val2 -average) * (val2 - average)) + ((val3 - average) * (val3 - average))) / 3.0); //Calculates the std. deviation.
	printf("The average of standard deviation of the three values %d,%d and %d is %.3lf\n", val1, val2, val3, std_dev);


	return 0;
};