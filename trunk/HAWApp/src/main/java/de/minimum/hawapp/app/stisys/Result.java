package de.minimum.hawapp.app.stisys;

import java.util.Date;

public interface Result {

    /**
     * Gibt den Namen der Pruefung / der Uebung / des Praktikums wieder
     * 
     * @return the name
     */
    public String getName();

    /**
     * Gibt den Professor der Pruefung / der Uebung / des Praktikums wieder
     * 
     * @return the prof
     */
    public String getProf();

    /**
     * Gibt das Datum der Pruefung / der Uebung / des Praktikums wieder
     * 
     * @return the date
     */
    public Date getDate();

    /**
     * Gibt die Punkte einer Pruefung wieder
     * 
     * @return the points
     */
    public int getPoints();

    /**
     * Gibt den Erfolg einer Uebung / eines Praktikums
     * 
     * @return the success
     */
    public boolean isSuccess();

}
