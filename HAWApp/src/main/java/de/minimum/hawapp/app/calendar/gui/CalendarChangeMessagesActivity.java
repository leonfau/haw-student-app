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
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.ChangeMessage;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarChangeMessagesActivity extends ListActivity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private ArrayAdapter<ChangeMessage> changeMessageAdapter;
    private String lectureUUID;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeMessageAdapter = new ArrayAdapter<ChangeMessage>(this, R.layout.activity_stisys_list_item_1,
                        new ArrayList<ChangeMessage>());
        setContentView(R.layout.calendar_main);
        setListAdapter(changeMessageAdapter);
        lectureUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.LECTURE_UUID);

        final Button b1 = (Button)findViewById(R.id.cal_btn_subLectures);
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                viewSubscribedLectures();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        showChangeMessages(lectureUUID);
    }

    protected void viewSubscribedLectures() {
        final Intent intent = new Intent(this, CalendarSubscribedLectureActivity.class);
        startActivity(intent);
    }

    private void showChangeMessages(final String lectureUUID) {
        showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        new AsyncTask<String, Void, Void>() {
            List<ChangeMessage> changeMessages;
            private boolean sucessful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                changeMessageAdapter.clear();
                if (sucessful) {
                    changeMessageAdapter.addAll(changeMessages);
                    changeMessageAdapter.sort(new Comparator<ChangeMessage>() {

                        @Override
                        public int compare(final ChangeMessage lhs, final ChangeMessage rhs) {
                            return lhs.getChangeat().compareTo(rhs.getChangeat());
                        }
                    });
                    setListAdapter(changeMessageAdapter);

                }
                else {
                    showToastSomethingFailed();
                }

                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final String... params) {
                try {
                    final String lectureUUID = params[0];
                    final Lecture actualLecture = calManager.getLecture(lectureUUID);
                    changeMessages = calManager.getChangeMessages(actualLecture);
                    sucessful = !changeMessages.isEmpty();
                }
                catch(final Throwable e) {
                    e.printStackTrace();
                    sucessful = false;
                }

                return null;
            }
        }.execute(lectureUUID);
    }

    private void showToastSomethingFailed() {
        Toast.makeText(this, "Es gibt keine ChangeMessages oder ein Problem ist aufgetreten", Toast.LENGTH_LONG).show();
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
