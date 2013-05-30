package de.minimum.hawapp.app.calendar.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;

public class CalendarProviderImpl implements CalendarProvider {

	Context context;

	public CalendarProviderImpl(Context context) {
		this.context = context;
	}

	@Override
	public Set<Calendar> getCalendars() {
		Set<Calendar> calendars = new HashSet<Calendar>();
		// Projection array. Creating indices for this array instead of doing
		// dynamic lookups improves performance.
		String[] EVENT_PROJECTION = new String[] { Calendars._ID, // 0
				Calendars.ACCOUNT_NAME, // 1
				Calendars.CALENDAR_DISPLAY_NAME, // 2
		};

		// The indices for the projection array above.
		int PROJECTION_ID_INDEX = 0;
		int PROJECTION_ACCOUNT_NAME_INDEX = 1;
		int PROJECTION_DISPLAY_NAME_INDEX = 2;

		// Run query
		Cursor cur = null;
		ContentResolver cr = ((Context) context).getContentResolver();
		Uri uri = Calendars.CONTENT_URI;
		String selection = null;
		String[] selectionArgs = null;
		// Submit the query and get a Cursor object back.
		cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

		// Use the cursor to step through the returned records
		while (cur.moveToNext()) {
			long calID = 0;
			String displayName = null;
			String accountName = null;

			// Get the field values
			calID = cur.getLong(PROJECTION_ID_INDEX);
			displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
			accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);

			calendars.add(new CalendarImpl(calID, accountName, displayName));
		}

		return calendars;
	}

	public long addEvent(Calendar calendar, Event event) {
		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Events.DTSTART, event.getStartMillis());
		values.put(Events.DTEND, event.getEndMillis());
		values.put(Events.TITLE, event.getTitle());
		values.put(Events.DESCRIPTION,
				event.getDescription() + " " + event.getLocation());
		values.put(Events.CALENDAR_ID, calendar.getID());
		values.put(Events.EVENT_TIMEZONE, "Europe/Berlin");
		Uri uri = cr.insert(Events.CONTENT_URI, values);

		// get the event ID that is the last element in the Uri
		long eventID = Long.parseLong(uri.getLastPathSegment());

		return eventID;
	}

	@Override
	public List<Long> addEvents(Calendar calendar, List<Event> events) {
		List<Long> idList = new ArrayList<Long>();

		for (Event event : events) {
			idList.add(this.addEvent(calendar, event));
		}
		return idList;
	}

	@Override
	public void deleteEvent(Event event) {
		Uri deleteUri = null;
		deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI,
				event.getID());
		context.getContentResolver().delete(deleteUri, null, null);
	}

	@Override
	public void deleteEvents(List<Event> events) {
		for (Event event : events) {
			this.deleteEvent(event);
		}
	}

	@Override
	public void updateEvent(Event event) {
		ContentValues values = new ContentValues();
		Uri updateUri = null;
		// The new title for the event
		values.put(Events.DTSTART, event.getStartMillis());
		values.put(Events.DTEND, event.getEndMillis());
		values.put(Events.TITLE, event.getTitle());
		values.put(Events.DESCRIPTION,
				event.getDescription() + " " + event.getLocation());
		updateUri = ContentUris.withAppendedId(Events.CONTENT_URI,
				event.getID());
		context.getContentResolver().update(updateUri, values, null, null);
	}

}
