package de.minimum.hawapp.app.pub.beans;

public class UpperDirFTPFile extends FTPFileBeansImpl {

	public UpperDirFTPFile(String fileName, String absolutePath,
			boolean directory) {
		super(fileName, absolutePath, directory);
	}

	@Override
	public String toString() {
		return "..";
	}
	
	@Override
	public int compareTo(FTPFile arg) {
		return -1;
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
