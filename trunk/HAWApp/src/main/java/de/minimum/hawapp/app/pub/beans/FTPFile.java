package de.minimum.hawapp.app.pub.beans;

/**
 * repräsentiert eine Datei die über das Pub heruntergeladen werden kann oder wurde
 * @author Erwin
 *
 */
public interface FTPFile {

	/**
	 * gibt zurück ob es sich um einen Ordner oder eine Datei handelt
	 * @return
	 */
	public boolean isDirectory();
	
	/**
	 * gibt den Dateinamen im Format "filename.ext" oder "dirname" zurück
	 * @return
	 */
	public String getFileName();
	
	/**
	 * Gibt den Absoluten Pfad der Datei im Pub zurück im Format "/x/y/z/" Pfad muss immer auf einem Slash "/" enden
	 * @return
	 */
	public String getAbsolutePath();
}
