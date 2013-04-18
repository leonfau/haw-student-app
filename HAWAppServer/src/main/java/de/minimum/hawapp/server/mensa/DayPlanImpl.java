package de.minimum.hawapp.server.mensa;

import java.util.List;

public class DayPlanImpl implements DayPlan {
//	public static enum Day {MONTAG, DIENSTAG, MITTWOCH, DONNERSTAG, FREITAG}
	
	
	private Day day;
	private List<Meal> meals;
	
	public DayPlanImpl(Day day, List<Meal> meals){
		this.day = day;
		this.meals = meals;
	}

	@Override
	public Day getDay() {
		return day;
	}

	@Override
	public List<Meal> getMeals() {
		return meals;
	}
	
	@Override
	public String toString(){
		return day.toString() + ": " + this.meals.toString();
	}

}
