package com.setlisto.utils;

import java.util.Date;

public class DateUtils {
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, day);
		cal.set(java.util.Calendar.HOUR_OF_DAY, hour);
		cal.set(java.util.Calendar.MINUTE, minute);
		cal.set(java.util.Calendar.SECOND, second);
		return cal.getTime();
	}
}
