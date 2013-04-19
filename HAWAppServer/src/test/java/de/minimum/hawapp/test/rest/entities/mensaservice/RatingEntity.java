package de.minimum.hawapp.test.rest.entities.mensaservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RatingEntity {
    private int posRatingInPercent;

    public int getPosRatingInPercent() {
        return this.posRatingInPercent;
    }

    public void setPosRatingInPercent(int posRatingInPercent) {
        this.posRatingInPercent = posRatingInPercent;
    }

}
