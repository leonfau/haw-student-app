package de.minimum.hawapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StiSysActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        TextView textview = new TextView(this);
        textview.setText("This is the StiSysActivity tab");
        setContentView(textview);
    }
}