package de.minimum.hawapp.app.calendar.gui;

import java.util.ArrayList;
import java.util.Set;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.provider.Calendar;
import de.minimum.hawapp.app.calendar.provider.CalendarProvider;
import de.minimum.hawapp.app.calendar.provider.CalendarProviderImpl;

public class CalendarCalendarActivity extends ListActivity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private CalendarProvider calProvider;
    private ArrayAdapter<Calendar> calendarAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -100;
        params.height = 70;
        params.width = 1000;
        params.y = -50;
        this.getWindow().setAttributes(params);
        
        
        calProvider = new CalendarProviderImpl(this);
        calendarAdapter = new ArrayAdapter<Calendar>(this, R.layout.activity_stisys_list_item_1,
                        new ArrayList<Calendar>());
        setContentView(R.layout.calendar_subcripted_lectures);
        setListAdapter(calendarAdapter);

    }

    @Override
    public void onResume() {
        showLectureSubscription();
        super.onResume();
    }

    private void showLectureSubscription() {
        showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        new AsyncTask<Void, Void, Void>() {
            Set<Calendar> calendars;

            @Override
            protected void onPostExecute(final Void arg0) {
                dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                calendarAdapter.clear();
                calendarAdapter.addAll(calendars);
                // calendarAdapter.sort(new Comparator<LectureSubscribtion>() {
                //
                // @Override
                // public int compare(final LectureSubscribtion lhs, final
                // LectureSubscribtion rhs) {
                // return lhs.getLectureName().compareTo(rhs.getLectureName());
                // }
                // });
                setListAdapter(calendarAdapter);
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... params) {
                calendars = calProvider.getCalendars();
                return null;
            }
        }.execute();
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {

        super.onListItemClick(l, v, position, id);

    }

    @Override
    protected Dialog onCreateDialog(final int id) {
        ProgressDialog mProgressDialog;
        switch(id) {
            case DIALOG_DOWNLOAD_JSON_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Lade Veranstaltungspläne...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
        }
        return null;
    }
}
