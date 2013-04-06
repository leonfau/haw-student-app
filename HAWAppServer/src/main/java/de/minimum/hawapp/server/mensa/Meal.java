package de.minimum.hawapp.server.mensa;

public interface Meal {

    /**
     * Gibt die Beschreibung des Essens zurï¿½ck
     */
    public String getDescription();

    /**
     * Gibt den Preis fï¿½r Studierende zurï¿½ck
     */
    public double getStudentPrice();

    /**
     * Gibt den Preis fï¿½r nicht Studenten zurï¿½ck
     */
    public double getOthersPrice();
    
   /**
    * Gibt das Rating Objekt des Gerichts zurück 
    */
    public Rating getRating();

}
