package de.minimum.hawapp.app.calendar.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.ChangeMessage;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarChangeMessagesActivity extends ListActivity {
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private ArrayAdapter<ChangeMessage> changeMessageAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeMessageAdapter = new ArrayAdapter<ChangeMessage>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<ChangeMessage>());
        setContentView(R.layout.calendar_main);
        setListAdapter(changeMessageAdapter);
        final String lectureUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.LECTURE_UUID);
        final Lecture actualLecture = calManager.getLecture(lectureUUID);
        showChangeMessages(actualLecture);

        final Button b1 = (Button)findViewById(R.id.cal_btn_subLectures);
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                viewSubscribedLectures();
            }
        });

    }

    protected void viewSubscribedLectures() {
        final Intent intent = new Intent(this, CalendarSubscribedLectureActivity.class);
        startActivity(intent);
    }

    private void showChangeMessages(final Lecture lecture) {

        new AsyncTask<Lecture, Void, Void>() {
            List<ChangeMessage> changeMessages;

            @Override
            protected void onPostExecute(final Void arg0) {
                changeMessageAdapter.clear();
                changeMessageAdapter.addAll(changeMessages);
                changeMessageAdapter.sort(new Comparator<ChangeMessage>() {

                    @Override
                    public int compare(final ChangeMessage lhs, final ChangeMessage rhs) {
                        return lhs.getChangeat().compareTo(rhs.getChangeat());
                    }
                });
                setListAdapter(changeMessageAdapter);

                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Lecture... params) {
                final Lecture actualLecture = params[0];
                changeMessages = calManager.getChangeMessages(actualLecture);
                return null;
            }
        }.execute(lecture);
    }

}
