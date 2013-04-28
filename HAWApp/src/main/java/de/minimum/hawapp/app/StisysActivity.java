package de.minimum.hawapp.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;
import de.minimum.hawapp.app.stisys.StisysImpl;

public class StisysActivity extends ExpandableListActivity {
    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";

    private ExpandableListAdapter mAdapter;

    private static String cookie = "";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();

        final LoginTask login = new LoginTask();
        login.execute(intent.getStringExtra("login"), intent.getStringExtra("password"));

        final List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        final List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for(int i = 0; i < 6; i++) {
            final Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, "B-AI " + (i + 1));
            // curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" :
            // "This group is odd");

            final List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for(int j = 0; j < 5; j++) {
                final Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, "Praktikum Graphentheoretische Konzepte und Algorithmen (GKAP)");
                curChildMap.put(IS_EVEN, "18.01.2013 Klauck (KLC) 		Ergebnis: pvl");
            }
            childData.add(children);
        }

        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(this, groupData, R.layout.simple_expandable_list_item_1,
                        new String[] { NAME, IS_EVEN }, new int[] { android.R.id.text1, android.R.id.text2 },
                        childData, R.layout.simple_expandable_list_item_2, new String[] { NAME, IS_EVEN }, new int[] {
                                        android.R.id.text1, android.R.id.text2 });
        setListAdapter(mAdapter);
    }

    class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(final String... params) {
            final String login = params[0];
            Log.i("StisysActivity LoginTask", "Login: " + login);
            final String password = params[1];
            Log.i("StisysActivity LoginTask", "Password: " + password);
            String cookie;

            final StisysImpl stisys = new StisysImpl();
            cookie = stisys.login(login, password);

            return cookie;
        }

        @Override
        protected void onPostExecute(final String result) {
            cookie = result;
            System.out.println(cookie);
        }
    }

}