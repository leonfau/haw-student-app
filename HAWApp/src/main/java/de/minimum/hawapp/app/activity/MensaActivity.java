package de.minimum.hawapp.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.mensa.beans.DayPlan;
import de.minimum.hawapp.app.mensa.beans.Meal;
import de.minimum.hawapp.app.mensa.management.MensaManager;
import de.minimum.hawapp.app.util.InternetConnectionUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MensaActivity extends NetworkingActivity {

	private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
	
	private final String NO_INTERNET_CONNECTION_MSG = "Keine Internetverbindung vorhanden";
	private final String NO_DATA_FROM_SERVICE = "Heute kein Essen vorhanden oder Service nicht verfügbar";
	
    ArrayList<HashMap<String, Object>> myArrList;
    
	private DayPlan mealsToday; //Wird vom Restservice befüllt beim get()
	private MensaManager manager;
	private ListView listView;
	
	private String notificationOnError;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mensa);
		manager = ManagerFactory.getManager(MensaManager.class);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog mProgressDialog;
		switch (id) {
		case DIALOG_DOWNLOAD_JSON_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Downloading.....");
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
		showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
		get();
	}
	
	@Override
	void put() {
		//TODO send rating
	}
	
	@Override
	void get() {
		new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void arg0) {
               super.onPostExecute(arg0);
               display();
               dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
   			   removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            }

			@Override
			protected Void doInBackground(Void... arg0) {
				if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
					mealsToday = manager.getPlanForToday();
					notificationOnError = NO_DATA_FROM_SERVICE;
				} else {
					mealsToday = null;
					notificationOnError = NO_INTERNET_CONNECTION_MSG;
				}
				return null;
			}
        }.execute();
	}
	
	@Override
	void display() {
		listView = (ListView)findViewById(R.id.mensaList);
		myArrList = new ArrayList<HashMap<String,Object>>();
		TextView date = (TextView)findViewById(R.id.date);
		date.setText(manager.getTodayAsStringRepresantation());
		if (mealsToday != null) {
			for (Meal meal : mealsToday.getMeals()) {
				HashMap<String, Object> e = new HashMap<String, Object>();
				e.put("BESCHREIBUNG", meal.getDescription());
				e.put("RATING", meal.getRating().getPosRatingInPercent());	
				String price = String.valueOf(meal.getStudentPrice() + "€ / " + String.valueOf(meal.getOthersPrice() +"€")).replace('.', ',');
				e.put("PREIS", price);
				myArrList.add(e);
			}
		} else {
			Toast.makeText(getApplicationContext(), notificationOnError, Toast.LENGTH_SHORT).show();
		}

		listView.setAdapter(new MealAdapter(MensaActivity.this,myArrList));
	}
	
//	private void updateNewRatingPoint(int position,String newRatingPoint){		
//	    View v = listView.getChildAt(position - listView.getFirstVisiblePosition()); 
//	    // Update RatingBar
//	    RatingBar rating = (RatingBar)v.findViewById(R.id.ColratingBar);
//	    rating.setEnabled(true); // Enabled
//	    rating.setRating(Float.valueOf(newRatingPoint));
//	    rating.setEnabled(false); // False
//	}

}