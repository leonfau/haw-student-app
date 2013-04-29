package de.minimum.hawapp.app.mensa.beans;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as=RatingBeanImpl.class)
public interface Rating {
    /**
     * Gibt neue aktuelle Bewertung in Prozent zur�ck
     * 
     * @return
     */
    public int getPosRatingInPercent();

}
