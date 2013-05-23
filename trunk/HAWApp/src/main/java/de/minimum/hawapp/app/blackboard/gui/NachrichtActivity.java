package de.minimum.hawapp.app.blackboard.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import de.minimum.hawapp.app.R;

public class NachrichtActivity extends Activity {
	private String titel;
	private String autor;
	private String datum;
	private String text;
	private String kontakt;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_nachricht);

		final Bundle b = getIntent().getExtras();
		titel = b.getString("titel");
		autor = b.getString("autor");
		datum = b.getString("datum");
		text = b.getString("text");
		kontakt = b.getString("kontakt");

		final TextView vTitel = (TextView) findViewById(R.id.sb_nachricht_titel_text_view);
		final TextView vAutor = (TextView) findViewById(R.id.sb_nachricht_autor_text_view);
		final TextView vDatum = (TextView) findViewById(R.id.sb_nachricht_datum_text_view);
		final TextView vText = (TextView) findViewById(R.id.sb_nachricht_text_text_view);
		final TextView vKontakt = (TextView) findViewById(R.id.sb_nachricht_kontakt_text_view);

		vTitel.setText("Titel: " + titel);
		vAutor.setText("Autor: " + autor);
		vDatum.setText("Datum: " + datum);
		vText.setText("Text:\n" + text);
		vKontakt.setText(kontakt);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
