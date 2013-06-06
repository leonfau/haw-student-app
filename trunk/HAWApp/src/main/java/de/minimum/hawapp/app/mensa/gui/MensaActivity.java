package de.minimum.hawapp.app.mensa.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.mensa.beans.DayPlan;
import de.minimum.hawapp.app.mensa.beans.Meal;
import de.minimum.hawapp.app.mensa.management.MensaManager;
import de.minimum.hawapp.app.util.InternetConnectionUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MensaActivity extends Activity {

	private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;

	private final String NO_INTERNET_CONNECTION_MSG = "Keine Internetverbindung vorhanden";
	private final String NO_DATA_FROM_SERVICE = "Heute kein Essen vorhanden oder Service nicht verfügbar";

	ArrayList<HashMap<String, Object>> myArrList;

	private DayPlan mealsToday; // Wird vom Restservice befüllt beim get()
	private DayPlan mealsCached;

	private MensaManager manager;
	private ListView listView;

	private String notificationOnError;
	private boolean dialogShown;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mensa);
		manager = ManagerFactory.getManager(MensaManager.class);
		myArrList = new ArrayList<HashMap<String, Object>>();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog mProgressDialog;
		switch (id) {
		case DIALOG_DOWNLOAD_JSON_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Lade Essen...");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mealsCached == null) {
			dialogShown = true;
			showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
		}
		getMeals();
	}


	void rate(UUID mealId, final int stars) {
		Meal mealToRate = null;
		for (Meal meal : mealsToday.getMeals()) {
			if (meal.getId().equals(mealId)) {
				mealToRate = meal;
			}
		}
		final Meal rated = mealToRate;
		new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void arg0) {
               super.onPostExecute(arg0);
               getMeals();
            }

			@Override
			protected Void doInBackground(Void... arg0) {
				if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
					manager.rate(rated, stars);
				}
				return null;
			}
        }.execute();
	}


	void getMeals() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPostExecute(Void arg0) {
				super.onPostExecute(arg0);
				display();
				if (dialogShown) {
					dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
					removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
					dialogShown = false;
				}

			}

			@Override
			protected Void doInBackground(Void... arg0) {
				if (InternetConnectionUtil
						.hasInternetConnection(getApplicationContext())) {
					mealsCached = mealsToday;
					mealsToday = manager.getPlanForToday();
					if (mealsCached == null)
						mealsCached = mealsToday;
					notificationOnError = NO_DATA_FROM_SERVICE;
				} else {
					mealsToday = null;
					notificationOnError = NO_INTERNET_CONNECTION_MSG;
				}
				return null;
			}
		}.execute();
	}

	void display() {
		TextView date = (TextView) findViewById(R.id.date);
		date.setText(manager.getTodayAsStringRepresantation());
		listView = (ListView) findViewById(R.id.mensaList);

		if (mealsToday == null) {
			Toast.makeText(getApplicationContext(), notificationOnError,
					Toast.LENGTH_SHORT).show();
			mealsToday = mealsCached;
		}
		if (mealsToday != null && !mealsToday.equals(mealsCached)) {
			myArrList = new ArrayList<HashMap<String, Object>>();
			for (Meal meal : mealsToday.getMeals()) {
				HashMap<String, Object> e = new HashMap<String, Object>();
				e.put("UUID", meal.getId());
				e.put("BESCHREIBUNG", meal.getDescription());
				e.put("RATING", meal.getRating().getPosRatingInPercent());
				String price = formatPrices(meal.getStudentPrice(),
						meal.getOthersPrice());
				e.put("PREIS", price);
				myArrList.add(e);
			}
		}
		listView.setAdapter(new MealAdapter(MensaActivity.this, myArrList));
	}

	private String formatPrices(double studentPrice, double otherPrice) {
		DecimalFormat df = new DecimalFormat("#0.00");

		StringBuilder prices = new StringBuilder();
		prices.append(df.format(studentPrice));
		prices.append("€ /");
		prices.append(df.format(otherPrice));
		prices.append("€");

		return prices.toString().replace('.', ',');
	}

	// private void updateNewRatingPoint(int position,String newRatingPoint){
	// View v = listView.getChildAt(position -
	// listView.getFirstVisiblePosition());
	// // Update RatingBar
	// RatingBar rating = (RatingBar)v.findViewById(R.id.ColratingBar);
	// rating.setEnabled(true); // Enabled
	// rating.setRating(Float.valueOf(newRatingPoint));
	// rating.setEnabled(false); // False
	// }

	public void showDialogVote(int position, String strMealBeschreibung, final UUID mealId) {
		final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
		final AlertDialog.Builder adb = new AlertDialog.Builder(this);
		final LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View Viewlayout = inflater.inflate(
				R.layout.activity_mensa_dialog,
				(ViewGroup) findViewById(R.id.layout_dialog));
		final RatingBar rating = (RatingBar) Viewlayout
				.findViewById(R.id.ratingBar); // ratingBar

		final TextView txtBeschreibung = (TextView) Viewlayout
				.findViewById(R.id.mealDescription);
		txtBeschreibung.setText(strMealBeschreibung);

		rating.setMax(5);
		rating.setNumStars(5);
		popDialog.setIcon(android.R.drawable.btn_star_big_on);
		popDialog.setTitle("Bewerten!");

		popDialog.setView(Viewlayout);

		// Button OK
		popDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						int stars =(int) rating.getRating();
						if (stars != 0) rate(mealId, stars);
						dialog.dismiss();
					}
				}).setNegativeButton("Abbrechen",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}

				});

		popDialog.create();
		popDialog.show();
	}

	public class MealAdapter implements ListAdapter {

		private ArrayList<HashMap<String, Object>> myArr;
		private Context context;

		public MealAdapter(Context c,
				ArrayList<HashMap<String, Object>> myArrList) {
			// TODO Auto-generated constructor stub
			this.context = c;
			this.myArr = myArrList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myArr.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public int getItemViewType(int arg0) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.activity_mensa_row,
						null);
			}

			TextView txtView = (TextView) convertView
					.findViewById(R.id.mealDescription);
			txtView.setPadding(5, 0, 0, 0);
			txtView.setText(myArr.get(position).get("BESCHREIBUNG").toString());
			final UUID id = UUID.fromString(myArr.get(position).get("UUID").toString());
			
			TableLayout tableLayout = (TableLayout) convertView.findViewById(R.id.tableLayout1); 
			tableLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// display in short period of time
					// Toast.makeText(context,"coming soon",
					// Toast.LENGTH_LONG).show();
					String strMealBeschreibung = myArr.get(position)
							.get("BESCHREIBUNG").toString();
					showDialogVote(position, strMealBeschreibung, id); // Click Show
																	// Dialog
																	// Vote

				}
			});
//			txtView.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// display in short period of time
//					// Toast.makeText(context,"coming soon",
//					// Toast.LENGTH_LONG).show();
//					String strMealBeschreibung = myArr.get(position)
//							.get("BESCHREIBUNG").toString();
//					showDialogVote(position, strMealBeschreibung, id); // Click Show
//																	// Dialog
//																	// Vote
//
//				}
//			});
			
			

			RatingBar rating = (RatingBar) convertView
					.findViewById(R.id.rating);
			rating.setEnabled(false);
			int numStars = 5;
			rating.setMax(numStars);
			rating.setStepSize((float) 0.5);

			float ratingAktuell = (float) (numStars
					* Double.parseDouble(String.valueOf(myArr.get(position)
							.get("RATING"))) / 100.0);
			rating.setRating(ratingAktuell);

			TextView txtViewPreis = (TextView) convertView
					.findViewById(R.id.price);
			txtViewPreis.setPadding(5, 0, 0, 0);
			txtViewPreis.setText(myArr.get(position).get("PREIS").toString());

			return convertView;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean areAllItemsEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEnabled(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}