package de.minimum.hawapp.server.calendar.api;

/*
 * Under Construction!
 */
public interface Appointment {

	String getUid();

	String getShortName();

	void setShortName(String shortName);

	java.util.Date getDay();

	void setDay(java.util.Date day);
}
