package de.minimum.hawapp.app.blackboard.gui;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.blackboard.exceptions.OfferCreationFailedException;
import de.minimum.hawapp.app.context.ManagerFactory;

public class NewOfferActivity extends Activity {
	private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;

	private static final int REQUEST_PATH = 1;
	String curFileName;
	String curFilePath;

	private EditText titelET;
	private EditText bildET;
	private EditText textET;
	private EditText contactET;
	private BlackboardManager manager;
	private EditText categoryEditText;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_new_offer);
		manager = ManagerFactory.getManager(BlackboardManager.class);
		manager.setContext(this);

		categoryEditText = (EditText) findViewById(R.id.sb_new_offer_category_edit_text);
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

	}

	public void getfile(final View view) {
		final Intent intent1 = new Intent(this, FileChooser.class);
		startActivityForResult(intent1, REQUEST_PATH);
	}

	// Listen for results.
	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		// See which child activity is calling us back.
		if (requestCode == REQUEST_PATH) {
			if (resultCode == RESULT_OK) {
				curFileName = data.getStringExtra("GetFileName");
				curFilePath = data.getStringExtra("GetPath");
				bildET.setText(curFilePath + "/" + curFileName);
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		ProgressDialog mProgressDialog;
		switch (id) {
		case DIALOG_DOWNLOAD_JSON_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Updating.....");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
			return mProgressDialog;
		}
		return null;
	}

	private void setOffer(final String category, final String header,
			final String bildPath, final String description,
			final String contact) {

		showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
		new AsyncTask<Void, Void, Void>() {

			private Long result;

			@Override
			protected void onPostExecute(final Void arg0) {

				super.onPostExecute(arg0);
				String ausgabe = "Result = " + result + "\n";
				ausgabe += "Kategorie: " + category + ",\n";
				ausgabe += "Titel: " + header + ",\n";
				ausgabe += "Bild: " + bildPath + ",\n";
				ausgabe += "Text: " + description + ",\n";
				ausgabe += "Kontakt: " + contact + "\n";
				Toast.makeText(NewOfferActivity.this, ausgabe,
						Toast.LENGTH_SHORT).show();

				dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
				removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				try {
					final File image = new File(bildPath);
					result = manager.createOffer(category, header, description,
							contact, image);

				} catch (final OfferCreationFailedException e) {
					e.printStackTrace();
				}
				return null;
			}

		}.execute();

	}
}
