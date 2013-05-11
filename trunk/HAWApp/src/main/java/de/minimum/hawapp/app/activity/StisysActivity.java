package de.minimum.hawapp.app.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.stisys.Result;
import de.minimum.hawapp.app.stisys.StisysImpl;

public class StisysActivity extends ExpandableListActivity {
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";

    private ExpandableListAdapter mAdapter;

    protected static String login = "";
    protected static String password = "";
    private static Map<Integer, List<Result>> results;
    public static final int DIALOG_DOWNLOAD_INFORMATION_PROGRESS = 0;

    @Override
    protected Dialog onCreateDialog(final int id) {
        ProgressDialog mProgressDialog;
        switch(id) {
            case DIALOG_DOWNLOAD_INFORMATION_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Loading Information...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
        final ParseTask parseTask = new ParseTask();
        parseTask.execute(login, password);
    }

    @Override
    protected void onResume() {
        super.onResume();
    };

    private void generateStisysUI() {
        final List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        final List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

        final Set<Integer> resultKeySet = results.keySet();
        final List<Integer> resultKeyList = new ArrayList<Integer>(resultKeySet);
        Collections.sort(resultKeyList);

        for(final Integer i : resultKeyList) {
            final Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, "B-AI " + i);

            final List<Result> listResult = results.get(i);
            final List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for(final Result r : listResult) {
                final Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, r.getName());
                curChildMap.put(DESCRIPTION, r.getDate() + " " + r.getProf() + " " + r.getErgebnis());
            }
            childData.add(children);
        }

        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(this, groupData, R.layout.activity_mensa_list_item_1,
                        new String[] { NAME, DESCRIPTION }, new int[] { android.R.id.text1, android.R.id.text2 },
                        childData, R.layout.activity_mensa_list_item_2, new String[] { NAME, DESCRIPTION },
                        new int[] { android.R.id.text1, android.R.id.text2 });
        setListAdapter(mAdapter);
    }

    class ParseTask extends AsyncTask<String, Void, Map<Integer, List<Result>>> {
        @Override
        protected Map<Integer, List<Result>> doInBackground(final String... params) {
            final String login = params[0];
            // Log.i("StisysActivity LoginTask", "Login: " + login);
            final String password = params[1];
            // Log.i("StisysActivity Lo

            final StisysImpl stisys = new StisysImpl();
            // cookie = stisys.getReportPage(cookie);

            final Map<Integer, List<Result>> ret = stisys.parseReportPage(stisys.getReportPage(stisys.login(login,
                            password)));
            return ret;
        }

        @Override
        protected void onPostExecute(final Map<Integer, List<Result>> result) {
            results = result;
            generateStisysUI();
            dismissDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
            removeDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
        }
    }

}