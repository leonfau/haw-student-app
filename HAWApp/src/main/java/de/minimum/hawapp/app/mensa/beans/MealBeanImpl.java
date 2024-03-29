package de.minimum.hawapp.app.mensa.beans;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonProperty;

public class MealBeanImpl implements Meal {

	@JsonProperty("id")
	private UUID id;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("studentPrice")
	private double studentPrice;
	
	@JsonProperty("othersPrice")
	private double othersPrice;
	
	@JsonProperty("rating")
	private Rating rating;

	public MealBeanImpl(){};
	
	@Override
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Meal)) return false;
		Meal compareMeal = (Meal) o;
		if (!this.description.equals(compareMeal.getDescription())) return false;
		if (!this.rating.equals(compareMeal.getRating())) return false;
		return true;
	}

}
