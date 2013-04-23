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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meal> getMeals() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

}
