package de.minimum.hawapp.app.blackboard.gui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.blackboard.api.Image;
import de.minimum.hawapp.app.context.ManagerFactory;

public class OfferActivity extends Activity {
	private String title;
	private String datum;
	private String kontakt;
	private String text;
	private final BlackboardManager manager = ManagerFactory
			.getManager(BlackboardManager.class);

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final WindowManager.LayoutParams params = getWindow().getAttributes();
		params.x = -100;
		params.height = 70;
		params.width = 1000;
		params.y = -50;
		getWindow().setAttributes(params);

		setContentView(R.layout.sb_offer);

		final Bundle b = getIntent().getExtras();
		title = b.getString("TITLE");
		datum = b.getString("DATE");
		text = b.getString("TEXT");
		kontakt = b.getString("CONTAKT");
		final long imageId = b.getLong("IMAGE");

		if (imageId >= 0) {
			loadImage(imageId);
		}

		final TextView vTitel = (TextView) findViewById(R.id.sb_offer_title_text_view);
		final TextView vDatum = (TextView) findViewById(R.id.sb_offer_date_text_view);
		final TextView vText = (TextView) findViewById(R.id.sb_offer_text_text_view);
		final TextView vKontakt = (TextView) findViewById(R.id.sb_offer_contakt_text_view);

		vTitel.setText("Titel: " + title);
		vDatum.setText("Datum: " + datum);
		vText.setText("Text:\n" + text);
		vKontakt.setText(kontakt);
	}

	private void setImage(final Bitmap image) {
		((ImageView) findViewById(R.id.offerImage)).setImageBitmap(image);
	}

	private void loadImage(final long imageId) {
		new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected void onPostExecute(final Bitmap result) {
				setImage(result);
			};

			@Override
			protected Bitmap doInBackground(final Void... arg0) {
				final Image img = manager.getImageById(OfferActivity.this,
						imageId);
				final byte[] data = img.getImage();
				final BitmapFactory.Options options = new BitmapFactory.Options();
				return BitmapFactory.decodeByteArray(data, 0, data.length,
						options);
			}

		}.execute();
	}

}
