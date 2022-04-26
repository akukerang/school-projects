/*
Assignment 7
Date: 4/16/2022
2d arrays, and calculating
*/
#define _CRT_SECURE_NO_WARNINGS  //things for VS
#pragma warning(disable:6031)
#include <stdio.h>
double calculateAverage(int list[], int numOfItems);
int main() {
	int a[4][3];
	double quiz_ave[3];
	double st_ave[4];
	int sum;
	int tempArray[4];
	for (int i = 0; i < 4; i++) { //Inputs for student scores
		printf("You are on Student %d: \n", i+1);
		for (int j = 0; j < 3; j++) {
			printf("Enter Quiz %d: ", j+1);
			scanf(" %d", &a[i][j]);
		}
	}

	//Calculates quiz avg
	for (int i = 0; i < 3; i++) { 
		for (int j = 0; j < 4; j++) {
			tempArray[j] = a[j][i]; //stores the column in an temporary 1d array
		}
		quiz_ave[i] = calculateAverage(tempArray, 4); //calculates average of column
	}
	//Calculates student average
	for (int i = 0; i < 4; i++) {
		st_ave[i] = calculateAverage(a[i], 3); //gets the row of the 2d array
	}

	//Prints Table
	printf("                Q1     Q2     Q3   Student Average\n"); //formatting as a table is hard
	for (int i = 0; i < 4; i++) {
		printf("Student %d: ", i);
		for (int j = 0; j < 3; j++) {
			printf("%7d", a[i][j]); //prints student scores
		}
		printf("%16.2lf ", st_ave[i]); //prints student average
		printf("\n");
	}

	printf("Quiz Avg:");
	for (int i = 0; i < 3; i++) {
		printf("%7.2lf ", quiz_ave[i]); //prints quiz average

	}
	return 0;
};
double calculateAverage(int list[], int numOfItems) {
	int sum = 0;
	for (int i = 0; i < numOfItems; i++) {
		sum += list[i];
	}
	return sum / (double)numOfItems;


}
