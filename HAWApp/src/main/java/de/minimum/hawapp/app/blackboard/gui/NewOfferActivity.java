package de.minimum.hawapp.app.blackboard.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class NewOfferActivity extends Activity {

	private EditText titelET;
	private EditText bildET;
	private EditText textET;
	private EditText contactET;
	private BlackboardManager manager;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_new_offer);
		manager = ManagerFactory.getManager(BlackboardManager.class);

		// final Spinner categorySpinner = (Spinner)
		// findViewById(R.id.sb_new_offer_spinner);
		final EditText categoryEditText = (EditText) findViewById(R.id.sb_new_offer_category_edit_text);

		// categoryNames = new ArrayList<Object>();
		// final Bundle b = getIntent().getExtras();
		// final List<String> cNames = b.getStringArrayList("CATEGORY");
		// for (int i = 0; i < cNames.size(); i++) {
		// categoryNames.add(cNames.get(i));
		// }

		// final ArrayAdapter<Object> categorieAdapter = new
		// ArrayAdapter<Object>(
		// this, android.R.layout.simple_spinner_item, categoryNames);
		// categoryEditText.setAdapter(categorieAdapter);

		titelET = (EditText) findViewById(R.id.sb_new_offer_titel_edit_text);
		bildET = (EditText) findViewById(R.id.sb_new_offer_image_edit_text);
		textET = (EditText) findViewById(R.id.sb_new_offer_text_edit_text);
		contactET = (EditText) findViewById(R.id.sb_new_offer_contakt_edit_text);

		final Button btnCreate = (Button) findViewById(R.id.sb_new_offer_btn_create);
		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// final String category = categoryEditText.getSelectedItem() +
				// "";
				final String category = categoryEditText.getText().toString();
				final String titel = titelET.getText().toString();
				final String bild = bildET.getText().toString();
				final String text = textET.getText().toString();
				final String contact = contactET.getText().toString();
				setOffer(category, titel, bild, text, contact);

			}

		});

		final Button btnImage = (Button) findViewById(R.id.sb_new_offer_image_btn);
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				Log.i("SB_Neue_Nachricht", "BILD Url");
			}
		});
	}

	private void setOffer(final String category, final String titel,
			final String bild, final String text, final String contact) {
		// try {

		String ausgabe = "";

		ausgabe += "Kategorie: " + category + ",\n";
		ausgabe += "Titel: " + titel + ",\n";
		ausgabe += "Bild: " + bild + ",\n";
		ausgabe += "Text: " + text + ",\n";
		ausgabe += "Kontakt: " + contact + "\n";

		// manager.createOffer(category, titel, text, contact, image);
		Toast.makeText(NewOfferActivity.this, ausgabe, Toast.LENGTH_SHORT)
				.show();
		//
		// } catch (final OfferCreationFailedException e) {
		// Toast.makeText(NewOfferActivity.this, e.toString(),
		// Toast.LENGTH_SHORT).show();
		//
		// }
	}
}
