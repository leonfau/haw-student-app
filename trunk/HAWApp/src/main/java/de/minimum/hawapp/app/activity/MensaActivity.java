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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MensaActivity extends NetworkingActivity {
    private static final String NAME = "NAME";
    private static final String BESCHREIBUNG = "DESCRIPTION";
    private static final String PREIS = "PREIS";

	private ArrayList<HashMap<String, String>>  mylist;
    
	DayPlan mealsToday; //Wird vom Restservice befüllt beim get()
	MensaManager manager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
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
               display();
               super.onPostExecute(arg0);
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
		ListView list = (ListView) findViewById(R.id.SCHEDULE);
		mylist = new ArrayList<HashMap<String, String>>();
		if (mealsToday != null) {
    	for (Meal meal : mealsToday.getMeals()) {
    		HashMap<String, String> map = new HashMap<String, String>();
			map.put(NAME, "Name muss raus");
			map.put(BESCHREIBUNG, meal.getDescription());
			String price = String.valueOf(meal.getStudentPrice() + "€ / " + String.valueOf(meal.getOthersPrice() +"€")).replace('.', ',');
			map.put(PREIS, price);
			mylist.add(map);	
    	}
		ListAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.row, new String[] {
				NAME, BESCHREIBUNG, PREIS }, new int[] { R.id.name,
				R.id.beschreibung, R.id.preis});
		list.setAdapter(mSchedule);
		}
	}

}