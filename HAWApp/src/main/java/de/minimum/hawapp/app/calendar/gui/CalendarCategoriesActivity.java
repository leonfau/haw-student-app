package de.minimum.hawapp.app.calendar.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarCategoriesActivity extends ListActivity {
    public final static String LECTURE_UUID = "calendar_lecture_uuid";
    public final static String CATEGORY_UUID = "calendar_category_uuid";
    public final static String APPOINTMENT_UUID = "calendar_appointment_uuid";
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private ArrayAdapter<Category> categoryAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<Category>());
        setContentView(R.layout.calendar_main);
        setListAdapter(categoryAdapter);

        final Button b1 = (Button)findViewById(R.id.cal_btn_subLectures);
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                viewSubscribedLectures();
            }
        });
        final Button b2 = (Button)findViewById(R.id.cal_btn_Calendars);
        b2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                viewCalendars();
            }
        });

    }

    protected void viewCalendars() {
        final Intent intent = new Intent(this, CalendarCalendarActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showCategories();
    }

    protected void viewSubscribedLectures() {
        final Intent intent = new Intent(this, CalendarSubscribedLectureActivity.class);
        startActivity(intent);
    }

    private void showCategories() {
        showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        new AsyncTask<Void, Void, Void>() {
            private List<Category> categories;
            private boolean successful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                categoryAdapter.clear();
                if (successful) {
                    categoryAdapter.addAll(categories);
                    categoryAdapter.sort(new Comparator<Category>() {

                        @Override
                        public int compare(final Category lhs, final Category rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });
                    setListAdapter(categoryAdapter);
                }
                else {
                    showToastSomethingFailed();
                }

                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                try {
                    categories = calManager.getCategories();
                    successful = !categories.isEmpty();
                }
                catch(final Throwable e) {
                    e.printStackTrace();
                    successful = false;
                }

                return null;
            }
        }.execute();
    }

    private void showToastSomethingFailed() {
        Toast.makeText(this, "Es gibt keine Einträge oder ein Problem ist aufgetreten", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Category lecture = categoryAdapter.getItem(position);
        final Intent intent = new Intent(this, CalendarLecturesActivity.class);
        intent.putExtra(CATEGORY_UUID, lecture.getUuid());
        startActivity(intent);

        super.onListItemClick(l, v, position, id);

    }

    @Override
    protected Dialog onCreateDialog(final int id) {
        ProgressDialog mProgressDialog;
        switch(id) {
            case DIALOG_DOWNLOAD_JSON_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Updating.....");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
        }
        return null;
    }
}
