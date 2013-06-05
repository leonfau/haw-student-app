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

	private final BlackboardManager manager = ManagerFactory
			.getManager(BlackboardManager.class);
	private List<String> allCategoryNamesCached;
	private List<String> allCategoryNamesDB;
	private List<Offer> ownOffers;

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
		manager.setContext(this);

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

	@Override
	protected void onResume() {
		super.onResume();
		if (allCategoryNamesCached == null) {
			dialogShown = true;
			showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
		}
		getCategoryNames();
	}

	private void display() {
		if (allCategoryNamesDB == null) {
			Toast.makeText(getApplicationContext(), notificationOnError,
					Toast.LENGTH_SHORT).show();
			allCategoryNamesDB = allCategoryNamesCached;

		}
		if (ownOffers == null) {
			getAllOwnOffers();
		}
		categorySpinner = (Spinner) findViewById(R.id.sb_categorie_spinner);
		offerListView = (ListView) findViewById(R.id.sb_offerList);

		final List<String> categoryNames = new ArrayList<String>();
		categoryNames.add(CATEGORY_ALL);
		categoryNames.add(CATEGORY_IGNORE);
		categoryNames.add(CATEGORY_OWN);
		categoryNames.addAll(allCategoryNamesDB);
		setCategoryAdapter(categoryNames);

		final Button newOfferBtn = (Button) findViewById(R.id.sb_newOffer_btn);
		newOfferBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(getBaseContext(),
						NewOfferActivity.class);
				final Bundle b = new Bundle();
				final ArrayList<String> newCNames = new ArrayList<String>();
				newCNames.addAll(allCategoryNamesDB);
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
				if (dialogShown) {
					dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
					removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
					dialogShown = false;
				}
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				if (InternetConnectionUtil
						.hasInternetConnection(getApplicationContext())) {
					allCategoryNamesCached = allCategoryNamesDB;
					allCategoryNamesDB = manager.getAllCategoryNames();
					if (allCategoryNamesCached == null) {
						allCategoryNamesCached = allCategoryNamesDB;
					}
					notificationOnError = NO_DATA_FROM_SERVICE;
				} else {
					allCategoryNamesDB = null;
					notificationOnError = NO_INTERNET_CONNECTION_MSG;
				}
				return null;
			}

		}.execute();
	}

	private void setCategoryAdapter(final List<String> categoryNames) {
		final ArrayAdapter<String> categorieAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, categoryNames);
		categorySpinner.setAdapter(categorieAdapter);
		categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(final AdapterView<?> adapter,
					final View view, final int index, final long arg3) {
				getOfferList(index);
			}

			@Override
			public void onNothingSelected(final AdapterView<?> arg0) {

			}
		});
	}

	/***************************************************************
	 * Nachrichtenliste
	 ***************************************************************/
	private void getOfferList(final int index) {

		if (offerListOfCategoryCached == null) {
			dialogShown = true;
			showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
		}

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPostExecute(final Void arg0) {
				super.onPostExecute(arg0);
				setOfferListView();
				if (dialogShown) {
					dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
					removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
					dialogShown = false;
				}
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				if (InternetConnectionUtil
						.hasInternetConnection(getApplicationContext())) {
					final String categoryName = allCategoryNamesDB.get(index);
					offerListOfCategoryCached = offerListOfCategory;
					switch (index) {
					case 0:
						// Alle Nachrichten
						offerListOfCategory = manager.getAllOffers();
						break;
					case 1:
						// Papierkorb
						offerListOfCategory = manager.getIgnoredOffers();
						break;

					case 2:
						// Meine Nachrichten
						offerListOfCategory = manager.getAllOwnOffers();
						break;

					default:
						Log.v("SB OfferList", "sonstige Nachrichten ");
						// TODO: wirft fehler
						try {
							final Category category = manager
									.getCategory(categoryName);
							getOfferBy(category);
						} catch (final Exception e) {

							Toast.makeText(
									getApplicationContext(),
									"Nachrichten der KAtegorie können nicht angezeigt werden",
									Toast.LENGTH_SHORT).show();
						}
						break;
					}
					if (offerListOfCategoryCached == null) {
						offerListOfCategoryCached = offerListOfCategory;
					}
					notificationOnError = NO_DATA_FROM_SERVICE;
				} else {
					allCategoryNamesDB = null;
					notificationOnError = NO_INTERNET_CONNECTION_MSG;
				}
				return null;
			}

		}.execute();
	}

	private void getOfferBy(final Category category) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(final Void... arg0) {
				offerListOfCategory = category.getAllOffers();
				Log.v("BB", ">> " + offerListOfCategory);

				return null;
			}

		}.execute();
	}

	private void setOfferListView() {
		if (offerListOfCategory == null) {
			Toast.makeText(getApplicationContext(), notificationOnError,
					Toast.LENGTH_SHORT).show();
			offerListOfCategory = offerListOfCategoryCached;
		}
		offerListView.setAdapter(new OfferAdapter(BlackBoardActivity.this,
				offerListOfCategory));
	}

	private void getAllOwnOffers() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(final Void... arg0) {
				ownOffers = manager.getAllOwnOffers();
				return null;
			}

		}.execute();
	}

	/***************************************************************
	 * OnButtonClick
	 ***************************************************************/

	private void setOfferToIgnoreList(final Offer offer) {
		manager.ignoreOffer(offer);
	}

	private void deleteOffer(final int index, final Offer offer) {
		manager.removeOwnOffer(offer);
		getOfferList(index);
	}

	/***************************************************************
	 * OfferAdapter
	 ***************************************************************/

	public class OfferAdapter implements ListAdapter {

		private final List<Offer> myArr;
		private final Context context;

		public OfferAdapter(final Context c, final List<Offer> myArrList) {
			context = c;
			myArr = myArrList;
		}

		@Override
		public int getCount() {
			return myArr.size();
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
		public View getView(final int position, View convertView,
				final ViewGroup parent) {

			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.sb_offerlist_frag, null);
			}

			// TITEL
			final TextView title = (TextView) convertView
					.findViewById(R.id.sb_offerlist_titel);
			final Offer offer = myArr.get(position);
			title.setText(offer.getHeader());

			title.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					final Intent intent = new Intent(BlackBoardActivity.this,
							OfferActivity.class);
					final Bundle b = new Bundle();
					b.putString("TITLE", offer.getHeader());
					b.putString("DATE", offer.getDateOfCreation() + "");
					b.putString("TEXT", offer.getDescription());
					b.putString("CONTAKT", offer.getContact());
					b.putString("IMAGE", offer.getImageId() + "");
					intent.putExtras(b);

					startActivity(intent);
				}
			});

			// Button Rubbish
			final ImageButton rubbishBtn = (ImageButton) convertView
					.findViewById(R.id.sb_offerlist_rubbish_btn);

			rubbishBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					setOfferToIgnoreList(offer);
				}

			});

			// Button Delete
			final ImageButton deleteBtn = (ImageButton) convertView
					.findViewById(R.id.sb_offerlist_delete_btn);
			if (!ownOffers.contains(offer)) {
				deleteBtn.setVisibility(View.GONE);
			}
			deleteBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {

					// TODO: Nachricht löschen
					// TODO: prüfen ob eigene nachricht
					deleteOffer(position, offer);
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
