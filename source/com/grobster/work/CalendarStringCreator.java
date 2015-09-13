package com.grobster.work;

import java.util.*;

public class CalendarStringCreator {
	public static String createSixDigitYearMonthDay() {
		Calendar cal = Calendar.getInstance(); // create instance of Calendar
		int month = cal.get(Calendar.MONTH) + 1; // calendar months are 0 based but need to be 1 based
		String monthString; // converts month integer to String
		
		if (month < 10) {
			monthString = TwoDigitNumberCreator.createTwoDigitNumber(month);
		} else {
			monthString = String.valueOf(month);
		}
		
		String yearString = String.valueOf(cal.get(Calendar.YEAR)).substring(2); // get last 2 digits of year
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String dayString;
		
		if (day < 10) {
			dayString = TwoDigitNumberCreator.createTwoDigitNumber(day);
		} else {
			dayString = String.valueOf(day);
		}
		
		StringBuilder auto = new StringBuilder();
		auto.append(yearString);
		auto.append(monthString);
		auto.append(dayString);
		
		System.out.println(auto.toString());
		
		return auto.toString();
		
	}
}