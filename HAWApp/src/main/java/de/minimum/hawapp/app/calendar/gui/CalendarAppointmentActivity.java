package de.minimum.hawapp.app.calendar.gui;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.AppointmentImpl;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarAppointmentActivity extends Activity {
    private static final int BEGIN_DATE_DIALOG_ID = 1;
    private static final int END_DATE_DIALOG_ID = 2;
    private static final int BEGIN_TIME_DIALOG_ID = 3;
    private static final int END_TIME_DIALOG_ID = 4;
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 5;
    private final Calendar beginCal = Calendar.getInstance(Locale.GERMANY);
    private final Calendar endCal = Calendar.getInstance(Locale.GERMANY);
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
                    Locale.GERMANY);

    private EditText name;
    private EditText begin;
    private EditText end;
    private EditText location;
    private EditText details;
    private Button save;
    private Appointment appointment = null;
    private String appointmentUUID;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_appointment);
        name = (EditText)findViewById(R.id.cal_app_name);
        begin = (EditText)findViewById(R.id.cal_app_begin);
        end = (EditText)findViewById(R.id.cal_app_end);
        location = (EditText)findViewById(R.id.cal_app_location);
        details = (EditText)findViewById(R.id.cal_app_details);

        appointmentUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.APPOINTMENT_UUID);

        save = (Button)findViewById(R.id.cal_btn_app_modify);
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                saveChanges();
            }
        });
        // begin.setTextIsSelectable(false);
        begin.setKeyListener(null);
        begin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                showDialog(BEGIN_DATE_DIALOG_ID);
            }
        });
        end.setKeyListener(null);
        end.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextFields();
    }

    private void updateTextFields() {
        showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        new AsyncTask<String, Void, Void>() {
            private boolean successful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                if (successful) {
                    if (appointment != null) {
                        beginCal.setTime(appointment.getBegin());
                        endCal.setTime(appointment.getEnd());
                        name.setText(appointment.getName());

                        final String beginText = dateFormat.format(appointment.getBegin());
                        begin.setText(beginText);

                        final String endText = dateFormat.format(appointment.getEnd());
                        end.setText(endText);

                        location.setText(appointment.getLocation());

                        details.setText(appointment.getDetails());

                    }
                }
                else {
                    showToastSomethingFailed();
                }
            }

            @Override
            protected Void doInBackground(final String... params) {
                try {
                    if (appointmentUUID != null) {
                        appointment = calManager.getAppointment(appointmentUUID);
                        successful = appointment != null;
                    }
                    else {
                        successful = true;
                    }
                    return null;
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
        Toast.makeText(this, "Es ist ein Problem ist aufgetreten", Toast.LENGTH_LONG).show();
    }

    private void saveChanges() {
        new AsyncTask<Void, Void, Void>() {
            boolean isSuccessful = false;

            @Override
            protected void onPostExecute(final Void arg0) {
                if (isSuccessful) {
                    showSavedCompletToast();
                }
                else {
                    showSavedFailToast();
                }

            }

            @Override
            protected Void doInBackground(final Void... params) {
                if (appointment == null) {
                    isSuccessful = createNewAppointment();
                    if (!isSuccessful) {
                        appointment = null;
                    }
                }
                else {
                    isSuccessful = modifyAppointment();
                }
                return null;
            }

        }.execute();

    }

    private boolean createNewAppointment() {
        appointment = new AppointmentImpl();
        putChangesInAppointment();
        final String lectureUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.LECTURE_UUID);
        return calManager.createNewAppointment(appointment, lectureUUID);

    }

    private boolean modifyAppointment() {
        putChangesInAppointment();
        return calManager.modifyExistingAppointment(appointment);
    }

    private void showSavedCompletToast() {
        Toast.makeText(this, "Änderungen wurden gespeichert", Toast.LENGTH_LONG).show();

    }

    private void showSavedFailToast() {
        Toast.makeText(this, "Änderungen konnten nicht gespeichert werden", Toast.LENGTH_LONG).show();
    }

    // private boolean modifyAppointment() {
    // try {
    // putChangesInAppointment();
    // }
    // catch(final ParseException e) {
    // return false;
    // }
    // }

    private boolean putChangesInAppointment() {
        boolean somethingHasChanged = false;
        if (!name.getText().toString().equals(appointment.getName())) {
            appointment.setName(name.getText().toString());
            somethingHasChanged = true;
        }
        if (appointment.getBegin() == null
                        || !begin.getText().toString().equals(dateFormat.format(appointment.getBegin()))) {
            appointment.setBegin(beginCal.getTime());
            somethingHasChanged = true;
        }
        if (appointment.getEnd() == null || !end.getText().toString().equals(dateFormat.format(appointment.getEnd()))) {
            appointment.setEnd(endCal.getTime());
            somethingHasChanged = true;
        }
        if (!location.getText().toString().equals(appointment.getLocation())) {
            appointment.setLocation(location.getText().toString());
            somethingHasChanged = true;
        }
        if (!details.getText().toString().equals(appointment.getDetails())) {
            appointment.setDetails(name.getText().toString());
            somethingHasChanged = true;
        }
        return somethingHasChanged;
    }

    @Override
    protected Dialog onCreateDialog(final int id) {
        ProgressDialog mProgressDialog;
        switch(id) {
            case BEGIN_DATE_DIALOG_ID:
                return new DatePickerDialog(this, beginDatePickerListener, beginCal.get(Calendar.YEAR),
                                beginCal.get(Calendar.MONTH), beginCal.get(Calendar.DAY_OF_MONTH));
            case BEGIN_TIME_DIALOG_ID:
                return new TimePickerDialog(this, beginTimePickerListener, beginCal.get(Calendar.HOUR_OF_DAY),
                                beginCal.get(Calendar.MINUTE), true);
            case END_DATE_DIALOG_ID:
                return new DatePickerDialog(this, endDatePickerListener, endCal.get(Calendar.YEAR),
                                endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH));
            case END_TIME_DIALOG_ID:
                return new TimePickerDialog(this, endTimePickerListener, endCal.get(Calendar.HOUR_OF_DAY),
                                endCal.get(Calendar.MINUTE), true);

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

    private final OnDateSetListener beginDatePickerListener = new OnDateSetListener() {

        @Override
        public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
            beginCal.set(Calendar.YEAR, year);
            beginCal.set(Calendar.MONTH, monthOfYear);
            beginCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showDialog(BEGIN_TIME_DIALOG_ID);
        }

    };
    private final OnTimeSetListener beginTimePickerListener = new OnTimeSetListener() {

        @Override
        public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
            beginCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            beginCal.set(Calendar.MINUTE, minute);
            begin.setText(dateFormat.format(beginCal.getTime()));
        }

    };
    private final OnDateSetListener endDatePickerListener = new OnDateSetListener() {

        @Override
        public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
            endCal.set(Calendar.YEAR, year);
            endCal.set(Calendar.MONTH, monthOfYear);
            endCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showDialog(END_TIME_DIALOG_ID);
        }

    };
    private final OnTimeSetListener endTimePickerListener = new OnTimeSetListener() {

        @Override
        public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
            endCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endCal.set(Calendar.MINUTE, minute);
            end.setText(dateFormat.format(endCal.getTime()));
        }

    };

}
