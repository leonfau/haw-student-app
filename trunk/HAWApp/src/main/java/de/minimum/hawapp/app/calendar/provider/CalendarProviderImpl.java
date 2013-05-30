package de.minimum.hawapp.app.calendar.provider;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;

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
	
	
}
