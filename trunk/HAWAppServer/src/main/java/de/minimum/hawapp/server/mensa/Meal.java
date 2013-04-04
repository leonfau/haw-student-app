package de.minimum.hawapp.server.mensa;

public interface Meal {

    /*
     * Gibt die Beschreibung des Essens zur�ck
     */
    public String getDescription();

    /*
     * Gibt den Preis f�r Studierende zur�ck
     */
    public double getStudentPrice();

    /*
     * Gibt den Preis f�r nicht Studenten zur�ck
     */
    public double getOthersPrice();

}
