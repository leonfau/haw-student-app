package de.minimum.hawapp.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.googlecode.androidannotations.annotations.rest.Post;

import de.minimum.hawapp.app.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_mensa_row, null);
		}
		
		TextView txtView = (TextView) convertView.findViewById(R.id.beschreibung);
		txtView.setPadding(5, 0, 0, 0);
		txtView.setText(myArr.get(position).get("BESCHREIBUNG").toString());
		txtView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//display in short period of time
				Toast.makeText(context,"coming soon", Toast.LENGTH_LONG).show();
			}
		});
		
		RatingBar rating = (RatingBar) convertView.findViewById(R.id.ColratingBar);
		rating.setEnabled(false);
		rating.setMax(5);
		//Rating.setRating(Integer.parseInt(""+myArr.get(position).get("RATING")));
		rating.setRating(Float.parseFloat(myArr.get(position).get("RATING").toString()));
		
		
		
		TextView txtViewPreis = (TextView) convertView.findViewById(R.id.preis);
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
