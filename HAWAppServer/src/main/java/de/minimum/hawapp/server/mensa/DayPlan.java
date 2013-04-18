package de.minimum.hawapp.server.mensa;

import java.util.List;
public interface DayPlan {

	/**
	 * 
	 * @return Tag des Essensplan
	 */
	public Day getDay();
	
	/**
	 * 
	 * @return Liste der Speisen für einen Tag
	 */
	public List<Meal> getMeals();

}
