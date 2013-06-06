package de.minimum.hawapp.app.blackboard.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.blackboard.api.Category;
import de.minimum.hawapp.app.blackboard.api.Offer;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.util.InternetConnectionUtil;

public class BlackBoardActivity extends Activity {
    private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    protected static final String CATEGORY_ALL = "Alles";
    protected static final String CATEGORY_IGNORE = "Papierkorb";
    protected static final String CATEGORY_OWN = "Meins";
    private final String NO_INTERNET_CONNECTION_MSG = "Keine Internetverbindung vorhanden";
    private final String NO_DATA_FROM_SERVICE = "keine Nachrichten vorhanden oder Service nicht verfügbar";

    private String notificationOnError;

    private final BlackboardManager manager = ManagerFactory.getManager(BlackboardManager.class);
    private List<String> allCategoryNamesCached;
    private List<String> allCategoryNamesDB;
    // private List<Offer> ownOffers;

    private List<Offer> offerListOfCategory;
    private List<Offer> offerListOfCategoryCached;

    private boolean dialogShown;
    // Views
    private Spinner categorySpinner;
    private ListView offerListView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_main);

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

    @Override
    protected void onResume() {
        super.onResume();
        if (this.allCategoryNamesCached == null) {
            this.dialogShown = true;
            showDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
        }
        getCategoryNames();
    }

    private void display() {
        if (this.allCategoryNamesDB == null) {
            Toast.makeText(getApplicationContext(), this.notificationOnError, Toast.LENGTH_SHORT).show();
            this.allCategoryNamesDB = this.allCategoryNamesCached;

        }
        this.categorySpinner = (Spinner)findViewById(R.id.sb_categorie_spinner);
        this.offerListView = (ListView)findViewById(R.id.sb_offerList);

        final List<String> categoryNames = new ArrayList<String>();
        categoryNames.add(BlackBoardActivity.CATEGORY_ALL);
        categoryNames.add(BlackBoardActivity.CATEGORY_IGNORE);
        categoryNames.add(BlackBoardActivity.CATEGORY_OWN);
        categoryNames.addAll(this.allCategoryNamesDB);
        setCategoryAdapter(categoryNames);

        final Button newOfferBtn = (Button)findViewById(R.id.sb_newOffer_btn);
        newOfferBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(getBaseContext(), NewOfferActivity.class);
                final Bundle b = new Bundle();
                final ArrayList<String> newCNames = new ArrayList<String>();
                newCNames.addAll(BlackBoardActivity.this.allCategoryNamesDB);
                b.putStringArrayList("CATEGORY", newCNames);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    /***************************************************************
     * Kategorie
     ***************************************************************/
    private void getCategoryNames() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(final Void arg0) {

                super.onPostExecute(arg0);
                display();
                if (BlackBoardActivity.this.dialogShown) {
                    dismissDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
                    removeDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
                    BlackBoardActivity.this.dialogShown = false;
                }
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
                    BlackBoardActivity.this.allCategoryNamesCached = BlackBoardActivity.this.allCategoryNamesDB;
                    BlackBoardActivity.this.allCategoryNamesDB = BlackBoardActivity.this.manager
                                    .getAllCategoryNames(BlackBoardActivity.this);
                    if (BlackBoardActivity.this.allCategoryNamesCached == null) {
                        BlackBoardActivity.this.allCategoryNamesCached = BlackBoardActivity.this.allCategoryNamesDB;
                    }
                    BlackBoardActivity.this.notificationOnError = BlackBoardActivity.this.NO_DATA_FROM_SERVICE;
                }
                else {
                    BlackBoardActivity.this.allCategoryNamesDB = null;
                    BlackBoardActivity.this.notificationOnError = BlackBoardActivity.this.NO_INTERNET_CONNECTION_MSG;
                }
                return null;
            }

        }.execute();
    }

    private void setCategoryAdapter(final List<String> categoryNames) {
        final ArrayAdapter<String> categorieAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, categoryNames);
        this.categorySpinner.setAdapter(categorieAdapter);
        this.categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> adapter, final View view, final int index, final long arg3) {
                getOfferList(adapter.getItemAtPosition(index).toString());
            }

            @Override
            public void onNothingSelected(final AdapterView<?> arg0) {

            }
        });
    }

    /***************************************************************
     * Nachrichtenliste
     ***************************************************************/
    private void getOfferList(final String categoryName) {

        if (this.offerListOfCategoryCached == null) {
            this.dialogShown = true;
            showDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
        }

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(final Void arg0) {
                super.onPostExecute(arg0);
                setOfferListView();
                if (BlackBoardActivity.this.dialogShown) {
                    dismissDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
                    removeDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
                    BlackBoardActivity.this.dialogShown = false;
                }
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
                    BlackBoardActivity.this.offerListOfCategoryCached = BlackBoardActivity.this.offerListOfCategory;
                    if (categoryName.equals(BlackBoardActivity.CATEGORY_ALL))
                        // Alle Nachrichten
                        BlackBoardActivity.this.offerListOfCategory = BlackBoardActivity.this.manager
                                        .getAllOffers(BlackBoardActivity.this);
                    else if (categoryName.equals(BlackBoardActivity.CATEGORY_IGNORE))
                        // Papierkorb
                        BlackBoardActivity.this.offerListOfCategory = BlackBoardActivity.this.manager
                                        .getIgnoredOffers(BlackBoardActivity.this);

                    else if (categoryName.equals(BlackBoardActivity.CATEGORY_OWN))
                        // Meine Nachrichten
                        BlackBoardActivity.this.offerListOfCategory = BlackBoardActivity.this.manager
                                        .getAllOwnOffers(BlackBoardActivity.this);

                    else {
                        Log.v("SB OfferList", "sonstige Nachrichten ");
                        // TODO: wirft fehler
                        try {
                            final Category category = BlackBoardActivity.this.manager.getCategory(
                                            BlackBoardActivity.this, categoryName);
                            BlackBoardActivity.this.offerListOfCategory = category.getAllOffers();// getOfferBy(category);
                        }
                        catch(final Exception e) {
                            Log.v("Blackboard", e.getMessage(), e);
                        }
                    }
                    if (BlackBoardActivity.this.offerListOfCategoryCached == null) {
                        BlackBoardActivity.this.offerListOfCategoryCached = BlackBoardActivity.this.offerListOfCategory;
                    }
                    BlackBoardActivity.this.notificationOnError = BlackBoardActivity.this.NO_DATA_FROM_SERVICE;
                }
                else {
                    BlackBoardActivity.this.allCategoryNamesDB = null;
                    BlackBoardActivity.this.notificationOnError = BlackBoardActivity.this.NO_INTERNET_CONNECTION_MSG;
                }
                return null;
            }

        }.execute();
    }

    private void setOfferListView() {
        if (this.offerListOfCategory == null) {
            Toast.makeText(getApplicationContext(), this.notificationOnError, Toast.LENGTH_SHORT).show();
            this.offerListOfCategory = this.offerListOfCategoryCached;
        }
        this.offerListView.setAdapter(new OfferAdapter(BlackBoardActivity.this, this.offerListOfCategory));
    }

    /***************************************************************
     * OnButtonClick
     ***************************************************************/

    private void setOfferToIgnoreList(final Offer offer) {
        if (this.manager.ignoreOffer(this, offer)) {
            this.offerListOfCategory.remove(offer);
            setOfferListView();
        }
    }

    private void deleteOffer(final Offer offer) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    BlackBoardActivity.this.offerListOfCategory.remove(offer);
                    setOfferListView();
                }
            };

            @Override
            protected Boolean doInBackground(final Void... arg0) {
                return BlackBoardActivity.this.manager.removeOwnOffer(BlackBoardActivity.this, offer);
            }

        }.execute();
    }

    /***************************************************************
     * OfferAdapter
     ***************************************************************/

    public class OfferAdapter implements ListAdapter {

        private final List<Offer> myArr;
        private final Context context;

        public OfferAdapter(final Context c, final List<Offer> myArrList) {
            this.context = c;
            this.myArr = myArrList;
        }

        @Override
        public int getCount() {
            return this.myArr.size();
        }

        @Override
        public Object getItem(final int position) {
            return position;
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public int getItemViewType(final int arg0) {
            return 1;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            final LayoutInflater inflater = (LayoutInflater)this.context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.sb_offerlist_frag, null);
            }

            // TITEL
            final TextView title = (TextView)convertView.findViewById(R.id.sb_offerlist_titel);
            final Offer offer = this.myArr.get(position);
            title.setText(offer.getHeader());

            title.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {
                    final Intent intent = new Intent(BlackBoardActivity.this, OfferActivity.class);
                    final Bundle b = new Bundle();
                    b.putString("TITLE", offer.getHeader());
                    b.putString("DATE", offer.getDateOfCreation() + "");
                    b.putString("TEXT", offer.getDescription());
                    b.putString("CONTAKT", offer.getContact());
                    b.putLong("IMAGE", offer.getImageId() == null ? -1 : offer.getImageId());
                    intent.putExtras(b);

                    startActivity(intent);
                }
            });

            // Button Rubbish
            final ImageButton rubbishBtn = (ImageButton)convertView.findViewById(R.id.sb_offerlist_rubbish_btn);
            if (BlackBoardActivity.this.categorySpinner.getSelectedItem().equals(BlackBoardActivity.CATEGORY_IGNORE)
                            || BlackBoardActivity.this.categorySpinner.getSelectedItem().equals(
                                            BlackBoardActivity.CATEGORY_OWN))
                rubbishBtn.setVisibility(View.INVISIBLE);

            rubbishBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {
                    setOfferToIgnoreList(offer);
                }

            });

            // Button Delete
            final ImageButton deleteBtn = (ImageButton)convertView.findViewById(R.id.sb_offerlist_delete_btn);
            if (!BlackBoardActivity.this.categorySpinner.getSelectedItem().equals(BlackBoardActivity.CATEGORY_OWN)) {
                deleteBtn.setVisibility(View.INVISIBLE);
            }
            deleteBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {

                    // TODO: Nachricht löschen
                    // TODO: prüfen ob eigene nachricht
                    deleteOffer(offer);
                }

            });

            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void registerDataSetObserver(final DataSetObserver arg0) {

        }

        @Override
        public void unregisterDataSetObserver(final DataSetObserver arg0) {

        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(final int arg0) {
            return false;
        }
    }
}
