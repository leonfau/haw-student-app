package de.minimum.hawapp.server.mensa;

import java.util.List;

public class DayPlanImpl implements DayPlan {
//	public static enum Day {MONTAG, DIENSTAG, MITTWOCH, DONNERSTAG, FREITAG}
	public static enum Day {Montag, Dienstag, Mittwoch, Donnerstag, Freitag}
	public Day day;
	public List<Meal> mealList;
	
	public DayPlanImpl(Day day, List<Meal> meals){
		this.day = day;
		this.mealList = meals;
	}

	@Override
	public Day getDay() {
		return day;
	}

	@Override
	public List<Meal> getMeals() {
		return mealList;
	}
	
	@Override
	public String toString(){
		return day.toString() + ": " + mealList.toString();
	}

}
