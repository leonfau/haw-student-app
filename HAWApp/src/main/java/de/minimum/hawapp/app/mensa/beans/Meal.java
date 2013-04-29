package de.minimum.hawapp.app.mensa.beans;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as=MealBeanImpl.class)
public interface Meal {

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
