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
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.context.ManagerFactory;

public class CalendarLecturesActivity extends ListActivity {
    private final CalendarManager calManager = ManagerFactory.getManager(CalendarManager.class);
    private ArrayAdapter<Lecture> lectureAdapter;
    private Category actualCategory;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lectureAdapter = new ArrayAdapter<Lecture>(this, android.R.layout.simple_list_item_1, new ArrayList<Lecture>());
        setContentView(R.layout.calendar_main);
        setListAdapter(lectureAdapter);
        final String categoryUUID = getIntent().getExtras().getString(CalendarCategoriesActivity.CATEGORY_UUID);
        actualCategory = calManager.getCategory(categoryUUID);

        showLectures(actualCategory);

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

    private void showLectures(final Category category) {

        new AsyncTask<Category, Void, Void>() {
            List<Lecture> lectures;

            @Override
            protected void onPostExecute(final Void arg0) {
                lectureAdapter.clear();
                lectureAdapter.addAll(lectures);
                lectureAdapter.sort(new Comparator<Lecture>() {

                    @Override
                    public int compare(final Lecture lhs, final Lecture rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                setListAdapter(lectureAdapter);

                super.onPostExecute(arg0);
            }

            @Override
            protected Void doInBackground(final Category... params) {
                actualCategory = params[0];
                lectures = calManager.getLectures(actualCategory);
                return null;
            }
        }.execute(category);
    }

    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Lecture lecture = lectureAdapter.getItem(position);
        final Intent intent = new Intent(this, CalendarLectureActivity.class);
        intent.putExtra(CalendarCategoriesActivity.LECTURE_UUID, lecture.getUuid());
        startActivity(intent);

        super.onListItemClick(l, v, position, id);

    }
}
