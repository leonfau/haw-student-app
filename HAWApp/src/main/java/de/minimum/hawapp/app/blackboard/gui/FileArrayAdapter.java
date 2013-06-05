package de.minimum.hawapp.app.blackboard.gui;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.minimum.hawapp.app.R;

public class FileArrayAdapter extends ArrayAdapter<Item> {

	private final Context c;
	private final int id;
	private final List<Item> items;

	public FileArrayAdapter(final Context context,
			final int textViewResourceId, final List<Item> objects) {
		super(context, textViewResourceId, objects);
		c = context;
		id = textViewResourceId;
		items = objects;
	}

	@Override
	public Item getItem(final int i) {
		return items.get(i);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			final LayoutInflater vi = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(id, null);
		}

		/* create a new view of my layout and inflate it in the row */
		// convertView = ( RelativeLayout ) inflater.inflate( resource, null );

		final Item o = items.get(position);
		if (o != null) {
			final TextView t1 = (TextView) v.findViewById(R.id.TextView01);
			final TextView t2 = (TextView) v.findViewById(R.id.TextView02);
			final TextView t3 = (TextView) v.findViewById(R.id.TextViewDate);
			/* Take the ImageView from layout and set the city's image */
			final ImageView imageCity = (ImageView) v
					.findViewById(R.id.fd_Icon1);
			final String uri = "drawable/" + o.getImage();
			final int imageResource = c.getResources().getIdentifier(uri, null,
					c.getPackageName());

			final Drawable image = c.getResources().getDrawable(imageResource);
			imageCity.setImageDrawable(image);

			if (t1 != null) {
				t1.setText(o.getName());
			}
			if (t2 != null) {
				t2.setText(o.getData());
			}
			if (t3 != null) {
				t3.setText(o.getDate());
			}
		}
		return v;
	}
}