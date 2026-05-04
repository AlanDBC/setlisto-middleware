package com.setlisto.others.test;

import java.util.Calendar;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(2025, Calendar.DECEMBER, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		
		Date d = cal.getTime();
		System.out.println(d);
		
	}
}
