package de.minimum.hawapp.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CalendarlectureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarlecture);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendarlecture, menu);
        return true;
    }

}
