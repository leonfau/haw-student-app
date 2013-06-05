package de.minimum.hawapp.app.pub.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jcraft.jsch.SftpException;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.pub.beans.FTPFile;
import de.minimum.hawapp.app.pub.management.PubManager;
import de.minimum.hawapp.app.util.InternetConnectionUtil;

public class PubActivity extends Activity {

    PubManager manager;

    private final List<FTPFile> listItems = new ArrayList<FTPFile>();
    private ArrayAdapter<FTPFile> adapter;
    private ListView lv;
    private String loading;

    private FTPFile currentDirectory;
    public static final int DIALOG_DOWNLOAD_INFORMATION_PROGRESS = 0;

    @Override
    protected Dialog onCreateDialog(final int id) {
        ProgressDialog mProgressDialog;
        switch(id) {
            case DIALOG_DOWNLOAD_INFORMATION_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(loading + " Pub...");
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
        setContentView(R.layout.activity_pub);

        manager = ManagerFactory.getManager(PubManager.class);

        adapter = new ArrayAdapter<FTPFile>(this, R.layout.activity_pub_list_item_1, listItems);
        lv = (ListView)findViewById(R.id.publist);
        loading = getString(R.string.loading);

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> av, final View view, final int pos, final long id) {
                showDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
                handleFile(adapter.getItem(pos));
            }
        });
        lv.setAdapter(adapter);
        currentDirectory = manager.getRootDirectory();
        showDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
        readCurrentDirectory();
    }

    private void readCurrentDirectory() {
        new AsyncTask<Void, Void, Void>() {

            List<FTPFile> filesFromDir;

            @Override
            protected void onPostExecute(final Void arg0) {
                super.onPostExecute(arg0);
                updateList(filesFromDir);
                dismissDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
                    try {
                        filesFromDir = manager.readDirectory(currentDirectory);
                    }
                    catch(final IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    catch(final SftpException e) {
                        e.printStackTrace();
                    }

                }
                else {}
                return null;
            }
        }.execute();
    }

    private void updateList(final List<FTPFile> files) {
        listItems.clear();
        for(final FTPFile file : files) {
            listItems.add(file);
        }
        Collections.sort(listItems);
        if (!currentDirectory.equals(manager.getRootDirectory())) {
            listItems.add(0, manager.getUpperDirectory(currentDirectory));
        }
        adapter.notifyDataSetChanged();
        lv.setSelectionAfterHeaderView();
    }

    private void downloadAndRunFile(final FTPFile file) {
        new AsyncTask<Void, Void, Void>() {

            FTPFile downloaded;

            @Override
            protected void onPostExecute(final Void arg0) {
                super.onPostExecute(arg0);
                runFileFromActivity(downloaded);
                dismissDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
            }

            @Override
            protected Void doInBackground(final Void... arg0) {
                if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
                    try {
                        downloaded = manager.downloadFile(file);
                    }
                    catch(final IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    catch(final SftpException e) {
                        e.printStackTrace();
                    }

                }
                else {}
                return null;
            }
        }.execute();
    }

    private void runFileFromActivity(final FTPFile downloaded) {
        manager.runFile(downloaded, this);
    }

    private void handleFile(final FTPFile clickedFile) {
        if (clickedFile.isDirectory()) {
            currentDirectory = clickedFile;
            readCurrentDirectory();
        }
        else {
            downloadAndRunFile(clickedFile);
        }
    }
}
