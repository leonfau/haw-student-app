package de.minimum.hawapp.app.stisys;

import java.util.Date;

public class ResultImpl implements Result {
    private final String name;
    private final String prof;
    private final Date date;
    private final int points;
    private final boolean success; // zusaetzlich fuer die Uebungen / Praktika

    /**
     * Eintrag fuer eine Klausur
     * 
     * @param name
     * @param prof
     * @param date
     * @param points
     */
    public ResultImpl(final String name, final String prof, final Date date, final int points) {
        this.name = name;
        this.prof = prof;
        this.date = date;
        this.points = points;
        success = true;
    }

    /**
     * Eintrag fuer ein Praktikum / eine Uebung
     * 
     * @param name
     * @param prof
     * @param date
     * @param success
     */
    public ResultImpl(final String name, final String prof, final Date date, final boolean success) {
        this.name = name;
        this.prof = prof;
        this.date = date;
        this.success = success;
        points = 0;
    }

    /**
     * Gibt den Namen der Pruefung / der Uebung / des Praktikums wieder
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt den Professor der Pruefung / der Uebung / des Praktikums wieder
     * 
     * @return the prof
     */
    public String getProf() {
        return prof;
    }

    /**
     * Gibt das Datum der Pruefung / der Uebung / des Praktikums wieder
     * 
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gibt die Punkte einer Pruefung wieder
     * 
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gibt den Erfolg einer Uebung / eines Praktikums
     * 
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    };

}
