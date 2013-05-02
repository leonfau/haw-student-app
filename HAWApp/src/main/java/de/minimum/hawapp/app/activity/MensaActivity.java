package de.minimum.hawapp.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.mensa.beans.DayPlan;
import de.minimum.hawapp.app.mensa.beans.Meal;
import de.minimum.hawapp.app.mensa.management.MensaManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

public class MensaActivity extends NetworkingActivity {
    private static final String BESCHREIBUNG = "BESCHREIBUNG";
    private static final String PREIS = "PREIS";
    private static final String RATING = "RATING";
	private static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    ArrayList<HashMap<String, Object>> myArrList;
	private ArrayList<HashMap<String, String>>  mylist;
    
	DayPlan mealsToday; //Wird vom Restservice befüllt beim get()
	MensaManager manager;
	private ListView listView;
	
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
				mealsToday = manager.getPlanForToday();
				return null;
			}
        }.execute();
	}
	
	@Override
	void display() {
		listView = (ListView)findViewById(R.id.mensaList);
		myArrList = new ArrayList<HashMap<String,Object>>();
		

		
		for (Meal meal : mealsToday.getMeals()) {
			HashMap<String, Object> e = new HashMap<String, Object>();
			e.put(BESCHREIBUNG, meal.getDescription());
			e.put(RATING, meal.getRating().getPosRatingInPercent());	
			String price = String.valueOf(meal.getStudentPrice() + "€ / " + String.valueOf(meal.getOthersPrice() +"€")).replace('.', ',');
			e.put(PREIS, price);
			myArrList.add(e);
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