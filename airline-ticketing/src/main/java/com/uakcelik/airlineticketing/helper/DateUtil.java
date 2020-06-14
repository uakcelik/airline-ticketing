package com.uakcelik.airlineticketing.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String DATE		 		= "dd/MM/yyyy";
	public static final String DATETIME			= "dd/MM/yyyy HH:mm:ss";
	
	public static String format(Date date, String format){
		if(date == null) return "";
		return new SimpleDateFormat(format).format(date);
	}
	
	public static Date format(String dateStr, String format){
		if(dateStr == null) return null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
