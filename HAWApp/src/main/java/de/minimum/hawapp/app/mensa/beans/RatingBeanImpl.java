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
}
