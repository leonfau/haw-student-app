package de.minimum.hawapp.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import de.minimum.hawapp.app.blackboard.gui.SchwarzesBrettActivity;
import de.minimum.hawapp.app.calendar.gui.CalendarCategoriesActivity;
import de.minimum.hawapp.app.mensa.gui.MensaActivity;
import de.minimum.hawapp.app.pub.gui.PubActivity;
import de.minimum.hawapp.app.stisys.gui.LoginActivity_;
import de.minimum.hawapp.app.stisys.gui.StisysActivity;

public class MainActivity extends TabActivity {

    private static View stisys;
    private static View pub;
    protected static boolean boolStisys = false;
    protected static boolean boolPub = false;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TabHost tabHost = getTabHost(); // The activity TabHost
        final Resources res = getResources(); // Resource object to get
                                              // Drawables
        TabHost.TabSpec spec; // Resusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, LoginActivity_.class);
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("Anmeldung").setIndicator("", res.getDrawable(R.drawable.ic_tab_anmeldung))
                        .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, MensaActivity.class);
        spec = tabHost.newTabSpec("Mensa").setIndicator("", res.getDrawable(R.drawable.ic_tab_mensa))
                        .setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, CalendarCategoriesActivity.class);
        spec = tabHost.newTabSpec("Kalender").setIndicator("", res.getDrawable(R.drawable.ic_tab_kalender))
                        .setContent(intent);
        tabHost.addTab(spec);

        

        
        intent = new Intent().setClass(this, SchwarzesBrettActivity.class);
        spec = tabHost.newTabSpec("Blackboard").setIndicator("", res.getDrawable(R.drawable.ic_tab_brett))
                        .setContent(intent);
        tabHost.addTab(spec);

        
        //TODO: Pub.class
        intent = new Intent().setClass(this, PubActivity.class);
        spec = tabHost.newTabSpec("Pub").setIndicator("", res.getDrawable(R.drawable.ic_tab_pub))
                        .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, StisysActivity.class);
        spec = tabHost.newTabSpec("Stisys").setIndicator("", res.getDrawable(R.drawable.ic_tab_stisys))
                        .setContent(intent);
        tabHost.addTab(spec);

        MainActivity.stisys = tabHost.getTabWidget().getChildTabViewAt(5);
        MainActivity.stisys.setEnabled(MainActivity.boolStisys);

        MainActivity.pub = tabHost.getTabWidget().getChildTabViewAt(4);
        MainActivity.pub.setEnabled(MainActivity.boolPub);
        MainActivity.pub.setVisibility(View.GONE);
        MainActivity.stisys.setVisibility(View.GONE);

        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#06091C"));
        tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#06091C"));
        tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#06091C"));
        tabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.parseColor("#06091C"));
        tabHost.getTabWidget().getChildAt(4).setBackgroundColor(Color.parseColor("#06091C"));
        tabHost.getTabWidget().getChildAt(5).setBackgroundColor(Color.parseColor("#06091C"));

        tabHost.setCurrentTab(0);
    }

    public static void enableStisysTab() {
        MainActivity.boolStisys = true;
        MainActivity.boolPub = true; 
        MainActivity.stisys.setEnabled(MainActivity.boolStisys);  
        MainActivity.stisys.setEnabled(MainActivity.boolPub);
        MainActivity.pub.setVisibility(View.VISIBLE);
        MainActivity.stisys.setVisibility(View.VISIBLE);

    }
}
