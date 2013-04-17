package de.minimum.hawapp.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 

import de.minimum.hawapp.gui.schwarzesbrett.NachrichtActivity;
import de.minimum.hawapp.gui.schwarzesbrett.NeueNachrichtActivity; 
import android.app.Activity; 
import android.content.Context;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View; 
import android.widget.AdapterView;
import android.widget.ArrayAdapter; 
import android.widget.ListView; 

public class SchwarzesBrettActivity extends Activity {

	/*
	 * ####################################################################################################
	 * Test Daten
	 * ####################################################################################################
	 */
	// Anfragen
	String[] titelAnfragen = { "Fahrrad zu verkaufen", "neuer Drucker",
			"Fernseher" };
	String[] autorenAnfragen = { "Alex", "Wlad", "Alex" };
	String[] datumAnfragen = { "10.04.2013", "04.03.2013", "29.03.2013" };
	String[] textAnfragen = {
			"Guterhaltenes, wenig gefahrenes SPECIALIZED Mountainbike mit folgenden Komponenten: "
					+ "Rehmenhöhe : 18 , 165 bis 180 cm Körpergröße Rahmen: M4 Alu, endverstärkt, ORE Ober-und Unterrohr, "
					+ "Scheibenbremsauflage, austauschbares Schaltauge. GabeL: Fox FloatF-80 RLT,80mmLuftfederung,einstellbar,"
					+ "lockout Bremsen: Avid SD-5 Schaltwerk: Shimano XTR, 27-Gang Schalthebel: Shimano LX Cassette: Shimano LX Kurbel: "
					+ "Specialized Strongarm 7050 Pedale: Shimano 515 Klickpedale Kettenblätter: "
					+ "4-Arm, 44/32/22 Sattelstütze: Alu schwarz gefedert Felgen: Mavic X225, 26 "
					+ "Neupreis: 2000 Euro ", "Neuer Drucker zu verkaufen",
			"Alter Fernseher zu verkaufen" };
	String[] kontaktAnfragen = { "Alex S. Tel. 040123456", "Str. Rosenstr 14",
			"Alex S. Tel. 040123456" };
	int anzahlAnfragen = titelAnfragen.length;

	// Angebote
	String[] titelAngebote = { "Nebenjob", "Mitbewohner", "Tutorium" };
	String[] autorenAngebote = { "Alex", "Wlad", "Alex" };
	String[] datumAngebote = { "10.04.2013", "04.03.2013", "29.03.2013" };
	String[] textAngebote = { "Aushilfe (W/M) Aufgaben: Programmieren ...",
			"Suche Mitbewohner", "Java tutorium am 04.05.2013" };
	String[] kontaktAngebote = { "Alex S. Tel. 040123456", "Str. Rosenstr 14",
			"Berliner Tor R565" };
	int anzahlAngebote = titelAngebote.length;

	// ####################################################################################################

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_main);

		createPinnwand(titelAnfragen, autorenAnfragen, datumAnfragen,
				anzahlAnfragen, textAnfragen, kontaktAnfragen);
	}
 

	/*
	 * erstellt die Pinnwand
	 */
	private void createPinnwand(final String[] titel, final String[] autor,
			final String[] datum, int anzahl, final String[] text,
			final String[] kontakt) {
		ListView listview = (ListView) findViewById(R.id.sb_main_listview);
		String[] values = titel;

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int i = (int) id;
				Intent intent = new Intent(SchwarzesBrettActivity.this,
						NachrichtActivity.class);

				Bundle b = new Bundle();
				b.putString("titel", titel[i]);
				b.putString("autor", autor[i]);
				b.putString("datum", datum[i]);
				b.putString("text", text[i]);
				b.putString("kontakt", kontakt[i]);
				intent.putExtras(b);

				startActivity(intent);
			}

		});
	}

	public void loadNeueNachricht(View view) {
		Intent intent = new Intent(getBaseContext(),
				NeueNachrichtActivity.class);
		startActivity(intent);
	}

	public void loadAnfragen(View view) { 
		createPinnwand(titelAnfragen, autorenAnfragen, datumAnfragen,
				anzahlAnfragen, textAnfragen, kontaktAnfragen);
	}

	public void loadAngebote(View view) { 
		createPinnwand(titelAngebote, autorenAngebote, datumAngebote,
				anzahlAngebote, textAngebote, kontaktAngebote);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}
