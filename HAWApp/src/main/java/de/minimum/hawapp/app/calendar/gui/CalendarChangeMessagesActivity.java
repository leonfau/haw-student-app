package de.minimum.hawapp.app.calendar.gui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.ChangeMessage;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarChangeMessagesActivity extends ListActivity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private static final int DIALOG_DETAILS = 1;
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private ArrayAdapter<ChangeMessage> changeMessageAdapter;
    private String lectureUUID;

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

        changeMessageAdapter = new ArrayAdapter<ChangeMessage>(this, R.layout.activity_stisys_list_item_1,
                        new ArrayList<ChangeMessage>());
        setContentView(R.layout.calendar_simple_list_activity);
        setListAdapter(changeMessageAdapter);
        lectureUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.LECTURE_UUID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showChangeMessages(lectureUUID);
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
                mProgressDialog.setMessage("Lade Veranstaltungspläne...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            case DIALOG_DETAILS:

        }
        return null;
    }

    private String message;

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final ChangeMessage changeMessage = changeMessageAdapter.getItem(position);
        final DateFormat dateformat = DateFormat
                        .getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.GERMANY);
        message = String.format("Geändert am: %s\nVon: %s\nGrund: %s\nWas: %s",
                        dateformat.format(changeMessage.getChangeat()), changeMessage.getPerson(),
                        changeMessage.getReason(), changeMessage.getWhat());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Details").setMessage(message);

        builder.create().show();
        super.onListItemClick(l, v, position, id);
    }
}
