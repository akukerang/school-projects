/*
Assignment 4
Date: March 2, 2022
Switch statements, BMI calculator
*/
#define _CRT_SECURE_NO_WARNINGS 
#pragma warning(disable:6031)
#include <stdio.h>

int main() {
	char menuchoice;
	double weight, height, x, y;
	printf("Please pick a choice: \na Information \nb. BMI Calculator\nc. Cartesian Plane point locator\nd. Exit\n");
	scanf("%c", &menuchoice);
	switch (menuchoice) {
		case 'a':
		case 'A':
			printf("This program includes a BMI calculator, and a cartesian plane point locator");
			break;
		case 'b':
		case 'B':
			printf("Please enter your body weight in lbs: ");
			scanf("%lf", &weight);
			printf("Please enter your height in inches: ");
			scanf("%lf", &height);
			double bmi = (703.0 * weight) / (height * height);
			printf("You're BMI is %lf\n", bmi);
			if (bmi >= 30) {
				printf("You are obese\n");
			}
			else if (bmi >= 25) {
				printf("You are overweight\n");
			}
			else if (bmi >= 18.5) {
				printf("You are normal\n");
			}
			else {
				printf("You are underweight\n");
			}
			break;

		case 'c':
		case 'C':
			printf("Please enter an x and y value: ");
			scanf("%lf %lf", &x, &y);
			if (x == 0 && y == 0) {
				printf("(%.1lf, %.1lf) is on the origin",x,y);
			}
			else if (x == 0 && y != 0) {
				printf("(%.1lf, %.1lf) is on the y axis", x, y);
			}
			else if (y == 0 && x != 0) {
				printf("(%.1lf, %.1lf) is on the x axis", x, y);
			}
			else if (y > 0 && x > 0) {
				//q1
				printf("(%.1lf, %.1lf) is in Q1", x, y);
			}
			else if (y > 0 && x < 0) {
				//q2
				printf("(%.1lf, %.1lf) is in Q2", x, y);
			}
			else if (y < 0 && x < 0) {
				//q3
				printf("(%.1lf, %.1lf) is in Q3", x, y);
			}
			else {
				//q4
				printf("(%.1lf, %.1lf) is in Q4", x, y);
			}
			break;
		case 'd':
		case 'D':
			break;

	
	
	}



	return 0;
};