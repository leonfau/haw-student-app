package de.minimum.hawapp.app.calendar.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
import de.minimum.hawapp.app.calendar.provider.Calendar;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarLectureActivity extends ListActivity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private static final int DIALOG_SELECT_CALENDAR = 1;
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private CalendarAboService aboService;
    private ArrayAdapter<Appointment> appointmentAdapter;
    private ArrayAdapter<Calendar> calendarAdapter;
    private Lecture lecture;
    private Button subscribeLectureBtn;
    private Button newAppointmentBtn;
    private final String abbestellen = "abbestellen";
    private final String abonnieren = "abonnieren";
    private TextView lecturename;
    private TextView lecturername;
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

        aboService = calManager.getCalendarAboService(this);
        appointmentAdapter = new ArrayAdapter<Appointment>(this, R.layout.activity_stisys_list_item_1,
                        new ArrayList<Appointment>());
        setContentView(R.layout.calendarlecture);
        setListAdapter(appointmentAdapter);
        lecturename = (TextView)findViewById(R.id.lectureName);
        lecturername = (TextView)findViewById(R.id.lecturerName);
        subscribeLectureBtn = (Button)findViewById(R.id.subscripeLecture);

        lectureUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.LECTURE_UUID);

        // abonnieren Button
        subscribeLectureBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                processSubscribeLectureButton();
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        showAppointmentsAndLectureDetails();
        if (aboService.isSubscribted(lectureUUID)) {

            subscribeLectureBtn.setText(abbestellen);
        }
        else {
            subscribeLectureBtn.setText(abonnieren);
        }
    }

    private void showAppointmentsAndLectureDetails() {
        if (appointmentAdapter.isEmpty()) {
            showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }
        new AsyncTask<Void, Void, Void>() {
            List<Appointment> appointments;
            private boolean successful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                try {
                    dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                    removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                }
                catch(final IllegalArgumentException e) {

                }

                appointmentAdapter.clear();
                if (successful) {
                    lecturename.setText(lecture.getName());
                    lecturername.setText(lecture.getLecturerName());
                    appointmentAdapter.addAll(appointments);
                    appointmentAdapter.sort(new Comparator<Appointment>() {
                        @Override
                        public int compare(final Appointment lhs, final Appointment rhs) {
                            return lhs.getBegin().compareTo(rhs.getBegin());
                        }
                    });
                    setListAdapter(appointmentAdapter);
                }
                else {
                    showToastSomethingFailed();
                }

                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... params) {
                try {
                    lecture = calManager.getLecture(lectureUUID);
                    appointments = calManager.getAppointments(lecture);
                    successful = !appointments.isEmpty();
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
        Toast.makeText(this, "Entweder hat die Vorlesungen keine Termine oder es ist ein Problem ist aufgetreten",
                        Toast.LENGTH_LONG).show();
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
            unsubscribeLecture();
            subscribeLectureBtn.setText(abonnieren);
        }
        else {
            showDialog(DIALOG_SELECT_CALENDAR);
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
            case DIALOG_SELECT_CALENDAR:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                calendarAdapter = new ArrayAdapter<Calendar>(this, R.layout.activity_stisys_list_item_1,
                                new ArrayList<Calendar>(aboService.getCalendars()));
                builder.setTitle("Kalender auswählen").setAdapter(calendarAdapter,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, final int which) {
                                        subscribeLecture(calendarAdapter.getItem(which));

                                    }
                                });
                return builder.create();

        }
        return null;
    }

    private void subscribeLecture(final Calendar selectedCalendar) {
        final Toast toastSuccess = Toast.makeText(this, "Vorlesung abonniert", Toast.LENGTH_LONG);
        final Toast toastFailed = Toast.makeText(this, "Beim Abonieren ist ein Fehler aufgetreten", Toast.LENGTH_LONG);
        new AsyncTask<Void, Void, Void>() {
            private boolean successful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                if (successful) {
                    toastSuccess.show();
                }
                else {
                    toastFailed.show();
                }
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... params) {
                try {
                    aboService.subscribeLecture(lecture, selectedCalendar);
                    successful = true;
                }
                catch(final Throwable e) {
                    e.printStackTrace();
                    successful = false;
                }

                return null;
            }
        }.execute();
    }

    private void unsubscribeLecture() {
        final Toast toastSuccess = Toast.makeText(this, "Vorlesung abbestellt", Toast.LENGTH_LONG);
        final Toast toastFailed = Toast
                        .makeText(this, "Beim Abbestellen ist ein Fehler aufgetreten", Toast.LENGTH_LONG);
        new AsyncTask<Void, Void, Void>() {
            private boolean successful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                if (successful) {
                    toastSuccess.show();
                }
                else {
                    toastFailed.show();
                }

                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... params) {
                try {
                    aboService.unsubscribeLecture(lectureUUID);
                    successful = true;
                }
                catch(final Throwable e) {
                    e.printStackTrace();
                    successful = false;
                }

                return null;
            }
        }.execute();
    }
}
