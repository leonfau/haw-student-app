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
import de.minimum.hawapp.app.blackboard.api.Offer;
import de.minimum.hawapp.app.blackboard.beans.OfferBean;
import de.minimum.hawapp.app.context.ManagerFactory;

public class SchwarzesBrettActivity extends Activity {

	private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;

	protected static final String CATEGORY_ALL = "Alles";

	protected static final String CATEGORY_RUBBISH = "Papierkorb";

	protected static final String CATEGORY_OWN = "Meins";

	BlackboardManager manager = ManagerFactory
			.getManager(BlackboardManager.class);

	private Spinner categorySpinner;

	private List<String> allCategoryNames;

	private ListView offerListView;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_main);

		categorySpinner = (Spinner) findViewById(R.id.sb_categorie_spinner);
		offerListView = (ListView) findViewById(R.id.sb_offerList);

		final Button newOfferBtn = (Button) findViewById(R.id.sb_newOffer_btn);
		newOfferBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(getBaseContext(),
						NewOfferActivity.class);
				final Bundle b = new Bundle();
				final ArrayList<String> newCNames = new ArrayList<String>();
				newCNames.addAll(allCategoryNames);
				newCNames.remove(CATEGORY_ALL);
				newCNames.remove(CATEGORY_RUBBISH);
				newCNames.remove(CATEGORY_OWN);

				b.putStringArrayList("CATEGORY", newCNames);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		getCategory();
	}

	private void getCategory() {
		showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
		new AsyncTask<Void, Void, Void>() {

			private final List<String> categoryNames = new ArrayList<String>();
			private boolean successful = false;
			private List<String> newNames;

			@Override
			protected void onPostExecute(final Void arg0) {
				dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
				removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
				categoryNames.clear();
				if (successful) {
					categoryNames.add(CATEGORY_ALL);
					categoryNames.add(CATEGORY_RUBBISH);
					categoryNames.add(CATEGORY_OWN);
					categoryNames.addAll(newNames);
					setCategoryAdapter(categoryNames);
				} else {
					showToastSomethingFailed();
				}

				super.onPostExecute(arg0);
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				try {
					newNames = manager.getAllCategoryNames();
					successful = !newNames.isEmpty();
				} catch (final Throwable e) {
					e.printStackTrace();
					successful = false;
				}
				return null;
			}

		}.execute();
	}

	private void setCategoryAdapter(final List<String> names) {
		allCategoryNames = names;
		final ArrayAdapter<String> categorieAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, allCategoryNames);
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

	private void getOfferList(final int index) {

		showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
		new AsyncTask<Void, Void, Void>() {

			private boolean successful;
			private List<Offer> offer = new ArrayList<Offer>();

			@Override
			protected void onPostExecute(final Void arg0) {
				dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
				removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
				if (successful) {
					setOfferList(offer);
				} else {
					showToastSomethingFailed();
				}

				super.onPostExecute(arg0);
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				try {
					switch (index) {
					case 0:
						// Alle Nachrichten
						Log.v("SB OfferList", "alle Nachrichten");
						offer = manager.getAllOffers();
						break;
					case 1:
						// Papierkorb
						Log.v("SB OfferList", "Papierkorb");
						offer = manager.getIgnoredOffers();
						break;

					case 2:
						// Meine Nachrichten
						Log.v("SB OfferList", "Meine Nachrichten");
						offer = manager.getAllOwnOffers();
						break;

					default:
						// Log.v("SB OfferList", "sonstige Nachrichten");
						// final String aktCategoryName = allCategoryNames
						// .get(index);
						// final Category category = manager
						// .getCategory(aktCategoryName);
						// offer = category.getAllOffers();
						final Offer o = new OfferBean("Test",
								"das ist ein Test", "ich", "Angebote");
						offer.add(o);
						break;
					}
					successful = !offer.isEmpty();

				} catch (final Throwable e) {
					e.printStackTrace();
					successful = false;
				}
				return null;
			}

		}.execute();
	}

	private void setOfferList(final List<Offer> offerList) {
		offerListView.setAdapter(new OfferAdapter(SchwarzesBrettActivity.this,
				offerList));
	}

	private void showToastSomethingFailed() {
		Toast.makeText(this,
				"Es gibt keine Einträge oder ein Problem ist aufgetreten",
				Toast.LENGTH_LONG).show();
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
					final Intent intent = new Intent(
							SchwarzesBrettActivity.this, OfferActivity.class);
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
					// TODO: Nachricht in die Rubbish Liste verschieben
					Log.v("BB", title.getText() + " rubbish");

				}
			});

			// Button Delete
			final ImageButton deleteBtn = (ImageButton) convertView
					.findViewById(R.id.sb_offerlist_delete_btn);
			deleteBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {

					// TODO: Nachricht löschen
					// TODO: prüfen ob eigene nachricht
					Log.v("BB", title.getText() + " delete");

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
