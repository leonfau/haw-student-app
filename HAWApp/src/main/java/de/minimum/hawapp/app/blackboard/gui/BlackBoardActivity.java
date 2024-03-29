package de.minimum.hawapp.app.blackboard.gui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
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
	protected static final String CATEGORY_SEARCH_RESULTS = "Suchergebnisse";
	private final String NO_INTERNET_CONNECTION_MSG = "Keine Internetverbindung vorhanden";
	private final String NO_DATA_FROM_SERVICE = "keine Nachrichten vorhanden oder Service nicht verfügbar";

	private String notificationOnError;

	private final BlackboardManager manager = ManagerFactory
			.getManager(BlackboardManager.class);
	private List<String> allCategoryNamesCached;
	private List<String> allCategoryNamesDB;
	// private List<Offer> ownOffers;

	private List<Offer> offerListOfCategory;
	private List<Offer> offerListOfCategoryCached;

	private boolean dialogShown;
	// Views
	private Spinner categorySpinner;
	private ListView offerListView;
	private List<Offer> searchResult;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_main);

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
			showDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
		}
		getCategoryNames();
	}

	private void display() {
		if (allCategoryNamesDB == null) {
			Toast.makeText(getApplicationContext(), notificationOnError,
					Toast.LENGTH_SHORT).show();
			allCategoryNamesDB = allCategoryNamesCached;

		}
		categorySpinner = (Spinner) findViewById(R.id.sb_categorie_spinner);

		offerListView = (ListView) findViewById(R.id.sb_offerList);

		final List<String> categoryNames = new ArrayList<String>();
		categoryNames.add(BlackBoardActivity.CATEGORY_ALL);
		categoryNames.add(BlackBoardActivity.CATEGORY_IGNORE);
		categoryNames.add(BlackBoardActivity.CATEGORY_OWN);
		categoryNames.add(BlackBoardActivity.CATEGORY_SEARCH_RESULTS);
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

		searchResult = new ArrayList<Offer>();
		final EditText searchET = (EditText) findViewById(R.id.sb_search_edittext);
		final ImageButton searchBtn = (ImageButton) findViewById(R.id.sb_search_btn);
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				searchOffer(searchET.getText().toString());
				searchET.setText("");
			}

		});
	}

	private void searchOffer(final String searchString) {
		new AsyncTask<Void, Void, List<Offer>>() {

			@Override
			protected void onPostExecute(final List<Offer> result) {
				if (result != null) {
					searchResult.clear();
					searchResult.addAll(result);
					categorySpinner.setSelection(3);
					setOfferListView();
				}
			}

			@Override
			protected List<Offer> doInBackground(final Void... arg0) {

				return manager.searchOffers(BlackBoardActivity.this,
						searchString);
			}

		}.execute();
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
					dismissDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
					removeDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
					dialogShown = false;
				}
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				if (InternetConnectionUtil
						.hasInternetConnection(getApplicationContext())) {
					allCategoryNamesCached = allCategoryNamesDB;
					allCategoryNamesDB = manager
							.getAllCategoryNames(BlackBoardActivity.this);
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
				this, R.layout.sb_simple_spinner_item, categoryNames);
		categorySpinner.setAdapter(categorieAdapter);
		categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(final AdapterView<?> adapter,
					final View view, final int index, final long arg3) {
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

		if (offerListOfCategoryCached == null) {
			dialogShown = true;
			showDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
		}

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPostExecute(final Void arg0) {
				super.onPostExecute(arg0);
				setOfferListView();
				if (dialogShown) {
					dismissDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
					removeDialog(BlackBoardActivity.DIALOG_DOWNLOAD_JSON_PROGRESS);
					dialogShown = false;
				}
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				if (InternetConnectionUtil
						.hasInternetConnection(getApplicationContext())) {
					offerListOfCategoryCached = offerListOfCategory;
					if (categoryName.equals(BlackBoardActivity.CATEGORY_ALL)) {
						// Alle Nachrichten
						offerListOfCategory = manager
								.getAllOffers(BlackBoardActivity.this);
					} else if (categoryName
							.equals(BlackBoardActivity.CATEGORY_IGNORE)) {
						// Papierkorb
						offerListOfCategory = manager
								.getIgnoredOffers(BlackBoardActivity.this);
					} else if (categoryName
							.equals(BlackBoardActivity.CATEGORY_OWN)) {
						// Meine Nachrichten
						offerListOfCategory = manager
								.getAllOwnOffers(BlackBoardActivity.this);
					} else if (categoryName
							.equals(BlackBoardActivity.CATEGORY_SEARCH_RESULTS)) {
						offerListOfCategory.clear();
						offerListOfCategory.addAll(searchResult);

					} else {
						Log.v("SB OfferList", "sonstige Nachrichten ");
						final Category category = manager.getCategory(
								BlackBoardActivity.this, categoryName);
						offerListOfCategory = category.getAllOffers();
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

	private void setOfferListView() {
		if (offerListOfCategory == null) {
			Toast.makeText(getApplicationContext(), notificationOnError,
					Toast.LENGTH_SHORT).show();
			offerListOfCategory = offerListOfCategoryCached;
		}
		Collections
				.sort(offerListOfCategory, OfferDateComperator.getInstance());
		offerListView.setAdapter(new OfferAdapter(BlackBoardActivity.this,
				offerListOfCategory));
	}

	/***************************************************************
	 * OnButtonClick
	 ***************************************************************/

	private void setOfferToIgnoreList(final Offer offer) {
		if (manager.ignoreOffer(this, offer)) {
			offerListOfCategory.remove(offer);
			setOfferListView();
		}
	}

	private void deleteOffer(final Offer offer) {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPostExecute(final Boolean result) {
				if (result) {
					offerListOfCategory.remove(offer);
					setOfferListView();
				}
			};

			@Override
			protected Boolean doInBackground(final Void... arg0) {
				return manager.removeOwnOffer(BlackBoardActivity.this, offer);
			}

		}.execute();
	}

	private void revertOffer(final Offer offer) {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPostExecute(final Boolean result) {
				if (result) {
					offerListOfCategory.remove(offer);
					setOfferListView();
				}
			};

			@Override
			protected Boolean doInBackground(final Void... arg0) {
				return manager.unignoreOffer(BlackBoardActivity.this, offer);
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
					final Date date = offer.getDateOfCreation();

					b.putString("TITLE", offer.getHeader());
					b.putString("DATE", (DateFormat.getDateInstance(
							DateFormat.FULL, Locale.GERMAN)).format(date
							.getDate()));
					b.putString("TEXT", offer.getDescription());
					b.putString("CONTAKT", offer.getContact());
					b.putLong(
							"IMAGE",
							offer.getImageId() == null ? -1 : offer
									.getImageId());
					intent.putExtras(b);

					startActivity(intent);
				}
			});

			// Button Rubbish
			final ImageButton rubbishBtn = (ImageButton) convertView
					.findViewById(R.id.sb_offerlist_rubbish_btn);
			if (categorySpinner.getSelectedItem().equals(
					BlackBoardActivity.CATEGORY_IGNORE)
					|| categorySpinner.getSelectedItem().equals(
							BlackBoardActivity.CATEGORY_OWN)) {
				rubbishBtn.setVisibility(View.INVISIBLE);
			}

			rubbishBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					setOfferToIgnoreList(offer);
				}

			});

			// Button Delete
			final ImageButton deleteBtn = (ImageButton) convertView
					.findViewById(R.id.sb_offerlist_delete_btn);
			if (!categorySpinner.getSelectedItem().equals(
					BlackBoardActivity.CATEGORY_OWN)) {
				deleteBtn.setVisibility(View.INVISIBLE);
			}
			final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialog,
						final int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						deleteOffer(offer);
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			};

			deleteBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					final AlertDialog.Builder builder = new AlertDialog.Builder(
							BlackBoardActivity.this);
					builder.setMessage(
							"Bist du sicher das du diese Nachricht löschen willst?")
							.setPositiveButton("Ja", dialogClickListener)
							.setNegativeButton("Nein", dialogClickListener)
							.show();
				}

			});

			// Button Revert
			final ImageButton revertBtn = (ImageButton) convertView
					.findViewById(R.id.sb_offerlist_revert_btn);
			if (!categorySpinner.getSelectedItem().equals(
					BlackBoardActivity.CATEGORY_IGNORE)) {
				revertBtn.setVisibility(View.INVISIBLE);
			}
			revertBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					revertOffer(offer);
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

	private static class OfferDateComperator implements Comparator<Offer> {

		private static final OfferDateComperator instance = new OfferDateComperator();

		private OfferDateComperator() {
		}

		@Override
		public int compare(final Offer lhs, final Offer rhs) {
			int res = lhs.getDateOfCreation()
					.compareTo(rhs.getDateOfCreation());
			if (res == 0) {
				res = lhs.getHeader().compareTo(rhs.getHeader());
			}
			return res;
		}

		public static OfferDateComperator getInstance() {
			return OfferDateComperator.instance;
		}

	}
}
