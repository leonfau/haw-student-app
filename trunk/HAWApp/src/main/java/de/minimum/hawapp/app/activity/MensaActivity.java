package de.minimum.hawapp.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.mensa.beans.DayPlan;
import de.minimum.hawapp.app.mensa.beans.Meal;
import de.minimum.hawapp.app.mensa.management.MensaManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

public class MensaActivity extends NetworkingActivity {
    private static final String BESCHREIBUNG = "DESCRIPTION";
    private static final String PREIS = "PREIS";
    private static final String RATING = "RATING";
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
	public void onResume() {
		super.onResume();
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
            }

			@Override
			protected Void doInBackground(Void... arg0) {
//				mealsToday = manager.getPlanForToday();
				return null;
			}
        }.execute();
	}
	
	@Override
	void display() {
		listView = (ListView)findViewById(R.id.mensaList);
		myArrList = new ArrayList<HashMap<String,Object>>();
		
//GUI TEST	
		for (int i = 0; i < 25; i++) {
			HashMap<String, Object> e = new HashMap<String, Object>();
			e.put("BESCHREIBUNG", "Broccoliauflauf mit gekochtem Schinken und Penne (1,2,3,4,14,20,22) Napoli Soße (19) "+i);
			e.put("RATING", (i % 5)+"");		
			e.put("PREIS", "10.50 € / 20.10 €");
			myArrList.add(e);
		}
		
//		for (Meal meal : mealsToday.getMeals()) {
//			HashMap<String, Object> e = new HashMap<String, Object>();
//			e.put(BESCHREIBUNG, meal.getDescription());
//			e.put(RATING, meal.getRating()+"");	
//			String price = String.valueOf(meal.getStudentPrice() + "€ / " + String.valueOf(meal.getOthersPrice() +"€")).replace('.', ',');
//			e.put(PREIS, price);
//			myArrList.add(e);
//		}

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