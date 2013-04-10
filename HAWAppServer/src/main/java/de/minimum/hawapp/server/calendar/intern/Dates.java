package de.minimum.hawapp.server.calendar.intern;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class Dates {
	public static Date getDate(String germanDatum){
		try {
			return DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN).parse(germanDatum);
		} catch (ParseException e) {
			return null;
		}
	}
	public static Date add(Date date, Date time){
		return new Date(date.getTime()+time.getTime()+60*60*1000);
	}
	public static Date getDateAndTime(String date, String time){
		return add(getDate(date), getTime(time));
	}
	public static Date getTime(String time){
		try {
			return DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMAN).parse(time);
		} catch (ParseException e) {
			return null;
		}
	}
}
