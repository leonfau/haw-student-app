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
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.intern.CalendarAboService;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.calendar.intern.LectureSubscribtion;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarSubscribedLectureActivity extends ListActivity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private CalendarAboService aboService;
    private ArrayAdapter<LectureSubscribtion> lectureSubscribtionAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -100;
        params.height = 70;
        params.width = 1000;
        params.y = -50;
        this.getWindow().setAttributes(params);
        
        
        aboService = calManager.getCalendarAboService(this);
        lectureSubscribtionAdapter = new ArrayAdapter<LectureSubscribtion>(this,R.layout.activity_stisys_list_item_1,
                        new ArrayList<LectureSubscribtion>());
        setContentView(R.layout.calendar_subcripted_lectures);
        setListAdapter(lectureSubscribtionAdapter);

    }

    @Override
    public void onResume() {
        showLectureSubscription();
        super.onResume();
    }

    private void showLectureSubscription() {
        showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        new AsyncTask<Void, Void, Void>() {
            List<LectureSubscribtion> lectureSubscriptions;

            @Override
            protected void onPostExecute(final Void arg0) {
                dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                lectureSubscribtionAdapter.clear();
                lectureSubscribtionAdapter.addAll(lectureSubscriptions);
                lectureSubscribtionAdapter.sort(new Comparator<LectureSubscribtion>() {

                    @Override
                    public int compare(final LectureSubscribtion lhs, final LectureSubscribtion rhs) {
                        return lhs.getLectureName().compareTo(rhs.getLectureName());
                    }
                });
                setListAdapter(lectureSubscribtionAdapter);
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... params) {
                lectureSubscriptions = aboService.getSubscribtedLectures();
                return null;
            }
        }.execute();
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final LectureSubscribtion lecturesubscribtion = lectureSubscribtionAdapter.getItem(position);
        final Intent intent = new Intent(this, CalendarLectureActivity.class);
        intent.putExtra(CalendarCategoriesActivity.LECTURE_UUID, lecturesubscribtion.getLectureUUID());
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
