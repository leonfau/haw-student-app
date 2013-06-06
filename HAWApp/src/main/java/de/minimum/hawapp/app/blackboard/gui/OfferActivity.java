package de.minimum.hawapp.app.blackboard.gui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
    private BlackboardManager manager = ManagerFactory.getManager(BlackboardManager.class);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_offer);

        final Bundle b = getIntent().getExtras();
        this.title = b.getString("TITLE");
        this.datum = b.getString("DATE");
        this.text = b.getString("TEXT");
        this.kontakt = b.getString("CONTAKT");
        long imageId = b.getLong("IMAGE");

        if (imageId >= 0)
            loadImage(imageId);

        final TextView vTitel = (TextView)findViewById(R.id.sb_offer_title_text_view);
        final TextView vDatum = (TextView)findViewById(R.id.sb_offer_date_text_view);
        final TextView vText = (TextView)findViewById(R.id.sb_offer_text_text_view);
        final TextView vKontakt = (TextView)findViewById(R.id.sb_offer_contakt_text_view);

        vTitel.setText("Titel: " + this.title);
        vDatum.setText("Datum: " + this.datum);
        vText.setText("Text:\n" + this.text);
        vKontakt.setText(this.kontakt);
    }

    private void setImage(Bitmap image) {
        ((ImageView)findViewById(R.id.offerImage)).setImageBitmap(image);
    }

    private void loadImage(final long imageId) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected void onPostExecute(Bitmap result) {
                setImage(result);
            };

            @Override
            protected Bitmap doInBackground(final Void... arg0) {
                Image img = OfferActivity.this.manager.getImageById(OfferActivity.this, imageId);
                final byte[] data = img.getImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                return BitmapFactory.decodeByteArray(data, 0, data.length, options);
            }

        }.execute();
    }

}
