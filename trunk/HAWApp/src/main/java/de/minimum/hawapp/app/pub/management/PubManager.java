package de.minimum.hawapp.app.pub.management;

import java.util.List;


import com.jcraft.jsch.SftpException;

import de.minimum.hawapp.app.pub.beans.FTPFile;

import android.app.Activity;

/**
 * Management Klasse welche dei Logik für das Pubmodul beinhaltet
 * @author Erwin
 *
 */
public interface PubManager {

	/**
	 * Gibt eine Repräsentation des obersten Ordners im Pub als FTPFile zurück
	 * @return
	 */
	public FTPFile getRootDirectory();
	
	/**
	 * Lädt die übergebene Datei aus dem Pub herunter und speichert sie unter dem gleichen Namen im Downloadordner
	 * @param remoteFile
	 * @return
	 * @throws IllegalArgumentException wenn Ordner und keine Datei übergeben wird
	 * @throws SftpException wenn Download fehlschlägt
	 */
	public FTPFile downloadFile(FTPFile remoteFile) throws IllegalArgumentException, SftpException;
	
	/**
	 * Gibt den übergeordneten Ordner zurück
	 * @param dir
	 * @return
	 * @throws IllegalArgumentException Wenn Datei statt Ordner oder root übergeben wird
	 */
	public FTPFile getUpperDirectory(FTPFile dir) throws IllegalArgumentException;
	
	/**
	 * Gibt eine Liste mit den Dateien in einem Ordner zurück.
	 * @param dir
	 * @return
	 * @throws IllegalArgumentException wenn Datei statt Ordner übergeben wird
	 * @throws SftpException wenn lesen fehlschlägt
	 */
	public List<FTPFile> readDirectory(FTPFile dir) throws IllegalArgumentException, SftpException;
	
	/**
	 * Führt die übergebene Datei in einem Intent mit der passenden App aus
	 * @param localFile
	 * @param fromActivity
	 * @throws IllegalArgumentException wenn Ordner und keine Datei übergeben wird
	 */
	public void runFile(FTPFile localFile, Activity fromActivity) throws IllegalArgumentException;
	
	/**
	 * Speichert den Übergebenen Ordner als Favorit persistent
	 * @param favoriteDir
	 * @throws IllegalArgumentException wenn Datei statt Ordner übergeben wird
	 */
	public void makeFavorite(FTPFile favoriteDir) throws IllegalArgumentException;
	
	/**
	 * Entfernt die persistente Speicherung als Favorit des übergebenen Ordners
	 * @param favoriteDir
	 * @throws IllegalArgumentException wenn Datei statt Ordner übergeben wird
	 */
	public void removeFavorite(FTPFile favoriteDir) throws IllegalArgumentException;
	
	/**
	 * Lädt die gespeicherten Favorisierten Ordner und gibt sie in einer Liste zurück
	 * @return
	 */
	public List<FTPFile> loadFavorites();
	
}