package de.minimum.hawapp.server.mensa;

public class RatingImpl implements Rating {
	int ratingCount;
	int ratingValue;
	
    private RatingImpl() {
        this.ratingCount = 0;
        this.ratingValue = 0;
    }

    public static RatingImpl Rating() {
        return new RatingImpl();
    }

    @Override
    public int getPosRatingInPercent() {
    	if (ratingCount <= 0) return 0;
        return (ratingValue/ratingCount)*20;
    }

    @Override
    public void rate(int stars) {
        ratingValue += stars;
        ratingCount++;
    }

}
