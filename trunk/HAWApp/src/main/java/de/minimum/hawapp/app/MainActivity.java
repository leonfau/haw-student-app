package de.minimum.hawapp.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	 
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	 
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, LoginActivity_.class);
	 
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("artists").setIndicator("",
	                      res.getDrawable(R.drawable.ic_tab_anmeldung))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	 
	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, MensaActivity.class);
	    spec = tabHost.newTabSpec("albums").setIndicator("",
	                      res.getDrawable(R.drawable.ic_tab_mensa))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	 
	    intent = new Intent().setClass(this, StisysActivity.class);
	    spec = tabHost.newTabSpec("songs").setIndicator("StiSys",
	                      res.getDrawable(R.drawable.ic_tab_stisys))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, KalenderActivity.class);
	    spec = tabHost.newTabSpec("songs").setIndicator("",
	                      res.getDrawable(R.drawable.ic_tab_kalender))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, SchwarzesBrettActivity.class);
	    spec = tabHost.newTabSpec("songs").setIndicator("",
	                      res.getDrawable(R.drawable.ic_tab_kalender))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	 
	    tabHost.setCurrentTab(0);
	}
}
