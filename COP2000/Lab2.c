/*
Bonus Lab 2
Date: 3/30/2022
*/
#define _CRT_SECURE_NO_WARNINGS
#define RATE 0.45
#pragma warning(disable:6031)
#include <stdio.h>
#include <math.h>
double findDiff(double start, double end);
double calcReimb(double miles);
double calcMPG(double miles, double gallons);
void printInfo(double miles, double reimbursment, double MPG);
int main() {
	double start, end, gallons, miles, reimbursement, MPG;

	printf("MILEAGE REIMBURSEMENT AND MPG CALCULATOR\n\n");
	printf("Enter beginning odometer reading => "); //Scanners
	scanf("%lf", &start);
	printf("Enter ending odometer reading => ");
	scanf("%lf", &end);
	printf("Enter gallons of gas consumed => ");
	scanf("%lf", &gallons);
	miles = findDiff(start, end); //runs functions and stores in variables
	reimbursement = calcReimb(miles);
	MPG = calcMPG(miles, gallons);
	printInfo(miles, reimbursement, MPG); //prints information
	if (MPG < 25) { //if mpg is less than 25, let user know their car is not efficient.
		printf("This car is not efficient.\n");
	}
	return 0;
};
double findDiff(double start, double end) {
	return fabs(end - start); 
}
double calcReimb(double miles) {
	return RATE * miles;
}
double calcMPG(double miles, double gallons) {
	return miles / gallons;
}
void printInfo(double miles, double reimbursment, double MPG) {
	printf("\nYou have travelled %.1lf miles. At $%.2lf per mile,\n",miles,RATE);
	printf("your reimbursment is $%.2lf.\n\n", reimbursment);
	printf("Your vehicle gas consumption is : %.1lf MPG\n\n", MPG);
}