package de.minimum.hawapp.app.mensa.beans;

import java.util.List;
public interface DayPlan {

	/**
	 * 
	 * @return Tag des Essensplan
	 */
	public String getDay();
	
	/**
	 * 
	 * @return Liste der Speisen für einen Tag
	 */
	public List<Meal> getMeals();

}
