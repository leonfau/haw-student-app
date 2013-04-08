package de.minimum.hawapp.server.mensa;

import de.minimum.hawapp.server.facade.serviceprovider.JsonObject;

public interface Meal extends JsonObject {

    /**
     * Gibt die Beschreibung des Essens zur�ck
     */
    public String getDescription();

    /**
     * Gibt den Preis f�r Studierende zur�ck
     */
    public double getStudentPrice();

    /**
     * Gibt den Preis f�r nicht Studenten zur�ck
     */
    public double getOthersPrice();

    /**
     * Gibt das Rating Objekt des Gerichts zur�ck
     */
    public Rating getRating();

}
