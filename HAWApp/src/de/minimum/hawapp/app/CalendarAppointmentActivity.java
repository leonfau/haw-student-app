package de.minimum.hawapp.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CalendarAppointmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_appointment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar_appointment, menu);
        return true;
    }

}
