package de.minimum.hawapp.app.blackboard.gui;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.blackboard.exceptions.OfferCreationFailedException;
import de.minimum.hawapp.app.context.ManagerFactory;

public class NewOfferActivity extends Activity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;

    private static final int REQUEST_PATH = 1;
    private String curFilePath;

    private EditText titelET;
    private EditText bildET;
    private EditText textET;
    private EditText contactET;
    private BlackboardManager manager;
    private Spinner categorySpinner;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_new_offer);
        this.manager = ManagerFactory.getManager(BlackboardManager.class);

        this.categorySpinner = (Spinner)findViewById(R.id.sb_new_offer_spinner);
        this.titelET = (EditText)findViewById(R.id.sb_new_offer_titel_edit_text);
        this.bildET = (EditText)findViewById(R.id.sb_new_offer_image_edit_text);
        this.textET = (EditText)findViewById(R.id.sb_new_offer_text_edit_text);
        this.contactET = (EditText)findViewById(R.id.sb_new_offer_contakt_edit_text);

        final ArrayAdapter<String> categorieAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, getIntent().getExtras().getStringArrayList("CATEGORY"));
        this.categorySpinner.setAdapter(categorieAdapter);

        final Button btnCreate = (Button)findViewById(R.id.sb_new_offer_btn_create);
        btnCreate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                // final String category = categoryEditText.getSelectedItem() +
                // "";
                final String category = NewOfferActivity.this.categorySpinner.getSelectedItem().toString();
                final String titel = NewOfferActivity.this.titelET.getText().toString();
                final String bild = NewOfferActivity.this.bildET.getText().toString();
                final String text = NewOfferActivity.this.textET.getText().toString();
                final String contact = NewOfferActivity.this.contactET.getText().toString();
                setOffer(category, titel, bild, text, contact);

            }

        });

    }

    public void getfile(final View view) {
        // final Intent intent1 = new Intent(this, FileChooser.class);
        // startActivityForResult(intent1, NewOfferActivity.REQUEST_PATH);
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), NewOfferActivity.REQUEST_PATH);
    }

    // Listen for results.
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // See which child activity is calling us back.
        if (requestCode == NewOfferActivity.REQUEST_PATH) {
            if (resultCode == Activity.RESULT_OK) {
                this.curFilePath = getPath(data.getData());
                this.bildET.setText(this.curFilePath);
            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected Dialog onCreateDialog(final int id) {
        ProgressDialog mProgressDialog;
        switch(id) {
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

    private void setOffer(final String category, final String header, final String bildPath, final String description,
                    final String contact) {

        showDialog(NewOfferActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
        new AsyncTask<Void, Void, Void>() {

            private Long result;

            @Override
            protected void onPostExecute(final Void arg0) {

                super.onPostExecute(arg0);
                String ausgabe = "Result = " + this.result + "\n";
                ausgabe += "Kategorie: " + category + ",\n";
                ausgabe += "Titel: " + header + ",\n";
                ausgabe += "Bild: " + bildPath + ",\n";
                ausgabe += "Text: " + description + ",\n";
                ausgabe += "Kontakt: " + contact + "\n";
                Toast.makeText(NewOfferActivity.this, ausgabe, Toast.LENGTH_SHORT).show();

                dismissDialog(NewOfferActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(NewOfferActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);

                NewOfferActivity.this.finish();
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                try {
                    File image = null;
                    if (bildPath != null && !bildPath.equals("")) {
                        image = new File(bildPath);
                        if (!image.exists()) {
                            Toast.makeText(NewOfferActivity.this, "Bild konnte nicht gefunden werden",
                                            Toast.LENGTH_SHORT).show();
                            image = null;
                        }
                    }
                    this.result = NewOfferActivity.this.manager.createOffer(NewOfferActivity.this, category, header,
                                    description, contact, image);

                }
                catch(final OfferCreationFailedException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();

    }
}
