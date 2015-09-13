package com.grobster.work;

public class TwoDigitNumberCreator {

	public static String createTwoDigitNumber(int number) {
		String twoDigitNumberString = null;
		switch (number) { // months must be represented using 2 digits
			case 1: twoDigitNumberString = "01";
					break;
			case 2: twoDigitNumberString = "02";
					break;
			case 3: twoDigitNumberString = "03";
					break;
			case 4: twoDigitNumberString = "04";
					break;
			case 5: twoDigitNumberString = "05";
					break;
			case 6: twoDigitNumberString = "06";
					break;
			case 7: twoDigitNumberString = "07";
					break;
			case 8: twoDigitNumberString = "08";
					break;
			case 9: twoDigitNumberString = "09";
					break;
		}
		return twoDigitNumberString;
	}
}