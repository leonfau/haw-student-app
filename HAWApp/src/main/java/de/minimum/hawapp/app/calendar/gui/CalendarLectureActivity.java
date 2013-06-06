package de.minimum.hawapp.app.calendar.gui;

import java.io.IOException;
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
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private CalendarAboService aboService;
    private ArrayAdapter<Appointment> appointmentAdapter;
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
        
        
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -100;
        params.height = 70;
        params.width = 1000;
        params.y = -50;
        this.getWindow().setAttributes(params);
        
        
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
        showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        new AsyncTask<Void, Void, Void>() {
            List<Appointment> appointments;
            private boolean successful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
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
            aboService.unsubscribeLecture(lectureUUID);
            subscribeLectureBtn.setText(abonnieren);
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
