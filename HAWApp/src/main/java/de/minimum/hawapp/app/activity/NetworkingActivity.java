package de.minimum.hawapp.app.activity;

import android.app.Activity;

public abstract class NetworkingActivity extends Activity {
	
	/**
	 * Sendet die vorher als Attribut festgelegten Daten mithilfe des gegebenen Managers in einem AsyncTask an das Backend
	 */
	abstract void put();
	
	/**
	 * Holt sich Daten vom Backend über Rest mit Hilfe eines AsyncTask und speichert Sie als Attribut der Klasse.
	 * Der AsyncTask muss in seiner onPostExecute() Methode display aufrufen.
	 * get() sollte bei onResume() in der Activity aufgerufen werden.
	 */
	abstract void get();
	
	/**
	 * Verwendet das Attribut der Klasse in dem die Daten stehen und aktualisiert damit die Oberfläche.
	 * Wird automatisch von get() aufgerufen.
	 */
	abstract void display();

}
