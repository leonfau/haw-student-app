package de.minimum.hawapp.server.facade.wrapper.mensa;

import de.minimum.hawapp.server.mensa.Rating;

public class RatingWrapper implements Rating {

    private int rating;

    public RatingWrapper(int rating) {
        this.rating = rating;
    }

    @Override
    public void ratePoitiv() {
        throw new UnsupportedOperationException("It's only a wrapper class");
    }

    @Override
    public void rateNegativ() {
        throw new UnsupportedOperationException("It's only a wrapper class");
    }

    @Override
    public int getPosRatingInPercent() {
        return this.rating;
    }

}
