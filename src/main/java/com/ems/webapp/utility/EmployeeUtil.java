package com.ems.webapp.utility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EmployeeUtil {
	public static boolean isOld(Date dob) {
		/*
		 * Given DOB, check if person is more than 24 years old
		 */
		LocalDate today = java.time.LocalDate.now();
		LocalDate ldob = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long years = java.time.temporal.ChronoUnit.YEARS.between(ldob, today);
		return years >= 24;
	}
}
