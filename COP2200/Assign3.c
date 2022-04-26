/*
Assignment 3
Date: 02/18/2022
If, else
*/
#define _CRT_SECURE_NO_WARNINGS 
#pragma warning(disable:6031)
#include <stdio.h>

int main() {
	double time;
	int hours, minutes;
	printf("Please enter how long it has been since freezer power failure in whole hours and minutes: ");
	scanf("%d %d", &hours, &minutes);
	time = hours + (minutes / 60.0);
	if (time <= 15) {
		double temperature = ((4.0 * time * time) / (time + 2.0)) - 20.0;
		double tempF = (temperature * (9.0 / 5)) + 32;
		printf("Current Temperature since Power Failure:\n%.1lf Celcius\n", temperature);
		printf("%.1lf Farenheit\n", tempF);
		if (temperature >= -10) {
			printf("The freezer has reached temperature where ice cream spoils\n");
		}
		else {
			printf("The ice cream has not spoiled\n");
		}

		if (temperature >= 4.5) {
			printf("The freezer has reached temperature where meat and poultry spoils, please discard them\n");
		}
		else {
			printf("The meat has not spoiled\n");
		}
	}
	else {
		printf("The freezer is at room temperature");
	}

	return 0;
};