package de.minimum.hawapp.server.mensa;

import java.util.UUID;


public interface Meal {

    /**
     * Gibt die Beschreibung des Essens zurück
     */
    public String getDescription();

    /**
     * Gibt den Preis für Studierende zurück
     */
    public double getStudentPrice();

    /**
     * Gibt den Preis für nicht Studenten zurück
     */
    public double getOthersPrice();

    /**
     * Gibt das Rating Objekt des Gerichts zurück
     */
    public Rating getRating();
    
    /**
     * Gibt eindeutige ID zur Identifikation des Gerichts zurück
     */
    public UUID getID();

}
