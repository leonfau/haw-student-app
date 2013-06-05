package de.minimum.hawapp.app.pub.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jcraft.jsch.SftpException;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.pub.beans.FTPFile;
import de.minimum.hawapp.app.pub.management.PubManager;
import de.minimum.hawapp.app.util.InternetConnectionUtil;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PubActivity extends Activity {

	PubManager manager;
	
    private List<FTPFile> listItems=new ArrayList<FTPFile>();
    private ArrayAdapter<FTPFile> adapter;
    private ListView lv;
    
    private FTPFile currentDirectory;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub);
		manager = ManagerFactory.getManager(PubManager.class);
		
		adapter=new ArrayAdapter<FTPFile>(this,
	            android.R.layout.simple_list_item_1,
	            listItems);
		lv =(ListView) findViewById(R.id.publist);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int pos,
					long id) {
					handleFile(adapter.getItem(pos));
			}
		});
		lv.setAdapter(adapter);
		currentDirectory = manager.getRootDirectory();
		readCurrentDirectory();
	}
	
	private void readCurrentDirectory() {
		new AsyncTask<Void, Void, Void>() {

			List<FTPFile> filesFromDir;
			
			@Override
			protected void onPostExecute(Void arg0) {
				super.onPostExecute(arg0);
				updateList(filesFromDir);
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
					try {
						filesFromDir = manager.readDirectory(currentDirectory);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SftpException e) {
						e.printStackTrace();
					}
					
				} else {
				}
				return null;
			}
		}.execute();
	}
	
	private void updateList(List<FTPFile> files) {
		listItems.clear();
		for(FTPFile file : files) {
			listItems.add(file);
		}
		Collections.sort(listItems);
		if (!currentDirectory.equals(manager.getRootDirectory())) listItems.add(0, manager.getUpperDirectory(currentDirectory));
		adapter.notifyDataSetChanged();
		lv.setSelectionAfterHeaderView();
	}
	
	private void downloadAndRunFile(final FTPFile file) {
		new AsyncTask<Void, Void, Void>() {

			FTPFile downloaded;
			
			@Override
			protected void onPostExecute(Void arg0) {
				super.onPostExecute(arg0);
				runFileFromActivity(downloaded);
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
					try {
						downloaded = manager.downloadFile(file);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SftpException e) {
						e.printStackTrace();
					}
					
				} else {
				}
				return null;
			}
		}.execute();
	}
	
	private void runFileFromActivity(FTPFile downloaded) {
		manager.runFile(downloaded, this);
	}
	
	private void handleFile(FTPFile clickedFile) {
		if (clickedFile.isDirectory()) {
			currentDirectory = clickedFile;
			readCurrentDirectory();
		} else {
			downloadAndRunFile(clickedFile);
		}
	}
}
