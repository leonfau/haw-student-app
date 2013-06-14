package de.minimum.hawapp.app.pub.management;

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

	private final String ERROR_ARGUMENT_MUST_BE_FILE = "Argument must be File not Directory";
	private final String ERROR_ARGUMENT_MUST_BE_DIR = "Argument must be Directory not File";

	private ChannelSftp sftpChannel;
	private Session session;

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
				|| !sftpChannel.isConnected())
			connect();
		if (remoteFile.isDirectory())
			throw new IllegalArgumentException(ERROR_ARGUMENT_MUST_BE_FILE);

		String localAbsolutePath = Environment.getExternalStorageDirectory()
				+ SEPERATOR + Environment.DIRECTORY_DOWNLOADS;
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
	public void makeFavorite(FTPFile favoriteDir)
			throws IllegalArgumentException {
		// TODO
	}

	@Override
	public void removeFavorite(FTPFile favoriteDir)
			throws IllegalArgumentException {
		// TODO
	}

	@Override
	public List<FTPFile> loadFavorites() {
		// TODO Nice to Have
		return new ArrayList<FTPFile>();
	}

}
