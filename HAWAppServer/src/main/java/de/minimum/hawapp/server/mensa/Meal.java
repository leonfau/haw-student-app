package de.minimum.hawapp.server.mensa;


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

}
