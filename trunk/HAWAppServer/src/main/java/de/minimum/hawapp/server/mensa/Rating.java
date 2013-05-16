package de.minimum.hawapp.server.mensa;

public interface Rating {

    /**
     * Eine positive Bewertung hinzufügen
     */
    public void rate(int stars);

    /**
     * Gibt neue aktuelle Bewertung in Prozent zurück
     * 
     * @return
     */
    public int getPosRatingInPercent();

}
