package de.minimum.hawapp.app.calendar.provider;

public class EventImpl implements Event {
	private long ID;        	//
	private String title;		//TITLE
	private String description;	//DESCRIPTION
	private long startMillis;   //DTSTART
	private long endMillis;   //DTEND
	private String location;
	private static final String timezone = "Europe/Berlin";	//EVENT_TIMEZONE
	
	public EventImpl(String title, long startMillis, long endMillis){
		this.title = title;
		this.startMillis = startMillis;
		this.endMillis = endMillis;
	}
	
	public EventImpl(long id, String title, long startMillis, long endMillis){
		this.ID = id;
		this.title = title;
		this.startMillis = startMillis;
		this.endMillis = endMillis;
	}
	
	public static String getTimezone() {
		return timezone;
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long ID) {
		this.ID = ID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getStartMillis() {
		return startMillis;
	}
	public void setStartMillis(long startMillis) {
		this.startMillis = startMillis;
	}
	public long getEndMillis() {
		return endMillis;
	}
	public void setEndMillis(long endMillis) {
		this.endMillis = endMillis;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}


