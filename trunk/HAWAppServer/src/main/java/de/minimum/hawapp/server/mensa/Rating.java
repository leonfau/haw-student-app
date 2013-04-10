package de.minimum.hawapp.server.mensa;

public interface Rating {

    /**
     * Eine positive Bewertung hinzufügen
     */
    public void ratePoitiv();

    /**
     * Eine negative Bewertung hinzufügen
     */
    public void rateNegativ();

    /**
     * Gibt neue aktuelle Bewertung in Prozent zurück
     * 
     * @return
     */
    public int getPosRatingInPercent();

}
