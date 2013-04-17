package de.minimum.hawapp.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MensaActivity extends Activity {
    private static final String NAME = "NAME";
    private static final String BESCHREIBUNG = "DESCRIPTION";
    private static final String PREIS = "PREIS";
	private TextView userSelection;

    String[] essen = {"Hähnchenbrustfilet", "Vegetarischer Hamburger", "Tilapiafilet", "Putenfleisch"};
    String[] beschreibung = {"Hähnchenbrustfilet gefüllt mit Broccoli , dazu Basilikumsoße und Fusilli", "Vegetarischer Hamburger mit Gemüseschnitzel, Salat und Pommes Frites", "Tilapiafilet im Gemüsebeet auf Zucchiniwürfel mit Walnuß-Senfsoße und Rosmarinkartoffeln", "Putenfleisch mit Obst aus dem Wok und Reis"};
    String[] preis = {"2,20 / 3,30", "2,40/3,50", "4,50/5,30", "3,30/3,90"};
	private ArrayList<HashMap<String, String>>  mylist;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		ListView list = (ListView) findViewById(R.id.SCHEDULE);
		mylist = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < 4; i++) {
			map = new HashMap<String, String>();
			map.put(NAME, essen[i]);
			map.put(BESCHREIBUNG, beschreibung[i]);
			map.put(PREIS, preis[i]);
			mylist.add(map);			
		}
		ListAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.row, new String[] {
				NAME, BESCHREIBUNG, PREIS }, new int[] { R.id.name,
				R.id.beschreibung, R.id.preis});
		list.setAdapter(mSchedule);
	}
	

}