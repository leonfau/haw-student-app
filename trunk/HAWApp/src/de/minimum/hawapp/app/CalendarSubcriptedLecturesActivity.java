package de.minimum.hawapp.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CalendarSubcriptedLecturesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_subcripted_lectures);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar_subcripted_lectures, menu);
        return true;
    }

}
