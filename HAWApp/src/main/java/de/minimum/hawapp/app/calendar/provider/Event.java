package de.minimum.hawapp.app.calendar.provider;

public interface Event {
	
	public long getID();
	public void setID(long calID);
	public String getTitle();
	public void setTitle(String title);
	public String getDescription();
	public void setDescription(String description);
	public long getStartMillis();
	public void setStartMillis(long startMillis);
	public long getEndMillis();
	public void setEndMillis(long endMillis);
	public String getLocation();
	public void setLocation(String location);


}
