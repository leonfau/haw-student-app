package de.minimum.hawapp.gui.schwarzesbrett;

import de.minimum.hawapp.app.R;
import android.app.Activity; 
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class NeueNachrichtActivity extends Activity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_neue_nachricht);

		
	}

	public void finish(View view) {
		this.finish();
	}

	public void loadBildUrl(View view){
		Log.v("SB_Neue_Nachricht", "BILD Url");
	}

	public void erstellNachricht(View view){
		Log.v("SB_Neue_Nachricht", "Nachricht erstellen");
	} 
}
