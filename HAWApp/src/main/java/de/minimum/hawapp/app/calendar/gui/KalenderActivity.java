package de.minimum.hawapp.app.calendar.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.mensa.management.CalendarManager;

public class KalenderActivity extends ListActivity {
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private State state = State.CATEGORY;
    private ArrayAdapter<Category> categoryAdapter;
    private ArrayAdapter<Lecture> lectureAdapter;
    private ArrayAdapter<Appointment> appointmentAdapter;
    private Category actualCategory;
    private Lecture actualLecture;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<Category>());
        lectureAdapter = new ArrayAdapter<Lecture>(this, android.R.layout.simple_list_item_1, new ArrayList<Lecture>());
        appointmentAdapter = new ArrayAdapter<Appointment>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<Appointment>());
        setContentView(R.layout.calendar_main);
        setListAdapter(categoryAdapter);
        showCategories();
    }

    private void showCategories() {
        new AsyncTask<Void, Void, Void>() {
            private List<Category> categories;

            @Override
            protected void onPostExecute(final Void arg0) {
                categoryAdapter.clear();
                categoryAdapter.addAll(categories);
                categoryAdapter.sort(new Comparator<Category>() {

                    @Override
                    public int compare(final Category lhs, final Category rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                setListAdapter(categoryAdapter);
                state = State.CATEGORY;
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                categories = calManager.getCategories();
                return null;
            }
        }.execute();
    }

    private void showLectures(final Category category) {

        new AsyncTask<Category, Void, Void>() {
            List<Lecture> lectures;

            @Override
            protected void onPostExecute(final Void arg0) {
                lectureAdapter.clear();
                lectureAdapter.addAll(lectures);
                lectureAdapter.sort(new Comparator<Lecture>() {

                    @Override
                    public int compare(final Lecture lhs, final Lecture rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                setListAdapter(lectureAdapter);
                state = State.LECTURES;
                final Button b1 = (Button)findViewById(R.id.cal_btn_back);
                b1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        showCategories();
                    }
                });
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Category... params) {
                actualCategory = params[0];
                lectures = calManager.getLectures(actualCategory);
                return null;
            }
        }.execute(category);
    }

    private void showAppointments(final Lecture lecture) {

        new AsyncTask<Lecture, Void, Void>() {
            List<Appointment> appointments;

            @Override
            protected void onPostExecute(final Void arg0) {
                appointmentAdapter.clear();
                appointmentAdapter.addAll(appointments);
                appointmentAdapter.sort(new Comparator<Appointment>() {

                    @Override
                    public int compare(final Appointment lhs, final Appointment rhs) {
                        return lhs.getBegin().compareTo(rhs.getBegin());
                    }
                });
                setListAdapter(appointmentAdapter);
                state = State.APPOINTMENTS;
                final Button b1 = (Button)findViewById(R.id.cal_btn_back);
                b1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        showLectures(actualCategory);
                    }
                });
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Lecture... params) {
                actualLecture = params[0];
                appointments = calManager.getAppointments(actualLecture);
                return null;
            }
        }.execute(lecture);
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        if (state.equals(State.CATEGORY)) {
            showLectures(categoryAdapter.getItem(position));
        }
        if (state.equals(State.LECTURES)) {
            showAppointments(lectureAdapter.getItem(position));
        }
        super.onListItemClick(l, v, position, id);

    }

    private enum State {
        CATEGORY, LECTURES, APPOINTMENTS, APPOINTMENT
    }
}
