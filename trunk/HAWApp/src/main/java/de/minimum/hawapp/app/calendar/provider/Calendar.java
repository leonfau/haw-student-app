package de.minimum.hawapp.app.calendar.provider;

public interface Calendar {

	// ----------------------------required------------------------------------

	/**
	 * unique id
	 * @return
	 */
	public long getID();
	public void setID(long id);
	
	/**
	 * The account that was used to sync the entry to the device. If the
	 * account_type is not ACCOUNT_TYPE_LOCAL then the name and type must match
	 * an account on the device or the calendar will be deleted.
	 * 
	 * @return
	 */
	public String getAccountName();
	public void setAccountName(String accName);

	/**
	 * The type of the account that was used to sync the entry to the device. A
	 * type of ACCOUNT_TYPE_LOCAL will keep this event form being deleted if
	 * there are no matching accounts on the device.
	 * 
	 * @return
	 */
	public String getAccountType();
	public void setAccountType(String accType);

	/**
	 * The name of the calendar. Column name.
	 * 
	 * @return
	 */
	public String getName();
	public void setName(String name);

	/**
	 * The display name of the calendar. Column name.
	 * 
	 * @return
	 */
	public String getDisplayName();
	public void setDisplayName(String displayName);

	/**
	 * The color of the calendar. This should only be updated by the sync
	 * adapter, not other apps, as changing a calendar's color can adversely
	 * affect its display.
	 * 
	 * @return
	 */
	public String getColor();
	public void setColor(String color);

	/**
	 * The level of access that the user has for the calendar
	 * 
	 * @return
	 */
	public int getAccessLevel();
	public void setAccessLevel(int accessLevel);

	/**
	 * The owner account for this calendar, based on the calendar feed. This
	 * will be different from the _SYNC_ACCOUNT for delegated calendars. Column
	 * name.
	 */
	public String getOwnerAccount();
	public void setOwnerAccount(String ownerAcc);

	// -------------------------optional--------------------------------------
	/**
	 * Is this calendar synced and are its events stored on the device? 0 - Do
	 * not sync this calendar or store events for this calendar. 1 - Sync down
	 * events for this calendar.
	 * 
	 * @return INTEGER (boolean)
	 */
	public String getSyncEvents();
	public void setSyncEvents(String syncEvents);

	/**
	 * The time zone the calendar is associated with.
	 * 
	 * @return
	 */
	public String getTimeZone();
	public void setTimeZone(String timeZone);

	/**
	 * A comma separated list of reminder methods supported for this calendar in
	 * the format "#,#,#". Valid types are METHOD_DEFAULT, METHOD_ALERT,
	 * METHOD_EMAIL, METHOD_SMS, METHOD_ALARM. Column name.
	 * 
	 * @return
	 */
	public String getAllowedReminders();                        // 0
	public void setAllowedReminders(String allowedReminders);

	/**
	 * A comma separated list of availability types supported for this calendar
	 * in the format "#,#,#". Valid types are AVAILABILITY_BUSY,
	 * AVAILABILITY_FREE, AVAILABILITY_TENTATIVE. Setting this field to only
	 * AVAILABILITY_BUSY should be used to indicate that changing the
	 * availability is not supported.
	 * 
	 * @return
	 */
	public String getAllowedAvailability();
	public void setAllowedAvailability(String allowedAvailability);

	/**
	 * A comma separated list of attendee types supported for this calendar in
	 * the format "#,#,#". Valid types are TYPE_NONE, TYPE_OPTIONAL,
	 * TYPE_REQUIRED, TYPE_RESOURCE. Setting this field to only TYPE_NONE should
	 * be used to indicate that changing the attendee type is not supported.
	 * 
	 * @return
	 */
	public String getAllowedAttendeeTypes();
	public void settAllowedAttendeeTypes(String allowedAttendeeTypes);
}
