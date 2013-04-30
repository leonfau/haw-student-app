package de.minimum.hawapp.app.activity;

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
import de.minimum.hawapp.app.calendar.beans.AppointmentPO;
import de.minimum.hawapp.app.calendar.beans.CategoryPO;
import de.minimum.hawapp.app.calendar.beans.LecturePO;
import de.minimum.hawapp.app.rest.CalendarService;

public class KalenderActivity extends ListActivity {
    private State state = State.CATEGORY;
    private ArrayAdapter<CategoryPO> categoryAdapter;
    private ArrayAdapter<LecturePO> lectureAdapter;
    private ArrayAdapter<AppointmentPO> appointmentAdapter;
    private List<CategoryPO> categories;
    private CategoryPO actualCategory;
    private LecturePO actualLecture;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryAdapter = new ArrayAdapter<CategoryPO>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<CategoryPO>());
        lectureAdapter = new ArrayAdapter<LecturePO>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<LecturePO>());
        appointmentAdapter = new ArrayAdapter<AppointmentPO>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<AppointmentPO>());
        setContentView(R.layout.calendar_main);
        setListAdapter(categoryAdapter);
        showCategories();
    }

    private void showCategories() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(final Void arg0) {
                categoryAdapter.clear();
                categoryAdapter.addAll(categories);
                categoryAdapter.sort(new Comparator<CategoryPO>() {

                    @Override
                    public int compare(final CategoryPO lhs, final CategoryPO rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                setListAdapter(categoryAdapter);
                state = State.CATEGORY;
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                if (categories == null) {
                    categories = CalendarService.getCategories();
                }
                return null;
            }
        }.execute();
    }

    private void showLectures(final CategoryPO category) {

        new AsyncTask<CategoryPO, Void, Void>() {
            List<LecturePO> lectures;

            @Override
            protected void onPostExecute(final Void arg0) {
                lectureAdapter.clear();
                lectureAdapter.addAll(lectures);
                lectureAdapter.sort(new Comparator<LecturePO>() {

                    @Override
                    public int compare(final LecturePO lhs, final LecturePO rhs) {
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
            protected Void doInBackground(final CategoryPO... params) {
                actualCategory = params[0];
                lectures = actualCategory.getLectures();
                if (lectures.size() == 0) {
                    lectures.addAll(CalendarService.getLectures(actualCategory.getUuid()));
                }
                return null;
            }
        }.execute(category);
    }

    private void showAppointments(final LecturePO lecture) {

        new AsyncTask<LecturePO, Void, Void>() {
            List<AppointmentPO> appointments;

            @Override
            protected void onPostExecute(final Void arg0) {
                appointmentAdapter.clear();
                appointmentAdapter.addAll(appointments);
                appointmentAdapter.sort(new Comparator<AppointmentPO>() {

                    @Override
                    public int compare(final AppointmentPO lhs, final AppointmentPO rhs) {
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
            protected Void doInBackground(final LecturePO... params) {
                actualLecture = params[0];
                appointments = actualLecture.getAppointments();
                if (appointments.size() == 0) {
                    appointments.addAll(CalendarService.getAppointments(actualLecture.getUuid()));
                }
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
