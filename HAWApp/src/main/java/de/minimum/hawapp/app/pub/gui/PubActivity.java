package de.minimum.hawapp.app.pub.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.SftpException;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.login.Login;
import de.minimum.hawapp.app.pub.beans.FTPFile;
import de.minimum.hawapp.app.pub.beans.UpperDirFTPFile;
import de.minimum.hawapp.app.pub.management.PubManager;
import de.minimum.hawapp.app.util.InternetConnectionUtil;

public class PubActivity extends Activity {

	PubManager manager;

	private final List<FTPFile> listItems = new ArrayList<FTPFile>();
	private List<FTPFile> favorites = new ArrayList<FTPFile>();
	private PubAdapter adapter;
	private ListView lv;
	private String loading;

	private FTPFile currentDirectory;
	public static final int DIALOG_DOWNLOAD_INFORMATION_PROGRESS = 0;

	private final String NO_INTERNET_CONNECTION_MSG = "Keine Internetverbindung vorhanden";
	private final String ALREADY_BUSY_MSG = "Es wird bereits etwas heruntergeladen";
	
	private boolean busyDownloadung = false;

	@Override
	protected Dialog onCreateDialog(final int id) {
		ProgressDialog mProgressDialog;
		switch (id) {
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
	public void onResume() {
		super.onResume();
		if (!Login.loggedIn()) {
			Login.login(Login.getEncryptedLogin(), Login.getEncryptedPassword());
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub);
		loading = getString(R.string.loading);

		manager = ManagerFactory.getManager(PubManager.class);
		favorites = manager.loadFavorites(getApplicationContext());
		adapter = new PubAdapter(this, R.layout.activity_pub_item_row,
				listItems);
		lv = (ListView) findViewById(R.id.publist);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> av, final View view,
					final int pos, final long id) {
				showDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
				handleFile(adapter.getItem(pos));
			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int pos, long id) {
				FTPFile clicked = adapter.getItem(pos);
				if (clicked.isDirectory()) {
					if (favorites.contains(clicked)) {
						manager.removeFavorite(clicked, getApplicationContext());
						if (listItems.contains(clicked)) listItems.remove(clicked);
					} else {
						manager.makeFavorite(clicked, getApplicationContext());
						listItems.add(0, clicked);
					}
					adapter.notifyDataSetChanged();
				}
				return true;
			}
		});

		lv.setAdapter(adapter);
		if (InternetConnectionUtil
				.hasInternetConnection(getApplicationContext())) {
			currentDirectory = manager.getRootDirectory();
			TextView pathView = (TextView) findViewById(R.id.path);
			pathView.setText(currentDirectory.getAbsolutePath()
					+ currentDirectory.getFileName());
			showDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
			readCurrentDirectory();
		} else {
			Toast.makeText(getApplicationContext(), NO_INTERNET_CONNECTION_MSG,
					Toast.LENGTH_SHORT).show();
		}

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
				if (InternetConnectionUtil
						.hasInternetConnection(getApplicationContext())) {
					try {
						filesFromDir = manager.readDirectory(currentDirectory);
					} catch (final IllegalArgumentException e) {
						e.printStackTrace();
					} catch (final SftpException e) {
						e.printStackTrace();
					}

				} else {
				}
				return null;
			}
		}.execute();
	}

	private void updateList(final List<FTPFile> files) {
		listItems.clear();
		for (final FTPFile file : files) {
			listItems.add(file);
		}
		Collections.sort(listItems);
		if (!currentDirectory.equals(manager.getRootDirectory())) {
			listItems.add(0, manager.getUpperDirectory(currentDirectory));
		}
		
		Collections.sort(favorites);
		Collections.reverse(favorites);
		for (FTPFile fav : favorites) {
			listItems.add(0, fav);
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
				busyDownloadung = false;
				runFileFromActivity(downloaded);
				dismissDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
				removeDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
			}

			@Override
			protected Void doInBackground(final Void... arg0) {
				if (InternetConnectionUtil
						.hasInternetConnection(getApplicationContext())) {
					try {
						busyDownloadung = true;
						downloaded = manager.downloadFile(file);
					} catch (final IllegalArgumentException e) {
						e.printStackTrace();
					} catch (final SftpException e) {
						e.printStackTrace();
					}

				} else {
				}
				return null;
			}
		}.execute();
	}

	private void runFileFromActivity(final FTPFile downloaded) {
		manager.runFile(downloaded, this);
	}

	private void handleFile(final FTPFile clickedFile) {
		if (InternetConnectionUtil
				.hasInternetConnection(getApplicationContext())
				&& busyDownloadung == false) {
			if (clickedFile.isDirectory()) {
				currentDirectory = clickedFile;
				TextView pathView = (TextView) findViewById(R.id.path);
				pathView.setText(currentDirectory.getAbsolutePath()
						+ currentDirectory.getFileName());
				readCurrentDirectory();
			} else {
				downloadAndRunFile(clickedFile);
			}
		} else if (busyDownloadung) {
			Toast.makeText(getApplicationContext(), ALREADY_BUSY_MSG,
					Toast.LENGTH_SHORT).show();
		} else {
			dismissDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
			removeDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
			Toast.makeText(getApplicationContext(), NO_INTERNET_CONNECTION_MSG,
					Toast.LENGTH_SHORT).show();
		}

	}

	public class PubAdapter extends ArrayAdapter<FTPFile> {
		private final int layoutResourceId;
		private final Context context;
		private final List<FTPFile> data;

		public PubAdapter(final Context context, final int layoutResourceId,
				final List<FTPFile> data) {
			super(context, layoutResourceId, data);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.data = data;
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			View row = convertView;
			FileHolder holder = null;

			if (row == null) {
				final LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);

				holder = new FileHolder();
				holder.imgIcon = (ImageView) row
						.findViewById(R.id.imageViewPub);
				holder.txtTitle = (TextView) row.findViewById(R.id.textViewPub);
				holder.favImg = (ImageView) row.findViewById(R.id.favImgPub);
				row.setTag(holder);
			} else {
				holder = (FileHolder) row.getTag();
			}

			final FTPFile file = data.get(position);
			holder.txtTitle.setText(file.toString());
			holder.favImg.setImageResource(0);

			if (file.isDirectory()) {
				holder.imgIcon.setImageResource(R.drawable.directory_icon);
				if (!(file instanceof UpperDirFTPFile)) {
					if (favorites.contains(file)) {
						holder.favImg.setImageResource(R.drawable.star);
					} else {
						holder.favImg.setImageResource(R.drawable.star_empty);
					}
				}
			} else {
				holder.imgIcon.setImageResource(R.drawable.file_icon);
			}
			return row;
		}
	}

	static class FileHolder {
		ImageView imgIcon;
		TextView txtTitle;
		ImageView favImg;
	}

}