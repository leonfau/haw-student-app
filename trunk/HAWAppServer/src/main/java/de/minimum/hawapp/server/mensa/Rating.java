package de.minimum.hawapp.server.mensa;

public interface Rating {

    /**
     * Eine positive Bewertung hinzuf�gen
     */
    public void ratePoitiv();

    /**
     * Eine negative Bewertung hinzuf�gen
     */
    public void rateNegativ();

    /**
     * Gibt neue aktuelle Bewertung in Prozent zur�ck
     * 
     * @return
     */
    public int getPosRatingInPercent();

}
