package de.minimum.hawapp.app.pub.beans;

import java.util.Locale;

public class FTPFileBeansImpl implements FTPFile {

	protected final boolean directory;
	protected final String fileName;
	protected final String absolutePath;
	
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
	
	@Override
	public String toString() {
		return fileName;
	}

	@Override
	public int compareTo(FTPFile arg) {
		return fileName.toUpperCase(Locale.GERMANY).compareTo(arg.getFileName().toUpperCase());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof FTPFile)) return false;
		FTPFile compare = (FTPFile) o;
		return fileName.equals(compare.getFileName()) &&
				absolutePath.equals(compare.getAbsolutePath()) &&
				directory == compare.isDirectory();
	}
	
}
