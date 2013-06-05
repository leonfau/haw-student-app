package de.minimum.hawapp.app.blackboard.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import de.minimum.hawapp.app.R;

public class OfferActivity extends Activity {
	private String title;
	private String datum;
	private String kontakt;
	private String text;
	private String image;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_offer);

		final Bundle b = getIntent().getExtras();
		title = b.getString("TITLE");
		datum = b.getString("DATE");
		text = b.getString("TEXT");
		kontakt = b.getString("CONTAKT");
		image = b.getString("IMAGE");

		final TextView vTitel = (TextView) findViewById(R.id.sb_offer_title_text_view);
		final TextView vDatum = (TextView) findViewById(R.id.sb_offer_date_text_view);
		final TextView vText = (TextView) findViewById(R.id.sb_offer_text_text_view);
		final TextView vKontakt = (TextView) findViewById(R.id.sb_offer_contakt_text_view);

		vTitel.setText("Titel: " + title);
		vDatum.setText("Datum: " + datum);
		vText.setText("Text:\n" + text);
		vKontakt.setText(kontakt);
	}

}
