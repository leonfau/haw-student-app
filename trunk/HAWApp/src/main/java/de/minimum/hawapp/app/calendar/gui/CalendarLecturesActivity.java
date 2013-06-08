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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarLecturesActivity extends ListActivity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private ArrayAdapter<Lecture> lectureAdapter;
    // private Category actualCategory;
    private String categoryUUID;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -100;
        params.height = 70;
        params.width = 1000;
        params.y = -50;
        getWindow().setAttributes(params);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        lectureAdapter = new ArrayAdapter<Lecture>(this, R.layout.activity_stisys_list_item_1, new ArrayList<Lecture>());
        setContentView(R.layout.calendar_simple_list_activity);
        setListAdapter(lectureAdapter);
        categoryUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.CATEGORY_UUID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showLectures();
    }

    private void showLectures() {
        if (lectureAdapter.isEmpty()) {
            showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }
        new AsyncTask<Void, Void, Void>() {
            List<Lecture> lectures;
            private boolean successful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                try {
                    dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                    removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                }
                catch(final IllegalArgumentException e) {

                }

                lectureAdapter.clear();
                if (successful) {
                    lectureAdapter.addAll(lectures);
                    lectureAdapter.sort(new Comparator<Lecture>() {

                        @Override
                        public int compare(final Lecture lhs, final Lecture rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });
                    setListAdapter(lectureAdapter);
                }
                else {
                    showToastSomethingFailed();
                }

                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... params) {
                try {
                    final Category actualCategory = calManager.getCategory(categoryUUID);
                    lectures = calManager.getLectures(actualCategory);
                    successful = !lectures.isEmpty();
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
        Toast.makeText(this, "Endweder hat die Katgorie keine Vorelsungen oder es ist ein Problem ist aufgetreten",
                        Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Lecture lecture = lectureAdapter.getItem(position);
        final Intent intent = new Intent(this, CalendarLectureActivity.class);
        intent.putExtra(CalendarCategoriesActivity.LECTURE_UUID, lecture.getUuid());
        startActivity(intent);

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
