package de.minimum.hawapp.app.calendar.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarCategoriesActivity extends ListActivity {
    public final static String LECTURE_UUID = "calendar_lecture_uuid";
    public final static String CATEGORY_UUID = "calendar_category_uuid";
    public final static String APPOINTMENT_UUID = "calendar_appointment_uuid";
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private ArrayAdapter<Category> categoryAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,
                        new ArrayList<Category>());
        setContentView(R.layout.calendar_main);
        setListAdapter(categoryAdapter);
        showCategories();

        final Button b1 = (Button)findViewById(R.id.cal_btn_subLectures);
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                viewSubscribedLectures();
            }
        });

    }

    protected void viewSubscribedLectures() {
        final Intent intent = new Intent(this, CalendarSubscribedLectureActivity.class);
        startActivity(intent);
    }

    private void showCategories() {
        new AsyncTask<Void, Void, Void>() {
            private List<Category> categories;

            @Override
            protected void onPostExecute(final Void arg0) {
                categoryAdapter.clear();
                categoryAdapter.addAll(categories);
                categoryAdapter.sort(new Comparator<Category>() {

                    @Override
                    public int compare(final Category lhs, final Category rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                setListAdapter(categoryAdapter);
                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                categories = calManager.getCategories();
                return null;
            }
        }.execute();
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Category lecture = categoryAdapter.getItem(position);
        final Intent intent = new Intent(this, CalendarLecturesActivity.class);
        intent.putExtra(CATEGORY_UUID, lecture.getUuid());
        startActivity(intent);

        super.onListItemClick(l, v, position, id);

    }
}
