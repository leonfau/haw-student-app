package de.minimum.hawapp.app.calendar.provider;

public class CalendarImpl implements Calendar {
	private long id;
	private String accName;
	private String accType;
	private String name;
	private String displayName;
	private String color;
	private int accessLevel;
	private String ownerAcc;
	private String syncEvents;
	private String timeZone;
	private String allowedReminders;
	private String allowedAvailability;
	private String allowedAttendeeTypes;

	public CalendarImpl(String accName, String accType, String name, String displayName, String color, int accessLevel, String ownerAcc){
		this.accName = accName;
		this.accType = accType;
		this.name = name;
		this.displayName = displayName;
		this.displayName = displayName;
		this.color = color;
		this.accessLevel = accessLevel;
		this.ownerAcc = ownerAcc;
	}
	
	public CalendarImpl(long id, String accName, String displayName){
		this.id = id;
		this.accName = accName;
		this.displayName = displayName;
	}
	
	@Override
	public long getID(){
		return id;
	}
	
	@Override
	public void setID(long id){
		this.id = id;
	}
	
	@Override
	public String getAccountName() {
		return accName;
	}

	@Override
	public void setAccountName(String accName) {
		this.accName = accName;

	}

	@Override
	public String getAccountType() {
		return accType;
	}

	@Override
	public void setAccountType(String accType) {
		this.accType = accType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int getAccessLevel() {
		return accessLevel;
	}

	@Override
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;

	}

	@Override
	public String getOwnerAccount() {
		return ownerAcc;
	}

	@Override
	public void setOwnerAccount(String ownerAcc) {
		this.ownerAcc = ownerAcc;

	}

	@Override
	public String getSyncEvents() {
		return syncEvents;
	}

	@Override
	public void setSyncEvents(String syncEvents) {
		this.syncEvents = syncEvents;

	}

	@Override
	public String getTimeZone() {
		return timeZone;
	}

	@Override
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;

	}

	@Override
	public String getAllowedReminders() {
		return allowedReminders;
	}

	@Override
	public void setAllowedReminders(String allowedReminders) {
		this.allowedReminders = allowedReminders;

	}

	@Override
	public String getAllowedAvailability() {
		return allowedAvailability;
	}

	@Override
	public void setAllowedAvailability(String allowedAvailability) {
		this.allowedAvailability = allowedAvailability;

	}

	@Override
	public String getAllowedAttendeeTypes() {
		return allowedAttendeeTypes;
	}

	@Override
	public void settAllowedAttendeeTypes(String allowedAttendeeTypes) {
		this.allowedAttendeeTypes = allowedAttendeeTypes;

	}
	
	public String toString(){
		return this.displayName;
	}

}
