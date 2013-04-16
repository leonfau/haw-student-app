package de.minimum.hawapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class KalenderActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        TextView textview = new TextView(this);
        textview.setText("This is the Kalender tab");
        setContentView(textview);
    }
}
