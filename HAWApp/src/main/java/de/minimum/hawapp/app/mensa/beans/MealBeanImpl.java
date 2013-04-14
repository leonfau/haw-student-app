package de.minimum.hawapp.app.mensa.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class MealBeanImpl implements Meal {

	@JsonProperty("description")
	private String description;
	
	@JsonProperty("studentPrice")
	private double studentPrice;
	
	@JsonProperty("othersPrice")
	private double othersPrice;
	
	@JsonProperty("rating")
	private Rating rating;

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public double getStudentPrice() {
		return studentPrice;
	}

	public void setStudentPrice(double studentPrice) {
		this.studentPrice = studentPrice;
	}
	
	@Override
	public double getOthersPrice() {
		return othersPrice;
	}

	public void setOthersPrice(double othersPrice) {
		this.othersPrice = othersPrice;
	}

	@Override
	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

}
