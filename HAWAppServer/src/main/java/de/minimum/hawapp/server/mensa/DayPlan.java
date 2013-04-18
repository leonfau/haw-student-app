package de.minimum.hawapp.server.mensa;

import java.util.List;

import de.minimum.hawapp.server.mensa.DayPlanImpl.Day;

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
