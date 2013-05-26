package de.minimum.hawapp.app.calendar.gui;

import java.io.IOException;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.calendar.intern.CalendarAboService;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarLectureActivity extends ListActivity {
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private CalendarAboService aboService;
    private ArrayAdapter<Appointment> appointmentAdapter;
    private Lecture lecture;
    private Button subscribeLectureBtn;
    private Button newAppointmentBtn;
    private final String abbestellen = "abbestellen";
    private final String abonieren = "abbonieren";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboService = calManager.getCalendarAboService(this);
        appointmentAdapter = new ArrayAdapter<Appointment>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<Appointment>());
        setContentView(R.layout.calendarlecture);
        setListAdapter(appointmentAdapter);

        final String lectureUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.LECTURE_UUID);
        lecture = calManager.getLecture(lectureUUID);
        showAppointments(lecture);
        final TextView lecturename = (TextView)findViewById(R.id.lectureName);
        lecturename.setText(lecture.getName());
        final TextView lecturername = (TextView)findViewById(R.id.lecturerName);
        lecturername.setText(lecture.getLecturerName());
        // abonnieren Button
        subscribeLectureBtn = (Button)findViewById(R.id.subscripeLecture);

        subscribeLectureBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                processSubscribeLectureButton();
            }
        });
        if (aboService.isSubscribted(lectureUUID)) {

            subscribeLectureBtn.setText("abbestellen");
        }
        final Button changeLogBtn = (Button)findViewById(R.id.cal_btn_changelog);

        changeLogBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                processChangeLogButton();
            }
        });

        // new AppointmentButton
        newAppointmentBtn = (Button)findViewById(R.id.newAppointment);

        newAppointmentBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                processCreateNewAppointmentButton();
            }
        });

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
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Lecture... params) {
                final Lecture actualLecture = params[0];
                appointments = calManager.getAppointments(actualLecture);
                return null;
            }
        }.execute(lecture);
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Appointment appointment = appointmentAdapter.getItem(position);
        final Intent intent = new Intent(this, CalendarAppointmentActivity.class);
        intent.putExtra(CalendarCategoriesActivity.APPOINTMENT_UUID, appointment.getUuid());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);

    }

    private void processSubscribeLectureButton() {
        if (subscribeLectureBtn.getText().equals(abbestellen)) {
            aboService.unsubscribeLecture(lecture.getUuid());
            subscribeLectureBtn.setText(abonieren);
        }
        else {
            aboService.subscribeLecture(lecture);
            subscribeLectureBtn.setText(abbestellen);
        }
        try {
            aboService.persist();
        }
        catch(final IOException e) {
            Toast.makeText(getApplicationContext(), "Änderungen konnten nicht gespeichert werden", Toast.LENGTH_SHORT)
                            .show();
        }
    }

    protected void processChangeLogButton() {
        final Intent intent = new Intent(this, CalendarChangeMessagesActivity.class);
        intent.putExtra(CalendarCategoriesActivity.LECTURE_UUID, lecture.getUuid());
        startActivity(intent);
    }

    private void processCreateNewAppointmentButton() {
        final Intent intent = new Intent(this, CalendarAppointmentActivity.class);
        intent.putExtra(CalendarCategoriesActivity.LECTURE_UUID, lecture.getUuid());
        startActivity(intent);
        // new AsyncTask<Void, Void, Void>() {
        //
        // @Override
        // protected void onPostExecute(final Void arg0) {
        //
        // }
        //
        // @Override
        // protected Void doInBackground(final Void... params) {
        // final Appointment appointment = new AppointmentImpl();
        // appointment.setBegin(new Date(System.currentTimeMillis()));
        // appointment.setEnd(new Date(System.currentTimeMillis()));
        // appointment.setName("Neuer Termin");
        // CalendarService.createNewAppoinment(appointment,
        // "6ded8547-a533-4d67-b001-16d633075b7a");
        // return null;
        // }
        // }.execute();

    }

}
