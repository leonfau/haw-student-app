package de.minimum.hawapp.app.blackboard.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import de.minimum.hawapp.app.R;

public class NeueNachrichtActivity extends Activity {

	private Spinner kategorie;
	private EditText titel;
	private EditText bild;
	private EditText text;
	private EditText kontakt;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_neue_nachricht);

		kategorie = (Spinner) findViewById(R.id.sb_neueNachricht_spinner);
		final String[] items = new String[] { "Angebote", "Anfragen" };

		final ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this,
				android.R.layout.simple_spinner_item, items);
		kategorie.setAdapter(adapter);

		titel = (EditText) findViewById(R.id.sb_neue_nachricht_titel_edit_text);
		bild = (EditText) findViewById(R.id.sb_neue_nachricht_bild_edit_text);
		text = (EditText) findViewById(R.id.sb_neue_nachricht_text_edit_text);
		kontakt = (EditText) findViewById(R.id.sb_neue_nachricht_kontakt_edit_text);
	}

	public void loadBildUrl(final View view) {
		Log.i("SB_Neue_Nachricht", "BILD Url");
	}

	public void erstellNachricht(final View view) {
		String ausgabe = "";
		ausgabe += "Kategorie: " + kategorie.getSelectedItem() + ",\n";
		ausgabe += "Titel: " + titel.getText() + ",\n";
		ausgabe += "Bild: " + bild.getText() + ",\n";
		ausgabe += "Text: " + text.getText() + ",\n";
		ausgabe += "Kontakt: " + kontakt.getText() + "\n";
		Toast.makeText(NeueNachrichtActivity.this, ausgabe, Toast.LENGTH_SHORT)
				.show();
	}
}
