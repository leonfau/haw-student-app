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
import android.provider.BaseColumns;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;

public class CalendarProviderImpl implements CalendarProvider {

    Context context;

    public CalendarProviderImpl(final Context context) {
        this.context = context;
    }

    @Override
    public Set<Calendar> getCalendars() {
        final Set<Calendar> calendars = new HashSet<Calendar>();
        // Projection array. Creating indices for this array instead of doing
        // dynamic lookups improves performance.
        final String[] EVENT_PROJECTION = new String[] { BaseColumns._ID, // 0
                        Calendars.ACCOUNT_NAME, // 1
                        Calendars.CALENDAR_DISPLAY_NAME, // 2
        };

        // The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        final int PROJECTION_DISPLAY_NAME_INDEX = 2;

        // Run query
        Cursor cur = null;
        final ContentResolver cr = context.getContentResolver();
        final Uri uri = Calendars.CONTENT_URI;
        final String selection = null;
        final String[] selectionArgs = null;
        // Submit the query and get a Cursor object back.
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        // Use the cursor to step through the returned records
        while(cur.moveToNext()) {
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

    @Override
    public long addEvent(final long calendarID, final Event event) {
        final ContentResolver cr = context.getContentResolver();
        final ContentValues values = new ContentValues();
        values.put(Events.DTSTART, event.getStartMillis());
        values.put(Events.DTEND, event.getEndMillis());
        values.put(Events.TITLE, event.getTitle());
        values.put(Events.DESCRIPTION, event.getDescription());
        values.put(Events.EVENT_LOCATION, event.getLocation());
        values.put(Events.CALENDAR_ID, calendarID);
        values.put(Events.EVENT_TIMEZONE, "Europe/Berlin");
        final Uri uri = cr.insert(Events.CONTENT_URI, values);

        // get the event ID that is the last element in the Uri
        final long eventID = Long.parseLong(uri.getLastPathSegment());

        return eventID;
    }

    @Override
    public List<Long> addEvents(final Calendar calendar, final List<Event> events) {
        final List<Long> idList = new ArrayList<Long>();

        for(final Event event : events) {
            idList.add(addEvent(calendar.getID(), event));
        }
        return idList;
    }

    @Override
    public void deleteEvent(final Event event) {
        deleteEventById(event.getID());
    }

    @Override
    public void deleteEventById(final long id) {
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, id);
        context.getContentResolver().delete(deleteUri, null, null);
    }

    @Override
    public void deleteEvents(final List<Event> events) {
        for(final Event event : events) {
            deleteEvent(event);
        }
    }

    @Override
    public void deleteEventsById(final List<Long> ids) {
        for(final Long id : ids) {
            deleteEventById(id);
        }
    }

    @Override
    public void updateEvent(final Event event) {
        final ContentValues values = new ContentValues();
        Uri updateUri = null;
        // The new title for the event
        values.put(Events.DTSTART, event.getStartMillis());
        values.put(Events.DTEND, event.getEndMillis());
        values.put(Events.TITLE, event.getTitle());
        values.put(Events.DESCRIPTION, event.getDescription());
        values.put(Events.EVENT_LOCATION, event.getLocation());
        updateUri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getID());
        context.getContentResolver().update(updateUri, values, null, null);
    }

}
