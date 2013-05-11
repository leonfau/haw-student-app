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
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof DayPlan)) return false;
		DayPlan comparePlan = (DayPlan) o;
		if (!this.day.equals(comparePlan.getDay())) return false;
		for (Meal m :comparePlan.getMeals()) {
			if (!meals.contains(m)) return false;
		}
		return true;
	}

}
