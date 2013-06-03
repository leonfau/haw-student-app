package de.minimum.hawapp.app.pub.beans;

public class FTPFileBeansImpl implements FTPFile {

	private final boolean directory;
	private final String fileName;
	private final String absolutePath;
	
	public FTPFileBeansImpl(String fileName, String absolutePath, boolean directory) {
		this.fileName = fileName;
		this.absolutePath = absolutePath;
		this.directory = directory;
	}

	public boolean isDirectory() {
		return directory;
	}

	public String getFileName() {
		return fileName;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}
}
