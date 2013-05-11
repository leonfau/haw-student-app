package de.minimum.hawapp.app.mensa.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class RatingBeanImpl implements Rating {

	@JsonProperty("posRatingInPercent")
	private int posRatingInPercent;

	public RatingBeanImpl(){};
	
	@Override
	public int getPosRatingInPercent() {
		return posRatingInPercent;
	}

	public void setposRatingInPercent(int posRatingInPercent) {
		this.posRatingInPercent = posRatingInPercent;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Meal)) return false;
		Rating compareRating = (Rating) o;
		if (this.posRatingInPercent != compareRating.getPosRatingInPercent()) return false;
		return true;
	}
}
