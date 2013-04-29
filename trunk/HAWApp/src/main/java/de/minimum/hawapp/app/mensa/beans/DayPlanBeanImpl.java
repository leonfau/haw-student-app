package de.minimum.hawapp.app.mensa.beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class DayPlanBeanImpl implements DayPlan {

	@JsonProperty("day")
	String day;
	
	@JsonProperty("meals")
	List<Meal> meals;
	
	public DayPlanBeanImpl(){};
	
	@Override
	public String getDay() {
		return day;
	}

	@Override
	public List<Meal> getMeals() {
		return meals;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

}
