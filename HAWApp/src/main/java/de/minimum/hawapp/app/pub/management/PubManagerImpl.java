package de.minimum.hawapp.app.pub.management;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import de.minimum.hawapp.app.login.Login;
import de.minimum.hawapp.app.pub.beans.FTPFile;
import de.minimum.hawapp.app.pub.beans.FTPFileBeansImpl;
import de.minimum.hawapp.app.pub.beans.UpperDirFTPFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

public class PubManagerImpl implements PubManager {

	private final String server = "sftp.informatik.haw-hamburg.de";
	private String user;
	private String pw;

	private final String FTP_TYPE = "sftp";
	private final String SEPERATOR = "/";
	private final String URI_PREFIX = "file:/";
	private final String FAV_FILENAME = "fav";
	private final char PERSIST_SEPERATOR = '#';
	
	private final String ERROR_ARGUMENT_MUST_BE_FILE = "Argument must be File not Directory";
	private final String ERROR_ARGUMENT_MUST_BE_DIR = "Argument must be Directory not File";

	private ChannelSftp sftpChannel;
	private Session session;

	private List<FTPFile> favorites = new ArrayList<FTPFile>();
	
	private void connect() {
		user = Login.decrypt(Login.getEncryptedLogin());
		pw = Login.decrypt(Login.getEncryptedPassword());
		JSch jsch = new JSch();
		session = null;
		try {
			session = jsch.getSession(user, server, 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pw);
			session.connect();
			Channel channel = session.openChannel(FTP_TYPE);
			channel.connect();
			sftpChannel = (ChannelSftp) channel;
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	@Override
	public FTPFile getRootDirectory() {
		return new FTPFileBeansImpl("pub", "/home/", true);
	}

	@Override
	public FTPFile downloadFile(FTPFile remoteFile)
			throws IllegalArgumentException, SftpException {
		if (sftpChannel == null || sftpChannel.isClosed()
				|| !sftpChannel.isConnected()) {
			connect();
		}
			
		if (remoteFile.isDirectory())
			throw new IllegalArgumentException(ERROR_ARGUMENT_MUST_BE_FILE);
		String localAbsolutePath = Environment.getExternalStorageDirectory()
				+ SEPERATOR + Environment.DIRECTORY_DOWNLOADS + SEPERATOR;
		sftpChannel.get(
				remoteFile.getAbsolutePath() + remoteFile.getFileName(),
				localAbsolutePath + remoteFile.getFileName());
		sftpChannel.exit();
		session.disconnect();
		return new FTPFileBeansImpl(remoteFile.getFileName(),
				localAbsolutePath, false);
	}

	@Override
	public List<FTPFile> readDirectory(FTPFile dir)
			throws IllegalArgumentException, SftpException {
		if (sftpChannel == null || sftpChannel.isClosed()
				|| !sftpChannel.isConnected())
			connect();
		if (!dir.isDirectory())
			throw new IllegalArgumentException(ERROR_ARGUMENT_MUST_BE_DIR);
		sftpChannel.cd(dir.getAbsolutePath() + dir.getFileName());

		@SuppressWarnings("unchecked")
		Vector<LsEntry> list = sftpChannel.ls("*");
		List<FTPFile> resultFiles = new ArrayList<FTPFile>();

		for (LsEntry ls : list) {
			if (!ls.getFilename().equals("Gesperrt")) {
				resultFiles.add(new FTPFileBeansImpl(ls.getFilename(), dir
						.getAbsolutePath() + dir.getFileName() + SEPERATOR, ls
						.getAttrs().isDir() || ls.getAttrs().isLink()));
			}
		}

		sftpChannel.exit();
		session.disconnect();
		return resultFiles;
	}

	@Override
	public void runFile(FTPFile localFile, Activity fromActivity)
			throws IllegalArgumentException {
		if (localFile.isDirectory())
			throw new IllegalArgumentException(ERROR_ARGUMENT_MUST_BE_FILE);

		String file = URI_PREFIX + localFile.getAbsolutePath()
				+ localFile.getFileName();
		Intent newApp = new Intent(android.content.Intent.ACTION_VIEW);
		MimeTypeMap mime = MimeTypeMap.getSingleton();
		String mimetype = mime.getExtensionFromMimeType("txt");
		if (file.lastIndexOf(".") != -1) {
			String ext = file.substring(file.lastIndexOf(".") + 1);
			mimetype = mime.getMimeTypeFromExtension(ext);
		}
		newApp.setDataAndType(Uri.parse(file), mimetype);
		fromActivity.startActivity(newApp);
	}


	@Override
	public FTPFile getUpperDirectory(FTPFile dir)
			throws IllegalArgumentException {
		if (!dir.isDirectory() || dir.equals(getRootDirectory()))
			throw new IllegalArgumentException(ERROR_ARGUMENT_MUST_BE_DIR);
		String oldPath = dir.getAbsolutePath();
		oldPath = oldPath.substring(0, oldPath.length() - 1); // letzen Slash
																// abschneiden
		String path = oldPath.substring(0, oldPath.lastIndexOf(SEPERATOR) + 1); // bis
																				// zum
																				// vorletzen
																				// Slash
		String filename = oldPath.substring(oldPath.lastIndexOf(SEPERATOR) + 1);
		return new UpperDirFTPFile(filename, path, true);
	}

	@Override
	public void makeFavorite(FTPFile favoriteDir, Context context)
			throws IllegalArgumentException {
		
		favorites.add(favoriteDir);
		try {
			persist(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeFavorite(FTPFile favoriteDir, Context context)
			throws IllegalArgumentException {
		favorites.remove(favoriteDir);
		try {
			persist(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<FTPFile> loadFavorites(Context context) {
		try {
			favorites = load(context);
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return favorites;
	}
	
	private void persist(Context context) throws IOException {
        FileOutputStream fOut = null;
        PrintStream ps = null;
        try {
            fOut = context.openFileOutput(FAV_FILENAME, Context.MODE_PRIVATE);
            ps = new PrintStream(fOut);
            for (FTPFile fav : favorites) {
            	String toWrite = fav.getAbsolutePath() + "#" + fav.getFileName();
            	ps.println(toWrite);
            }
            ps.flush();
            ps.close();
        }
        finally {
            if (fOut != null)
                fOut.close();
        }
    }

    private List<FTPFile> load(Context context) throws StreamCorruptedException, IOException,
                    ClassNotFoundException {
    	FileInputStream fIn = null;
    	InputStreamReader isr = null;
        BufferedReader br = null;
        
        List<FTPFile> result = new ArrayList<FTPFile>();
        try {
            fIn = context.openFileInput(FAV_FILENAME);
            isr = new InputStreamReader(fIn);
            br = new BufferedReader(isr);
            
            result = new ArrayList<FTPFile>();
            
            String line = br.readLine();
            while (line != null) {
            	result.add(new FTPFileBeansImpl(line.substring(line.lastIndexOf(PERSIST_SEPERATOR)+1),
            			line.substring(0,line.lastIndexOf(PERSIST_SEPERATOR)), true));
            	line = br.readLine();
            }
            br.close();
        }
        finally {
            if (fIn != null)
                fIn.close();
        }
        return result;
    }
}
    
